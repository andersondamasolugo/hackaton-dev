import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { MenuTreeResponse } from '../../shared/models';

/**
 * Servicio para obtener el árbol de categorías del menú.
 * Consume GET /menu/tree del backend.
 * Si el endpoint falla, retorna un fallback estático local.
 */
@Injectable({ providedIn: 'root' })
export class MenuService {
  private readonly http = inject(HttpClient);

  /**
   * Obtiene el árbol de categorías del menú.
   * Usa fallback estático local como fuente primaria (sin DB disponible).
   * Si se desea consumir del backend, descomentar la línea con http.get.
   *
   * @returns observable con la estructura jerárquica del menú
   */
  getMenuTree(): Observable<MenuTreeResponse> {
    // Backend no disponible o sin DB — usar menú estático directamente
    return of(this.getStaticFallback());

    // Descomentar para consumir del backend cuando esté disponible:
    // return this.http
    //   .get<MenuTreeResponse>(`${environment.apiBaseUrl}/menu/tree`)
    //   .pipe(catchError(() => of(this.getStaticFallback())));
  }

  /**
   * Retorna un árbol de menú estático como fallback
   * cuando el backend no está disponible.
   *
   * @returns estructura de menú con categorías predefinidas
   */
  private getStaticFallback(): MenuTreeResponse {
    return {
      categories: [
        {
          id: 'rentas-voluntarias',
          label: 'Rentas Voluntarias',
          icon: 'policy',
          items: [
            { id: 'expedir-rv', label: 'Expedir Rentas Voluntarias', route: '/poliza/expedir', icon: 'add_circle' },
            { id: 'consultar-poliza', label: 'Consultar Póliza', route: '/poliza/consultar', icon: 'search' },
            { id: 'activar-poliza', label: 'Activar Póliza', route: '/poliza/activar', icon: 'check_circle' },
            { id: 'beneficiarios', label: 'Actualización Beneficiarios', route: '/beneficiarios', icon: 'people' },
            { id: 'clientes-rv', label: 'Consulta General Clientes RV', route: '/clientes', icon: 'person_search' },
          ],
        },
        {
          id: 'parametrizacion',
          label: 'Parametrización Ramo RV',
          icon: 'settings',
          items: [
            { id: 'param-ramo', label: 'Parametrización del Ramo', route: '/parametrizacion', icon: 'tune' },
          ],
        },
      ],
    };
  }
}
