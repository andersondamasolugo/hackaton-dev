import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { PolizaService } from '../poliza.service';
import { PolizaResponse } from '../../../shared/models';

/**
 * Componente de expedición de pólizas.
 * Formulario reactivo con validaciones por campo.
 * Muestra número de póliza generado en caso de éxito.
 */
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

  /** Formulario reactivo de expedición. */
  expedirForm = this.fb.nonNullable.group({
    tipoIdentificacion: ['', Validators.required],
    numeroIdentificacion: ['', Validators.required],
    nombreTomador: ['', Validators.required],
    fechaInicioVigencia: ['', Validators.required],
    montoRenta: [0, [Validators.required, Validators.min(1)]],
  });

  /** Indica si hay una petición en curso. */
  isLoading = false;

  /** Mensaje de éxito mostrado al usuario. */
  successMessage = '';

  /** Mensaje de error mostrado al usuario. */
  errorMessage = '';

  /** Póliza creada tras expedición exitosa. */
  createdPoliza: PolizaResponse | null = null;

  /** Envía el formulario de expedición al backend. */
  onSubmit(): void {
    if (this.expedirForm.invalid) {
      this.expedirForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const formValue = this.expedirForm.getRawValue();

    this.polizaService
      .expedir({
        tipoIdentificacion: formValue.tipoIdentificacion,
        numeroIdentificacion: formValue.numeroIdentificacion,
        nombreTomador: formValue.nombreTomador,
        fechaInicioVigencia: formValue.fechaInicioVigencia,
        montoRenta: formValue.montoRenta,
      })
      .subscribe({
        next: (poliza) => {
          this.createdPoliza = poliza;
          this.successMessage = 'Póliza expedida exitosamente';
          this.isLoading = false;
          this.expedirForm.reset();
        },
        error: () => {
          this.errorMessage = 'Error al expedir la póliza. Verifique los datos e intente nuevamente.';
          this.isLoading = false;
        },
      });
  }

  /** Navega de vuelta al menú principal. */
  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
