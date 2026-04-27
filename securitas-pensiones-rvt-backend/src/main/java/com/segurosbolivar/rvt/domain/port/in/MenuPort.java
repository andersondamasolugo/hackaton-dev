package com.segurosbolivar.rvt.domain.port.in;

import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuTreeResponse;

/**
 * Puerto de entrada para consulta del árbol de menú de categorías.
 */
public interface MenuPort {

    /**
     * Retorna la estructura jerárquica del menú de categorías.
     *
     * @return árbol de categorías con sus ítems
     */
    MenuTreeResponse getMenuTree();
}
