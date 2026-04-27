# MAPA DE PERSONAS Y SECUENCIA DE EJECUCIÓN

## Asignación sugerida
- **person1** → `01-PROMPT-LIDER-TECNICO.md` (**rol mixto: líder técnico + desarrollador backend**)
- **person2** → `02-PROMPT-PROJECT-MANAGER.md`
- **person3** → `03-PROMPT-PRODUCT-OWNER.md`
- **person4** → `04-PROMPT-INTEGRADOR-N8N.md`
- **person5** → `05-PROMPT-DESARROLLADOR.md`

## Asignación personalizada (nombres reales)
- **juan gonzalez** → Líder técnico + desarrollador backend (`01`)
- **miguel mora** → Project Manager (`02`)
- **angela mora** → Product Owner (`03`)
- **juan campos** → Integrador n8n (`04`)
- **anderson lugo** → Desarrollador frontend/backend integración (`05`) y **coordinador de PR/merge** del repositorio público.

## Orden de arranque recomendado (primeros 30-45 min)
1. Person1 define contratos v1 y arranca implementación backend core.
2. Product Owner alinea historias contra esos contratos.
3. PM cierra cronograma y checkpoints con dependencias reales.
4. Person5 inicia frontend con contrato congelado v1.
5. Integrador n8n conecta al contrato v1 y usa mocks cuando aplique.

## Frontera de trabajo (para evitar choque entre person1 y person5)
- **person1:** backend core (auth/login, menu/tree, endpoint piloto de póliza), contratos OpenAPI y reglas técnicas.
- **person5:** frontend Angular MVVM, integración UI Bundle, consumo de APIs y flujo funcional demo.
- Cambios de contrato después de Checkpoint 1 solo con versionado (`v1.1`) y nota en handoff.

## Sincronización PR (repositorio central)
- Repo de coordinación: `REPO-CENTRAL-HACKATON`
- Naming de ramas sugerido:
  - `person1/techlead-backend-core`
  - `person2/pm-plan-riesgos`
  - `person3/po-historias-criterios`
  - `person4/n8n-workflows-integracion`
  - `person5/dev-frontend-flujo-rvt`
- Regla: no mezclar más de un objetivo grande por PR.
- Merge strategy sugerida: squash merge para mantener historial limpio del hackatón.
- Gatekeeper de merge: **Anderson Lugo** valida checklist de integración antes de aprobar merge a `main`.

## Checkpoints colaborativos
- **Checkpoint 1 (min 90):** contrato API y alcance funcional congelado.
- **Checkpoint 2 (min 240):** integración parcial end-to-end validada.
- **Checkpoint 3 (min 340):** evidencia final + handoff por rol.

## Regla anti-choques
Nadie cambia alcance de otro rol sin registrar impacto en su `HANDOFF-<rol>.md` y notificar en checkpoint.
