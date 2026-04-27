# PROMPT 1 — LÍDER TÉCNICO + DESARROLLADOR BACKEND (6 HORAS)

Usa como contexto el archivo `00-PROMPT-BASE-KIRO.md` y `presentacion-arquitectura.html`.

## Misión
Definir el marco técnico ejecutable del hackatón y, además, implementar un slice técnico real en backend para acelerar la integración del equipo.

## Lineamiento transversal obligatorio
- Backend: **Java 21 + arquitectura hexagonal + Spring Boot + Gradle**.

## Objetivo de la sesión (timebox 6h)
1. **(60 min)** Aterrizar arquitectura objetivo y reglas de integración en 1 página.
2. **(80 min)** Definir/ajustar contratos API-first del dominio piloto.
3. **(150 min)** Implementar backend base (auth demo + menú + endpoint piloto + pruebas básicas).
4. **(30 min)** Publicar ejemplos request/response + errores estándar.
5. **(40 min)** Handoff técnico para person5 (dev) y person4 (n8n).

## Entregables obligatorios
1. `README-lider-tecnico.md`
2. `SPEC-lider-tecnico.md`
3. `api-contracts/openapi-rvt-v1.yaml` (esqueleto mínimo)
4. `arquitectura/decision-log.md` (ADR-lite)
5. `HANDOFF-lider-tecnico.md`
6. `backend-evidencias-lider.md` (curl, payloads, códigos HTTP)

## Contenido mínimo del OpenAPI
- Auth demo: `POST /auth/login` (ADMIN/ADMIN demo)
- Menú/árbol: `GET /menu/tree`
- Rentas Voluntarias:
  - `POST /rvt/polizas/expedicion`
  - `POST /rvt/polizas/{id}/activar`
  - `GET /rvt/polizas/{id}`
- Beneficiarios:
  - `GET /rvt/beneficiarios/{clienteId}`
  - `PUT /rvt/beneficiarios/{clienteId}`
- Clientes:
  - `GET /rvt/clientes?filtros=...`
- Parametrización:
  - `GET /rvt/parametrizacion/ramo`
  - `PUT /rvt/parametrizacion/ramo`

## Tareas de desarrollo obligatorias (rol mixto)
- Implementar en `securitas-pensiones-rvt-backend`:
  - `POST /auth/login` (stub ADMIN/ADMIN)
  - `GET /menu/tree` (árbol estático)
  - Un endpoint piloto funcional de negocio:
    - preferido: `POST /rvt/polizas/expedicion`
- Estandarizar errores de API (`code`, `message`, `traceId`, `timestamp`).
- Configurar conexión DB para pruebas de integración (sin acoplar dominio a tablas).
- Dejar contrato y ejemplos listos para consumo de frontend/n8n.

## Distribución anti-choques con person5 (desarrollador)
- **person1 (este prompt):** liderazgo técnico + backend core + contrato + auth/menu + endpoint piloto.
- **person5 (prompt 5):** frontend MVVM + consumo de APIs + flujo UI + módulo secundario.
- Si person5 necesita endpoint no implementado, usar mock acordado y registrar deuda técnica.

## Restricciones
- No tocar repos restringidos del prompt base.
- No diseñar acoplado a tablas Oracle.
- No bloquear al equipo por espera de SSO real.

## Configuración obligatoria Gradle + DB
Incluye en el plan técnico y en ejemplos de implementación la conexión Oracle común:

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

Ejemplo Gradle/properties:

```properties
DB_URL=jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(HOST=10.1.7.81)(PORT=1521))(CONNECT_DATA=(SID=svpa)))
DB_USERNAME=rvt
DB_PASSWORD=rvt
```

## Criterios de aceptación
- Contratos claros y versionados.
- Dependencias entre roles explícitas.
- Plan de integración incremental definido.
- Riesgos top-5 con mitigación.
- Backend base ejecutable con al menos 3 endpoints funcionales.
- Handoff consumible por frontend y n8n sin reuniones adicionales.
