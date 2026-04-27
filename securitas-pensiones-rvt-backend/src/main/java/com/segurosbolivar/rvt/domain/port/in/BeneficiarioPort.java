package com.segurosbolivar.rvt.domain.port.in;

import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ActualizarBeneficiarioRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.BeneficiarioResponse;

import java.util.List;

/**
 * Puerto de entrada para operaciones de beneficiarios de pólizas.
 */
public interface BeneficiarioPort {

    /**
     * Lista todos los beneficiarios asociados a una póliza.
     *
     * @param numeroPoliza número de la póliza
     * @return lista de beneficiarios de la póliza
     */
    List<BeneficiarioResponse> listarPorPoliza(String numeroPoliza);

    /**
     * Actualiza los datos de un beneficiario existente.
     *
     * @param beneficiarioId identificador del beneficiario
     * @param request datos actualizados del beneficiario
     * @return datos del beneficiario actualizado
     */
    BeneficiarioResponse actualizar(Long beneficiarioId, ActualizarBeneficiarioRequest request);
}
