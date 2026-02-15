---
description: Tech stack and architecture decisions; includes detected tools and important skill/agent paths.
---

# Tech Stack & Architecture

## Stack

| Camada | Tecnologia | Versao |
|--------|------------|--------|
| Linguagem | Kotlin | 2.1.0 |
| Framework | Kotlin Multiplatform (KMP) | kotlin-multiplatform plugin |
| Database | *(ex: PostgreSQL 16)* | |
| Build | Gradle Wrapper | 8.7 |
| Testes | kotlin("test") across targets; Kotest may be used in examples | |

## Arquitetura

*(Descreva o padrao: monolito, microservices, hexagonal, etc.)*

## Estrutura de diretorios

```
src/
├── ...
```

## Dependencias criticas

*(Libs/servicos externos dos quais o projeto depende)*

## Relevant Kotlin Skills & Agents

The repository includes the following Kotlin-focused skills and agent commands (use these paths to load guidance or invoke automated commands):

Skills:
- .gemini/skills/kotlin-fundamentals/SKILL.md
- .gemini/skills/kotlin-multiplatform-library-fundamentals/SKILL.md
- .gemini/skills/kotlin-oo-fundamental/SKILL.md
- .gemini/skills/kotlin-functional-fundamental/SKILL.md

Gemini commands:
- .gemini/commands/kotlin-architect.toml  # design & scaffold KMP modules
- .gemini/commands/kotlin-tester.toml     # generate and run Kotest tests
- .gemini/commands/kotlin-release-operator.toml # Dokka, packaging, CI snippets

GitHub agent stubs:
- .github/agents/kotlin-architect.agent.md
- .github/agents/kotlin-tester.agent.md
- .github/agents/kotlin-release-operator.agent.md

Notes:
- Kotlin version detected: 2.1.0 (from gradle.properties)
- Gradle wrapper detected: 8.7 (from gradle/wrapper/gradle-wrapper.properties)
- Project is configured as Kotlin Multiplatform with JVM, JS(IR) and mingwX64 targets (see build.gradle.kts)
