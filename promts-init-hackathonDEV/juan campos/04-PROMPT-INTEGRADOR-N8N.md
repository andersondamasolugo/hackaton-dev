# PROMPT 4 — INTEGRADOR DE PROCESOS (n8n) (6 HORAS)

Usa como contexto `00-PROMPT-BASE-KIRO.md`, contratos OpenAPI y priorización de Product Owner.

## Misión
Orquestar un flujo de negocio demo con n8n que conecte eventos clave entre frontend y backend, con observabilidad básica y manejo de errores.

## Lineamiento transversal obligatorio
- Backend: **Java 21 + arquitectura hexagonal + Spring Boot + Gradle**.

## Objetivo de la sesión (timebox 6h)
1. **(60 min)** Diseñar flujo n8n end-to-end para caso piloto.
2. **(130 min)** Construir workflow(s) con nodos HTTP + validaciones.
3. **(80 min)** Configurar manejo de errores, reintentos y rutas de compensación simples.
4. **(60 min)** Preparar evidencias (capturas + export JSON workflow).
5. **(30 min)** Documentar handoff técnico.

## Entregables obligatorios
1. `README-integrador-n8n.md`
2. `SPEC-integrador-n8n.md`
3. `workflows-n8n/expedicion-poliza-demo.json`
4. `observabilidad-n8n.md`
5. `HANDOFF-integrador-n8n.md`

## Flujo mínimo sugerido
- Trigger manual o webhook.
- Validar payload (cliente, producto, beneficiarios mínimos).
- Invocar endpoint de expedición.
- Si éxito: invocar consulta de póliza y registrar bitácora.
- Si error: registrar evento y notificar estado de fallo.

## Restricciones
- No invocar directamente estructuras Oracle ni dependencias legadas.
- No acoplar workflow a payloads no versionados.
- No asumir endpoints no definidos por contrato.

## Configuración obligatoria Gradle + DB (dependencia de integración)
Para pruebas e integración efectiva, considera la conexión Oracle común del equipo:

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

Variables estándar usadas por servicios Gradle:

```properties
DB_URL=jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(HOST=10.1.7.81)(PORT=1521))(CONNECT_DATA=(SID=svpa)))
DB_USERNAME=rvt
DB_PASSWORD=rvt
```

## Criterios de aceptación
- Workflow importable y reproducible en otra máquina.
- Manejo de estado exitoso y de error probado.
- Evidencia clara para demo funcional.
