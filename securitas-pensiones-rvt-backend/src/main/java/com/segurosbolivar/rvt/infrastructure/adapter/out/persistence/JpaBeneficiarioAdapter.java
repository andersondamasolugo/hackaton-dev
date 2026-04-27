package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Beneficiario;
import com.segurosbolivar.rvt.domain.port.out.BeneficiarioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación JPA del repositorio de beneficiarios contra Oracle.
 * Filtra por NIT_NEGOCIO = 72 (Ramo Rentas Voluntarias).
 */
@Repository
@Profile("real")
public class JpaBeneficiarioAdapter implements BeneficiarioRepository {

    private static final long RAMO_RVT = 72L;
    private final BeneficiarioJpaJpaRepository jpaRepository;

    public JpaBeneficiarioAdapter(BeneficiarioJpaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Beneficiario> findByNumeroPoliza(String numeroPoliza) {
        try {
            int numeroInterno = Integer.parseInt(numeroPoliza);
            return jpaRepository.findByNitNegocioAndNumeroInternoAndSenalActivo(RAMO_RVT, numeroInterno, "S").stream()
                    .map(BeneficiarioJpaEntity::toDomain)
                    .toList();
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Beneficiario> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Beneficiario save(Beneficiario beneficiario) {
        return beneficiario;
    }
}
