package com.segurosbolivar.rvt.application.usecase;

import com.segurosbolivar.rvt.domain.exception.PolizaNotFoundException;
import com.segurosbolivar.rvt.domain.model.EstadoPoliza;
import com.segurosbolivar.rvt.domain.model.Poliza;
import com.segurosbolivar.rvt.domain.port.in.PolizaPort;
import com.segurosbolivar.rvt.domain.port.out.PolizaRepository;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ExpedirPolizaRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.PolizaResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso de pólizas de Rentas Voluntarias.
 * Implementa expedición, consulta y activación de pólizas
 * siguiendo la arquitectura hexagonal (puerto de entrada).
 */
@Service
public class PolizaUseCase implements PolizaPort {

    private final PolizaRepository polizaRepository;

    public PolizaUseCase(PolizaRepository polizaRepository) {
        this.polizaRepository = polizaRepository;
    }

    /**
     * Expide una nueva póliza en estado PENDIENTE.
     * Genera un número de póliza con formato "RVT-" + primeros 8 caracteres de UUID.
     *
     * @param request datos de la póliza a expedir
     * @return datos de la póliza creada
     */
    @Override
    public PolizaResponse expedir(ExpedirPolizaRequest request) {
        String numeroPoliza = "RVT-" + UUID.randomUUID().toString().substring(0, 8);

        Poliza poliza = new Poliza(
                numeroPoliza,
                EstadoPoliza.PENDIENTE,
                request.tipoIdentificacion(),
                request.numeroIdentificacion(),
                request.nombreTomador(),
                request.fechaInicioVigencia(),
                request.montoRenta(),
                LocalDateTime.now()
        );

        Poliza saved = polizaRepository.save(poliza);
        return toResponse(saved);
    }

    /**
     * Consulta los datos de una póliza por su número.
     *
     * @param numeroPoliza número único de la póliza
     * @return datos de la póliza encontrada
     * @throws PolizaNotFoundException si no existe la póliza
     */
    @Override
    public PolizaResponse consultar(String numeroPoliza) {
        Poliza poliza = polizaRepository.findByNumeroPoliza(numeroPoliza)
                .orElseThrow(() -> new PolizaNotFoundException(numeroPoliza));
        return toResponse(poliza);
    }

    /**
     * Activa una póliza cambiando su estado de PENDIENTE a ACTIVA.
     *
     * @param numeroPoliza número único de la póliza a activar
     * @return datos de la póliza activada
     * @throws PolizaNotFoundException si no existe la póliza
     * @throws com.segurosbolivar.rvt.domain.exception.PolizaNoActivableException si no está en estado PENDIENTE
     */
    @Override
    public PolizaResponse activar(String numeroPoliza) {
        Poliza poliza = polizaRepository.findByNumeroPoliza(numeroPoliza)
                .orElseThrow(() -> new PolizaNotFoundException(numeroPoliza));

        poliza.activar();

        Poliza saved = polizaRepository.save(poliza);
        return toResponse(saved);
    }

    /**
     * Mapea una entidad de dominio Poliza a su DTO de respuesta.
     *
     * @param poliza entidad de dominio
     * @return DTO de respuesta con los datos de la póliza
     */
    private PolizaResponse toResponse(Poliza poliza) {
        return new PolizaResponse(
                poliza.getNumeroPoliza(),
                poliza.getEstado().name(),
                poliza.getTipoIdentificacion(),
                poliza.getNumeroIdentificacion(),
                poliza.getNombreTomador(),
                poliza.getFechaInicioVigencia(),
                poliza.getMontoRenta(),
                poliza.getFechaCreacion()
        );
    }
}
