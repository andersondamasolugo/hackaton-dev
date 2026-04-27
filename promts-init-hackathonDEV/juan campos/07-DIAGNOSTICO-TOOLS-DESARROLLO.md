# DIAGNÓSTICO DE TOOLS DE DESARROLLO (WINDOWS 11 / macOS)

Objetivo: validar e instalar herramientas mínimas para el hackatón:
- OpenJDK 21 (Java 21)
- Spring Boot (vía plugin/dependencias Gradle; CLI opcional)
- Gradle
- Angular 21 + MVVM
- Python 3
- Node.js (npm/npx)
- Git

---

## 1) Criterio técnico importante (para evitar bloqueos)

**Spring Boot NO requiere CLI `spring` para desarrollar** si se usa Gradle con `org.springframework.boot`.

Por eso, el mínimo exigido es:
- OpenJDK 21 (Java 21)
- Gradle
- Node/npm/npx
- Angular CLI
- Python 3
- Git

La CLI `spring` queda como opcional.

---

## 2) Windows 11 — Scoop + npm/npx

### 2.1 Instalación guiada (primero esto)

```powershell
# Instalar Scoop (si no existe)
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
iwr -useb get.scoop.sh | iex

# Repos útiles
scoop bucket add java
scoop bucket add extras

# Tools base
scoop install temurin21-jdk
scoop install gradle
scoop install nodejs-lts
scoop install python
scoop install git

# Angular CLI 21 (global)
npm install -g @angular/cli@21
```

### 2.2 Comprobación (después de instalar)

Ejecutar en PowerShell:

```powershell
$ErrorActionPreference = 'Continue'

function Test-Tool {
  param(
    [string]$Name,
    [string]$Command,
    [string]$Args = '--version'
  )

  Write-Host "`n=== $Name ==="
  if (-not (Get-Command $Command -ErrorAction SilentlyContinue)) {
    Write-Host "NO INSTALADO o no está en PATH"
    return
  }

  try {
    & $Command $Args 2>&1
  } catch {
    Write-Host "Error ejecutando $Command $Args"
    Write-Host $_
  }
}

Write-Host "=== Comprobación Windows 11 ==="
Test-Tool -Name 'scoop'   -Command 'scoop'  -Args '--version'
Test-Tool -Name 'java'    -Command 'java'   -Args '-version'
Test-Tool -Name 'javac'   -Command 'javac'  -Args '-version'
Test-Tool -Name 'gradle'  -Command 'gradle' -Args '-v'
Test-Tool -Name 'node'    -Command 'node'   -Args '-v'
Test-Tool -Name 'npm'     -Command 'npm'    -Args '-v'
Test-Tool -Name 'npx'     -Command 'npx'    -Args '-v'
Test-Tool -Name 'python'  -Command 'python' -Args '--version'
Test-Tool -Name 'python3' -Command 'python3' -Args '--version'
Test-Tool -Name 'git'     -Command 'git'    -Args '--version'

# Angular CLI (estándar recomendado: npx, no depende de ng en PATH)
Write-Host "`n=== angular-cli (npx) ==="
npx -y @angular/cli@21 version 2>&1
```

### 2.3 Si `ng version` no muestra salida

Prueba este bloque:

```powershell
Get-Command ng* | Select-Object Name, Source
npx -y @angular/cli@21 version
```

Si `npx -y @angular/cli@21 version` funciona, el problema es de ejecución de `ng.ps1` en PowerShell (policy) o PATH, no de instalación.

### 2.4 Verificación rápida final

```powershell

# Verificación
java -version
gradle -v
node -v
npm -v
npx -v
npx -y @angular/cli@21 version
python --version
git --version
```

---

## 3) macOS — Homebrew + npm

### 3.1 Instalación guiada (primero esto)

```bash
# Instalar Homebrew (si no existe)
# Ir a https://brew.sh y ejecutar el comando oficial de instalación

# Tools base
brew install openjdk@21
brew install gradle
brew install node
brew install python
brew install git

# Angular CLI 21
npm install -g @angular/cli@21
```

### 3.2 Comprobación (después de instalar)

```bash
brew --version
java -version
javac -version
gradle -v
node -v
npm -v
npx -v
npx -y @angular/cli@21 version
python3 --version
git --version
```

### 3.3 Verificación rápida final

```bash
java -version
gradle -v
node -v
npm -v
npx -v
npx -y @angular/cli@21 version
python3 --version
git --version
```

---

## 4) Verificación de proyecto (Angular + Spring Boot + Gradle)

### Angular 21
```bash
npx -y @angular/cli@21 new rvt-frontend --routing --style=scss
cd rvt-frontend
npx -y @angular/cli@21 version
```

### Spring Boot + Gradle (Java 21)
Validar que `build.gradle` use:
- plugin `org.springframework.boot`
- `sourceCompatibility` / `toolchain` Java 21

Comando de verificación:
```bash
./gradlew -v
./gradlew tasks
```

---

## 5) Checklist final por persona (DoR técnico)

- [ ] OpenJDK 21 (Java 21) OK
- [ ] Gradle OK
- [ ] Node + npm + npx OK
- [ ] Angular CLI 21 OK
- [ ] Python 3 OK
- [ ] Git OK
- [ ] Proyecto Angular arranca
- [ ] Proyecto Spring Boot/Gradle compila

---

## 6) Conexión Oracle para pruebas integradas (hackatón)

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

Variables estándar:

```properties
DB_URL=jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(HOST=10.1.7.81)(PORT=1521))(CONNECT_DATA=(SID=svpa)))
DB_USERNAME=rvt
DB_PASSWORD=rvt
```

> Nota: para producción, mover credenciales a vault/secret manager y nunca dejarlas en texto plano.
