import { Component, inject, signal, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ParametroService } from '../parametro.service';
import { ParametroResponse } from '../../../shared/models';

/**
 * Componente de lista de parámetros del ramo.
 * Usa signals para detección de cambios confiable.
 */
@Component({
  selector: 'app-parametrizacion-lista',
  templateUrl: './parametrizacion-lista.component.html',
  styleUrl: './parametrizacion-lista.component.css',
  imports: [],
})
export class ParametrizacionListaComponent implements OnInit {
  private readonly parametroService = inject(ParametroService);
  private readonly router = inject(Router);

  parametros = signal<ParametroResponse[]>([]);
  isLoading = signal(false);
  errorMessage = signal('');

  ngOnInit(): void {
    this.loadParametros();
  }

  private loadParametros(): void {
    this.isLoading.set(true);
    this.errorMessage.set('');

    this.parametroService.listar().subscribe({
      next: (response) => {
        this.parametros.set(response);
        this.isLoading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al consultar parámetros. Intente nuevamente.');
      },
    });
  }

  onEdit(parametroId: number): void {
    this.router.navigate(['/parametrizacion/editar', parametroId]);
  }

  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
