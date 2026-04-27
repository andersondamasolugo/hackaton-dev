import { Component, inject, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ParametroService } from '../parametro.service';
import { ParametroResponse } from '../../../shared/models';

/**
 * Componente de edición de parámetros del ramo.
 * Formulario reactivo para actualizar valor y descripción de un parámetro.
 * Muestra información de rango como referencia de solo lectura.
 * Maneja errores de validación de rango (400) del backend.
 */
@Component({
  selector: 'app-parametrizacion-editar',
  templateUrl: './parametrizacion-editar.component.html',
  styleUrl: './parametrizacion-editar.component.css',
  imports: [ReactiveFormsModule],
})
export class ParametrizacionEditarComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly parametroService = inject(ParametroService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  /** Identificador del parámetro obtenido de la ruta. */
  parametroId = 0;

  /** Datos del parámetro cargado para mostrar info de referencia. */
  parametro: ParametroResponse | null = null;

  /** Formulario reactivo de edición. */
  editarForm = this.fb.nonNullable.group({
    valor: ['', Validators.required],
    descripcion: [''],
  });

  /** Indica si se está cargando el parámetro. */
  isLoadingData = false;

  /** Indica si hay una petición de guardado en curso. */
  isLoading = false;

  /** Mensaje de éxito mostrado al usuario. */
  successMessage = '';

  /** Mensaje de error mostrado al usuario. */
  errorMessage = '';

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.parametroId = idParam ? Number(idParam) : 0;
    this.loadParametro();
  }

  /**
   * Carga los datos del parámetro para pre-llenar el formulario
   * y mostrar la información de rango como referencia.
   */
  private loadParametro(): void {
    this.isLoadingData = true;

    this.parametroService.listar().subscribe({
      next: (parametros) => {
        this.parametro =
          parametros.find((p) => p.parametroId === this.parametroId) ?? null;
        if (this.parametro) {
          this.editarForm.patchValue({
            valor: this.parametro.valor,
            descripcion: this.parametro.descripcion,
          });
        }
        this.isLoadingData = false;
      },
      error: () => {
        this.isLoadingData = false;
        this.errorMessage = 'Error al cargar datos del parámetro.';
      },
    });
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

    this.parametroService
      .actualizar(this.parametroId, {
        valor: formValue.valor,
        descripcion: formValue.descripcion || undefined,
      })
      .subscribe({
        next: (updated) => {
          this.parametro = updated;
          this.successMessage = 'Parámetro actualizado exitosamente';
          this.isLoading = false;
        },
        error: (err: HttpErrorResponse) => {
          this.isLoading = false;
          if (err.status === 400) {
            const body = err.error;
            this.errorMessage =
              body?.message ?? 'Valor fuera de rango permitido';
          } else if (err.status === 404) {
            this.errorMessage = 'Parámetro no encontrado';
          } else {
            this.errorMessage =
              'Error al actualizar el parámetro. Intente nuevamente.';
          }
        },
      });
  }

  /** Navega de vuelta a la lista de parámetros. */
  goBack(): void {
    this.router.navigate(['/parametrizacion']);
  }
}
