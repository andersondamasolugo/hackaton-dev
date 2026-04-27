import { Component, inject, signal, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { BeneficiarioService } from '../beneficiario.service';
import { BeneficiarioResponse } from '../../../shared/models';

/**
 * Componente de edición de beneficiarios.
 * Carga datos del beneficiario desde la lista de la póliza.
 * Usa signals para detección de cambios confiable.
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

  beneficiarioId = 0;
  numeroPoliza = '';
  beneficiario = signal<BeneficiarioResponse | null>(null);
  isLoadingData = signal(false);
  isLoading = signal(false);
  successMessage = signal('');
  errorMessage = signal('');

  editarForm = this.fb.nonNullable.group({
    nombreCompleto: ['', Validators.required],
    tipoIdentificacion: ['', Validators.required],
    numeroIdentificacion: ['', Validators.required],
    parentesco: ['', Validators.required],
    porcentajeParticipacion: [0, [Validators.required, Validators.min(0), Validators.max(100)]],
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.beneficiarioId = idParam ? Number(idParam) : 0;
    this.numeroPoliza = this.route.snapshot.queryParamMap.get('poliza') ?? '';

    if (this.numeroPoliza) {
      this.loadBeneficiario();
    }
  }

  private loadBeneficiario(): void {
    this.isLoadingData.set(true);

    this.beneficiarioService.listarPorPoliza(this.numeroPoliza).subscribe({
      next: (lista) => {
        const found = lista.find(b => b.beneficiarioId === this.beneficiarioId);
        if (found) {
          this.beneficiario.set(found);
          this.editarForm.patchValue({
            nombreCompleto: found.nombreCompleto,
            tipoIdentificacion: found.tipoIdentificacion,
            numeroIdentificacion: found.numeroIdentificacion,
            parentesco: found.parentesco,
            porcentajeParticipacion: Number(found.porcentajeParticipacion),
          });
        }
        this.isLoadingData.set(false);
      },
      error: () => {
        this.isLoadingData.set(false);
        this.errorMessage.set('Error al cargar datos del beneficiario.');
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
          this.successMessage.set('Beneficiario actualizado exitosamente');
          this.isLoading.set(false);
        },
        error: (err: HttpErrorResponse) => {
          this.isLoading.set(false);
          if (err.status === 400) {
            this.errorMessage.set(err.error?.message ?? 'La suma de porcentajes supera el 100%');
          } else if (err.status === 404) {
            this.errorMessage.set('Beneficiario no encontrado');
          } else {
            this.errorMessage.set('Error al actualizar. Intente nuevamente.');
          }
        },
      });
  }

  goBack(): void {
    this.router.navigate(['/beneficiarios']);
  }
}
