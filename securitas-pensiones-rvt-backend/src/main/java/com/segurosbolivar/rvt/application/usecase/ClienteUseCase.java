package com.segurosbolivar.rvt.application.usecase;

import com.segurosbolivar.rvt.domain.exception.ClienteNotFoundException;
import com.segurosbolivar.rvt.domain.exception.FiltroRequeridoException;
import com.segurosbolivar.rvt.domain.model.Cliente;
import com.segurosbolivar.rvt.domain.port.in.ClientePort;
import com.segurosbolivar.rvt.domain.port.out.ClienteRepository;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ClienteDetalleResponse;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ClienteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso de clientes de Rentas Voluntarias.
 * Implementa búsqueda por criterios y consulta de detalle consolidado.
 */
@Service
public class ClienteUseCase implements ClientePort {

    private final ClienteRepository clienteRepository;

    public ClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Busca clientes por criterios de filtro.
     * Al menos un filtro debe estar presente; de lo contrario lanza FiltroRequeridoException.
     * La búsqueda por nombre es case-insensitive y parcial (contains).
     *
     * @param tipoId   tipo de identificación del cliente
     * @param numeroId número de identificación del cliente
     * @param nombre   nombre o parte del nombre del cliente
     * @return lista de clientes que coinciden con los criterios
     * @throws FiltroRequeridoException si todos los filtros son nulos o vacíos
     */
    @Override
    public List<ClienteResponse> buscar(String tipoId, String numeroId, String nombre) {
        if (isBlank(tipoId) && isBlank(numeroId) && isBlank(nombre)) {
            throw new FiltroRequeridoException();
        }

        List<Cliente> clientes = clienteRepository.buscar(tipoId, numeroId, nombre);

        return clientes.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Consulta el detalle consolidado de un cliente incluyendo sus pólizas.
     *
     * @param clienteId identificador del cliente
     * @return detalle completo del cliente con pólizas asociadas
     * @throws ClienteNotFoundException si no existe el cliente
     */
    @Override
    public ClienteDetalleResponse consultarDetalle(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(clienteId));

        return toDetalleResponse(cliente);
    }

    /**
     * Mapea una entidad de dominio Cliente a su DTO de respuesta resumido.
     *
     * @param cliente entidad de dominio
     * @return DTO de respuesta con datos resumidos del cliente
     */
    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getTipoIdentificacion(),
                cliente.getNumeroIdentificacion(),
                cliente.getNombre(),
                cliente.getEstado(),
                0
        );
    }

    /**
     * Mapea una entidad de dominio Cliente a su DTO de detalle consolidado.
     * En el demo, la lista de pólizas se obtiene del mock repository.
     *
     * @param cliente entidad de dominio
     * @return DTO de detalle con datos completos y pólizas asociadas
     */
    private ClienteDetalleResponse toDetalleResponse(Cliente cliente) {
        return new ClienteDetalleResponse(
                cliente.getId(),
                cliente.getTipoIdentificacion(),
                cliente.getNumeroIdentificacion(),
                cliente.getNombre(),
                cliente.getEstado(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEmail(),
                List.of()
        );
    }

    /**
     * Verifica si una cadena es nula o está vacía después de recortar espacios.
     *
     * @param value cadena a verificar
     * @return true si es nula o vacía
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
