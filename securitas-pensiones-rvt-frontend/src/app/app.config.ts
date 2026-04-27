import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import {
  provideClientHydration,
  withEventReplay,
} from '@angular/platform-browser';

import { routes } from './app.routes';
import { jwtInterceptor } from './core/auth';

/**
 * Application-level providers configuration.
 * HttpClient is provided with functional interceptors for JWT support.
 */
export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideClientHydration(withEventReplay()),
  ],
};
