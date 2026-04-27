package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

/**
 * Detalle de error de validación por campo.
 * @param field nombre del campo con error
 * @param message mensaje de validación del campo
 */
public record FieldError(
    String field,
    String message
) {}
