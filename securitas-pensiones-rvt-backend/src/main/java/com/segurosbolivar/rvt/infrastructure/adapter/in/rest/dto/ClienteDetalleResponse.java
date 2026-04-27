package com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto;

import java.util.List;

/**
 * DTO de response con detalle consolidado de un cliente.
 * @param clienteId identificador del cliente
 * @param tipoIdentificacion tipo de identificación
 * @param numeroIdentificacion número de identificación
 * @param nombre nombre del cliente
 * @param estado estado del cliente
 * @param direccion dirección del cliente
 * @param telefono teléfono de contacto
 * @param email correo electrónico
 * @param polizas lista de pólizas asociadas al cliente
 */
public record ClienteDetalleResponse(
    Long clienteId,
    String tipoIdentificacion,
    String numeroIdentificacion,
    String nombre,
    String estado,
    String direccion,
    String telefono,
    String email,
    List<PolizaResumen> polizas
) {}
