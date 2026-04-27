package com.segurosbolivar.rvt.infrastructure.adapter.out.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.segurosbolivar.rvt.domain.model.EstadoPoliza;
import com.segurosbolivar.rvt.domain.model.Poliza;
import com.segurosbolivar.rvt.domain.port.out.PolizaRepository;

/**
 * Implementación mock del repositorio de pólizas.
 * Almacena pólizas en memoria usando ConcurrentHashMap.
 * Pre-carga datos de ejemplo para el demo.
 * Activa solo con el profile "mock" para desarrollo sin Oracle.
 */
@Repository
@Profile("mock")
public class MockPolizaRepository implements PolizaRepository {

    private final ConcurrentHashMap<String, Poliza> store = new ConcurrentHashMap<>();

    /**
     * Inicializa el repositorio con pólizas de ejemplo para el demo.
     */
    public MockPolizaRepository() {
        prePopulate();
    }

    /**
     * Persiste una póliza en el almacenamiento en memoria.
     *
     * @param poliza entidad de dominio a persistir
     * @return póliza persistida
     */
    @Override
    public Poliza save(Poliza poliza) {
        store.put(poliza.getNumeroPoliza(), poliza);
        return poliza;
    }

    /**
     * Busca una póliza por su número en el almacenamiento en memoria.
     *
     * @param numeroPoliza número de la póliza
     * @return póliza encontrada o vacío si no existe
     */
    @Override
    public Optional<Poliza> findByNumeroPoliza(String numeroPoliza) {
        return Optional.ofNullable(store.get(numeroPoliza));
    }

    /**
     * Pre-carga 3 pólizas de ejemplo para el demo.
     */
    private void prePopulate() {
        store.put("RVT-DEMO-001", new Poliza(
                "RVT-DEMO-001",
                EstadoPoliza.PENDIENTE,
                "CC",
                "1234567890",
                "Juan Pérez García",
                LocalDate.of(2025, 8, 1),
                new BigDecimal("5000000.00"),
                LocalDateTime.of(2025, 7, 14, 10, 0, 0)
        ));

        store.put("RVT-DEMO-002", new Poliza(
                "RVT-DEMO-002",
                EstadoPoliza.ACTIVA,
                "CE",
                "9876543210",
                "María López Rodríguez",
                LocalDate.of(2025, 6, 15),
                new BigDecimal("8500000.00"),
                LocalDateTime.of(2025, 6, 10, 14, 30, 0)
        ));

        store.put("RVT-DEMO-003", new Poliza(
                "RVT-DEMO-003",
                EstadoPoliza.PENDIENTE,
                "NIT",
                "900123456",
                "Empresa Ejemplo S.A.S.",
                LocalDate.of(2025, 9, 1),
                new BigDecimal("12000000.00"),
                LocalDateTime.of(2025, 7, 1, 9, 15, 0)
        ));
    }
}
