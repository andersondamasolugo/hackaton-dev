package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

/**
 * DTO de response para autenticación exitosa.
 * @param token token JWT generado
 * @param expiresIn tiempo de expiración en segundos
 * @param username nombre de usuario autenticado
 */
public record LoginResponse(
    String token,
    long expiresIn,
    String username
) {}
