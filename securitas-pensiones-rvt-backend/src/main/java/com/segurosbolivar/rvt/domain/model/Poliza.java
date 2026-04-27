package com.segurosbolivar.rvt.domain.model;

import com.segurosbolivar.rvt.domain.exception.PolizaNoActivableException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa una póliza de Rentas Voluntarias.
 * No es una entidad JPA — pertenece a la capa de dominio puro.
 */
public class Poliza {

    private String numeroPoliza;
    private EstadoPoliza estado;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreTomador;
    private LocalDate fechaInicioVigencia;
    private BigDecimal montoRenta;
    private LocalDateTime fechaCreacion;

    public Poliza() {
    }

    public Poliza(String numeroPoliza, EstadoPoliza estado, String tipoIdentificacion,
                  String numeroIdentificacion, String nombreTomador,
                  LocalDate fechaInicioVigencia, BigDecimal montoRenta,
                  LocalDateTime fechaCreacion) {
        this.numeroPoliza = numeroPoliza;
        this.estado = estado;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreTomador = nombreTomador;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.montoRenta = montoRenta;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Activa la póliza cambiando su estado de PENDIENTE a ACTIVA.
     *
     * @throws PolizaNoActivableException si el estado actual no es PENDIENTE
     */
    public void activar() {
        if (this.estado != EstadoPoliza.PENDIENTE) {
            throw new PolizaNoActivableException(this.numeroPoliza);
        }
        this.estado = EstadoPoliza.ACTIVA;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public EstadoPoliza getEstado() {
        return estado;
    }

    public void setEstado(EstadoPoliza estado) {
        this.estado = estado;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreTomador() {
        return nombreTomador;
    }

    public void setNombreTomador(String nombreTomador) {
        this.nombreTomador = nombreTomador;
    }

    public LocalDate getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(LocalDate fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public BigDecimal getMontoRenta() {
        return montoRenta;
    }

    public void setMontoRenta(BigDecimal montoRenta) {
        this.montoRenta = montoRenta;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
