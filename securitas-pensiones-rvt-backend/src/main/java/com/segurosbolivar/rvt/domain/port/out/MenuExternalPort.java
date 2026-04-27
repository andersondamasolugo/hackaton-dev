package com.segurosbolivar.rvt.domain.port.out;

import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuTreeResponse;

/**
 * Puerto de salida para consulta del menú desde servicio externo (Person1).
 */
public interface MenuExternalPort {

    /**
     * Obtiene el árbol de menú desde el servicio externo.
     *
     * @return estructura jerárquica del menú
     */
    MenuTreeResponse getMenuTree();
}
