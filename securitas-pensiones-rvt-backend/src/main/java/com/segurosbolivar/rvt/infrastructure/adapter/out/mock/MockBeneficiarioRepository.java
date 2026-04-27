package com.segurosbolivar.rvt.infrastructure.adapter.out.mock;

import com.segurosbolivar.rvt.domain.model.Beneficiario;
import com.segurosbolivar.rvt.domain.port.out.BeneficiarioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación mock del repositorio de beneficiarios.
 * Almacena beneficiarios en memoria usando ConcurrentHashMap.
 * Pre-carga datos de ejemplo vinculados a las pólizas demo.
 * Activa solo con el profile "mock" para desarrollo sin Oracle.
 */
@Repository
@Profile("mock")
public class MockBeneficiarioRepository implements BeneficiarioRepository {

    private final ConcurrentHashMap<Long, Beneficiario> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(3L);

    /**
     * Inicializa el repositorio con beneficiarios de ejemplo para el demo.
     */
    public MockBeneficiarioRepository() {
        prePopulate();
    }

    /**
     * Busca todos los beneficiarios asociados a una póliza.
     *
     * @param numeroPoliza número de la póliza
     * @return lista de beneficiarios de la póliza
     */
    @Override
    public List<Beneficiario> findByNumeroPoliza(String numeroPoliza) {
        return store.values().stream()
                .filter(b -> b.getNumeroPoliza().equals(numeroPoliza))
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
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Persiste un beneficiario en el almacenamiento en memoria.
     * Si el beneficiario no tiene ID, se le asigna uno auto-incremental.
     *
     * @param beneficiario entidad de dominio a persistir
     * @return beneficiario persistido
     */
    @Override
    public Beneficiario save(Beneficiario beneficiario) {
        if (beneficiario.getId() == null) {
            beneficiario.setId(idSequence.incrementAndGet());
        }
        store.put(beneficiario.getId(), beneficiario);
        return beneficiario;
    }

    /**
     * Pre-carga beneficiarios de ejemplo vinculados a las pólizas demo.
     * RVT-DEMO-001: 2 beneficiarios (60% + 30% = 90%)
     * RVT-DEMO-002: 1 beneficiario (100%)
     */
    private void prePopulate() {
        store.put(1L, new Beneficiario(
                1L,
                "RVT-DEMO-001",
                "Carlos Pérez López",
                "CC",
                "1098765432",
                "Hijo",
                new BigDecimal("60.00")
        ));

        store.put(2L, new Beneficiario(
                2L,
                "RVT-DEMO-001",
                "Ana María García",
                "CC",
                "5567890123",
                "Cónyuge",
                new BigDecimal("30.00")
        ));

        store.put(3L, new Beneficiario(
                3L,
                "RVT-DEMO-002",
                "Pedro López Rodríguez",
                "CC",
                "8901234567",
                "Hijo",
                new BigDecimal("100.00")
        ));
    }
}
