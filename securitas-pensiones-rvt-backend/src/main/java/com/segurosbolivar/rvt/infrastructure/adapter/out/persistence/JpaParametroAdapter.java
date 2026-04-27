package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Parametro;
import com.segurosbolivar.rvt.domain.port.out.ParametroRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación JPA del repositorio de parámetros del ramo.
 * Convierte entre entidades de dominio y entidades JPA.
 * Activa solo con el profile "real" para conexión a Oracle.
 */
@Repository
@Profile("real")
public class JpaParametroAdapter implements ParametroRepository {

    private final ParametroJpaJpaRepository jpaRepository;

    public JpaParametroAdapter(ParametroJpaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Retorna todos los parámetros del ramo desde la base de datos.
     *
     * @return lista completa de parámetros como entidades de dominio
     */
    @Override
    public List<Parametro> findAll() {
        return jpaRepository.findAll().stream()
                .map(ParametroJpaEntity::toDomain)
                .toList();
    }

    /**
     * Busca un parámetro por su identificador.
     *
     * @param id identificador del parámetro
     * @return parámetro encontrado o vacío si no existe
     */
    @Override
    public Optional<Parametro> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ParametroJpaEntity::toDomain);
    }

    /**
     * Persiste un parámetro en la base de datos.
     *
     * @param parametro entidad de dominio a persistir
     * @return parámetro persistido con datos actualizados
     */
    @Override
    public Parametro save(Parametro parametro) {
        ParametroJpaEntity entity = ParametroJpaEntity.fromDomain(parametro);
        ParametroJpaEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }
}
