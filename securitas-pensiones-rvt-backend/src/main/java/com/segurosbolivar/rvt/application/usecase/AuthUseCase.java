package com.segurosbolivar.rvt.application.usecase;

import com.segurosbolivar.rvt.domain.exception.CredencialesInvalidasException;
import com.segurosbolivar.rvt.domain.port.in.AuthPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.LoginResponse;
import com.segurosbolivar.rvt.infrastructure.config.JwtUtil;
import org.springframework.stereotype.Service;

/**
 * Caso de uso de autenticación.
 * Valida credenciales fijas ADMIN/ADMIN para el demo del hackatón
 * y genera un token JWT mediante JwtUtil.
 */
@Service
public class AuthUseCase implements AuthPort {

    private static final String VALID_USERNAME = "ADMIN";
    private static final String VALID_PASSWORD = "ADMIN";

    private final JwtUtil jwtUtil;

    public AuthUseCase(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Autentica un usuario con las credenciales proporcionadas.
     * Solo acepta ADMIN/ADMIN para el demo.
     *
     * @param request datos de login (username, password)
     * @return respuesta con token JWT y datos de sesión
     * @throws CredencialesInvalidasException si las credenciales no son ADMIN/ADMIN
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        if (!VALID_USERNAME.equals(request.username()) || !VALID_PASSWORD.equals(request.password())) {
            throw new CredencialesInvalidasException();
        }

        String token = jwtUtil.generateToken(request.username());
        return new LoginResponse(token, jwtUtil.getExpirationSeconds(), request.username());
    }
}
