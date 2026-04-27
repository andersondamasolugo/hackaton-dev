import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import {
  ActualizarBeneficiarioRequest,
  BeneficiarioResponse,
} from '../../shared/models';

/**
 * Servicio para operaciones de beneficiarios de pólizas RVT.
 * Consume los endpoints de listado por póliza y actualización del backend.
 */
@Injectable({ providedIn: 'root' })
export class BeneficiarioService {
  private readonly http = inject(HttpClient);

  /**
   * Lista los beneficiarios asociados a una póliza.
   * @param numeroPoliza número único de la póliza
   * @returns observable con la lista de beneficiarios
   */
  listarPorPoliza(numeroPoliza: string): Observable<BeneficiarioResponse[]> {
    return this.http.get<BeneficiarioResponse[]>(
      `${environment.apiBaseUrl}/beneficiarios/poliza/${encodeURIComponent(numeroPoliza)}`,
    );
  }

  /**
   * Actualiza los datos de un beneficiario.
   * @param beneficiarioId identificador del beneficiario
   * @param request datos actualizados del beneficiario
   * @returns observable con el beneficiario actualizado
   */
  actualizar(
    beneficiarioId: number,
    request: ActualizarBeneficiarioRequest,
  ): Observable<BeneficiarioResponse> {
    return this.http.put<BeneficiarioResponse>(
      `${environment.apiBaseUrl}/beneficiarios/${beneficiarioId}`,
      request,
    );
  }
}
