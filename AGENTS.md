---
description: Top-level agent catalogue and usage memo for repository operators and assistants.
---

# Agents Memo (AGENTS.md)

This file is the top-level catalogue of agents, commands, and guidance for repository-integrated assistants. It should be the first place to look for available agents, their purpose, and where to find their implementation.

## Agents Catalogue

- kotlin-architect — Design and scaffold KMP modules; consult .gemini/commands/kotlin-architect.toml and .github/agents/kotlin-architect.agent.md
- kotlin-tester — Generate and run tests, property checks, and CI test jobs; consult .gemini/commands/kotlin-tester.toml and .github/agents/kotlin-tester.agent.md
- kotlin-release-operator — Package artifacts, run Dokka, and prepare release metadata; consult .gemini/commands/kotlin-release-operator.toml and .github/agents/kotlin-release-operator.agent.md

## How to use

1. Consult this AGENTS.md for the canonical agent list.
2. For CLI automation, use .gemini/commands/*.toml files.
3. For human-invoked workflows, consult .github/agents/*.agent.md and .github/prompts/*.prompt.md.
4. For runtime agent behavior and system prompts, consult .context/ai-assistant-guide.md.

## Hierarchy and ownership

AGENTS.md (root) -> GEMINI.md -> .github/copilot-instructions.md -> .context/ai-assistant-guide.md

