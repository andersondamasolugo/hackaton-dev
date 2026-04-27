import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { BeneficiarioService } from '../beneficiario.service';
import { BeneficiarioResponse } from '../../../shared/models';

/**
 * Componente de lista de beneficiarios.
 * Permite buscar beneficiarios por número de póliza y muestra
 * una tabla con nombre, identificación, parentesco y porcentaje.
 */
@Component({
  selector: 'app-beneficiarios-lista',
  templateUrl: './beneficiarios-lista.component.html',
  styleUrl: './beneficiarios-lista.component.css',
  imports: [FormsModule, CommonModule],
})
export class BeneficiariosListaComponent {
  private readonly beneficiarioService = inject(BeneficiarioService);
  private readonly router = inject(Router);

  /** Número de póliza ingresado por el usuario. */
  numeroPoliza = '';

  /** Lista de beneficiarios encontrados. */
  beneficiarios: BeneficiarioResponse[] = [];

  /** Indica si ya se realizó una búsqueda. */
  hasSearched = false;

  /** Indica si hay una petición en curso. */
  isLoading = false;

  /** Mensaje de error genérico. */
  errorMessage = '';

  /** Busca beneficiarios por el número de póliza ingresado. */
  onSearch(): void {
    const trimmed = this.numeroPoliza.trim();
    if (!trimmed) {
      return;
    }

    this.isLoading = true;
    this.beneficiarios = [];
    this.hasSearched = false;
    this.errorMessage = '';

    this.beneficiarioService.listarPorPoliza(trimmed).subscribe({
      next: (response) => {
        this.beneficiarios = response;
        this.hasSearched = true;
        this.isLoading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading = false;
        this.hasSearched = true;
        if (err.status === 404) {
          this.beneficiarios = [];
        } else {
          this.errorMessage =
            'Error al consultar beneficiarios. Intente nuevamente.';
        }
      },
    });
  }

  /**
   * Navega al formulario de edición de un beneficiario.
   * @param beneficiarioId identificador del beneficiario a editar
   */
  onEdit(beneficiarioId: number): void {
    this.router.navigate(['/beneficiarios/editar', beneficiarioId]);
  }

  /** Navega de vuelta al menú principal. */
  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
