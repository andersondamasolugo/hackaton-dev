import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { BeneficiarioService } from '../beneficiario.service';
import { BeneficiarioResponse } from '../../../shared/models';

/**
 * Componente de lista de beneficiarios.
 * Usa signals para detección de cambios confiable.
 */
@Component({
  selector: 'app-beneficiarios-lista',
  templateUrl: './beneficiarios-lista.component.html',
  styleUrl: './beneficiarios-lista.component.css',
  imports: [FormsModule],
})
export class BeneficiariosListaComponent {
  private readonly beneficiarioService = inject(BeneficiarioService);
  private readonly router = inject(Router);

  numeroPoliza = '';
  beneficiarios = signal<BeneficiarioResponse[]>([]);
  hasSearched = signal(false);
  isLoading = signal(false);
  errorMessage = signal('');

  onSearch(): void {
    const trimmed = this.numeroPoliza.trim();
    if (!trimmed) return;

    this.isLoading.set(true);
    this.beneficiarios.set([]);
    this.hasSearched.set(false);
    this.errorMessage.set('');

    this.beneficiarioService.listarPorPoliza(trimmed).subscribe({
      next: (response) => {
        this.beneficiarios.set(response);
        this.hasSearched.set(true);
        this.isLoading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading.set(false);
        this.hasSearched.set(true);
        if (err.status === 404) {
          this.beneficiarios.set([]);
        } else {
          this.errorMessage.set('Error al consultar beneficiarios. Intente nuevamente.');
        }
      },
    });
  }

  onEdit(beneficiarioId: number): void {
    this.router.navigate(['/beneficiarios/editar', beneficiarioId], {
      queryParams: { poliza: this.numeroPoliza }
    });
  }

  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
