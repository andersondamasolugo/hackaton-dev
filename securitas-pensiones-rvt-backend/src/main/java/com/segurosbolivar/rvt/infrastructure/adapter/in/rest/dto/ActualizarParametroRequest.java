package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de request para actualización de un parámetro del ramo.
 * @param valor nuevo valor del parámetro
 * @param descripcion descripción opcional del parámetro
 */
public record ActualizarParametroRequest(
    @NotBlank String valor,
    String descripcion
) {}
