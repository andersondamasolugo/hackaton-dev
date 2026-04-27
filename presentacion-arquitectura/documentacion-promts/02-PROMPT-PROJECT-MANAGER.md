# PROMPT 2 — PROJECT MANAGER (6 HORAS)

Usa como contexto `00-PROMPT-BASE-KIRO.md` y los contratos definidos por el líder técnico.

## Misión
Convertir el alcance técnico en un plan operativo de hackatón (6 horas efectivas), con tareas paralelas, hitos, seguimiento y control de riesgos.

## Lineamiento transversal obligatorio
- Backend: **Java 21 + arquitectura hexagonal + Spring Boot + Gradle**.

## Objetivo de la sesión (timebox 6h)
1. **(70 min)** Definir WBS ligera por rol.
2. **(90 min)** Armar cronograma en bloques de 30 minutos.
3. **(70 min)** Matriz de riesgos, bloqueos y contingencias.
4. **(70 min)** Definir tablero de seguimiento (To Do / Doing / Done) y reglas de escalamiento.
5. **(60 min)** Preparar rituales: kickoff, checkpoints y cierre con demo final.

## Entregables obligatorios
1. `README-project-manager.md`
2. `SPEC-project-manager.md`
3. `plan-ejecucion-hackaton.md`
4. `riesgos-y-mitigacion.md`
5. `HANDOFF-project-manager.md`

## Contenido mínimo requerido
- Matriz RACI de 5 roles.
- Plan de comunicación (cada cuánto, quién reporta qué).
- Definición de artefactos de evidencia por rol.
- Criterios de éxito del hackatón (mínimo demo end-to-end).
- Checklist para consolidar paquete final (incluye idea de ZIP con artefactos relevantes).

## Restricciones
- No redefinir arquitectura base ya aprobada.
- No asignar tareas ambiguas sin entregable concreto.
- No mezclar responsabilidades de roles (evitar choques).

## Configuración obligatoria Gradle + DB (alineación del equipo)
Incluye en el plan operativo la dependencia técnica de conexión Oracle para pruebas:

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

Formato de variables para proyectos Gradle:

```properties
DB_URL=jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(HOST=10.1.7.81)(PORT=1521))(CONNECT_DATA=(SID=svpa)))
DB_USERNAME=rvt
DB_PASSWORD=rvt
```

## Criterios de aceptación
- Cada persona sabe qué hacer en los primeros 15 minutos.
- Hay checkpoints que detectan atraso temprano.
- El plan permite continuar aun con dependencias incompletas (mocks).
