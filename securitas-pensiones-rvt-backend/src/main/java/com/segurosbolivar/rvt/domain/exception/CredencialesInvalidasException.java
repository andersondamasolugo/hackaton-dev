package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando las credenciales de autenticación son inválidas.
 * El mensaje es genérico para no revelar detalles internos.
 */
public class CredencialesInvalidasException extends RvtDomainException {

    public CredencialesInvalidasException() {
        super("CREDENCIALES_INVALIDAS", "Credenciales inválidas");
    }
}
