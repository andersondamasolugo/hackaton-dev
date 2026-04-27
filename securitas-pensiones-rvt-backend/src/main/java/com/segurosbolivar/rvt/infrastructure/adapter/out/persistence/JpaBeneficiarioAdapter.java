package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.segurosbolivar.rvt.domain.model.Beneficiario;
import com.segurosbolivar.rvt.domain.port.out.BeneficiarioRepository;

/**
 * Implementación JPA del repositorio de beneficiarios contra Oracle.
 * Busca por NUMERO_INTERNO (el parámetro "numeroPoliza" se interpreta como número interno).
 */
@Repository
@Profile("real")
public class JpaBeneficiarioAdapter implements BeneficiarioRepository {

    private final BeneficiarioJpaJpaRepository jpaRepository;

    public JpaBeneficiarioAdapter(BeneficiarioJpaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Beneficiario> findByNumeroPoliza(String numeroPoliza) {
        try {
            int numeroInterno = Integer.parseInt(numeroPoliza);
            return jpaRepository.findByNumeroInternoAndSenalActivo(numeroInterno, "S").stream()
                    .map(BeneficiarioJpaEntity::toDomain)
                    .toList();
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Beneficiario> findById(Long id) {
        // En Oracle, el ID es la secuencia_beneficiario — búsqueda simplificada
        return jpaRepository.findAll().stream()
                .filter(e -> e.getSecuenciaBeneficiario() != null && e.getSecuenciaBeneficiario().longValue() == id)
                .findFirst()
                .map(BeneficiarioJpaEntity::toDomain);
    }

    @Override
    public Beneficiario save(Beneficiario beneficiario) {
        // Read-only para Oracle legado en el demo
        return beneficiario;
    }
}
