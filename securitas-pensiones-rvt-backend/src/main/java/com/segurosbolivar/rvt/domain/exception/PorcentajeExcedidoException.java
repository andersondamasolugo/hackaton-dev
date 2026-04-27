package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando la suma de porcentajes de participación de beneficiarios supera 100%.
 */
public class PorcentajeExcedidoException extends RvtDomainException {

    public PorcentajeExcedidoException() {
        super("PORCENTAJE_EXCEDIDO",
                "La suma de porcentajes de participación no puede superar 100%");
    }
}
