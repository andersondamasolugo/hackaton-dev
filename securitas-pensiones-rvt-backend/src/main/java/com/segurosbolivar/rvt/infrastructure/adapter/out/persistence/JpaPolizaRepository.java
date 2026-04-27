package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Poliza;
import com.segurosbolivar.rvt.domain.port.out.PolizaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación JPA del repositorio de pólizas contra Oracle.
 * Filtra por NIT_NEGOCIO = 72 (Ramo Rentas Voluntarias).
 */
@Repository
@Profile("real")
public class JpaPolizaRepository implements PolizaRepository {

    private static final long RAMO_RVT = 72L;
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

    @Override
    public Optional<Poliza> findByNumeroPoliza(String numeroPoliza) {
        Optional<JpaPolizaEntity> result = jpaRepository.findByNumeroPolizaAndNitNegocio(numeroPoliza, RAMO_RVT);

        if (result.isEmpty()) {
            try {
                int numeroInterno = Integer.parseInt(numeroPoliza);
                result = jpaRepository.findByNumeroInternoAndNitNegocio(numeroInterno, RAMO_RVT);
            } catch (NumberFormatException ignored) {
            }
        }

        return result.map(JpaPolizaEntity::toDomain);
    }
}
