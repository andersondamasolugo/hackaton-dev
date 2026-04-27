import { Component, inject, signal, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ClienteService } from '../cliente.service';
import { ClienteDetalleResponse } from '../../../shared/models';

@Component({
  selector: 'app-clientes-detalle',
  templateUrl: './clientes-detalle.component.html',
  styleUrl: './clientes-detalle.component.css',
  imports: [],
})
export class ClientesDetalleComponent implements OnInit {
  private readonly clienteService = inject(ClienteService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  cliente = signal<ClienteDetalleResponse | null>(null);
  isLoading = signal(false);
  errorMessage = signal('');

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const clienteId = idParam ? Number(idParam) : 0;
    if (clienteId) this.loadCliente(clienteId);
    else this.errorMessage.set('Identificador de cliente inválido');
  }

  private loadCliente(clienteId: number): void {
    this.isLoading.set(true);
    this.clienteService.consultarDetalle(clienteId).subscribe({
      next: (response) => {
        this.cliente.set(response);
        this.isLoading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.isLoading.set(false);
        this.errorMessage.set(err.status === 404 ? 'Cliente no encontrado' : 'Error al consultar el cliente.');
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/clientes']);
  }
}
