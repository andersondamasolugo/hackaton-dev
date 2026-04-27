package com.segurosbolivar.rvt.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilidad para generación y validación de tokens JWT.
 * Usa un secret fijo para el demo del hackatón (documentado como deuda técnica).
 */
@Component
public class JwtUtil {

    private static final String SECRET = "rvt-hackathon-demo-secret-key-2026-seguros-bolivar-min-256-bits!!";
    private static final long EXPIRATION_MILLIS = 60 * 60 * 1000L; // 60 minutos

    private final SecretKey key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un token JWT con el username como subject.
     *
     * @param username nombre de usuario autenticado
     * @return token JWT firmado
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_MILLIS);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    /**
     * Valida un token JWT verificando firma y expiración.
     *
     * @param token token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extrae el username (subject) de un token JWT válido.
     *
     * @param token token JWT del cual extraer el username
     * @return username contenido en el token
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Retorna el tiempo de expiración en segundos.
     *
     * @return segundos de expiración del token
     */
    public long getExpirationSeconds() {
        return EXPIRATION_MILLIS / 1000;
    }
}
