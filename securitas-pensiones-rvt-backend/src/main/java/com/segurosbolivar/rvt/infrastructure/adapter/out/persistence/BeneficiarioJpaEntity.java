package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Beneficiario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Entidad JPA que mapea la tabla BENEFICIARIO en la base de datos.
 * Provee métodos estáticos para convertir entre dominio y persistencia.
 */
@Entity
@Table(name = "BENEFICIARIO")
public class BeneficiarioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beneficiario_id")
    private Long id;

    @Column(name = "numero_poliza", nullable = false, length = 20)
    private String numeroPoliza;

    @Column(name = "nombre_completo", nullable = false, length = 200)
    private String nombreCompleto;

    @Column(name = "tipo_identificacion", nullable = false, length = 10)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = false, length = 30)
    private String numeroIdentificacion;

    @Column(name = "parentesco", nullable = false, length = 50)
    private String parentesco;

    @Column(name = "porcentaje_participacion", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentajeParticipacion;

    public BeneficiarioJpaEntity() {
    }

    /**
     * Convierte una entidad de dominio Beneficiario a entidad JPA.
     *
     * @param beneficiario entidad de dominio
     * @return entidad JPA lista para persistir
     */
    public static BeneficiarioJpaEntity fromDomain(Beneficiario beneficiario) {
        BeneficiarioJpaEntity entity = new BeneficiarioJpaEntity();
        entity.id = beneficiario.getId();
        entity.numeroPoliza = beneficiario.getNumeroPoliza();
        entity.nombreCompleto = beneficiario.getNombreCompleto();
        entity.tipoIdentificacion = beneficiario.getTipoIdentificacion();
        entity.numeroIdentificacion = beneficiario.getNumeroIdentificacion();
        entity.parentesco = beneficiario.getParentesco();
        entity.porcentajeParticipacion = beneficiario.getPorcentajeParticipacion();
        return entity;
    }

    /**
     * Convierte esta entidad JPA a entidad de dominio Beneficiario.
     *
     * @return entidad de dominio con los datos de persistencia
     */
    public Beneficiario toDomain() {
        return new Beneficiario(
                this.id,
                this.numeroPoliza,
                this.nombreCompleto,
                this.tipoIdentificacion,
                this.numeroIdentificacion,
                this.parentesco,
                this.porcentajeParticipacion
        );
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
