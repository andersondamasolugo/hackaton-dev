package com.segurosbolivar.rvt.infrastructure.config;

import com.segurosbolivar.rvt.infrastructure.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de Spring Security para el demo RVT.
 * Deshabilita CSRF, permite acceso público a landing, Swagger, auth y hola mundo.
 * Los demás endpoints requieren autenticación vía JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Landing page, Swagger UI y API docs
                .requestMatchers("/", "/hola").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/v3/api-docs/**").permitAll()
                // H2 console (solo en profile mock)
                .requestMatchers("/h2-console/**").permitAll()
                // Auth endpoint
                .requestMatchers("/auth/login").permitAll()
                // Todo lo demás requiere autenticación JWT
                .anyRequest().authenticated()
            )
            // Agregar filtro JWT antes del filtro de autenticación estándar
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // Permitir frames para H2 console
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
