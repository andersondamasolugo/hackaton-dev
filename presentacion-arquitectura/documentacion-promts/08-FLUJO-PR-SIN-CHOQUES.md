# FLUJO DE PR SIN CHOQUES (HACKATON DEV)

Repo central: `REPO-CENTRAL-HACKATON`

## 1) Regla de oro
Antes de abrir PR, cada persona debe actualizar su rama con `main` y verificar que no rompe contratos API/UI.

## 2) Convención de ramas
- `juan-gonzalez/techlead-backend-core`
- `miguel-mora/pm-plan-riesgos`
- `angela-mora/po-historias-criterios`
- `juan-campos/n8n-workflows-integracion`
- `anderson-lugo/dev-frontend-integracion`

## 3) Orden recomendado de PR (para evitar bloqueos)
1. `juan-gonzalez/techlead-backend-core` (contratos + backend base)
2. `angela-mora/po-historias-criterios` (trazabilidad funcional)
3. `miguel-mora/pm-plan-riesgos` (plan operativo final)
4. `juan-campos/n8n-workflows-integracion` (integración contra contrato)
5. `anderson-lugo/dev-frontend-integracion` (integración final E2E)

## 4) Checklist obligatorio antes de abrir PR
- [ ] Rama actualizada con `main`
- [ ] Cambios limitados al scope del rol
- [ ] Evidencias incluidas (capturas, requests/responses, JSON n8n, etc.)
- [ ] Handoff actualizado (`HANDOFF-<rol>.md`)
- [ ] No se tocaron repositorios restringidos

## 5) Checklist de revisión (Anderson gatekeeper)
- [ ] El PR no pisa responsabilidades de otro rol
- [ ] Contratos API no cambiaron sin versionado (`v1` → `v1.1`)
- [ ] No hay secretos nuevos en archivos
- [ ] Estructura y naming consistentes
- [ ] Se puede integrar sin romper el flujo demo

## 6) Comandos mínimos por persona (Windows/macOS)
```bash
git checkout main
git pull origin main
git checkout -b <mi-rama>
# ... cambios ...
git add .
git commit -m "feat: <resumen-corto>"
git push -u origin <mi-rama>
```

## 7) Plantilla corta para descripción de PR
```markdown
## Contexto
¿Qué problema o entregable cubre este PR?

## Cambios
- Punto 1
- Punto 2

## Evidencia
- Capturas / requests / outputs

## Riesgos
- Riesgo 1 + mitigación

## Checklist
- [ ] Scope correcto
- [ ] Handoff actualizado
- [ ] Sin cambios fuera del rol
```
