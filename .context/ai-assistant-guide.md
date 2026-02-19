---
description: System prompt and navigation hints for AI agents operating on this repository.
---

# AI Assistant Guide

## Quick Start

1. Leia `AGENTS.md` (root) para comandos de build/test e code style
2. Leia `.itzamna/kernel.md` para classificacao de tarefas (Reflexo/Deliberado/Profundo)
3. Leia `.itzamna/memory.md` para estado atual do projeto

## Key Facts

- **Linguagem**: Kotlin 2.1.0 Multiplatform (JVM, JS IR, mingwX64)
- **Dependencias**: ZERO externas — apenas Kotlin stdlib + kotlin.test
- **Testes**: `kotlin.test` apenas (sem Kotest, sem JUnit)
- **Linter**: Nenhum configurado; seguir `kotlin.code.style=official`
- **Build**: `gradlew.bat build` / `gradlew.bat check`
- **Modulos**: `:datastructures`, `:algorithms`, `:extensions`, `:optimization`, `:bom`

## Where to Put New Code

- Data structures: `datastructures/src/commonMain/kotlin/br/uem/din/datastructures/`
- Algorithms: `algorithms/src/commonMain/kotlin/br/uem/din/algorithms/`
- Extensions: `extensions/src/commonMain/kotlin/br/uem/din/extensions/`
- Optimization: `optimization/src/commonMain/kotlin/br/uem/din/optimization/`
- Testes comuns: `<modulo>/src/commonTest/kotlin/br/uem/din/`
- Platform-specific: `datastructures/src/{jvm,js,native}Main/kotlin/` (apenas expect/actual)

## Build commands by module

- `gradlew.bat :datastructures:check`
- `gradlew.bat :algorithms:check`
- `gradlew.bat :extensions:check`
- `gradlew.bat :optimization:check`
- `gradlew.bat check` (todos)

## Kotlin Skills & Agents

Skills (.opencode/skills/):
- kotlin-fundamentals, kotlin-oo-fundamental, kotlin-functional-fundamental
- kotlin-multiplatform-library-fundamentals, kotest-fundamentals

Agent commands (.gemini/commands/):
- kotlin-architect.toml — design & scaffold KMP modules
- kotlin-tester.toml — generate and run tests
- kotlin-release-operator.toml — packaging, Dokka, release prep

Agent stubs (.github/agents/):
- kotlin-architect.agent.md, kotlin-tester.agent.md, kotlin-release-operator.agent.md

## Context Hierarchy

```
AGENTS.md (root)
  -> GEMINI.md
  -> .github/copilot-instructions.md
  -> .context/ai-assistant-guide.md (this file)
  -> .itzamna/kernel.md (cognitive routing)
```
