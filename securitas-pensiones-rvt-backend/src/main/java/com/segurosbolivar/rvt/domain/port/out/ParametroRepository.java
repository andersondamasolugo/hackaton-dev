package com.segurosbolivar.rvt.domain.port.out;

import com.segurosbolivar.rvt.domain.model.Parametro;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de parámetros del ramo.
 */
public interface ParametroRepository {

    /**
     * Retorna todos los parámetros del ramo.
     *
     * @return lista completa de parámetros
     */
    List<Parametro> findAll();

    /**
     * Busca un parámetro por su identificador.
     *
     * @param id identificador del parámetro
     * @return parámetro encontrado o vacío si no existe
     */
    Optional<Parametro> findById(Long id);

    /**
     * Persiste un parámetro (creación o actualización).
     *
     * @param parametro entidad de dominio a persistir
     * @return parámetro persistido
     */
    Parametro save(Parametro parametro);
}
