import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { ClienteResponse, ClienteDetalleResponse } from '../../shared/models';

/**
 * Servicio para operaciones de clientes RVT.
 * Consume los endpoints de búsqueda y detalle del backend.
 */
@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly http = inject(HttpClient);

  /**
   * Busca clientes por criterios opcionales.
   * Solo incluye parámetros no vacíos en la petición.
   * @param tipoId tipo de identificación (CC, CE, NIT, PA)
   * @param numeroId número de identificación
   * @param nombre nombre del cliente
   * @returns observable con la lista de clientes encontrados
   */
  buscar(
    tipoId?: string,
    numeroId?: string,
    nombre?: string,
  ): Observable<ClienteResponse[]> {
    let params = new HttpParams();

    if (tipoId?.trim()) {
      params = params.set('tipoId', tipoId.trim());
    }
    if (numeroId?.trim()) {
      params = params.set('numeroId', numeroId.trim());
    }
    if (nombre?.trim()) {
      params = params.set('nombre', nombre.trim());
    }

    return this.http.get<ClienteResponse[]>(
      `${environment.apiBaseUrl}/clientes/buscar`,
      { params },
    );
  }

  /**
   * Consulta el detalle consolidado de un cliente.
   * @param clienteId identificador único del cliente
   * @returns observable con el detalle del cliente
   */
  consultarDetalle(clienteId: number): Observable<ClienteDetalleResponse> {
    return this.http.get<ClienteDetalleResponse>(
      `${environment.apiBaseUrl}/clientes/${clienteId}`,
    );
  }
}
