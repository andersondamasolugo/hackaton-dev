package com.segurosbolivar.rvt.infrastructure.adapter.out.persistence;

import com.segurosbolivar.rvt.domain.model.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que mapea la tabla CLIENTE en la base de datos.
 * Provee métodos estáticos para convertir entre dominio y persistencia.
 */
@Entity
@Table(name = "CLIENTE")
public class ClienteJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;

    @Column(name = "tipo_identificacion", nullable = false, length = 10)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = false, length = 30)
    private String numeroIdentificacion;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "direccion", length = 300)
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "email", length = 100)
    private String email;

    public ClienteJpaEntity() {
    }

    /**
     * Convierte una entidad de dominio Cliente a entidad JPA.
     *
     * @param cliente entidad de dominio
     * @return entidad JPA lista para persistir
     */
    public static ClienteJpaEntity fromDomain(Cliente cliente) {
        ClienteJpaEntity entity = new ClienteJpaEntity();
        entity.id = cliente.getId();
        entity.tipoIdentificacion = cliente.getTipoIdentificacion();
        entity.numeroIdentificacion = cliente.getNumeroIdentificacion();
        entity.nombre = cliente.getNombre();
        entity.estado = cliente.getEstado();
        entity.direccion = cliente.getDireccion();
        entity.telefono = cliente.getTelefono();
        entity.email = cliente.getEmail();
        return entity;
    }

    /**
     * Convierte esta entidad JPA a entidad de dominio Cliente.
     *
     * @return entidad de dominio con los datos de persistencia
     */
    public Cliente toDomain() {
        return new Cliente(
                this.id,
                this.tipoIdentificacion,
                this.numeroIdentificacion,
                this.nombre,
                this.estado,
                this.direccion,
                this.telefono,
                this.email
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
