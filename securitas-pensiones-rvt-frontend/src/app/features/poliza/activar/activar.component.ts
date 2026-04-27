import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { PolizaService } from '../poliza.service';
import { PolizaResponse } from '../../../shared/models';

/**
 * Componente de activación de pólizas.
 * Permite buscar una póliza por número, ver sus datos
 * y activarla si está en estado PENDIENTE.
 */
@Component({
  selector: 'app-activar',
  templateUrl: './activar.component.html',
  styleUrl: './activar.component.css',
  imports: [FormsModule, CommonModule, DecimalPipe],
})
export class ActivarComponent {
  private readonly polizaService = inject(PolizaService);
  private readonly router = inject(Router);

  /** Número de póliza ingresado por el usuario. */
  numeroPoliza = '';

  /** Póliza encontrada tras la búsqueda. */
  poliza: PolizaResponse | null = null;

  /** Indica si hay una búsqueda en curso. */
  isSearching = false;

  /** Indica si hay una activación en curso. */
  isActivating = false;

  /** Mensaje de éxito tras activación. */
  successMessage = '';

  /** Mensaje cuando la póliza no se encuentra. */
  notFoundMessage = '';

  /** Mensaje de error genérico o de conflicto. */
  errorMessage = '';

  /** Busca la póliza por el número ingresado. */
  onSearch(): void {
    const trimmed = this.numeroPoliza.trim();
    if (!trimmed) {
      return;
    }

    this.isSearching = true;
    this.poliza = null;
    this.successMessage = '';
    this.notFoundMessage = '';
    this.errorMessage = '';

    this.polizaService.consultar(trimmed).subscribe({
      next: (response) => {
        this.poliza = response;
        this.isSearching = false;
      },
      error: (err: HttpErrorResponse) => {
        this.isSearching = false;
        if (err.status === 404) {
          this.notFoundMessage = 'Póliza no encontrada';
        } else {
          this.errorMessage = 'Error al consultar la póliza. Intente nuevamente.';
        }
      },
    });
  }

  /** Activa la póliza encontrada. */
  onActivar(): void {
    if (!this.poliza) {
      return;
    }

    this.isActivating = true;
    this.successMessage = '';
    this.errorMessage = '';

    this.polizaService.activar(this.poliza.numeroPoliza).subscribe({
      next: (response) => {
        this.poliza = response;
        this.successMessage = 'Póliza activada exitosamente';
        this.isActivating = false;
      },
      error: (err: HttpErrorResponse) => {
        this.isActivating = false;
        if (err.status === 409) {
          this.errorMessage = 'La póliza no puede ser activada porque no está en estado pendiente';
        } else if (err.status === 404) {
          this.errorMessage = 'Póliza no encontrada';
        } else {
          this.errorMessage = 'Error al activar la póliza. Intente nuevamente.';
        }
      },
    });
  }

  /** Navega de vuelta al menú principal. */
  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
