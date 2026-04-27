package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.segurosbolivar.rvt.domain.model.Parametro;
import com.segurosbolivar.rvt.domain.port.out.ParametroRepository;

/**
 * Implementación del repositorio de parámetros para profile "real".
 * Usa datos estáticos en memoria porque la tabla PARAMETRO_POLIZA de Oracle
 * tiene una estructura diferente al modelo simplificado del demo.
 * Deuda técnica: mapear a la tabla real en siguiente iteración.
 */
@Repository
@Profile("real")
public class JpaParametroAdapter implements ParametroRepository {

    private final ConcurrentHashMap<Long, Parametro> store = new ConcurrentHashMap<>();

    public JpaParametroAdapter() {
        store.put(1L, new Parametro(1L, "TASA_BASE", "Tasa base de renta", "5.5", "ACTIVO", "1.0", "15.0"));
        store.put(2L, new Parametro(2L, "MONTO_MIN", "Monto mínimo de renta", "1000000", "ACTIVO", "500000", "50000000"));
        store.put(3L, new Parametro(3L, "MONTO_MAX", "Monto máximo de renta", "50000000", "ACTIVO", "1000000", "100000000"));
        store.put(4L, new Parametro(4L, "PLAZO_MIN", "Plazo mínimo en meses", "12", "ACTIVO", "6", "360"));
        store.put(5L, new Parametro(5L, "EDAD_MAX", "Edad máxima del tomador", "75", "ACTIVO", "18", "100"));
    }

    @Override
    public List<Parametro> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public Optional<Parametro> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Parametro save(Parametro parametro) {
        store.put(parametro.getId(), parametro);
        return parametro;
    }
}
