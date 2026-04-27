package com.segurosbolivar.rvt.application.usecase;

import com.segurosbolivar.rvt.domain.exception.BeneficiarioNotFoundException;
import com.segurosbolivar.rvt.domain.exception.PorcentajeExcedidoException;
import com.segurosbolivar.rvt.domain.model.Beneficiario;
import com.segurosbolivar.rvt.domain.port.in.BeneficiarioPort;
import com.segurosbolivar.rvt.domain.port.out.BeneficiarioRepository;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ActualizarBeneficiarioRequest;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.BeneficiarioResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Caso de uso de beneficiarios de pólizas de Rentas Voluntarias.
 * Implementa listado por póliza y actualización con validación de porcentaje.
 */
@Service
public class BeneficiarioUseCase implements BeneficiarioPort {

    private final BeneficiarioRepository beneficiarioRepository;

    public BeneficiarioUseCase(BeneficiarioRepository beneficiarioRepository) {
        this.beneficiarioRepository = beneficiarioRepository;
    }

    /**
     * Lista todos los beneficiarios asociados a una póliza.
     *
     * @param numeroPoliza número de la póliza
     * @return lista de beneficiarios; vacía si no hay ninguno
     */
    @Override
    public List<BeneficiarioResponse> listarPorPoliza(String numeroPoliza) {
        return beneficiarioRepository.findByNumeroPoliza(numeroPoliza).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Actualiza los datos de un beneficiario existente.
     * Valida que la suma de porcentajes de todos los beneficiarios de la misma póliza
     * (incluyendo el nuevo porcentaje) no supere 100%.
     *
     * @param beneficiarioId identificador del beneficiario
     * @param request datos actualizados del beneficiario
     * @return datos del beneficiario actualizado
     * @throws BeneficiarioNotFoundException si no existe el beneficiario
     * @throws PorcentajeExcedidoException si la suma de porcentajes supera 100%
     */
    @Override
    public BeneficiarioResponse actualizar(Long beneficiarioId, ActualizarBeneficiarioRequest request) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiarioId)
                .orElseThrow(() -> new BeneficiarioNotFoundException(beneficiarioId));

        List<Beneficiario> hermanos = beneficiarioRepository.findByNumeroPoliza(beneficiario.getNumeroPoliza());

        BigDecimal sumaOtros = hermanos.stream()
                .filter(b -> !b.getId().equals(beneficiarioId))
                .map(Beneficiario::getPorcentajeParticipacion)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalNuevo = sumaOtros.add(request.porcentajeParticipacion());
        if (totalNuevo.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new PorcentajeExcedidoException();
        }

        beneficiario.setNombreCompleto(request.nombreCompleto());
        beneficiario.setTipoIdentificacion(request.tipoIdentificacion());
        beneficiario.setNumeroIdentificacion(request.numeroIdentificacion());
        beneficiario.setParentesco(request.parentesco());
        beneficiario.setPorcentajeParticipacion(request.porcentajeParticipacion());

        Beneficiario saved = beneficiarioRepository.save(beneficiario);
        return toResponse(saved);
    }

    /**
     * Mapea una entidad de dominio Beneficiario a su DTO de respuesta.
     *
     * @param beneficiario entidad de dominio
     * @return DTO de respuesta con los datos del beneficiario
     */
    private BeneficiarioResponse toResponse(Beneficiario beneficiario) {
        return new BeneficiarioResponse(
                beneficiario.getId(),
                beneficiario.getNumeroPoliza(),
                beneficiario.getNombreCompleto(),
                beneficiario.getTipoIdentificacion(),
                beneficiario.getNumeroIdentificacion(),
                beneficiario.getParentesco(),
                beneficiario.getPorcentajeParticipacion()
        );
    }
}
