package com.segurosbolivar.rvt.domain.exception;

/**
 * Excepción base abstracta para todas las excepciones de dominio del sistema RVT.
 * Cada excepción concreta define un código único y un mensaje orientado al usuario.
 */
public abstract class RvtDomainException extends RuntimeException {

    private final String code;
    private final String userMessage;

    /**
     * Crea una nueva excepción de dominio RVT.
     *
     * @param code        código único de error para mapeo HTTP
     * @param userMessage mensaje genérico orientado al usuario (sin detalles internos)
     */
    protected RvtDomainException(String code, String userMessage) {
        super(userMessage);
        this.code = code;
        this.userMessage = userMessage;
    }

    public String getCode() {
        return code;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
