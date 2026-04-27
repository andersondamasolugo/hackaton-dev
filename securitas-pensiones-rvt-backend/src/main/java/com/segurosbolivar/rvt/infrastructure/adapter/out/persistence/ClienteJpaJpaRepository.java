package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz Spring Data JPA para acceso a la tabla CLIENTE.
 * Provee operaciones CRUD automáticas y consultas derivadas por criterios.
 */
public interface ClienteJpaJpaRepository extends JpaRepository<ClienteJpaEntity, Long> {

    /**
     * Busca clientes por tipo de identificación.
     *
     * @param tipoIdentificacion tipo de identificación
     * @return lista de entidades de clientes
     */
    List<ClienteJpaEntity> findByTipoIdentificacion(String tipoIdentificacion);

    /**
     * Busca clientes por número de identificación.
     *
     * @param numeroIdentificacion número de identificación
     * @return lista de entidades de clientes
     */
    List<ClienteJpaEntity> findByNumeroIdentificacion(String numeroIdentificacion);

    /**
     * Busca clientes cuyo nombre contenga la cadena proporcionada (case-insensitive).
     *
     * @param nombre parte del nombre a buscar
     * @return lista de entidades de clientes
     */
    List<ClienteJpaEntity> findByNombreContainingIgnoreCase(String nombre);
}
