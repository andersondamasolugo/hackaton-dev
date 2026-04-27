package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para actualización de datos de un beneficiario.
 * @param nombreCompleto nombre completo del beneficiario
 * @param tipoIdentificacion tipo de identificación del beneficiario
 * @param numeroIdentificacion número de identificación del beneficiario
 * @param parentesco relación de parentesco con el tomador
 * @param porcentajeParticipacion porcentaje de participación (0-100)
 */
public record ActualizarBeneficiarioRequest(
    @NotBlank String nombreCompleto,
    @NotBlank String tipoIdentificacion,
    @NotBlank String numeroIdentificacion,
    @NotBlank String parentesco,
    @NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal porcentajeParticipacion
) {}
