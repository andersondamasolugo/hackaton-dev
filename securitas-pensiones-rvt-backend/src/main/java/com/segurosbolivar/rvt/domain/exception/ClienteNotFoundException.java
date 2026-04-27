package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando no se encuentra un cliente con el identificador proporcionado.
 */
public class ClienteNotFoundException extends RvtDomainException {

    public ClienteNotFoundException(Long clienteId) {
        super("CLIENTE_NOT_FOUND",
                "No se encontró el cliente con ID: " + clienteId);
    }
}
