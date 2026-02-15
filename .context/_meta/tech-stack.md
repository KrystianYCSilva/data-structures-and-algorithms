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

- .gemini/skills/kotlin-fundamentals/SKILL.md
- .gemini/skills/kotlin-multiplatform-library-fundamentals/SKILL.md
- .gemini/skills/kotlin-oo-fundamental/SKILL.md
- .gemini/skills/kotlin-functional-fundamental/SKILL.md

- .gemini/commands/kotlin-architect.toml
- .gemini/commands/kotlin-tester.toml
- .gemini/commands/kotlin-release-operator.toml

- .github/agents/kotlin-architect.agent.md
- .github/agents/kotlin-tester.agent.md
- .github/agents/kotlin-release-operator.agent.md
