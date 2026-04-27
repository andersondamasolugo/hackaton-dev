package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.util.List;

/**
 * Categoría del menú de navegación.
 * @param id identificador de la categoría
 * @param label etiqueta visible de la categoría
 * @param icon icono de la categoría
 * @param items lista de ítems dentro de la categoría
 */
public record MenuCategory(
    String id,
    String label,
    String icon,
    List<MenuItem> items
) {}
