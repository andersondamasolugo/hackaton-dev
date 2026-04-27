package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import com.segurosbolivar.rvt.domain.port.in.AuthPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para autenticación de usuarios.
 * Expone el endpoint POST /auth/login.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación y gestión de sesión")
public class AuthController {

    private final AuthPort authPort;

    public AuthController(AuthPort authPort) {
        this.authPort = authPort;
    }

    /**
     * Autentica un usuario y retorna un token JWT.
     *
     * @param request credenciales de login (username, password)
     * @return token JWT con tiempo de expiración y username
     */
    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Autentica con credenciales y retorna token JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authPort.login(request);
        return ResponseEntity.ok(response);
    }
}
