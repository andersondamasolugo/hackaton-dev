package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import com.segurosbolivar.rvt.domain.port.in.ClientePort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ClienteDetalleResponse;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ClienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para operaciones de consulta de clientes RVT.
 * Expone endpoints de búsqueda por criterios y consulta de detalle consolidado.
 */
@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints de búsqueda y consulta de clientes de Rentas Voluntarias")
public class ClienteController {

    private final ClientePort clientePort;

    public ClienteController(ClientePort clientePort) {
        this.clientePort = clientePort;
    }

    /**
     * Busca clientes por criterios de filtro. Al menos un filtro debe estar presente.
     *
     * @param tipoId   tipo de identificación (opcional)
     * @param numeroId número de identificación (opcional)
     * @param nombre   nombre o parte del nombre (opcional)
     * @return lista de clientes que coinciden con los criterios
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar clientes",
               description = "Busca clientes por tipo de identificación, número de identificación y/o nombre. Al menos un filtro es requerido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
        @ApiResponse(responseCode = "400", description = "No se proporcionó ningún filtro de búsqueda")
    })
    public ResponseEntity<List<ClienteResponse>> buscar(
            @Parameter(description = "Tipo de identificación (CC, CE, NIT, PA)")
            @RequestParam(required = false) String tipoId,
            @Parameter(description = "Número de identificación")
            @RequestParam(required = false) String numeroId,
            @Parameter(description = "Nombre o parte del nombre del cliente")
            @RequestParam(required = false) String nombre) {
        List<ClienteResponse> clientes = clientePort.buscar(tipoId, numeroId, nombre);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Consulta el detalle consolidado de un cliente incluyendo sus pólizas.
     *
     * @param clienteId identificador del cliente
     * @return detalle completo del cliente con pólizas asociadas
     */
    @GetMapping("/{clienteId}")
    @Operation(summary = "Consultar detalle de cliente",
               description = "Retorna el detalle consolidado de un cliente con sus pólizas asociadas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle del cliente obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDetalleResponse> consultarDetalle(@PathVariable Long clienteId) {
        ClienteDetalleResponse detalle = clientePort.consultarDetalle(clienteId);
        return ResponseEntity.ok(detalle);
    }
}
