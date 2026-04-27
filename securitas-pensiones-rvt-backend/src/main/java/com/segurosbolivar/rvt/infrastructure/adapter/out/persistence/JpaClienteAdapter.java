package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Cliente;
import com.segurosbolivar.rvt.domain.port.out.ClienteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación JPA del repositorio de clientes.
 * Convierte entre entidades de dominio y entidades JPA.
 * Activa solo con el profile "real" para conexión a Oracle.
 */
@Repository
@Profile("real")
public class JpaClienteAdapter implements ClienteRepository {

    private final ClienteJpaJpaRepository jpaRepository;

    public JpaClienteAdapter(ClienteJpaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Busca clientes por criterios de filtro usando consultas JPA derivadas.
     * Combina resultados de cada criterio proporcionado filtrando por intersección.
     *
     * @param tipoId   tipo de identificación
     * @param numeroId número de identificación
     * @param nombre   nombre o parte del nombre
     * @return lista de clientes que coinciden con los criterios
     */
    @Override
    public List<Cliente> buscar(String tipoId, String numeroId, String nombre) {
        List<ClienteJpaEntity> allEntities = jpaRepository.findAll();

        return allEntities.stream()
                .filter(entity -> matchesCriteria(entity, tipoId, numeroId, nombre))
                .map(ClienteJpaEntity::toDomain)
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
        return jpaRepository.findById(id)
                .map(ClienteJpaEntity::toDomain);
    }

    /**
     * Verifica si una entidad JPA coincide con los criterios de búsqueda.
     *
     * @param entity   entidad JPA a evaluar
     * @param tipoId   tipo de identificación esperado
     * @param numeroId número de identificación esperado
     * @param nombre   nombre o parte del nombre esperado
     * @return true si la entidad coincide con todos los criterios proporcionados
     */
    private boolean matchesCriteria(ClienteJpaEntity entity, String tipoId, String numeroId, String nombre) {
        boolean matches = true;

        if (tipoId != null && !tipoId.trim().isEmpty()) {
            matches = entity.getTipoIdentificacion().equalsIgnoreCase(tipoId.trim());
        }

        if (matches && numeroId != null && !numeroId.trim().isEmpty()) {
            matches = entity.getNumeroIdentificacion().equals(numeroId.trim());
        }

        if (matches && nombre != null && !nombre.trim().isEmpty()) {
            matches = entity.getNombre().toLowerCase().contains(nombre.trim().toLowerCase());
        }

        return matches;
    }
}
