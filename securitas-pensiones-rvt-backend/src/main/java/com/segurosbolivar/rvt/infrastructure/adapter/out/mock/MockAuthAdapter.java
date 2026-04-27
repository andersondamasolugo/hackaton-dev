package com.segurosbolivar.rvt.infrastructure.adapter.out.mock;

import com.segurosbolivar.rvt.domain.port.out.AuthExternalPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Adaptador mock para autenticación externa.
 * Retorna un token fijo para credenciales ADMIN/ADMIN cuando
 * el endpoint real de Person1 no está disponible.
 */
@Component
@Profile("mock")
public class MockAuthAdapter implements AuthExternalPort {

    private static final String MOCK_TOKEN = "mock-jwt-token-rvt-hackathon-2026";

    /**
     * Simula autenticación contra servicio externo.
     *
     * @param username nombre de usuario
     * @param password contraseña del usuario
     * @return token fijo de mock
     */
    @Override
    public String authenticate(String username, String password) {
        return MOCK_TOKEN;
    }
}
