import { RenderMode, ServerRoute } from '@angular/ssr';

/**
 * Server-side routing configuration.
 * Parameterized routes use client-side rendering since they
 * cannot be prerendered without known parameter values.
 */
export const serverRoutes: ServerRoute[] = [
  {
    path: 'beneficiarios/editar/:id',
    renderMode: RenderMode.Client,
  },
  {
    path: 'clientes/:id',
    renderMode: RenderMode.Client,
  },
  {
    path: 'parametrizacion/editar/:id',
    renderMode: RenderMode.Client,
  },
  {
    path: '**',
    renderMode: RenderMode.Prerender,
  },
];
