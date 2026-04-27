package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando se ejecuta una búsqueda sin diligenciar ningún filtro.
 */
public class FiltroRequeridoException extends RvtDomainException {

    public FiltroRequeridoException() {
        super("FILTRO_REQUERIDO",
                "Ingrese al menos un criterio de búsqueda");
    }
}
