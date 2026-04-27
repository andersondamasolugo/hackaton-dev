package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Parametro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que mapea la tabla PARAMETRO_RAMO en la base de datos.
 * Provee métodos estáticos para convertir entre dominio y persistencia.
 */
@Entity
@Table(name = "PARAMETRO_RAMO")
public class ParametroJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parametro_id")
    private Long id;

    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "valor", nullable = false, length = 100)
    private String valor;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "rango_minimo", length = 50)
    private String rangoMinimo;

    @Column(name = "rango_maximo", length = 50)
    private String rangoMaximo;

    public ParametroJpaEntity() {
    }

    /**
     * Convierte una entidad de dominio Parametro a entidad JPA.
     *
     * @param parametro entidad de dominio
     * @return entidad JPA lista para persistir
     */
    public static ParametroJpaEntity fromDomain(Parametro parametro) {
        ParametroJpaEntity entity = new ParametroJpaEntity();
        entity.id = parametro.getId();
        entity.codigo = parametro.getCodigo();
        entity.descripcion = parametro.getDescripcion();
        entity.valor = parametro.getValor();
        entity.estado = parametro.getEstado();
        entity.rangoMinimo = parametro.getRangoMinimo();
        entity.rangoMaximo = parametro.getRangoMaximo();
        return entity;
    }

    /**
     * Convierte esta entidad JPA a entidad de dominio Parametro.
     *
     * @return entidad de dominio con los datos de persistencia
     */
    public Parametro toDomain() {
        return new Parametro(
                this.id,
                this.codigo,
                this.descripcion,
                this.valor,
                this.estado,
                this.rangoMinimo,
                this.rangoMaximo
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRangoMinimo() {
        return rangoMinimo;
    }

    public void setRangoMinimo(String rangoMinimo) {
        this.rangoMinimo = rangoMinimo;
    }

    public String getRangoMaximo() {
        return rangoMaximo;
    }

    public void setRangoMaximo(String rangoMaximo) {
        this.rangoMaximo = rangoMaximo;
    }
}
