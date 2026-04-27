package com.segurosbolivar.rvt.domain.port.in;

import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ClienteDetalleResponse;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ClienteResponse;

import java.util.List;

/**
 * Puerto de entrada para operaciones de consulta de clientes.
 */
public interface ClientePort {

    /**
     * Busca clientes por criterios de filtro. Al menos un filtro debe estar presente.
     *
     * @param tipoId tipo de identificación del cliente
     * @param numeroId número de identificación del cliente
     * @param nombre nombre o parte del nombre del cliente
     * @return lista de clientes que coinciden con los criterios
     */
    List<ClienteResponse> buscar(String tipoId, String numeroId, String nombre);

    /**
     * Consulta el detalle consolidado de un cliente incluyendo sus pólizas.
     *
     * @param clienteId identificador del cliente
     * @return detalle completo del cliente con pólizas asociadas
     */
    ClienteDetalleResponse consultarDetalle(Long clienteId);
}
