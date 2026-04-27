package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.EstadoPoliza;
import com.segurosbolivar.rvt.domain.model.Poliza;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entidad JPA que mapea la tabla POLIZA real de Oracle.
 * Columnas mapeadas a la estructura existente del sistema legado.
 */
@Entity
@Table(name = "POLIZA")
public class JpaPolizaEntity {

    @Id
    @Column(name = "NUMERO_INTERNO")
    private Integer numeroInterno;

    @Column(name = "NUMERO_POLIZA", length = 20)
    private String numeroPoliza;

    @Column(name = "ESTADO_POLIZA", length = 3)
    private String estadoPoliza;

    @Column(name = "IDENTIFICACION_AFILIADO")
    private Long identificacionAfiliado;

    @Column(name = "VALOR_PRIMA_UNICA", precision = 18, scale = 2)
    private BigDecimal valorPrimaUnica;

    @Column(name = "VALOR_BASICO_RENTA", precision = 18, scale = 2)
    private BigDecimal valorBasicoRenta;

    @Column(name = "VALOR_MESADA", precision = 18, scale = 2)
    private BigDecimal valorMesada;

    @Column(name = "FECHA_EXPEDICION")
    private Date fechaExpedicion;

    @Column(name = "FECHA_VIGENCIA")
    private Date fechaVigencia;

    @Column(name = "MODALIDAD_PENSION", length = 3)
    private String modalidadPension;

    @Column(name = "CLASE_POLIZA", length = 10)
    private String clasePoliza;

    @Column(name = "TIPO_RENTA", length = 3)
    private String tipoRenta;

    @Column(name = "CAUSA_RENTA", length = 3)
    private String causaRenta;

    @Column(name = "NUMERO_BENEFICIARIOS")
    private Integer numeroBeneficiarios;

    @Column(name = "OBSERVACIONES", length = 250)
    private String observaciones;

    @Column(name = "NIT_NEGOCIO")
    private Long nitNegocio;

    @Column(name = "FECHA_ULT_MODIFICACION")
    private Date fechaUltModificacion;

    @Column(name = "USUARIO", length = 50)
    private String usuario;

    public JpaPolizaEntity() {
    }

    /**
     * Convierte esta entidad JPA Oracle a entidad de dominio Poliza.
     * Mapea los campos de Oracle a nuestro modelo simplificado.
     */
    public Poliza toDomain() {
        EstadoPoliza estado = "VIG".equals(this.estadoPoliza) ? EstadoPoliza.ACTIVA : EstadoPoliza.PENDIENTE;

        LocalDate fechaInicio = this.fechaVigencia != null
                ? new java.sql.Date(this.fechaVigencia.getTime()).toLocalDate()
                : LocalDate.now();

        LocalDateTime fechaCreacion = this.fechaExpedicion != null
                ? new java.sql.Timestamp(this.fechaExpedicion.getTime()).toLocalDateTime()
                : LocalDateTime.now();

        return new Poliza(
                this.numeroPoliza != null ? this.numeroPoliza : String.valueOf(this.numeroInterno),
                estado,
                "CC",
                this.identificacionAfiliado != null ? String.valueOf(this.identificacionAfiliado) : "0",
                "Póliza " + this.numeroInterno,
                fechaInicio,
                this.valorBasicoRenta != null ? this.valorBasicoRenta : BigDecimal.ZERO,
                fechaCreacion
        );
    }

    /**
     * Convierte una entidad de dominio a JPA (para inserts — no aplica en Oracle legado).
     */
    public static JpaPolizaEntity fromDomain(Poliza poliza) {
        JpaPolizaEntity entity = new JpaPolizaEntity();
        entity.numeroPoliza = poliza.getNumeroPoliza();
        entity.estadoPoliza = poliza.getEstado() == EstadoPoliza.ACTIVA ? "VIG" : "CTZ";
        entity.valorBasicoRenta = poliza.getMontoRenta();
        return entity;
    }

    // Getters
    public Integer getNumeroInterno() { return numeroInterno; }
    public String getNumeroPoliza() { return numeroPoliza; }
    public String getEstadoPoliza() { return estadoPoliza; }
    public Long getIdentificacionAfiliado() { return identificacionAfiliado; }
    public BigDecimal getValorPrimaUnica() { return valorPrimaUnica; }
    public BigDecimal getValorBasicoRenta() { return valorBasicoRenta; }
    public BigDecimal getValorMesada() { return valorMesada; }
    public Date getFechaExpedicion() { return fechaExpedicion; }
    public Date getFechaVigencia() { return fechaVigencia; }
    public String getModalidadPension() { return modalidadPension; }
    public String getClasePoliza() { return clasePoliza; }
    public String getTipoRenta() { return tipoRenta; }
    public String getCausaRenta() { return causaRenta; }
    public Integer getNumeroBeneficiarios() { return numeroBeneficiarios; }
    public String getObservaciones() { return observaciones; }
}
