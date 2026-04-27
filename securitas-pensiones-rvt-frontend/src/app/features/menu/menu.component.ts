import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { MenuService } from './menu.service';
import { MenuCategory, MenuItem } from '../../shared/models';
import { AuthService } from '../../core/auth/auth.service';

/**
 * Componente del menú de navegación.
 * Muestra un árbol de categorías con secciones expandibles/colapsables.
 * Cada ítem navega a su ruta correspondiente al hacer clic.
 */
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
  imports: [CommonModule],
})
export class MenuComponent implements OnInit {
  private readonly menuService = inject(MenuService);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  /** Lista de categorías del menú cargadas desde el backend. */
  categories: MenuCategory[] = [];

  /** Indica si el menú está cargando. */
  isLoading = true;

  /** Mensaje de error si falla la carga. */
  errorMessage = '';

  /** Set de IDs de categorías expandidas. */
  expandedCategories = new Set<string>();

  ngOnInit(): void {
    this.menuService.getMenuTree().subscribe({
      next: (response) => {
        this.categories = response.categories;
        this.isLoading = false;
        if (this.categories.length > 0) {
          this.expandedCategories.add(this.categories[0].id);
        }
      },
      error: () => {
        this.errorMessage = 'Error al cargar el menú';
        this.isLoading = false;
      },
    });
  }

  /**
   * Alterna la expansión/colapso de una categoría.
   * @param categoryId identificador de la categoría
   */
  toggleCategory(categoryId: string): void {
    if (this.expandedCategories.has(categoryId)) {
      this.expandedCategories.delete(categoryId);
    } else {
      this.expandedCategories.add(categoryId);
    }
  }

  /**
   * Indica si una categoría está expandida.
   * @param categoryId identificador de la categoría
   * @returns true si la categoría está expandida
   */
  isCategoryExpanded(categoryId: string): boolean {
    return this.expandedCategories.has(categoryId);
  }

  /**
   * Navega a la ruta del ítem seleccionado.
   * @param item ítem del menú con la ruta destino
   */
  navigateTo(item: MenuItem): void {
    this.router.navigate([item.route]);
  }

  /** Cierra la sesión y redirige al login. */
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
