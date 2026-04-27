package com.segurosbolivar.rvt.infrastructure.adapter.out.mock;

import com.segurosbolivar.rvt.domain.port.out.MenuExternalPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuCategory;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuItem;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuTreeResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador mock para el menú externo.
 * Retorna un árbol de categorías estático cuando el endpoint
 * real de Person1 (/menu/tree) no está disponible.
 */
@Component
@Profile("mock")
public class MockMenuAdapter implements MenuExternalPort {

    /**
     * Retorna el árbol de menú estático desde datos locales.
     *
     * @return estructura jerárquica del menú con categorías y sus ítems
     */
    @Override
    public MenuTreeResponse getMenuTree() {
        List<MenuCategory> categories = List.of(
            new MenuCategory(
                "rentas-voluntarias",
                "Rentas Voluntarias",
                "policy",
                List.of(
                    new MenuItem("expedir-rv", "Expedir Rentas Voluntarias", "/poliza/expedir", "add_circle"),
                    new MenuItem("consultar-poliza", "Consultar Póliza", "/poliza/consultar", "search"),
                    new MenuItem("activar-poliza", "Activar Póliza", "/poliza/activar", "check_circle"),
                    new MenuItem("beneficiarios", "Actualización Beneficiarios", "/beneficiarios", "people"),
                    new MenuItem("clientes-rv", "Consulta General Clientes RV", "/clientes", "person_search")
                )
            ),
            new MenuCategory(
                "parametrizacion",
                "Parametrización Ramo RV",
                "settings",
                List.of(
                    new MenuItem("param-ramo", "Parametrización del Ramo", "/parametrizacion", "tune")
                )
            )
        );
        return new MenuTreeResponse(categories);
    }
}
