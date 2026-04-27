# Changelog

Todos los cambios notables de este proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/)
y este proyecto adhiere a [Versionamiento Semántico](https://semver.org/lang/es/).

## [No publicado]

### Agregado
- Se verificó estructura de paquetes hexagonal del backend: domain (model, port.in, port.out, exception), application (usecase), infrastructure (adapter.in.rest, adapter.out.persistence, adapter.out.mock, config, filter)
- Se verificó compilación exitosa del proyecto Spring Boot con Gradle
- Se creó contrato OpenAPI (`openapi.yml`) con todos los endpoints del API: auth, menú, pólizas, beneficiarios, clientes y parametrización
- Se crearon records Java para DTOs de request: `LoginRequest`, `ExpedirPolizaRequest`, `ActualizarBeneficiarioRequest`, `ActualizarParametroRequest` con validaciones Jakarta
- Se crearon records Java para DTOs de response: `LoginResponse`, `MenuTreeResponse`, `MenuCategory`, `MenuItem`, `PolizaResponse`, `BeneficiarioResponse`, `ClienteResponse`, `ClienteDetalleResponse`, `PolizaResumen`, `ParametroResponse`, `ErrorResponse`, `FieldError`
- Se crearon entidades de dominio: `EstadoPoliza` (enum), `Poliza` (con método `activar()`), `Beneficiario`, `Cliente`, `Parametro` (con método `validarRango()`)
- Se creó jerarquía de excepciones de dominio: `RvtDomainException` (base abstracta), `PolizaNotFoundException`, `PolizaNoActivableException`, `BeneficiarioNotFoundException`, `PorcentajeExcedidoException`, `ValorFueraDeRangoException`, `ClienteNotFoundException`, `FiltroRequeridoException`, `CredencialesInvalidasException`
- Se crearon puertos de entrada (in): `AuthPort`, `MenuPort`, `PolizaPort`, `BeneficiarioPort`, `ClientePort`, `ParametroPort` con interfaces de casos de uso
- Se crearon puertos de salida (out): `PolizaRepository`, `BeneficiarioRepository`, `ClienteRepository`, `ParametroRepository`, `AuthExternalPort`, `MenuExternalPort` con interfaces de repositorios y servicios externos
- Se creó `GlobalExceptionHandler` con `@RestControllerAdvice` que mapea excepciones de dominio a códigos HTTP: 401 (credenciales), 404 (not found), 409 (conflicto), 400 (validación) y 500 (genérico sin stack trace), con correlationId UUID en cada respuesta
- Se creó `CorsConfig` para permitir requests del frontend Angular en desarrollo (`http://localhost:4200`)
- Se creó `JwtUtil` en `infrastructure/config/` para generación y validación de tokens JWT con jjwt (HS256, expiración 60 min, secret fijo para demo)
- Se creó `AuthUseCase` en `application/usecase/` que valida credenciales ADMIN/ADMIN y genera JWT; lanza `CredencialesInvalidasException` para credenciales inválidas
- Se creó `AuthController` en `infrastructure/adapter/in/rest/` con endpoint `POST /auth/login` que delega a `AuthPort`
- Se creó `MockAuthAdapter` en `infrastructure/adapter/out/mock/` con `@Profile("mock")` como fallback de autenticación externa
- Se creó `JwtAuthenticationFilter` en `infrastructure/filter/` que intercepta requests, valida JWT del header Authorization y establece SecurityContext
- Se actualizó `SecurityConfig` para inyectar `JwtAuthenticationFilter` antes de `UsernamePasswordAuthenticationFilter` y cambiar `anyRequest().permitAll()` a `anyRequest().authenticated()`
- Se creó `MenuUseCase` en `application/usecase/` que implementa `MenuPort` y retorna árbol de categorías estático con "Rentas Voluntarias" (5 ítems) y "Parametrización Ramo RV" (1 ítem)
- Se creó `MenuController` en `infrastructure/adapter/in/rest/` con endpoint `GET /menu/tree` y anotaciones Swagger
- Se creó `MockMenuAdapter` en `infrastructure/adapter/out/mock/` con `@Profile("mock")` que retorna el mismo árbol de menú estático como fallback
- Se implementó `PolizaUseCase` en `application/usecase/` con métodos `expedir`, `consultar` y `activar` siguiendo arquitectura hexagonal
- Se creó `PolizaController` en `infrastructure/adapter/in/rest/` con endpoints `POST /polizas/expedir` (201), `GET /polizas/consultar/{numeroPoliza}` (200), `PUT /polizas/activar/{numeroPoliza}` (200) con anotaciones Swagger
- Se creó `JpaPolizaEntity` en `infrastructure/adapter/out/persistence/` con mapeo JPA a tabla POLIZA y métodos `fromDomain`/`toDomain`
- Se creó `JpaPolizaJpaRepository` (Spring Data JPA) con consulta `findByNumeroPoliza`
- Se creó `JpaPolizaRepository` en `infrastructure/adapter/out/persistence/` con `@Profile("real")` que adapta entre dominio y JPA
- Se actualizó `MockPolizaRepository` en `infrastructure/adapter/out/mock/` con `@Repository`, `@Profile("mock")` y pre-carga de 3 pólizas de ejemplo para el demo (RVT-DEMO-001, RVT-DEMO-002, RVT-DEMO-003)
- Se implementó `BeneficiarioUseCase` en `application/usecase/` con métodos `listarPorPoliza` y `actualizar` con validación de porcentaje total ≤ 100%
- Se creó `BeneficiarioController` en `infrastructure/adapter/in/rest/` con endpoints `GET /beneficiarios/poliza/{numeroPoliza}` (200) y `PUT /beneficiarios/{beneficiarioId}` (200) con anotaciones Swagger y @Valid
- Se creó `MockBeneficiarioRepository` en `infrastructure/adapter/out/mock/` con `@Profile("mock")` y pre-carga de 3 beneficiarios: 2 para RVT-DEMO-001 (60%+30%=90%) y 1 para RVT-DEMO-002 (100%)
- Se creó `BeneficiarioJpaEntity` en `infrastructure/adapter/out/persistence/` con mapeo JPA a tabla BENEFICIARIO y métodos `fromDomain`/`toDomain`
- Se creó `BeneficiarioJpaJpaRepository` (Spring Data JPA) con consulta `findByNumeroPoliza`
- Se creó `JpaBeneficiarioAdapter` en `infrastructure/adapter/out/persistence/` con `@Profile("real")` que adapta entre dominio y JPA
- Se implementó `ClienteUseCase` en `application/usecase/` con métodos `buscar` (validación de al menos un filtro, búsqueda case-insensitive por nombre) y `consultarDetalle` (retorna detalle consolidado con pólizas)
- Se creó `ClienteController` en `infrastructure/adapter/in/rest/` con endpoints `GET /clientes/buscar` (query params: tipoId, numeroId, nombre) y `GET /clientes/{clienteId}` con anotaciones Swagger
- Se creó `MockClienteRepository` en `infrastructure/adapter/out/mock/` con `@Profile("mock")` y pre-carga de 4 clientes demo: Juan Pérez (CC), María López (CE), Empresa Ejemplo (NIT), Pedro Gómez (CC sin pólizas)
- Se creó `ClienteJpaEntity` en `infrastructure/adapter/out/persistence/` con mapeo JPA a tabla CLIENTE y métodos `fromDomain`/`toDomain`
- Se creó `ClienteJpaJpaRepository` (Spring Data JPA) con consultas derivadas por tipo identificación, número identificación y nombre (contains ignore case)
- Se creó `JpaClienteAdapter` en `infrastructure/adapter/out/persistence/` con `@Profile("real")` que adapta entre dominio y JPA con filtrado por criterios

### Eliminado
- Se eliminaron archivos `.gitkeep` de los paquetes: `infrastructure/config`, `infrastructure/filter`, `infrastructure/adapter/in/rest`, `infrastructure/adapter/out/persistence`, `infrastructure/adapter/out/mock`
