# AI Assistant Guide

This file provides guidance for AI agents operating on this repository and points to the primary skills and agents to use.

## Purpose
- Provide the system prompt and navigation hints for agents.
- List installed skills and agent commands relevant to Kotlin development in this repo.

## Kotlin Skills & Agents (paths)

Skills:
- .gemini/skills/kotlin-fundamentals/SKILL.md
- .gemini/skills/kotlin-multiplatform-library-fundamentals/SKILL.md
- .gemini/skills/kotlin-oo-fundamental/SKILL.md
- .gemini/skills/kotlin-functional-fundamental/SKILL.md

Agent commands (.gemini):
- .gemini/commands/kotlin-architect.toml
- .gemini/commands/kotlin-tester.toml
- .gemini/commands/kotlin-release-operator.toml

Agent stubs (.github):
- .github/agents/kotlin-architect.agent.md
- .github/agents/kotlin-tester.agent.md
- .github/agents/kotlin-release-operator.agent.md

## How to use
- Agents should read the skill files listed above when performing Kotlin-related tasks.
- Prefer .gemini commands for automated runs; consult .github/agents/ for human-invoked workflows.
