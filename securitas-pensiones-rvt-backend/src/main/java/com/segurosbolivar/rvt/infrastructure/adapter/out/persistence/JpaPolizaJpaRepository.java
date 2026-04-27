package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interfaz Spring Data JPA para acceso a la tabla POLIZA de Oracle.
 * Filtra por NIT_NEGOCIO = 72 (Ramo RVT) en los adapters.
 */
public interface JpaPolizaJpaRepository extends JpaRepository<JpaPolizaEntity, Integer> {

    Optional<JpaPolizaEntity> findByNumeroPolizaAndNitNegocio(String numeroPoliza, Long nitNegocio);

    Optional<JpaPolizaEntity> findByNumeroInternoAndNitNegocio(Integer numeroInterno, Long nitNegocio);
}
