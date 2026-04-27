package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz Spring Data JPA para acceso a la tabla BENEFICIARIO de Oracle.
 */
public interface BeneficiarioJpaJpaRepository extends JpaRepository<BeneficiarioJpaEntity, BeneficiarioJpaEntity.BeneficiarioId> {

    List<BeneficiarioJpaEntity> findByNitNegocioAndNumeroInternoAndSenalActivo(Long nitNegocio, Integer numeroInterno, String senalActivo);

    List<BeneficiarioJpaEntity> findByNitNegocioAndNumeroInterno(Long nitNegocio, Integer numeroInterno);
}
