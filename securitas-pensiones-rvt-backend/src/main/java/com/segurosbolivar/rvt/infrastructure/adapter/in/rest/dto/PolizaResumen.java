package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

/**
 * DTO resumido de póliza para incluir en detalle de cliente.
 * @param numeroPoliza número de la póliza
 * @param estado estado actual de la póliza
 * @param montoRenta monto de la renta
 */
public record PolizaResumen(
    String numeroPoliza,
    String estado,
    BigDecimal montoRenta
) {}
