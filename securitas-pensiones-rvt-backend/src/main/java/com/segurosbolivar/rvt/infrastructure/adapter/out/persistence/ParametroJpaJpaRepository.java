package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz Spring Data JPA para acceso a la tabla PARAMETRO_RAMO.
 * Provee operaciones CRUD automáticas para parámetros del ramo.
 */
public interface ParametroJpaJpaRepository extends JpaRepository<ParametroJpaEntity, Long> {
}
