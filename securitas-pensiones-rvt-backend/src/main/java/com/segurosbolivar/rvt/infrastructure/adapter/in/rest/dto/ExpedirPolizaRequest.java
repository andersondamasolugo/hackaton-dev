package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO de request para expedición de póliza de Rentas Voluntarias.
 * @param tipoIdentificacion tipo de identificación del tomador (CC, CE, NIT, PA)
 * @param numeroIdentificacion número de identificación del tomador
 * @param nombreTomador nombre completo del tomador
 * @param fechaInicioVigencia fecha de inicio de vigencia de la póliza
 * @param montoRenta monto de la renta (debe ser positivo)
 */
public record ExpedirPolizaRequest(
    @NotBlank String tipoIdentificacion,
    @NotBlank String numeroIdentificacion,
    @NotBlank String nombreTomador,
    @NotNull LocalDate fechaInicioVigencia,
    @NotNull @Positive BigDecimal montoRenta
) {}
