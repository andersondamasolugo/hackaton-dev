import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { PolizaService } from '../poliza.service';
import { PolizaResponse } from '../../../shared/models';

@Component({
  selector: 'app-expedir',
  templateUrl: './expedir.component.html',
  styleUrl: './expedir.component.css',
  imports: [ReactiveFormsModule],
})
export class ExpedirComponent {
  private readonly fb = inject(FormBuilder);
  private readonly polizaService = inject(PolizaService);
  private readonly router = inject(Router);

  expedirForm = this.fb.nonNullable.group({
    tipoIdentificacion: ['', Validators.required],
    numeroIdentificacion: ['', Validators.required],
    nombreTomador: ['', Validators.required],
    fechaInicioVigencia: ['', Validators.required],
    montoRenta: [0, [Validators.required, Validators.min(1)]],
  });

  isLoading = signal(false);
  successMessage = signal('');
  errorMessage = signal('');
  createdPoliza = signal<PolizaResponse | null>(null);

  onSubmit(): void {
    if (this.expedirForm.invalid) { this.expedirForm.markAllAsTouched(); return; }

    this.isLoading.set(true);
    this.successMessage.set('');
    this.errorMessage.set('');

    const v = this.expedirForm.getRawValue();
    this.polizaService.expedir({
      tipoIdentificacion: v.tipoIdentificacion,
      numeroIdentificacion: v.numeroIdentificacion,
      nombreTomador: v.nombreTomador,
      fechaInicioVigencia: v.fechaInicioVigencia,
      montoRenta: v.montoRenta,
    }).subscribe({
      next: (poliza) => {
        this.createdPoliza.set(poliza);
        this.successMessage.set('Póliza expedida exitosamente');
        this.isLoading.set(false);
        this.expedirForm.reset();
      },
      error: () => {
        this.errorMessage.set('Error al expedir la póliza. Verifique los datos.');
        this.isLoading.set(false);
      },
    });
  }

  goToMenu(): void { this.router.navigate(['/menu']); }
}
