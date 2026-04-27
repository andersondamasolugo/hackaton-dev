package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

/**
 * DTO de response con datos de un parámetro del ramo.
 * @param parametroId identificador del parámetro
 * @param codigo código del parámetro
 * @param descripcion descripción del parámetro
 * @param valor valor actual del parámetro
 * @param estado estado del parámetro
 * @param rangoMinimo valor mínimo permitido
 * @param rangoMaximo valor máximo permitido
 */
public record ParametroResponse(
    Long parametroId,
    String codigo,
    String descripcion,
    String valor,
    String estado,
    String rangoMinimo,
    String rangoMaximo
) {}
