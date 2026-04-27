package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO estándar de respuesta de error.
 * @param timestamp momento del error
 * @param status código HTTP
 * @param error tipo de error HTTP
 * @param message mensaje descriptivo del error
 * @param details lista de errores de validación por campo
 * @param correlationId identificador de correlación para trazabilidad
 */
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    List<FieldError> details,
    String correlationId
) {}
