package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Interfaz Spring Data JPA para acceso a la tabla POLIZA de Oracle.
 * Soporta búsqueda por NUMERO_POLIZA (texto) y NUMERO_INTERNO (numérico).
 */
public interface JpaPolizaJpaRepository extends JpaRepository<JpaPolizaEntity, Integer> {

    /**
     * Busca por NUMERO_POLIZA (campo texto).
     */
    Optional<JpaPolizaEntity> findByNumeroPoliza(String numeroPoliza);

    /**
     * Busca por NUMERO_INTERNO (campo numérico — PK real en Oracle).
     */
    Optional<JpaPolizaEntity> findByNumeroInterno(Integer numeroInterno);

    /**
     * Busca por NUMERO_POLIZA o NUMERO_INTERNO (intenta ambos).
     */
    @Query("SELECT p FROM JpaPolizaEntity p WHERE p.numeroPoliza = :valor OR CAST(p.numeroInterno AS string) = :valor")
    Optional<JpaPolizaEntity> findByNumeroPolizaOrNumeroInterno(@Param("valor") String valor);
}
