package com.segurosbolivar.rvt.domain.exception;

/**
 * Se lanza cuando no se encuentra un beneficiario con el identificador proporcionado.
 */
public class BeneficiarioNotFoundException extends RvtDomainException {

    public BeneficiarioNotFoundException(Long beneficiarioId) {
        super("BENEFICIARIO_NOT_FOUND",
                "No se encontró el beneficiario con ID: " + beneficiarioId);
    }
}
