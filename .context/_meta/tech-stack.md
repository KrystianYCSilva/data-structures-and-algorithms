---
description: Tech stack and architecture decisions based on actual build.gradle.kts and gradle.properties.
---

# Tech Stack & Architecture

## Stack

| Camada | Tecnologia | Versao |
|--------|------------|--------|
| Linguagem | Kotlin | 2.1.0 |
| Framework | Kotlin Multiplatform (KMP) | kotlin-multiplatform plugin |
| Build | Gradle Wrapper | 8.7 |
| Testes | kotlin.test (commonTest, jvmTest, jsTest) | stdlib |
| JS Test Runner | Karma + ChromeHeadless | via useKarma { useChromeHeadless() } |
| Publicacao | maven-publish plugin | Gradle built-in |

## Targets

| Target | Config | Notas |
|--------|--------|-------|
| JVM | `jvm { withJava() }` | Target principal |
| JS (IR) | `js(IR) { browser { ... }; nodejs() }` | Browser + Node, Karma para testes |
| Native | `mingwX64("native")` | Windows 64-bit, sem C-interop |

## Arquitetura

Biblioteca KMP com codigo compartilhado em `commonMain` e adaptadores por plataforma via `expect`/`actual`.
Nao ha modulos Gradle separados — tudo em um unico modulo raiz com source sets por target.

- Group: `br.uem.din`
- Version: `1.0-SNAPSHOT`
- Code style: `kotlin.code.style=official` (gradle.properties)

## Estrutura de diretorios

```
src/
  commonMain/kotlin/br/uem/din/    # Codigo principal (DS, algoritmos, heuristicas)
    datastructures/                  # 36 estruturas (stack, queue, heap, tree, graph, ...)
    algorithms/                      # ~45 algoritmos (sorting, searching, graph, dp, ...)
    extensions/                      # Extension functions utilitarias
  commonTest/kotlin/br/uem/din/    # Testes platform-independent (kotlin.test)
  jvmMain/kotlin/                   # actual declarations (ArrayStack, BitSet)
  jsMain/kotlin/                    # actual declarations JS
  nativeMain/kotlin/                # actual declarations mingwX64
  jvmTest/kotlin/                   # Testes JVM-specific
  jsTest/kotlin/                    # Testes JS-specific (Karma)
docs/                               # ALGORITHM_CATALOG, USAGE_EXAMPLES, PROJECT_ROADMAP
```

## Dependencias

**Unica dependencia**: `kotlin("test")` para commonTest, jvmTest, jsTest.

Nao ha dependencias externas (sem kotlinx.coroutines, sem kotlinx.serialization, sem Kotest, sem Dokka configurado).
Toda implementacao usa apenas Kotlin stdlib.
