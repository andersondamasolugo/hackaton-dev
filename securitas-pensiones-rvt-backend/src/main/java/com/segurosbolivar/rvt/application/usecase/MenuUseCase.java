package com.segurosbolivar.rvt.application.usecase;

import com.segurosbolivar.rvt.domain.port.in.MenuPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuCategory;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuItem;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuTreeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso del menú de categorías.
 * Retorna una estructura jerárquica estática del menú de navegación
 * para el demo del hackatón de Rentas Voluntarias.
 */
@Service
public class MenuUseCase implements MenuPort {

    /**
     * Retorna el árbol de categorías del menú con las opciones
     * de Rentas Voluntarias y Parametrización de Ramo.
     *
     * @return estructura jerárquica del menú
     */
    @Override
    public MenuTreeResponse getMenuTree() {
        List<MenuCategory> categories = List.of(
            buildRentasVoluntariasCategory(),
            buildParametrizacionCategory()
        );
        return new MenuTreeResponse(categories);
    }

    /**
     * Construye la categoría "Rentas Voluntarias" con sus ítems de navegación.
     *
     * @return categoría con opciones de pólizas, beneficiarios y clientes
     */
    private MenuCategory buildRentasVoluntariasCategory() {
        List<MenuItem> items = List.of(
            new MenuItem("expedir-rv", "Expedir Rentas Voluntarias", "/poliza/expedir", "add_circle"),
            new MenuItem("consultar-poliza", "Consultar Póliza", "/poliza/consultar", "search"),
            new MenuItem("activar-poliza", "Activar Póliza", "/poliza/activar", "check_circle"),
            new MenuItem("beneficiarios", "Actualización Beneficiarios", "/beneficiarios", "people"),
            new MenuItem("clientes-rv", "Consulta General Clientes RV", "/clientes", "person_search")
        );
        return new MenuCategory("rentas-voluntarias", "Rentas Voluntarias", "policy", items);
    }

    /**
     * Construye la categoría "Parametrización Ramo RV" con su ítem de navegación.
     *
     * @return categoría con opción de parametrización del ramo
     */
    private MenuCategory buildParametrizacionCategory() {
        List<MenuItem> items = List.of(
            new MenuItem("param-ramo", "Parametrización del Ramo", "/parametrizacion", "tune")
        );
        return new MenuCategory("parametrizacion", "Parametrización Ramo RV", "settings", items);
    }
}
