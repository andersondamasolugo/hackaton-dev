package com.segurosbolivar.rvt.application.usecase;

import com.segurosbolivar.rvt.domain.exception.ParametroNotFoundException;
import com.segurosbolivar.rvt.domain.model.Parametro;
import com.segurosbolivar.rvt.domain.port.in.ParametroPort;
import com.segurosbolivar.rvt.domain.port.out.ParametroRepository;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ActualizarParametroRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ParametroResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso de parametrización del ramo de Rentas Voluntarias.
 * Implementa listado y actualización de parámetros con validación de rango.
 */
@Service
public class ParametroUseCase implements ParametroPort {

    private final ParametroRepository parametroRepository;

    public ParametroUseCase(ParametroRepository parametroRepository) {
        this.parametroRepository = parametroRepository;
    }

    /**
     * Lista todos los parámetros del ramo de Rentas Voluntarias.
     *
     * @return lista de parámetros configurados como DTOs de respuesta
     */
    @Override
    public List<ParametroResponse> listar() {
        return parametroRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Actualiza el valor de un parámetro validando que esté dentro del rango permitido.
     * Busca el parámetro por ID, valida el rango con la lógica de dominio,
     * actualiza valor y descripción, y persiste los cambios.
     *
     * @param parametroId identificador del parámetro
     * @param request datos de actualización (valor, descripción)
     * @return datos del parámetro actualizado
     * @throws ParametroNotFoundException si no existe el parámetro
     * @throws com.segurosbolivar.rvt.domain.exception.ValorFueraDeRangoException si el valor está fuera del rango
     */
    @Override
    public ParametroResponse actualizar(Long parametroId, ActualizarParametroRequest request) {
        Parametro parametro = parametroRepository.findById(parametroId)
                .orElseThrow(() -> new ParametroNotFoundException(parametroId));

        parametro.validarRango(request.valor());

        parametro.setValor(request.valor());
        if (request.descripcion() != null) {
            parametro.setDescripcion(request.descripcion());
        }

        Parametro saved = parametroRepository.save(parametro);
        return toResponse(saved);
    }

    /**
     * Mapea una entidad de dominio Parametro a su DTO de respuesta.
     *
     * @param parametro entidad de dominio
     * @return DTO de respuesta con datos del parámetro
     */
    private ParametroResponse toResponse(Parametro parametro) {
        return new ParametroResponse(
                parametro.getId(),
                parametro.getCodigo(),
                parametro.getDescripcion(),
                parametro.getValor(),
                parametro.getEstado(),
                parametro.getRangoMinimo(),
                parametro.getRangoMaximo()
        );
    }
}
