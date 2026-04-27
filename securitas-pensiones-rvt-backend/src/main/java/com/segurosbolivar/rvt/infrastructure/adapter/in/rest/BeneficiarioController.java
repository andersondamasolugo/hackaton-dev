package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import com.segurosbolivar.rvt.domain.port.in.BeneficiarioPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ActualizarBeneficiarioRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.BeneficiarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para operaciones de beneficiarios de pólizas RVT.
 * Expone endpoints de listado por póliza y actualización de beneficiario.
 */
@RestController
@RequestMapping("/beneficiarios")
@Tag(name = "Beneficiarios", description = "Endpoints de consulta y actualización de beneficiarios de pólizas RVT")
public class BeneficiarioController {

    private final BeneficiarioPort beneficiarioPort;

    public BeneficiarioController(BeneficiarioPort beneficiarioPort) {
        this.beneficiarioPort = beneficiarioPort;
    }

    /**
     * Lista todos los beneficiarios asociados a una póliza.
     *
     * @param numeroPoliza número de la póliza
     * @return lista de beneficiarios (puede ser vacía)
     */
    @GetMapping("/poliza/{numeroPoliza}")
    @Operation(summary = "Listar beneficiarios por póliza",
               description = "Retorna todos los beneficiarios asociados a una póliza")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de beneficiarios obtenida exitosamente")
    })
    public ResponseEntity<List<BeneficiarioResponse>> listarPorPoliza(@PathVariable String numeroPoliza) {
        List<BeneficiarioResponse> beneficiarios = beneficiarioPort.listarPorPoliza(numeroPoliza);
        return ResponseEntity.ok(beneficiarios);
    }

    /**
     * Actualiza los datos de un beneficiario existente.
     *
     * @param beneficiarioId identificador del beneficiario
     * @param request datos actualizados del beneficiario
     * @return datos del beneficiario actualizado
     */
    @PutMapping("/{beneficiarioId}")
    @Operation(summary = "Actualizar beneficiario",
               description = "Actualiza los datos de un beneficiario. Valida que la suma de porcentajes no supere 100%")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Beneficiario actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o porcentaje excedido"),
        @ApiResponse(responseCode = "404", description = "Beneficiario no encontrado")
    })
    public ResponseEntity<BeneficiarioResponse> actualizar(
            @PathVariable Long beneficiarioId,
            @Valid @RequestBody ActualizarBeneficiarioRequest request) {
        BeneficiarioResponse response = beneficiarioPort.actualizar(beneficiarioId, request);
        return ResponseEntity.ok(response);
    }
}
