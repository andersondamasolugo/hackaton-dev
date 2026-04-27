package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando se intenta activar una póliza que no está en estado PENDIENTE.
 */
public class PolizaNoActivableException extends RvtDomainException {

    public PolizaNoActivableException(String numeroPoliza) {
        super("POLIZA_NO_ACTIVABLE",
                "La póliza " + numeroPoliza + " no puede ser activada porque no está en estado pendiente");
    }
}
