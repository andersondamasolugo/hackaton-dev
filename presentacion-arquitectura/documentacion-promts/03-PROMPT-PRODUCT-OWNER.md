# PROMPT 3 — PRODUCT OWNER (6 HORAS)

Usa como contexto `00-PROMPT-BASE-KIRO.md`, lineamientos de arquitectura y foco funcional de Rentas Voluntarias.

## Misión
Traducir el alcance funcional del legado (Forms XML + referencia visual) a historias claras, criterios de aceptación verificables y prioridades de negocio para demo.

## Lineamiento transversal obligatorio
- Backend: **Java 21 + arquitectura hexagonal + Spring Boot + Gradle**.

## Objetivo de la sesión (timebox 6h)
1. **(80 min)** Definir user journeys del flujo demo.
2. **(110 min)** Escribir historias de usuario por módulo.
3. **(70 min)** Definir criterios de aceptación Gherkin.
4. **(60 min)** Priorización MoSCoW (Must/Should/Could/Won’t).
5. **(40 min)** Validar consistencia con PM y líder técnico.

## Entregables obligatorios
1. `README-product-owner.md`
2. `SPEC-product-owner.md`
3. `historias-usuario-rvt.md`
4. `criterios-aceptacion-rvt.feature`
5. `HANDOFF-product-owner.md`

## Alcance funcional a cubrir
- Login demo ADMIN/ADMIN.
- Menú estático con árbol de categorías.
- Módulo Expedir Rentas Voluntarias (`VOLTRCOT_fmb_xml`): activar, expedir póliza, consultar póliza.
- Módulo Beneficiarios (`VOLTRABE_fmb.xml`).
- Consulta general de clientes (`CLICSGEN_fmb.xml`).
- Parametrización ramo RV (`POLTRPPO_fmb.xml`).

## Restricciones
- No pedir fidelidad 1:1 de Oracle Forms; priorizar experiencia moderna compatible con lineamientos.
- No introducir alcance fuera de demo en esta iteración.
- No depender de SSO real para aceptar historias.

## Configuración obligatoria Gradle + DB (contexto de pruebas)
Incluye en los criterios y notas de historia que backend/integraciones usarán esta conexión Oracle:

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

Variables esperadas para proyectos Gradle:

```properties
DB_URL=jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(HOST=10.1.7.81)(PORT=1521))(CONNECT_DATA=(SID=svpa)))
DB_USERNAME=rvt
DB_PASSWORD=rvt
```

## Criterios de aceptación
- Historias priorizadas y estimables.
- Criterios verificables por QA manual durante demo.
- Trazabilidad entre historia ↔ endpoint ↔ pantalla.
