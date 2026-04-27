import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ClienteService } from '../cliente.service';
import { ClienteDetalleResponse } from '../../../shared/models';

/**
 * Componente de detalle de cliente.
 * Muestra información consolidada del cliente y sus pólizas asociadas.
 * Obtiene el clienteId de los parámetros de ruta.
 */
@Component({
  selector: 'app-clientes-detalle',
  templateUrl: './clientes-detalle.component.html',
  styleUrl: './clientes-detalle.component.css',
  imports: [CommonModule],
})
export class ClientesDetalleComponent implements OnInit {
  private readonly clienteService = inject(ClienteService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  /** Detalle del cliente cargado. */
  cliente: ClienteDetalleResponse | null = null;

  /** Indica si hay una petición en curso. */
  isLoading = false;

  /** Mensaje de error genérico. */
  errorMessage = '';

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const clienteId = idParam ? Number(idParam) : 0;

    if (!clienteId) {
      this.errorMessage = 'Identificador de cliente inválido';
      return;
    }

    this.loadCliente(clienteId);
  }

  /** Navega de vuelta a la búsqueda de clientes. */
  goBack(): void {
    this.router.navigate(['/clientes']);
  }

  /**
   * Carga el detalle del cliente desde el backend.
   * @param clienteId identificador del cliente
   */
  private loadCliente(clienteId: number): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.clienteService.consultarDetalle(clienteId).subscribe({
      next: (response) => {
        this.cliente = response;
        this.isLoading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading = false;
        if (err.status === 404) {
          this.errorMessage = 'Cliente no encontrado';
        } else {
          this.errorMessage =
            'Error al consultar el detalle del cliente. Intente nuevamente.';
        }
      },
    });
  }
}
