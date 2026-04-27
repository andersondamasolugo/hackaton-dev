package com.segurosbolivar.rvt.domain.model;

/**
 * Entidad de dominio que representa un cliente de Rentas Voluntarias.
 */
public class Cliente {

    private Long id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String estado;
    private String direccion;
    private String telefono;
    private String email;

    public Cliente() {
    }

    public Cliente(Long id, String tipoIdentificacion, String numeroIdentificacion,
                   String nombre, String estado, String direccion,
                   String telefono, String email) {
        this.id = id;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.estado = estado;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
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
