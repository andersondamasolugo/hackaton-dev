package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import com.segurosbolivar.rvt.domain.port.in.ParametroPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ActualizarParametroRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ParametroResponse;
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
 * Controller REST para operaciones de parametrización del ramo RVT.
 * Expone endpoints de listado y actualización de parámetros.
 */
@RestController
@RequestMapping("/parametros")
@Tag(name = "Parametrización", description = "Endpoints de gestión de parámetros del ramo de Rentas Voluntarias")
public class ParametroController {

    private final ParametroPort parametroPort;

    public ParametroController(ParametroPort parametroPort) {
        this.parametroPort = parametroPort;
    }

    /**
     * Lista todos los parámetros del ramo de Rentas Voluntarias.
     *
     * @return lista de parámetros configurados
     */
    @GetMapping
    @Operation(summary = "Listar parámetros del ramo",
               description = "Retorna todos los parámetros configurados para el ramo de Rentas Voluntarias")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de parámetros obtenida exitosamente")
    })
    public ResponseEntity<List<ParametroResponse>> listar() {
        List<ParametroResponse> parametros = parametroPort.listar();
        return ResponseEntity.ok(parametros);
    }

    /**
     * Actualiza el valor de un parámetro validando que esté dentro del rango permitido.
     *
     * @param parametroId identificador del parámetro
     * @param request datos de actualización (valor, descripción)
     * @return datos del parámetro actualizado
     */
    @PutMapping("/{parametroId}")
    @Operation(summary = "Actualizar parámetro",
               description = "Actualiza el valor de un parámetro del ramo. Valida que el nuevo valor esté dentro del rango permitido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Parámetro actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Valor fuera del rango permitido o datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Parámetro no encontrado")
    })
    public ResponseEntity<ParametroResponse> actualizar(
            @PathVariable Long parametroId,
            @Valid @RequestBody ActualizarParametroRequest request) {
        ParametroResponse response = parametroPort.actualizar(parametroId, request);
        return ResponseEntity.ok(response);
    }
}
