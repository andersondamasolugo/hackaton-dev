package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz Spring Data JPA para acceso a la tabla BENEFICIARIO de Oracle.
 * Busca por NUMERO_INTERNO (vinculado a la póliza).
 */
public interface BeneficiarioJpaJpaRepository extends JpaRepository<BeneficiarioJpaEntity, BeneficiarioJpaEntity.BeneficiarioId> {

    /**
     * Busca beneficiarios activos por número interno de póliza.
     */
    List<BeneficiarioJpaEntity> findByNumeroInternoAndSenalActivo(Integer numeroInterno, String senalActivo);

    /**
     * Busca beneficiarios por número interno (todos).
     */
    List<BeneficiarioJpaEntity> findByNumeroInterno(Integer numeroInterno);
}
