package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interfaz Spring Data JPA para acceso a la tabla POLIZA.
 * Provee operaciones CRUD automáticas y consulta por número de póliza.
 */
public interface JpaPolizaJpaRepository extends JpaRepository<JpaPolizaEntity, String> {

    /**
     * Busca una entidad de póliza por su número único.
     *
     * @param numeroPoliza número de la póliza
     * @return entidad encontrada o vacío si no existe
     */
    Optional<JpaPolizaEntity> findByNumeroPoliza(String numeroPoliza);
}
