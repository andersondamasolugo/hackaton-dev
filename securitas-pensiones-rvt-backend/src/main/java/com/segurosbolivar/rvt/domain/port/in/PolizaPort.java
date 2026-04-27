package com.segurosbolivar.rvt.domain.port.in;

import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ExpedirPolizaRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.PolizaResponse;

/**
 * Puerto de entrada para operaciones de pólizas de Rentas Voluntarias.
 */
public interface PolizaPort {

    /**
     * Expide una nueva póliza en estado PENDIENTE.
     *
     * @param request datos de la póliza a expedir
     * @return datos de la póliza creada
     */
    PolizaResponse expedir(ExpedirPolizaRequest request);

    /**
     * Consulta los datos de una póliza por su número.
     *
     * @param numeroPoliza número único de la póliza
     * @return datos de la póliza encontrada
     */
    PolizaResponse consultar(String numeroPoliza);

    /**
     * Activa una póliza cambiando su estado de PENDIENTE a ACTIVA.
     *
     * @param numeroPoliza número único de la póliza a activar
     * @return datos de la póliza activada
     */
    PolizaResponse activar(String numeroPoliza);
}
