import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ParametroService } from '../parametro.service';
import { ParametroResponse } from '../../../shared/models';

/**
 * Componente de lista de parámetros del ramo.
 * Muestra una tabla con código, descripción, valor actual, estado y rango.
 * Permite navegar a la edición de cada parámetro.
 */
@Component({
  selector: 'app-parametrizacion-lista',
  templateUrl: './parametrizacion-lista.component.html',
  styleUrl: './parametrizacion-lista.component.css',
  imports: [CommonModule],
})
export class ParametrizacionListaComponent implements OnInit {
  private readonly parametroService = inject(ParametroService);
  private readonly router = inject(Router);

  /** Lista de parámetros cargados. */
  parametros: ParametroResponse[] = [];

  /** Indica si hay una petición en curso. */
  isLoading = false;

  /** Mensaje de error genérico. */
  errorMessage = '';

  ngOnInit(): void {
    this.loadParametros();
  }

  /** Carga todos los parámetros del ramo desde el backend. */
  private loadParametros(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.parametroService.listar().subscribe({
      next: (response) => {
        this.parametros = response;
        this.isLoading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading = false;
        this.errorMessage =
          'Error al consultar parámetros. Intente nuevamente.';
      },
    });
  }

  /**
   * Navega al formulario de edición de un parámetro.
   * @param parametroId identificador del parámetro a editar
   */
  onEdit(parametroId: number): void {
    this.router.navigate(['/parametrizacion/editar', parametroId]);
  }

  /** Navega de vuelta al menú principal. */
  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
