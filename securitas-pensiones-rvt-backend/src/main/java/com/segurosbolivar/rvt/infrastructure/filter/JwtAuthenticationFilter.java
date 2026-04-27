package com.segurosbolivar.rvt.infrastructure.filter;

import com.segurosbolivar.rvt.infrastructure.config.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de autenticación JWT que intercepta cada request HTTP.
 * Extrae el token del header Authorization, lo valida y establece
 * el SecurityContext con el usuario autenticado.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/auth/login",
            "/",
            "/hola",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api-docs/**",
            "/v3/api-docs/**",
            "/h2-console/**"
    );

    private final JwtUtil jwtUtil;
    private final AntPathMatcher pathMatcher;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.pathMatcher = new AntPathMatcher();
    }

    /**
     * Determina si el filtro debe omitirse para la request actual.
     * Se omite para rutas públicas como login, Swagger y H2 console.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return EXCLUDED_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    /**
     * Extrae y valida el token JWT del header Authorization.
     * Si es válido, establece la autenticación en el SecurityContext.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(BEARER_PREFIX.length());

            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, List.of());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
