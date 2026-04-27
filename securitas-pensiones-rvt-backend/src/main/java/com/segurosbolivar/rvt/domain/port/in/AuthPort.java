package com.segurosbolivar.rvt.domain.port.in;

import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.LoginResponse;

/**
 * Puerto de entrada para autenticación de usuarios.
 */
public interface AuthPort {

    /**
     * Autentica un usuario con las credenciales proporcionadas.
     *
     * @param request datos de login (username, password)
     * @return respuesta con token JWT y datos de sesión
     */
    LoginResponse login(LoginRequest request);
}
