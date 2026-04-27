import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { ExpedirPolizaRequest, PolizaResponse } from '../../shared/models';

/**
 * Servicio para operaciones de pólizas de Rentas Voluntarias.
 * Consume los endpoints de expedición, consulta y activación del backend.
 */
@Injectable({ providedIn: 'root' })
export class PolizaService {
  private readonly http = inject(HttpClient);

  /**
   * Expide una nueva póliza de Rentas Voluntarias.
   * @param request datos de la póliza a expedir
   * @returns observable con la póliza creada
   */
  expedir(request: ExpedirPolizaRequest): Observable<PolizaResponse> {
    return this.http.post<PolizaResponse>(
      `${environment.apiBaseUrl}/polizas/expedir`,
      request,
    );
  }

  /**
   * Consulta los datos de una póliza por su número.
   * @param numeroPoliza número único de la póliza
   * @returns observable con los datos de la póliza
   */
  consultar(numeroPoliza: string): Observable<PolizaResponse> {
    return this.http.get<PolizaResponse>(
      `${environment.apiBaseUrl}/polizas/consultar/${encodeURIComponent(numeroPoliza)}`,
    );
  }

  /**
   * Activa una póliza cambiando su estado de PENDIENTE a ACTIVA.
   * @param numeroPoliza número único de la póliza a activar
   * @returns observable con los datos de la póliza activada
   */
  activar(numeroPoliza: string): Observable<PolizaResponse> {
    return this.http.put<PolizaResponse>(
      `${environment.apiBaseUrl}/polizas/activar/${encodeURIComponent(numeroPoliza)}`,
      null,
    );
  }
}
