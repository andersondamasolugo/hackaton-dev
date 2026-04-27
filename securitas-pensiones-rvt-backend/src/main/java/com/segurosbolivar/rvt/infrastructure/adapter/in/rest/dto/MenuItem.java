package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

/**
 * Ítem individual del menú de navegación.
 * @param id identificador del ítem
 * @param label etiqueta visible del ítem
 * @param route ruta de navegación asociada
 * @param icon icono del ítem
 */
public record MenuItem(
    String id,
    String label,
    String route,
    String icon
) {}
