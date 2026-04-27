package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Beneficiario;
import com.segurosbolivar.rvt.domain.port.out.BeneficiarioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación JPA del repositorio de beneficiarios.
 * Convierte entre entidades de dominio y entidades JPA.
 * Activa solo con el profile "real" para conexión a Oracle.
 */
@Repository
@Profile("real")
public class JpaBeneficiarioAdapter implements BeneficiarioRepository {

    private final BeneficiarioJpaJpaRepository jpaRepository;

    public JpaBeneficiarioAdapter(BeneficiarioJpaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Busca todos los beneficiarios asociados a una póliza.
     *
     * @param numeroPoliza número de la póliza
     * @return lista de beneficiarios de dominio
     */
    @Override
    public List<Beneficiario> findByNumeroPoliza(String numeroPoliza) {
        return jpaRepository.findByNumeroPoliza(numeroPoliza).stream()
                .map(BeneficiarioJpaEntity::toDomain)
                .toList();
    }

    /**
     * Busca un beneficiario por su identificador.
     *
     * @param id identificador del beneficiario
     * @return beneficiario encontrado o vacío si no existe
     */
    @Override
    public Optional<Beneficiario> findById(Long id) {
        return jpaRepository.findById(id)
                .map(BeneficiarioJpaEntity::toDomain);
    }

    /**
     * Persiste un beneficiario convirtiendo de dominio a JPA entity.
     *
     * @param beneficiario entidad de dominio a persistir
     * @return beneficiario persistido convertido de vuelta a dominio
     */
    @Override
    public Beneficiario save(Beneficiario beneficiario) {
        BeneficiarioJpaEntity entity = BeneficiarioJpaEntity.fromDomain(beneficiario);
        BeneficiarioJpaEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }
}
