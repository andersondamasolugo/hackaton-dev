package com.segurosbolivar.rvt.domain.port.out;

import com.segurosbolivar.rvt.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia y consulta de clientes.
 */
public interface ClienteRepository {

    /**
     * Busca clientes por criterios de filtro.
     *
     * @param tipoId tipo de identificación
     * @param numeroId número de identificación
     * @param nombre nombre o parte del nombre
     * @return lista de clientes que coinciden con los criterios
     */
    List<Cliente> buscar(String tipoId, String numeroId, String nombre);

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return cliente encontrado o vacío si no existe
     */
    Optional<Cliente> findById(Long id);
}
