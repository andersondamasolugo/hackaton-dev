package com.segurosbolivar.rvt.domain.port.in;

import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ActualizarParametroRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ParametroResponse;

import java.util.List;

/**
 * Puerto de entrada para operaciones de parametrización del ramo.
 */
public interface ParametroPort {

    /**
     * Lista todos los parámetros del ramo de Rentas Voluntarias.
     *
     * @return lista de parámetros configurados
     */
    List<ParametroResponse> listar();

    /**
     * Actualiza el valor de un parámetro validando que esté dentro del rango permitido.
     *
     * @param parametroId identificador del parámetro
     * @param request datos de actualización (valor, descripción)
     * @return datos del parámetro actualizado
     */
    ParametroResponse actualizar(Long parametroId, ActualizarParametroRequest request);
}
