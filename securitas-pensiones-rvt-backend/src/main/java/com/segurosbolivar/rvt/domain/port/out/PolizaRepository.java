package com.segurosbolivar.rvt.domain.port.out;

import com.segurosbolivar.rvt.domain.model.Poliza;

import java.util.Optional;

/**
 * Puerto de salida para persistencia de pólizas.
 */
public interface PolizaRepository {

    /**
     * Persiste una póliza (creación o actualización).
     *
     * @param poliza entidad de dominio a persistir
     * @return póliza persistida
     */
    Poliza save(Poliza poliza);

    /**
     * Busca una póliza por su número único.
     *
     * @param numeroPoliza número de la póliza
     * @return póliza encontrada o vacío si no existe
     */
    Optional<Poliza> findByNumeroPoliza(String numeroPoliza);
}
