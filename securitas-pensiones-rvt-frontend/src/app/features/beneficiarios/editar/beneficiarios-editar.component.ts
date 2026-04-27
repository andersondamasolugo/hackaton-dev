import { Component, inject, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { BeneficiarioService } from '../beneficiario.service';

/**
 * Componente de edición de beneficiarios.
 * Formulario reactivo para actualizar datos de un beneficiario.
 * Muestra mensajes de éxito/error incluyendo porcentaje excedido (400).
 */
@Component({
  selector: 'app-beneficiarios-editar',
  templateUrl: './beneficiarios-editar.component.html',
  styleUrl: './beneficiarios-editar.component.css',
  imports: [ReactiveFormsModule],
})
export class BeneficiariosEditarComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly beneficiarioService = inject(BeneficiarioService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  /** Identificador del beneficiario obtenido de la ruta. */
  beneficiarioId = 0;

  /** Formulario reactivo de edición. */
  editarForm = this.fb.nonNullable.group({
    nombreCompleto: ['', Validators.required],
    tipoIdentificacion: ['', Validators.required],
    numeroIdentificacion: ['', Validators.required],
    parentesco: ['', Validators.required],
    porcentajeParticipacion: [
      0,
      [Validators.required, Validators.min(0), Validators.max(100)],
    ],
  });

  /** Indica si hay una petición en curso. */
  isLoading = false;

  /** Mensaje de éxito mostrado al usuario. */
  successMessage = '';

  /** Mensaje de error mostrado al usuario. */
  errorMessage = '';

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.beneficiarioId = idParam ? Number(idParam) : 0;
  }

  /** Envía el formulario de actualización al backend. */
  onSubmit(): void {
    if (this.editarForm.invalid) {
      this.editarForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const formValue = this.editarForm.getRawValue();

    this.beneficiarioService
      .actualizar(this.beneficiarioId, {
        nombreCompleto: formValue.nombreCompleto,
        tipoIdentificacion: formValue.tipoIdentificacion,
        numeroIdentificacion: formValue.numeroIdentificacion,
        parentesco: formValue.parentesco,
        porcentajeParticipacion: formValue.porcentajeParticipacion,
      })
      .subscribe({
        next: () => {
          this.successMessage = 'Beneficiario actualizado exitosamente';
          this.isLoading = false;
        },
        error: (err: HttpErrorResponse) => {
          this.isLoading = false;
          if (err.status === 400) {
            const body = err.error;
            this.errorMessage =
              body?.message ??
              'La suma de porcentajes de participación supera el 100%';
          } else if (err.status === 404) {
            this.errorMessage = 'Beneficiario no encontrado';
          } else {
            this.errorMessage =
              'Error al actualizar el beneficiario. Intente nuevamente.';
          }
        },
      });
  }

  /** Navega de vuelta a la lista de beneficiarios. */
  goBack(): void {
    this.router.navigate(['/beneficiarios']);
  }
}
