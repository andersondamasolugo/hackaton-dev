package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

/**
 * DTO de response con datos resumidos de un cliente.
 * @param clienteId identificador del cliente
 * @param tipoIdentificacion tipo de identificación
 * @param numeroIdentificacion número de identificación
 * @param nombre nombre del cliente
 * @param estado estado del cliente
 * @param numeroPolizas cantidad de pólizas asociadas
 */
public record ClienteResponse(
    Long clienteId,
    String tipoIdentificacion,
    String numeroIdentificacion,
    String nombre,
    String estado,
    int numeroPolizas
) {}
