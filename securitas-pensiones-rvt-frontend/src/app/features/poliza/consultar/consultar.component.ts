import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { PolizaService } from '../poliza.service';
import { PolizaResponse } from '../../../shared/models';

/**
 * Componente de consulta de pólizas.
 * Permite buscar una póliza por número y muestra sus datos en tabla.
 */
@Component({
  selector: 'app-consultar',
  templateUrl: './consultar.component.html',
  styleUrl: './consultar.component.css',
  imports: [FormsModule],
})
export class ConsultarComponent {
  private readonly polizaService = inject(PolizaService);
  private readonly router = inject(Router);

  numeroPoliza = '';
  poliza = signal<PolizaResponse | null>(null);
  isLoading = signal(false);
  notFoundMessage = signal('');
  errorMessage = signal('');

  onSearch(): void {
    const trimmed = this.numeroPoliza.trim();
    if (!trimmed) return;

    this.isLoading.set(true);
    this.poliza.set(null);
    this.notFoundMessage.set('');
    this.errorMessage.set('');

    this.polizaService.consultar(trimmed).subscribe({
      next: (response) => {
        this.poliza.set(response);
        this.isLoading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading.set(false);
        if (err.status === 404) {
          this.notFoundMessage.set('Póliza no encontrada');
        } else {
          this.errorMessage.set('Error al consultar la póliza. Intente nuevamente.');
        }
      },
    });
  }

  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
