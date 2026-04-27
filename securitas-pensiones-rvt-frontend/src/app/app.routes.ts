import { Routes } from '@angular/router';

import { authGuard } from './core/auth';

/**
 * Application routes configuration.
 * AuthGuard protects all routes except /login and the wildcard fallback.
 * Components are lazy-loaded via dynamic imports.
 */
export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/login/login.component').then(
        (m) => m.LoginComponent,
      ),
  },
  {
    path: '',
    redirectTo: 'menu',
    pathMatch: 'full',
  },
  {
    path: 'menu',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/menu/menu.component').then((m) => m.MenuComponent),
  },
  {
    path: 'poliza/expedir',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/poliza/expedir/expedir.component').then(
        (m) => m.ExpedirComponent,
      ),
  },
  {
    path: 'poliza/consultar',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/poliza/consultar/consultar.component').then(
        (m) => m.ConsultarComponent,
      ),
  },
  {
    path: 'poliza/activar',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/poliza/activar/activar.component').then(
        (m) => m.ActivarComponent,
      ),
  },
  {
    path: 'beneficiarios',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/beneficiarios/lista/beneficiarios-lista.component'
      ).then((m) => m.BeneficiariosListaComponent),
  },
  {
    path: 'beneficiarios/editar/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/beneficiarios/editar/beneficiarios-editar.component'
      ).then((m) => m.BeneficiariosEditarComponent),
  },
  {
    path: 'clientes',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/clientes/busqueda/clientes-busqueda.component'
      ).then((m) => m.ClientesBusquedaComponent),
  },
  {
    path: 'clientes/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/clientes/detalle/clientes-detalle.component'
      ).then((m) => m.ClientesDetalleComponent),
  },
  {
    path: 'parametrizacion',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/parametrizacion/lista/parametrizacion-lista.component'
      ).then((m) => m.ParametrizacionListaComponent),
  },
  {
    path: 'parametrizacion/editar/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/parametrizacion/editar/parametrizacion-editar.component'
      ).then((m) => m.ParametrizacionEditarComponent),
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];
