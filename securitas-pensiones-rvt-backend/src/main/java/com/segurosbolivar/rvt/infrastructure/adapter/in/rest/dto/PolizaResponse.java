package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de response con datos de una póliza de Rentas Voluntarias.
 * @param numeroPoliza número único de la póliza
 * @param estado estado actual (PENDIENTE, ACTIVA)
 * @param tipoIdentificacion tipo de identificación del tomador
 * @param numeroIdentificacion número de identificación del tomador
 * @param nombreTomador nombre completo del tomador
 * @param fechaInicioVigencia fecha de inicio de vigencia
 * @param montoRenta monto de la renta
 * @param fechaCreacion fecha y hora de creación de la póliza
 */
public record PolizaResponse(
    String numeroPoliza,
    String estado,
    String tipoIdentificacion,
    String numeroIdentificacion,
    String nombreTomador,
    LocalDate fechaInicioVigencia,
    BigDecimal montoRenta,
    LocalDateTime fechaCreacion
) {}
