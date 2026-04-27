import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';

import { AuthService } from './auth.service';

/**
 * Interceptor funcional que agrega el header Authorization: Bearer <token>
 * a cada request HTTP cuando existe un token activo.
 */
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(clonedRequest);
  }

  return next(req);
};
