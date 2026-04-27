package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import com.segurosbolivar.rvt.domain.port.in.PolizaPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ExpedirPolizaRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.PolizaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para operaciones de pólizas de Rentas Voluntarias.
 * Expone endpoints de expedición, consulta y activación.
 */
@RestController
@RequestMapping("/polizas")
@Tag(name = "Pólizas", description = "Endpoints de expedición, consulta y activación de pólizas RVT")
public class PolizaController {

    private final PolizaPort polizaPort;

    public PolizaController(PolizaPort polizaPort) {
        this.polizaPort = polizaPort;
    }

    /**
     * Expide una nueva póliza de Rentas Voluntarias.
     *
     * @param request datos de la póliza a expedir
     * @return póliza creada con número generado y estado PENDIENTE
     */
    @PostMapping("/expedir")
    @Operation(summary = "Expedir póliza", description = "Crea una nueva póliza en estado PENDIENTE con número generado")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Póliza creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos")
    })
    public ResponseEntity<PolizaResponse> expedir(@Valid @RequestBody ExpedirPolizaRequest request) {
        PolizaResponse response = polizaPort.expedir(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consulta los datos de una póliza por su número.
     *
     * @param numeroPoliza número único de la póliza
     * @return datos de la póliza encontrada
     */
    @GetMapping("/consultar/{numeroPoliza}")
    @Operation(summary = "Consultar póliza", description = "Retorna los datos de una póliza por su número")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Póliza encontrada"),
        @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<PolizaResponse> consultar(@PathVariable String numeroPoliza) {
        PolizaResponse response = polizaPort.consultar(numeroPoliza);
        return ResponseEntity.ok(response);
    }

    /**
     * Activa una póliza cambiando su estado de PENDIENTE a ACTIVA.
     *
     * @param numeroPoliza número único de la póliza a activar
     * @return datos de la póliza activada
     */
    @PutMapping("/activar/{numeroPoliza}")
    @Operation(summary = "Activar póliza", description = "Cambia el estado de una póliza de PENDIENTE a ACTIVA")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Póliza activada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Póliza no encontrada"),
        @ApiResponse(responseCode = "409", description = "Póliza no puede ser activada (no está en estado PENDIENTE)")
    })
    public ResponseEntity<PolizaResponse> activar(@PathVariable String numeroPoliza) {
        PolizaResponse response = polizaPort.activar(numeroPoliza);
        return ResponseEntity.ok(response);
    }
}
