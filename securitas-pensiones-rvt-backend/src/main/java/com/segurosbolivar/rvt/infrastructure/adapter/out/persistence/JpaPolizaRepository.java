package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Poliza;
import com.segurosbolivar.rvt.domain.port.out.PolizaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación JPA del repositorio de pólizas contra Oracle.
 * Busca por NUMERO_POLIZA o NUMERO_INTERNO (flexible para el demo).
 * Activa solo con el profile "real" para conexión a Oracle.
 */
@Repository
@Profile("real")
public class JpaPolizaRepository implements PolizaRepository {

    private final JpaPolizaJpaRepository jpaRepository;

    public JpaPolizaRepository(JpaPolizaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Poliza save(Poliza poliza) {
        JpaPolizaEntity entity = JpaPolizaEntity.fromDomain(poliza);
        JpaPolizaEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    /**
     * Busca una póliza por número. Intenta primero por NUMERO_POLIZA (texto),
     * luego por NUMERO_INTERNO (numérico) si el valor es un número.
     */
    @Override
    public Optional<Poliza> findByNumeroPoliza(String numeroPoliza) {
        // Primero intenta por NUMERO_POLIZA (texto)
        Optional<JpaPolizaEntity> result = jpaRepository.findByNumeroPoliza(numeroPoliza);

        // Si no encuentra y el valor es numérico, intenta por NUMERO_INTERNO
        if (result.isEmpty()) {
            try {
                int numeroInterno = Integer.parseInt(numeroPoliza);
                result = jpaRepository.findByNumeroInterno(numeroInterno);
            } catch (NumberFormatException ignored) {
                // No es numérico, no buscar por NUMERO_INTERNO
            }
        }

        return result.map(JpaPolizaEntity::toDomain);
    }
}
