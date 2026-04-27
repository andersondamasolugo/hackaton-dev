import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { PolizaService } from '../poliza.service';
import { PolizaResponse } from '../../../shared/models';

/**
 * Componente de consulta de pólizas.
 * Permite buscar una póliza por número y muestra sus datos.
 * Muestra "Póliza no encontrada" si no existe.
 */
@Component({
  selector: 'app-consultar',
  templateUrl: './consultar.component.html',
  styleUrl: './consultar.component.css',
  imports: [FormsModule, CommonModule, DecimalPipe],
})
export class ConsultarComponent {
  private readonly polizaService = inject(PolizaService);
  private readonly router = inject(Router);

  /** Número de póliza ingresado por el usuario. */
  numeroPoliza = '';

  /** Póliza encontrada tras la búsqueda. */
  poliza: PolizaResponse | null = null;

  /** Indica si hay una petición en curso. */
  isLoading = false;

  /** Mensaje cuando la póliza no se encuentra. */
  notFoundMessage = '';

  /** Mensaje de error genérico. */
  errorMessage = '';

  /** Busca la póliza por el número ingresado. */
  onSearch(): void {
    const trimmed = this.numeroPoliza.trim();
    if (!trimmed) {
      return;
    }

    this.isLoading = true;
    this.poliza = null;
    this.notFoundMessage = '';
    this.errorMessage = '';

    this.polizaService.consultar(trimmed).subscribe({
      next: (response) => {
        this.poliza = response;
        this.isLoading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading = false;
        if (err.status === 404) {
          this.notFoundMessage = 'Póliza no encontrada';
        } else {
          this.errorMessage = 'Error al consultar la póliza. Intente nuevamente.';
        }
      },
    });
  }

  /** Navega de vuelta al menú principal. */
  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
