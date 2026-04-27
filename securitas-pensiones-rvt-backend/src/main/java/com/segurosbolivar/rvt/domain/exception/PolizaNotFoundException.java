package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando no se encuentra una póliza con el número proporcionado.
 */
public class PolizaNotFoundException extends RvtDomainException {

    public PolizaNotFoundException(String numeroPoliza) {
        super("POLIZA_NOT_FOUND",
                "No se encontró la póliza con número: " + numeroPoliza);
    }
}
