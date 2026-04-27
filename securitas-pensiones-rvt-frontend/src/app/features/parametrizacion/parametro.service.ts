import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import {
  ActualizarParametroRequest,
  ParametroResponse,
} from '../../shared/models';

/**
 * Servicio para operaciones de parámetros del ramo RVT.
 * Consume los endpoints de listado y actualización del backend.
 */
@Injectable({ providedIn: 'root' })
export class ParametroService {
  private readonly http = inject(HttpClient);

  /**
   * Lista todos los parámetros del ramo.
   * @returns observable con la lista de parámetros
   */
  listar(): Observable<ParametroResponse[]> {
    return this.http.get<ParametroResponse[]>(
      `${environment.apiBaseUrl}/parametros`,
    );
  }

  /**
   * Actualiza el valor de un parámetro.
   * @param parametroId identificador del parámetro
   * @param request datos actualizados del parámetro
   * @returns observable con el parámetro actualizado
   */
  actualizar(
    parametroId: number,
    request: ActualizarParametroRequest,
  ): Observable<ParametroResponse> {
    return this.http.put<ParametroResponse>(
      `${environment.apiBaseUrl}/parametros/${parametroId}`,
      request,
    );
  }
}
