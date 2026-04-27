# Changelog

Todos los cambios notables de este proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/)
y este proyecto adhiere a [Versionamiento Semántico](https://semver.org/lang/es/).

## [No publicado]

### Agregado
- Se creó proyecto Angular 21 con estructura MVVM (core/auth, core/mock, core/config, shared/models, shared/components, shared/services)
- Se crearon componentes placeholder para todos los módulos funcionales: login, menu, poliza (expedir, consultar, activar), beneficiarios (lista, editar), clientes (busqueda, detalle), parametrizacion (lista, editar)
- Se configuró app.routes.ts con lazy loading para todas las rutas del sistema
- Se crearon archivos de entorno (environment.ts, environment.prod.ts) con apiBaseUrl y flag useMocks
- Se configuró HttpClient con soporte para interceptores DI
- Se configuró SSR con renderMode Client para rutas parametrizadas
- Se crearon interfaces TypeScript para DTOs compartidos: `auth.model.ts`, `menu.model.ts`, `poliza.model.ts`, `beneficiario.model.ts`, `cliente.model.ts`, `parametro.model.ts`, `error.model.ts`
- Se configuró barrel export (`index.ts`) con `export type` para modelos compartidos
- Se implementó `AuthService` con login vía HttpClient, almacenamiento de JWT en memoria y métodos logout/getToken/isAuthenticated
- Se implementó `authGuard` funcional (canActivateFn) que protege rutas y redirige a /login si no hay sesión activa
- Se implementó `jwtInterceptor` funcional (HttpInterceptorFn) que agrega header Authorization: Bearer a cada request
- Se implementó `LoginComponent` standalone con formulario reactivo (usuario, contraseña), estado de carga y mensaje de error genérico
- Se actualizó `app.config.ts` para usar `withInterceptors([jwtInterceptor])` en lugar de `withInterceptorsFromDi()`
- Se actualizó `app.routes.ts` para proteger todas las rutas (excepto /login y **) con `authGuard`
- Se actualizó barrel export `core/auth/index.ts` con AuthService, authGuard y jwtInterceptor
- Se creó `MenuService` en `features/menu/` que consume `GET /menu/tree` del backend con fallback estático local en caso de error
- Se implementó `MenuComponent` standalone con árbol de categorías expandible/colapsable, navegación por ruta al seleccionar ítems y botón de cerrar sesión
- Se crearon `menu.component.html` y `menu.component.css` con diseño sidebar, iconos Material Icons y estilos corporativos
- Se implementó `BeneficiarioService` con métodos `listarPorPoliza()` y `actualizar()` consumiendo endpoints REST del backend
- Se implementó `BeneficiariosListaComponent` standalone con búsqueda por número de póliza, tabla de beneficiarios (nombre, identificación, parentesco, porcentaje) y botón de edición por fila
- Se implementó `BeneficiariosEditarComponent` standalone con formulario reactivo (nombreCompleto, tipoIdentificacion, numeroIdentificacion, parentesco, porcentajeParticipacion) y manejo de errores 400/404
- Se implementó `ClienteService` con métodos `buscar()` (query params dinámicos con HttpParams) y `consultarDetalle()` consumiendo endpoints REST del backend
- Se implementó `ClientesBusquedaComponent` standalone con formulario de 3 filtros (tipoIdentificacion, numeroIdentificacion, nombre), validación de al menos un criterio, tabla de resultados con navegación al detalle por clic en fila
- Se implementó `ClientesDetalleComponent` standalone con vista de detalle consolidado (nombre, identificación, estado, dirección, teléfono, email), tabla de pólizas asociadas y botón volver
