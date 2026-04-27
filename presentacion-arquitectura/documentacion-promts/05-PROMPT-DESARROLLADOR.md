# PROMPT 5 — DESARROLLADOR (BACKEND + FRONTEND) (6 HORAS)

Usa como contexto `00-PROMPT-BASE-KIRO.md`, OpenAPI del líder técnico y criterios del Product Owner.

## Misión
Construir un demo funcional vertical (login simulado + menú + un flujo principal) respetando API-first y look & feel corporativo.

## Lineamiento transversal obligatorio
- Backend: **Java 21 + arquitectura hexagonal + Spring Boot + Gradle**.

## Objetivo de la sesión (timebox 6h)
1. **(60 min)** Preparar esqueleto backend/frontend y contrato local.
2. **(140 min)** Implementar flujo principal: login demo + menú + expedición/consulta póliza.
3. **(80 min)** Implementar módulo secundario (beneficiarios o clientes).
4. **(50 min)** Integrar UI bundle y consistencia visual.
5. **(30 min)** Pruebas básicas y documentación de handoff.

## Entregables obligatorios
1. `README-desarrollador.md`
2. `SPEC-desarrollador.md`
3. `evidencias-api-ui.md`
4. `HANDOFF-desarrollador.md`

## Reglas de implementación
- Backend en `securitas-pensiones-rvt-backend`:
  - Java 21, orientación hexagonal mínima.
  - Endpoints por casos de uso (no CRUD plano por tabla).
  - Respuestas DTO desacopladas de persistencia.
- Frontend en `securitas-pensiones-rvt-frontend`:
  - Angular 21 con estructura MVVM básica.
  - Integrar `@seguros-bolivar/ui-bundle` para estilos/componentes.
  - Menú estático de categorías según alcance.
- Auth temporal:
  - `ADMIN/ADMIN` como mecanismo demo documentado.

## Coordinación con person1 (líder técnico + dev backend)
- Consumir primero los endpoints implementados por person1 (`/auth/login`, `/menu/tree`, endpoint piloto).
- Si un endpoint no está disponible, usar mock local sin bloquear avance y registrarlo en `HANDOFF-desarrollador.md`.
- No modificar contrato sin notificar a person1 y registrar versión.

## Restricciones
- No tocar repositorios restringidos (reports/oracle-db/forms).
- No replicar Oracle Forms 1:1; adaptar a UX moderna.
- No bloquear por falta de SSO real: usar stub documentado.

## Configuración obligatoria Gradle + DB
Implementa y documenta conexión Oracle para pruebas de integración:

```text
SVPA_DESA_19=
  (DESCRIPTION=
    (ADDRESS=
      (COMMUNITY=tcp.world)
      (PROTOCOL=TCP)
      (HOST=10.1.7.81)
      (PORT=1521)
    )
    (CONNECT_DATA=
      (SID=svpa)
    )
  )
```

- Usuario: `rvt`
- Password: `rvt`

Variables sugeridas para `gradle.properties` / `.env` local:

```properties
DB_URL=jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(HOST=10.1.7.81)(PORT=1521))(CONNECT_DATA=(SID=svpa)))
DB_USERNAME=rvt
DB_PASSWORD=rvt
```

Además, agrega dependencia JDBC Oracle según estándar interno del repositorio backend.

## Criterios de aceptación
- Demo navegable extremo a extremo.
- Contratos respetados entre frontend/backend.
- Evidencias de funcionamiento (capturas, request/response).
- Lista de deuda técnica explícita para siguiente iteración.
