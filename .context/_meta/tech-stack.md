---
description: Tech stack, build metadata, modules and architecture.
---

# Tech Stack

## Stack

| Camada | Tecnologia | Versao/Config |
|---|---|---|
| Linguagem | Kotlin | 2.1.0 |
| Build | Gradle Wrapper | multi-modulo |
| Plugin | Kotlin Multiplatform | `kotlin("multiplatform")` |
| Testes | kotlin.test | unico framework |
| Publicacao | maven-publish | por modulo |
| BOM | java-platform | `:bom` |

- Kotlin `2.1.0` (from `gradle.properties`)
- Kotlin Multiplatform plugin `kotlin("multiplatform")`
- Gradle Wrapper (multi-module build)
- kotlin.test (single testing framework)
- maven-publish + java-platform (BOM)

## Build metadata

- Group: `br.uem.din`
- Version: `0.1.0`
- Root project name: `algoritmos-otimizacao`
- Repositories: `mavenCentral()`, `google()`, `gradlePluginPortal()`

## Targets

- JVM: `withJava()` + `jvmToolchain(8)`
- JS IR: browser + nodejs; tests with Karma + ChromeHeadless
- Native: `mingwX64("native")`

## Modulos

- `:datastructures`
- `:algorithms` -> depende de `:datastructures` (`api(project(":datastructures"))`)
- `:extensions` -> depende de `:datastructures` e `:algorithms`
- `:optimization` -> independente (sem dependencia interna)
- `:bom`
- `:sample` -> consome os demais modulos (`:datastructures`, `:algorithms`, `:optimization`) para exemplos praticos

## Arquitetura

- Base de dominio em `:datastructures`
- Camada de algoritmos classicos em `:algorithms` reaproveitando DS
- Camada ergonomica em `:extensions` com funcoes de extensao
- Trilha paralela independente para heuristicas em `:optimization`
- Alinhamento de versao para consumidores via `:bom`
- Modulo prático e didático `:sample` demonstrando casos de uso e capacidades

## Comandos principais

```sh
gradlew.bat check
gradlew.bat :datastructures:check
gradlew.bat :algorithms:check
gradlew.bat :extensions:check
gradlew.bat :optimization:check
```

## Links internos

- Workflow de publicacao: `../workflows/deployment.md`
- Navegacao do contexto: `../README.md`
