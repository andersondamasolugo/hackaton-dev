package com.segurosbolivar.rvt.domain.model;

import java.math.BigDecimal;

/**
 * Entidad de dominio que representa un beneficiario de una póliza de Rentas Voluntarias.
 */
public class Beneficiario {

    private Long id;
    private String numeroPoliza;
    private String nombreCompleto;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String parentesco;
    private BigDecimal porcentajeParticipacion;

    public Beneficiario() {
    }

    public Beneficiario(Long id, String numeroPoliza, String nombreCompleto,
                        String tipoIdentificacion, String numeroIdentificacion,
                        String parentesco, BigDecimal porcentajeParticipacion) {
        this.id = id;
        this.numeroPoliza = numeroPoliza;
        this.nombreCompleto = nombreCompleto;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.parentesco = parentesco;
        this.porcentajeParticipacion = porcentajeParticipacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public BigDecimal getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
    }
}
