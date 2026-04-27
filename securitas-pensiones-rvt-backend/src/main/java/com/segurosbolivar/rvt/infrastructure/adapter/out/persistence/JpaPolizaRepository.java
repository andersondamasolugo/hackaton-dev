package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Poliza;
import com.segurosbolivar.rvt.domain.port.out.PolizaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación JPA del repositorio de pólizas.
 * Convierte entre entidades de dominio y entidades JPA.
 * Activa solo con el profile "real" para conexión a Oracle.
 */
@Repository
@Profile("real")
public class JpaPolizaRepository implements PolizaRepository {

    private final JpaPolizaJpaRepository jpaRepository;

    public JpaPolizaRepository(JpaPolizaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Persiste una póliza convirtiendo de dominio a JPA entity.
     *
     * @param poliza entidad de dominio a persistir
     * @return póliza persistida convertida de vuelta a dominio
     */
    @Override
    public Poliza save(Poliza poliza) {
        JpaPolizaEntity entity = JpaPolizaEntity.fromDomain(poliza);
        JpaPolizaEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    /**
     * Busca una póliza por su número convirtiendo de JPA entity a dominio.
     *
     * @param numeroPoliza número de la póliza
     * @return póliza encontrada o vacío si no existe
     */
    @Override
    public Optional<Poliza> findByNumeroPoliza(String numeroPoliza) {
        return jpaRepository.findByNumeroPoliza(numeroPoliza)
                .map(JpaPolizaEntity::toDomain);
    }
}
