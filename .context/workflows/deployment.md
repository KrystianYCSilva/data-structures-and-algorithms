---
description: Build, test, and publication workflow based on actual project configuration.
---

# Build & Publication Workflow

## Build

```sh
gradlew.bat build        # Compila todos os targets (JVM, JS, Native)
```

Produz artefatos para JVM (JAR), JS (IR) e mingwX64 (klib).

## Test

```sh
gradlew.bat check        # Todos os testes
gradlew.bat jvmTest      # Apenas JVM
gradlew.bat jsTest       # Apenas JS (Karma/ChromeHeadless requerido)
```

## Publication

O plugin `maven-publish` esta configurado. Publicacao via:

```sh
gradlew.bat publish
```

Publica artefatos para repositorios Maven configurados. Atualmente `1.0-SNAPSHOT`.

## CI/CD

Nao ha pipeline CI/CD configurado no repositorio (sem GitHub Actions, sem Jenkinsfile). Build e testes sao executados localmente.

## Rollback

Sem mecanismo formal. Usar versionamento de artefatos e nao sobrescrever versoes publicadas.
