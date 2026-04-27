package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.segurosbolivar.rvt.domain.model.Beneficiario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Entidad JPA que mapea la tabla BENEFICIARIO real de Oracle.
 * Incluye todos los campos visibles en el form VOLTRABE.
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

    @Column(name = "APELLIDO1", length = 20)
    private String apellido1;

    @Column(name = "APELLIDO2", length = 20)
    private String apellido2;

    @Column(name = "NOMBRE_COMUN", length = 50)
    private String nombreComun;

    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @Column(name = "SEXO", length = 1)
    private String sexo;

    @Column(name = "PARENTESCO", length = 3)
    private String parentesco;

    @Column(name = "PORCENTAJE_BENEFICIARIO", precision = 16, scale = 4)
    private BigDecimal porcentajeBeneficiario;

    @Column(name = "ESTADO_BENEFICIARIO", length = 10)
    private String estadoBeneficiario;

    @Column(name = "SENAL_ACTIVO", length = 1)
    private String senalActivo;

    @Column(name = "SENAL_NOMINA", length = 1)
    private String senalNomina;

    @Column(name = "PORCENTAJE_NOMINA", precision = 16, scale = 4)
    private BigDecimal porcentajeNomina;

    public BeneficiarioJpaEntity() {
    }

    /**
     * Convierte a dominio con nombre completo construido desde apellidos + nombre.
     */
    public Beneficiario toDomain() {
        String nombreCompleto = buildNombreCompleto();

        return new Beneficiario(
                this.secuenciaBeneficiario != null ? this.secuenciaBeneficiario.longValue() : 0L,
                String.valueOf(this.numeroInterno),
                nombreCompleto,
                this.tipoDocumento != null ? this.tipoDocumento : "1",
                this.identificacionBeneficiario != null ? String.valueOf(this.identificacionBeneficiario) : "0",
                this.parentesco != null ? this.parentesco : "N/A",
                this.porcentajeBeneficiario != null ? this.porcentajeBeneficiario : BigDecimal.ZERO
        );
    }

    private String buildNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (apellido1 != null && !apellido1.isBlank()) sb.append(apellido1.trim());
        if (apellido2 != null && !apellido2.isBlank()) sb.append(" ").append(apellido2.trim());
        if (nombreComun != null && !nombreComun.isBlank()) sb.append(" ").append(nombreComun.trim());
        return sb.toString().trim();
    }

    public static BeneficiarioJpaEntity fromDomain(Beneficiario b) {
        BeneficiarioJpaEntity e = new BeneficiarioJpaEntity();
        e.nombreComun = b.getNombreCompleto();
        e.tipoDocumento = b.getTipoIdentificacion();
        e.parentesco = b.getParentesco();
        e.porcentajeBeneficiario = b.getPorcentajeParticipacion();
        return e;
    }

    // Getters
    public Long getNitNegocio() { return nitNegocio; }
    public Integer getNumeroInterno() { return numeroInterno; }
    public Integer getSecuenciaBeneficiario() { return secuenciaBeneficiario; }
    public String getTipoDocumento() { return tipoDocumento; }
    public Long getIdentificacionBeneficiario() { return identificacionBeneficiario; }
    public String getApellido1() { return apellido1; }
    public String getApellido2() { return apellido2; }
    public String getNombreComun() { return nombreComun; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public String getSexo() { return sexo; }
    public String getParentesco() { return parentesco; }
    public BigDecimal getPorcentajeBeneficiario() { return porcentajeBeneficiario; }
    public String getEstadoBeneficiario() { return estadoBeneficiario; }
    public String getSenalActivo() { return senalActivo; }

    public static class BeneficiarioId implements Serializable {
        private Long nitNegocio;
        private Integer numeroInterno;
        private Integer secuenciaBeneficiario;
        public BeneficiarioId() {}
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BeneficiarioId that = (BeneficiarioId) o;
            return Objects.equals(nitNegocio, that.nitNegocio) && Objects.equals(numeroInterno, that.numeroInterno) && Objects.equals(secuenciaBeneficiario, that.secuenciaBeneficiario);
        }
        @Override public int hashCode() { return Objects.hash(nitNegocio, numeroInterno, secuenciaBeneficiario); }
    }
}
