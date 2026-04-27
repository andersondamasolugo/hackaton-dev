import { Component, inject, signal, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ParametroService } from '../parametro.service';
import { ParametroResponse } from '../../../shared/models';

/**
 * Componente de edición de parámetros del ramo.
 * Usa signals para detección de cambios confiable.
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

  parametroId = 0;
  parametro = signal<ParametroResponse | null>(null);
  isLoadingData = signal(false);
  isLoading = signal(false);
  successMessage = signal('');
  errorMessage = signal('');

  editarForm = this.fb.nonNullable.group({
    valor: ['', Validators.required],
    descripcion: [''],
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.parametroId = idParam ? Number(idParam) : 0;
    this.loadParametro();
  }

  private loadParametro(): void {
    this.isLoadingData.set(true);

    this.parametroService.listar().subscribe({
      next: (parametros) => {
        const found = parametros.find(p => p.parametroId === this.parametroId) ?? null;
        this.parametro.set(found);
        if (found) {
          this.editarForm.patchValue({
            valor: found.valor,
            descripcion: found.descripcion,
          });
        }
        this.isLoadingData.set(false);
      },
      error: () => {
        this.isLoadingData.set(false);
        this.errorMessage.set('Error al cargar datos del parámetro.');
      },
    });
  }

  onSubmit(): void {
    if (this.editarForm.invalid) {
      this.editarForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.successMessage.set('');
    this.errorMessage.set('');

    const formValue = this.editarForm.getRawValue();

    this.parametroService
      .actualizar(this.parametroId, {
        valor: formValue.valor,
        descripcion: formValue.descripcion || undefined,
      })
      .subscribe({
        next: (updated) => {
          this.parametro.set(updated);
          this.successMessage.set('Parámetro actualizado exitosamente');
          this.isLoading.set(false);
        },
        error: (err: HttpErrorResponse) => {
          this.isLoading.set(false);
          if (err.status === 400) {
            this.errorMessage.set(err.error?.message ?? 'Valor fuera de rango permitido');
          } else if (err.status === 404) {
            this.errorMessage.set('Parámetro no encontrado');
          } else {
            this.errorMessage.set('Error al actualizar. Intente nuevamente.');
          }
        },
      });
  }

  goBack(): void {
    this.router.navigate(['/parametrizacion']);
  }
}
