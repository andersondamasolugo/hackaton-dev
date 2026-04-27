import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse } from '../../shared/models';

/**
 * Servicio de autenticación.
 * Almacena el JWT en memoria (no localStorage) por seguridad.
 * Consume POST /auth/login del backend.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private token: string | null = null;

  /**
   * Autentica al usuario contra el backend.
   * @param request credenciales de login
   * @returns observable con la respuesta de autenticación
   */
  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${environment.apiBaseUrl}/auth/login`, request)
      .pipe(
        tap((response) => {
          this.token = response.token;
        }),
      );
  }

  /** Cierra la sesión eliminando el token de memoria. */
  logout(): void {
    this.token = null;
  }

  /**
   * Retorna el token JWT almacenado en memoria.
   * @returns token o null si no hay sesión activa
   */
  getToken(): string | null {
    return this.token;
  }

  /**
   * Indica si el usuario tiene una sesión activa.
   * @returns true si existe un token almacenado
   */
  isAuthenticated(): boolean {
    return this.token !== null;
  }
}
