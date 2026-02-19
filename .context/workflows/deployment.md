---
description: Build, validation and publication workflow for release artifacts.
---

# Build & Publication Workflow

## 1) Preflight

```sh
gradlew.bat clean
gradlew.bat check
```

Gate obrigatorio: build e testes verdes em JVM + JS + Native.

## 2) Module validation

```sh
gradlew.bat :datastructures:check
gradlew.bat :algorithms:check
gradlew.bat :extensions:check
gradlew.bat :optimization:check
```

## 3) Publication artifacts

- `br.uem.din:datastructures:0.1.0`
- `br.uem.din:algorithms:0.1.0`
- `br.uem.din:extensions:0.1.0`
- `br.uem.din:optimization:0.1.0`
- `br.uem.din:bom:0.1.0`

## 4) Publish command

```sh
gradlew.bat publish
```

## 5) Release checklist (manual)

1. Versao coerente em todos os modulos
2. POM completo (name, description, SCM, license, developers)
3. CHANGELOG/roadmap/documentacao atualizada
4. Consumidor de teste resolvendo BOM e modulos

## 6) Rollback policy

- Nao sobrescrever versao publicada
- Corrigir em nova versao (`0.1.x`)
