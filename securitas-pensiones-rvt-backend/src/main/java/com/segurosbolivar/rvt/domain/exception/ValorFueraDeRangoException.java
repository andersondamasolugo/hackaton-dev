package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando el valor de un parámetro está fuera del rango permitido.
 */
public class ValorFueraDeRangoException extends RvtDomainException {

    public ValorFueraDeRangoException(String codigoParametro, String rangoMinimo, String rangoMaximo) {
        super("VALOR_FUERA_DE_RANGO",
                "El valor del parámetro " + codigoParametro
                        + " debe estar entre " + rangoMinimo + " y " + rangoMaximo);
    }
}
