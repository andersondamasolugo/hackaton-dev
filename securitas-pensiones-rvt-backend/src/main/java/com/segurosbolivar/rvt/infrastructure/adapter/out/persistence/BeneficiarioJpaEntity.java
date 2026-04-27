package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Beneficiario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidad JPA que mapea la tabla BENEFICIARIO real de Oracle.
 * PK compuesta: NIT_NEGOCIO + NUMERO_INTERNO + SECUENCIA_BENEFICIARIO.
 */
@Entity
@Table(name = "BENEFICIARIO")
@IdClass(BeneficiarioJpaEntity.BeneficiarioId.class)
public class BeneficiarioJpaEntity {

    @Id
    @Column(name = "NIT_NEGOCIO")
    private Long nitNegocio;

    @Id
    @Column(name = "NUMERO_INTERNO")
    private Integer numeroInterno;

    @Id
    @Column(name = "SECUENCIA_BENEFICIARIO")
    private Integer secuenciaBeneficiario;

    @Column(name = "TIPO_DOCUMENTO", length = 10)
    private String tipoDocumento;

    @Column(name = "IDENTIFICACION_BENEFICIARIO")
    private Long identificacionBeneficiario;

    @Column(name = "NOMBRE_COMUN", length = 50)
    private String nombreComun;

    @Column(name = "PARENTESCO", length = 3)
    private String parentesco;

    @Column(name = "PORCENTAJE_BENEFICIARIO", precision = 16, scale = 4)
    private BigDecimal porcentajeBeneficiario;

    @Column(name = "ESTADO_BENEFICIARIO", length = 10)
    private String estadoBeneficiario;

    @Column(name = "SENAL_ACTIVO", length = 1)
    private String senalActivo;

    @Column(name = "SEXO", length = 1)
    private String sexo;

    public BeneficiarioJpaEntity() {
    }

    /**
     * Convierte esta entidad JPA Oracle a entidad de dominio.
     */
    public Beneficiario toDomain() {
        return new Beneficiario(
                this.secuenciaBeneficiario != null ? this.secuenciaBeneficiario.longValue() : 0L,
                String.valueOf(this.numeroInterno),
                this.nombreComun != null ? this.nombreComun : "Sin nombre",
                this.tipoDocumento != null ? this.tipoDocumento : "CC",
                this.identificacionBeneficiario != null ? String.valueOf(this.identificacionBeneficiario) : "0",
                this.parentesco != null ? this.parentesco : "N/A",
                this.porcentajeBeneficiario != null ? this.porcentajeBeneficiario : BigDecimal.ZERO
        );
    }

    public static BeneficiarioJpaEntity fromDomain(Beneficiario beneficiario) {
        BeneficiarioJpaEntity entity = new BeneficiarioJpaEntity();
        entity.nombreComun = beneficiario.getNombreCompleto();
        entity.tipoDocumento = beneficiario.getTipoIdentificacion();
        entity.parentesco = beneficiario.getParentesco();
        entity.porcentajeBeneficiario = beneficiario.getPorcentajeParticipacion();
        return entity;
    }

    // Getters
    public Long getNitNegocio() { return nitNegocio; }
    public Integer getNumeroInterno() { return numeroInterno; }
    public Integer getSecuenciaBeneficiario() { return secuenciaBeneficiario; }
    public String getTipoDocumento() { return tipoDocumento; }
    public Long getIdentificacionBeneficiario() { return identificacionBeneficiario; }
    public String getNombreComun() { return nombreComun; }
    public String getParentesco() { return parentesco; }
    public BigDecimal getPorcentajeBeneficiario() { return porcentajeBeneficiario; }
    public String getEstadoBeneficiario() { return estadoBeneficiario; }
    public String getSenalActivo() { return senalActivo; }

    /**
     * Clase PK compuesta para la tabla BENEFICIARIO.
     */
    public static class BeneficiarioId implements Serializable {
        private Long nitNegocio;
        private Integer numeroInterno;
        private Integer secuenciaBeneficiario;

        public BeneficiarioId() {}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BeneficiarioId that = (BeneficiarioId) o;
            return Objects.equals(nitNegocio, that.nitNegocio)
                    && Objects.equals(numeroInterno, that.numeroInterno)
                    && Objects.equals(secuenciaBeneficiario, that.secuenciaBeneficiario);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nitNegocio, numeroInterno, secuenciaBeneficiario);
        }
    }
}
