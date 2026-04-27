package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.EstadoPoliza;
import com.segurosbolivar.rvt.domain.model.Poliza;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA que mapea la tabla POLIZA en la base de datos.
 * Provee métodos estáticos para convertir entre dominio y persistencia.
 */
@Entity
@Table(name = "POLIZA")
public class JpaPolizaEntity {

    @Id
    @Column(name = "numero_poliza", nullable = false, length = 20)
    private String numeroPoliza;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoPoliza estado;

    @Column(name = "tipo_identificacion", nullable = false, length = 10)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = false, length = 30)
    private String numeroIdentificacion;

    @Column(name = "nombre_tomador", nullable = false, length = 200)
    private String nombreTomador;

    @Column(name = "fecha_inicio_vigencia", nullable = false)
    private LocalDate fechaInicioVigencia;

    @Column(name = "monto_renta", nullable = false, precision = 18, scale = 2)
    private BigDecimal montoRenta;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    public JpaPolizaEntity() {
    }

    /**
     * Convierte una entidad de dominio Poliza a entidad JPA.
     *
     * @param poliza entidad de dominio
     * @return entidad JPA lista para persistir
     */
    public static JpaPolizaEntity fromDomain(Poliza poliza) {
        JpaPolizaEntity entity = new JpaPolizaEntity();
        entity.numeroPoliza = poliza.getNumeroPoliza();
        entity.estado = poliza.getEstado();
        entity.tipoIdentificacion = poliza.getTipoIdentificacion();
        entity.numeroIdentificacion = poliza.getNumeroIdentificacion();
        entity.nombreTomador = poliza.getNombreTomador();
        entity.fechaInicioVigencia = poliza.getFechaInicioVigencia();
        entity.montoRenta = poliza.getMontoRenta();
        entity.fechaCreacion = poliza.getFechaCreacion();
        return entity;
    }

    /**
     * Convierte esta entidad JPA a entidad de dominio Poliza.
     *
     * @return entidad de dominio con los datos de persistencia
     */
    public Poliza toDomain() {
        return new Poliza(
                this.numeroPoliza,
                this.estado,
                this.tipoIdentificacion,
                this.numeroIdentificacion,
                this.nombreTomador,
                this.fechaInicioVigencia,
                this.montoRenta,
                this.fechaCreacion
        );
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
