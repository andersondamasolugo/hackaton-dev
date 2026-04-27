package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando no se encuentra un parámetro con el identificador proporcionado.
 */
public class ParametroNotFoundException extends RvtDomainException {

    public ParametroNotFoundException(Long parametroId) {
        super("PARAMETRO_NOT_FOUND",
                "Parámetro no encontrado: " + parametroId);
    }
}
