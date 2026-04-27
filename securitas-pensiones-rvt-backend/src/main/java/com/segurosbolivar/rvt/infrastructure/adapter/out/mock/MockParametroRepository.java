package com.segurosbolivar.rvt.infrastructure.adapter.out.mock;

import com.segurosbolivar.rvt.domain.model.Parametro;
import com.segurosbolivar.rvt.domain.port.out.ParametroRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación mock del repositorio de parámetros del ramo.
 * Almacena parámetros en memoria usando ConcurrentHashMap.
 * Pre-carga datos de ejemplo representativos del ramo de Rentas Voluntarias.
 * Activa solo con el profile "mock" para desarrollo sin Oracle.
 */
@Repository
@Profile("mock")
public class MockParametroRepository implements ParametroRepository {

    private final ConcurrentHashMap<Long, Parametro> store = new ConcurrentHashMap<>();

    /**
     * Inicializa el repositorio con parámetros de ejemplo para el demo.
     */
    public MockParametroRepository() {
        prePopulate();
    }

    /**
     * Retorna todos los parámetros del ramo almacenados en memoria.
     *
     * @return lista completa de parámetros
     */
    @Override
    public List<Parametro> findAll() {
        return List.copyOf(store.values());
    }

    /**
     * Busca un parámetro por su identificador.
     *
     * @param id identificador del parámetro
     * @return parámetro encontrado o vacío si no existe
     */
    @Override
    public Optional<Parametro> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Persiste un parámetro en el almacenamiento en memoria.
     *
     * @param parametro entidad de dominio a persistir
     * @return parámetro persistido
     */
    @Override
    public Parametro save(Parametro parametro) {
        store.put(parametro.getId(), parametro);
        return parametro;
    }

    /**
     * Pre-carga 5 parámetros representativos del ramo de Rentas Voluntarias.
     * TASA_BASE: Tasa base de renta (1.0 - 15.0)
     * MONTO_MIN: Monto mínimo de renta (500000 - 50000000)
     * MONTO_MAX: Monto máximo de renta (1000000 - 100000000)
     * PLAZO_MIN: Plazo mínimo en meses (6 - 360)
     * EDAD_MAX: Edad máxima del tomador (18 - 100)
     */
    private void prePopulate() {
        store.put(1L, new Parametro(
                1L, "TASA_BASE", "Tasa base de renta",
                "5.5", "ACTIVO", "1.0", "15.0"
        ));

        store.put(2L, new Parametro(
                2L, "MONTO_MIN", "Monto mínimo de renta",
                "1000000", "ACTIVO", "500000", "50000000"
        ));

        store.put(3L, new Parametro(
                3L, "MONTO_MAX", "Monto máximo de renta",
                "50000000", "ACTIVO", "1000000", "100000000"
        ));

        store.put(4L, new Parametro(
                4L, "PLAZO_MIN", "Plazo mínimo en meses",
                "12", "ACTIVO", "6", "360"
        ));

        store.put(5L, new Parametro(
                5L, "EDAD_MAX", "Edad máxima del tomador",
                "75", "ACTIVO", "18", "100"
        ));
    }
}
