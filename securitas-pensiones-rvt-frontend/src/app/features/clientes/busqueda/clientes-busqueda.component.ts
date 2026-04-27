import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ClienteService } from '../cliente.service';
import { ClienteResponse } from '../../../shared/models';

@Component({
  selector: 'app-clientes-busqueda',
  templateUrl: './clientes-busqueda.component.html',
  styleUrl: './clientes-busqueda.component.css',
  imports: [FormsModule],
})
export class ClientesBusquedaComponent {
  private readonly clienteService = inject(ClienteService);
  private readonly router = inject(Router);

  tipoIdentificacion = '';
  numeroIdentificacion = '';
  nombre = '';

  clientes = signal<ClienteResponse[]>([]);
  hasSearched = signal(false);
  isLoading = signal(false);
  errorMessage = signal('');
  validationMessage = signal('');

  onSearch(): void {
    this.validationMessage.set('');
    this.errorMessage.set('');

    if (!this.tipoIdentificacion.trim() && !this.numeroIdentificacion.trim() && !this.nombre.trim()) {
      this.validationMessage.set('Ingrese al menos un criterio de búsqueda');
      return;
    }

    this.isLoading.set(true);
    this.clientes.set([]);
    this.hasSearched.set(false);

    this.clienteService.buscar(this.tipoIdentificacion, this.numeroIdentificacion, this.nombre).subscribe({
      next: (response) => {
        this.clientes.set(response);
        this.hasSearched.set(true);
        this.isLoading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading.set(false);
        this.hasSearched.set(true);
        if (err.status === 400) {
          this.validationMessage.set(err.error?.message ?? 'Ingrese al menos un criterio de búsqueda');
        } else {
          this.errorMessage.set('Error al consultar clientes. Intente nuevamente.');
        }
      },
    });
  }

  onSelectCliente(clienteId: number): void {
    this.router.navigate(['/clientes', clienteId]);
  }

  goToMenu(): void {
    this.router.navigate(['/menu']);
  }
}
