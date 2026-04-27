package com.segurosbolivar.rvt.domain.port.out;

/**
 * Puerto de salida para autenticación contra servicio externo (Person1).
 */
public interface AuthExternalPort {

    /**
     * Autentica un usuario contra el servicio externo de autenticación.
     *
     * @param username nombre de usuario
     * @param password contraseña del usuario
     * @return token JWT generado por el servicio externo
     */
    String authenticate(String username, String password);
}
