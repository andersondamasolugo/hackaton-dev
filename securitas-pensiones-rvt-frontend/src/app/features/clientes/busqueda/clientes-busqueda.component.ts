import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ClienteService } from '../cliente.service';
import { ClienteResponse } from '../../../shared/models';

/**
 * Componente de búsqueda de clientes.
 * Formulario con filtros de tipo identificación, número y nombre.
 * Valida que al menos un filtro esté diligenciado antes de buscar.
 * Muestra resultados en tabla con navegación al detalle.
 */
@Component({
  selector: 'app-clientes-busqueda',
  templateUrl: './clientes-busqueda.component.html',
  styleUrl: './clientes-busqueda.component.css',
  imports: [FormsModule, CommonModule],
})
export class ClientesBusquedaComponent {
  private readonly clienteService = inject(ClienteService);
  private readonly router = inject(Router);

  /** Tipo de identificación seleccionado. */
  tipoIdentificacion = '';

  /** Número de identificación ingresado. */
  numeroIdentificacion = '';

  /** Nombre del cliente ingresado. */
  nombre = '';

  /** Lista de clientes encontrados. */
  clientes: ClienteResponse[] = [];

  /** Indica si ya se realizó una búsqueda. */
  hasSearched = false;

  /** Indica si hay una petición en curso. */
  isLoading = false;

  /** Mensaje de error genérico. */
  errorMessage = '';

  /** Mensaje de validación cuando no hay filtros. */
  validationMessage = '';

  /** Busca clientes con los filtros ingresados. */
  onSearch(): void {
    this.validationMessage = '';
    this.errorMessage = '';

    if (!this.hasAnyFilter()) {
      this.validationMessage =
        'Ingrese al menos un criterio de búsqueda';
      return;
    }

    this.isLoading = true;
    this.clientes = [];
    this.hasSearched = false;

    this.clienteService
      .buscar(this.tipoIdentificacion, this.numeroIdentificacion, this.nombre)
      .subscribe({
        next: (response) => {
          this.clientes = response;
          this.hasSearched = true;
          this.isLoading = false;
        },
        error: (err: HttpErrorResponse) => {
          this.isLoading = false;
          this.hasSearched = true;
          if (err.status === 400) {
            this.validationMessage =
              err.error?.message ?? 'Ingrese al menos un criterio de búsqueda';
          } else {
            this.errorMessage =
              'Error al consultar clientes. Intente nuevamente.';
          }
        },
      });
  }

  /**
   * Navega al detalle de un cliente.
   * @param clienteId identificador del cliente
   */
  onSelectCliente(clienteId: number): void {
    this.router.navigate(['/clientes', clienteId]);
  }

  /** Navega de vuelta al menú principal. */
  goToMenu(): void {
    this.router.navigate(['/menu']);
  }

  /** Verifica si al menos un filtro tiene valor. */
  private hasAnyFilter(): boolean {
    return (
      !!this.tipoIdentificacion.trim() ||
      !!this.numeroIdentificacion.trim() ||
      !!this.nombre.trim()
    );
  }
}
