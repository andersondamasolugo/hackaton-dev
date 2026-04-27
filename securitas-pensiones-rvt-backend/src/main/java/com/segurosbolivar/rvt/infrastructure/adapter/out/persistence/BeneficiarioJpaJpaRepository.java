package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz Spring Data JPA para acceso a la tabla BENEFICIARIO.
 * Provee operaciones CRUD automáticas y consulta por número de póliza.
 */
public interface BeneficiarioJpaJpaRepository extends JpaRepository<BeneficiarioJpaEntity, Long> {

    /**
     * Busca todos los beneficiarios asociados a una póliza.
     *
     * @param numeroPoliza número de la póliza
     * @return lista de entidades de beneficiarios
     */
    List<BeneficiarioJpaEntity> findByNumeroPoliza(String numeroPoliza);
}
