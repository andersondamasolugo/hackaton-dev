package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import com.segurosbolivar.rvt.domain.port.in.MenuPort;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.MenuTreeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para el árbol de categorías del menú.
 * Expone el endpoint GET /menu/tree.
 */
@RestController
@RequestMapping("/menu")
@Tag(name = "Menú", description = "Endpoints del árbol de categorías de navegación")
public class MenuController {

    private final MenuPort menuPort;

    public MenuController(MenuPort menuPort) {
        this.menuPort = menuPort;
    }

    /**
     * Retorna el árbol jerárquico de categorías del menú.
     *
     * @return estructura de categorías con sus ítems de navegación
     */
    @GetMapping("/tree")
    @Operation(summary = "Obtener árbol de menú", description = "Retorna la estructura jerárquica de categorías del menú de navegación")
    public ResponseEntity<MenuTreeResponse> getMenuTree() {
        MenuTreeResponse response = menuPort.getMenuTree();
        return ResponseEntity.ok(response);
    }
}
