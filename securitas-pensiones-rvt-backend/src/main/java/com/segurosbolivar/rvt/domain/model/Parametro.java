package com.segurosbolivar.rvt.domain.model;

import com.segurosbolivar.rvt.domain.exception.ValorFueraDeRangoException;

import java.math.BigDecimal;

/**
 * Entidad de dominio que representa un parámetro del ramo de Rentas Voluntarias.
 */
public class Parametro {

    private Long id;
    private String codigo;
    private String descripcion;
    private String valor;
    private String estado;
    private String rangoMinimo;
    private String rangoMaximo;

    public Parametro() {
    }

    public Parametro(Long id, String codigo, String descripcion, String valor,
                     String estado, String rangoMinimo, String rangoMaximo) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.valor = valor;
        this.estado = estado;
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
    }

    /**
     * Valida que el nuevo valor esté dentro del rango permitido [rangoMinimo, rangoMaximo].
     * Solo aplica validación si ambos límites del rango están definidos.
     *
     * @param nuevoValor el valor a validar
     * @throws ValorFueraDeRangoException si el valor está fuera del rango permitido
     */
    public void validarRango(String nuevoValor) {
        if (rangoMinimo != null && rangoMaximo != null) {
            BigDecimal nuevo = new BigDecimal(nuevoValor);
            if (nuevo.compareTo(new BigDecimal(rangoMinimo)) < 0
                    || nuevo.compareTo(new BigDecimal(rangoMaximo)) > 0) {
                throw new ValorFueraDeRangoException(codigo, rangoMinimo, rangoMaximo);
            }
        }
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
