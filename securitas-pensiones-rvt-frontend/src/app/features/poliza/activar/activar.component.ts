import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { PolizaService } from '../poliza.service';
import { PolizaResponse } from '../../../shared/models';

@Component({
  selector: 'app-activar',
  templateUrl: './activar.component.html',
  styleUrl: './activar.component.css',
  imports: [FormsModule],
})
export class ActivarComponent {
  private readonly polizaService = inject(PolizaService);
  private readonly router = inject(Router);

  numeroPoliza = '';
  poliza = signal<PolizaResponse | null>(null);
  isSearching = signal(false);
  isActivating = signal(false);
  successMessage = signal('');
  notFoundMessage = signal('');
  errorMessage = signal('');

  onSearch(): void {
    const trimmed = this.numeroPoliza.trim();
    if (!trimmed) return;

    this.isSearching.set(true);
    this.poliza.set(null);
    this.successMessage.set('');
    this.notFoundMessage.set('');
    this.errorMessage.set('');

    this.polizaService.consultar(trimmed).subscribe({
      next: (response) => { this.poliza.set(response); this.isSearching.set(false); },
      error: (err: HttpErrorResponse) => {
        this.isSearching.set(false);
        if (err.status === 404) this.notFoundMessage.set('Póliza no encontrada');
        else this.errorMessage.set('Error al consultar la póliza.');
      },
    });
  }

  onActivar(): void {
    const p = this.poliza();
    if (!p) return;

    this.isActivating.set(true);
    this.successMessage.set('');
    this.errorMessage.set('');

    this.polizaService.activar(p.numeroPoliza).subscribe({
      next: (response) => { this.poliza.set(response); this.successMessage.set('Póliza activada exitosamente'); this.isActivating.set(false); },
      error: (err: HttpErrorResponse) => {
        this.isActivating.set(false);
        if (err.status === 409) this.errorMessage.set('La póliza no puede ser activada (no está en estado pendiente)');
        else this.errorMessage.set('Error al activar la póliza.');
      },
    });
  }

  goToMenu(): void { this.router.navigate(['/menu']); }
}
