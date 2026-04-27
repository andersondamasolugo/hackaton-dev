package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de request para autenticación de usuario.
 * @param username nombre de usuario
 * @param password contraseña del usuario
 */
public record LoginRequest(
    @NotBlank String username,
    @NotBlank String password
) {}
