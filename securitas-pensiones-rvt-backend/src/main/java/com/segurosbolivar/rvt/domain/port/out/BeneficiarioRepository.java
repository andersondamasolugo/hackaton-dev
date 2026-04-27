package com.segurosbolivar.rvt.domain.port.out;

import com.segurosbolivar.rvt.domain.model.Beneficiario;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de beneficiarios.
 */
public interface BeneficiarioRepository {

    /**
     * Busca todos los beneficiarios asociados a una póliza.
     *
     * @param numeroPoliza número de la póliza
     * @return lista de beneficiarios de la póliza
     */
    List<Beneficiario> findByNumeroPoliza(String numeroPoliza);

    /**
     * Busca un beneficiario por su identificador.
     *
     * @param id identificador del beneficiario
     * @return beneficiario encontrado o vacío si no existe
     */
    Optional<Beneficiario> findById(Long id);

    /**
     * Persiste un beneficiario (creación o actualización).
     *
     * @param beneficiario entidad de dominio a persistir
     * @return beneficiario persistido
     */
    Beneficiario save(Beneficiario beneficiario);
}
