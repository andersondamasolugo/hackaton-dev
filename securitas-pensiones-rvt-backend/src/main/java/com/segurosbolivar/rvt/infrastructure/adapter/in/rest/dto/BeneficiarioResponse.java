package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

/**
 * DTO de response con datos de un beneficiario.
 * @param beneficiarioId identificador del beneficiario
 * @param numeroPoliza número de póliza asociada
 * @param nombreCompleto nombre completo del beneficiario
 * @param tipoIdentificacion tipo de identificación del beneficiario
 * @param numeroIdentificacion número de identificación del beneficiario
 * @param parentesco relación de parentesco con el tomador
 * @param porcentajeParticipacion porcentaje de participación
 */
public record BeneficiarioResponse(
    Long beneficiarioId,
    String numeroPoliza,
    String nombreCompleto,
    String tipoIdentificacion,
    String numeroIdentificacion,
    String parentesco,
    BigDecimal porcentajeParticipacion
) {}
