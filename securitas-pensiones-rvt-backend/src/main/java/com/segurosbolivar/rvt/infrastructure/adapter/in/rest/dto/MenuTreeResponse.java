package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.util.List;

/**
 * DTO de response para el árbol de categorías del menú.
 * @param categories lista de categorías del menú
 */
public record MenuTreeResponse(
    List<MenuCategory> categories
) {}
