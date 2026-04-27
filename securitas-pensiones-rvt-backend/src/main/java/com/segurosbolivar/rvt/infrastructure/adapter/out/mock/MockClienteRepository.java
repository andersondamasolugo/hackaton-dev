package com.segurosbolivar.rvt.infrastructure.adapter.out.mock;

import com.segurosbolivar.rvt.domain.model.Cliente;
import com.segurosbolivar.rvt.domain.port.out.ClienteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación mock del repositorio de clientes.
 * Almacena clientes en memoria usando ConcurrentHashMap.
 * Pre-carga datos de ejemplo vinculados a las pólizas demo.
 * Activa solo con el profile "mock" para desarrollo sin Oracle.
 */
@Repository
@Profile("mock")
public class MockClienteRepository implements ClienteRepository {

    private final ConcurrentHashMap<Long, Cliente> store = new ConcurrentHashMap<>();

    /**
     * Inicializa el repositorio con clientes de ejemplo para el demo.
     */
    public MockClienteRepository() {
        prePopulate();
    }

    /**
     * Busca clientes por criterios de filtro en memoria.
     * Filtra por coincidencia exacta en tipoId y numeroId,
     * y por coincidencia parcial case-insensitive en nombre.
     *
     * @param tipoId   tipo de identificación (filtro exacto)
     * @param numeroId número de identificación (filtro exacto)
     * @param nombre   nombre o parte del nombre (filtro parcial, case-insensitive)
     * @return lista de clientes que coinciden con al menos uno de los criterios proporcionados
     */
    @Override
    public List<Cliente> buscar(String tipoId, String numeroId, String nombre) {
        return store.values().stream()
                .filter(cliente -> matchesCriteria(cliente, tipoId, numeroId, nombre))
                .toList();
    }

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return cliente encontrado o vacío si no existe
     */
    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Verifica si un cliente coincide con los criterios de búsqueda proporcionados.
     * Un cliente coincide si cumple con TODOS los criterios no vacíos.
     *
     * @param cliente  cliente a evaluar
     * @param tipoId   tipo de identificación esperado
     * @param numeroId número de identificación esperado
     * @param nombre   nombre o parte del nombre esperado
     * @return true si el cliente coincide con todos los criterios proporcionados
     */
    private boolean matchesCriteria(Cliente cliente, String tipoId, String numeroId, String nombre) {
        boolean matches = true;

        if (tipoId != null && !tipoId.trim().isEmpty()) {
            matches = cliente.getTipoIdentificacion().equalsIgnoreCase(tipoId.trim());
        }

        if (matches && numeroId != null && !numeroId.trim().isEmpty()) {
            matches = cliente.getNumeroIdentificacion().equals(numeroId.trim());
        }

        if (matches && nombre != null && !nombre.trim().isEmpty()) {
            matches = cliente.getNombre().toLowerCase().contains(nombre.trim().toLowerCase());
        }

        return matches;
    }

    /**
     * Pre-carga 4 clientes de ejemplo vinculados a las pólizas demo.
     * Client 1: CC 1234567890 "Juan Pérez García" (linked to RVT-DEMO-001)
     * Client 2: CE 9876543210 "María López Rodríguez" (linked to RVT-DEMO-002)
     * Client 3: NIT 900123456 "Empresa Ejemplo S.A.S." (linked to RVT-DEMO-003)
     * Client 4: CC 5551234567 "Pedro Gómez Martínez" (no policies)
     */
    private void prePopulate() {
        store.put(1L, new Cliente(
                1L, "CC", "1234567890",
                "Juan Pérez García", "ACTIVO",
                "Calle 100 #15-20, Bogotá",
                "3001234567", "juan.perez@email.com"
        ));

        store.put(2L, new Cliente(
                2L, "CE", "9876543210",
                "María López Rodríguez", "ACTIVO",
                "Carrera 7 #45-10, Bogotá",
                "3109876543", "maria.lopez@email.com"
        ));

        store.put(3L, new Cliente(
                3L, "NIT", "900123456",
                "Empresa Ejemplo S.A.S.", "ACTIVO",
                "Avenida El Dorado #68-35, Bogotá",
                "6012345678", "contacto@empresaejemplo.com"
        ));

        store.put(4L, new Cliente(
                4L, "CC", "5551234567",
                "Pedro Gómez Martínez", "ACTIVO",
                "Calle 50 #30-15, Medellín",
                "3045551234", "pedro.gomez@email.com"
        ));
    }
}
