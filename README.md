# 🏦 Demo Vertical RVT — Rentas Voluntarias

**Hackathon DEV 2026 — Seguros Bolívar**

Demo funcional vertical del sistema de Rentas Voluntarias (RVT), modernizando los módulos Oracle Forms legados hacia una arquitectura web moderna.

## Equipo

| Persona | Rol | Rama |
|---|---|---|
| Juan González | Líder técnico + dev backend | `juan-gonzalez/techlead-backend-core` |
| Miguel Mora | Project Manager | `miguel-mora/pm-plan-riesgos` |
| Ángela Mora | Product Owner | `angela-mora/po-historias-criterios` |
| Juan Campos | Integrador n8n | `juan-campos/n8n-workflows-integracion` |
| **Anderson Lugo** | Desarrollador + Gatekeeper PR | `anderson-lugo/dev-frontend-integracion` |

## Stack Técnico

| Capa | Tecnología |
|---|---|
| Backend | Java 21 + Spring Boot 3.4 + Gradle + Hexagonal |
| Frontend | Angular 21 + MVVM + Seguros Bolívar UI Bundle |
| Base de datos | Oracle (profile `real`) / H2 en memoria (profile `mock`) |
| Auth | JWT stub (ADMIN/ADMIN) |
| Chat IA | n8n webhook integrado |
| API Docs | Swagger UI (SpringDoc OpenAPI) |

## Cómo ejecutar

### Requisitos

- OpenJDK 21+ (o 25)
- Gradle 8+
- Node.js 20+ LTS
- Angular CLI 21 (`npm install -g @angular/cli@21`)

### Backend

```powershell
cd securitas-pensiones-rvt-backend

# Configurar Java
$env:JAVA_HOME = "$env:USERPROFILE\scoop\apps\openjdk\current"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Con datos mock (H2 en memoria)
.\gradlew.bat bootRun --args='--spring.profiles.active=mock'

# Con Oracle real (requiere acceso a red 10.1.7.81)
.\gradlew.bat bootRun --args='--spring.profiles.active=real'
```

Backend disponible en: `http://localhost:8080/rvt/api/v1/`

### Frontend

```powershell
cd securitas-pensiones-rvt-frontend
$env:PATH = "$env:APPDATA\npm;$env:PATH"
npx -y @angular/cli@21 serve
```

Frontend disponible en: `http://localhost:4200`

### Credenciales demo

- **Usuario**: `ADMIN`
- **Contraseña**: `ADMIN`

## URLs principales

| URL | Descripción |
|---|---|
| `http://localhost:4200` | Frontend Angular |
| `http://localhost:8080/rvt/api/v1/` | Landing backend |
| `http://localhost:8080/rvt/api/v1/swagger-ui.html` | Swagger UI |
| `http://localhost:8080/rvt/api/v1/hola` | Health check |
| `http://localhost:8080/rvt/api/v1/db-test/stats` | Stats Oracle (profile real) |

## Módulos implementados

### Menú (estático con JSON)
- Árbol de categorías: Rentas Voluntarias + Parametrización Ramo RV
- Fallback local si backend no disponible

### Pólizas
- **Expedir**: formulario con validaciones, genera número RVT-XXXXXXXX
- **Consultar**: búsqueda por número de póliza o número interno Oracle
- **Activar**: cambio de estado PENDIENTE → ACTIVA

### Beneficiarios
- Listar por número de póliza
- Editar datos con validación de porcentaje (suma ≤ 100%)

### Clientes
- Búsqueda con filtros (tipo ID, número ID, nombre)
- Detalle consolidado con pólizas asociadas

### Parametrización
- Tabla de parámetros del ramo (TASA_BASE, MONTO_MIN, etc.)
- Edición con validación de rango

## Datos demo (profile mock)

| Póliza | Estado | Tomador |
|---|---|---|
| RVT-DEMO-001 | PENDIENTE | Juan Pérez García |
| RVT-DEMO-002 | ACTIVA | María López Rodríguez |
| RVT-DEMO-003 | PENDIENTE | Empresa Ejemplo S.A.S. |

## Arquitectura

```
Backend (Hexagonal)
├── domain/          → Entidades, puertos, excepciones
├── application/     → Casos de uso
└── infrastructure/  → Controllers REST, JPA, mocks, config

Frontend (MVVM)
├── core/            → Auth, guards, interceptors
├── shared/          → Modelos, componentes, servicios
└── features/        → Login, menú, póliza, beneficiarios, clientes, parametrización
```

## Conexión Oracle

```properties
DB_URL=jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(HOST=10.1.7.81)(PORT=1521))(CONNECT_DATA=(SID=svpa)))
DB_USERNAME=rvt
DB_PASSWORD=rvt
```

Tablas principales: POLIZA (18,170), BENEFICIARIO (50,170), CLIENTES (32,566).

## Deuda técnica

- JWT stub con secret fijo (producción requiere Oracle Access Manager)
- JPA entities parcialmente mapeadas a Oracle (solo POLIZA y BENEFICIARIO)
- Sin pruebas unitarias ni de propiedad (timebox hackathon)
- UI Bundle simulado con CSS custom (no el paquete npm real)
- Módulo clientes y parametrización read-only contra Oracle

## Repositorios restringidos (solo análisis)

- `securitas-pensiones-forms` — Oracle Forms legado (referencia funcional)
- `securitas-pensiones-reports` — Reportes
- `securitas-pensiones-rvt-oracle-db` — DDL/DML de Oracle
