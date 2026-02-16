# Copilot instructions for algoritmos_otimizacao

Purpose: give future Copilot/assistant sessions the minimal, actionable facts to build, test, and reason about this repository.

Build, test, and lint commands
- Build (all targets): ./gradlew build  (Windows: gradlew.bat build)
- Run verification/tests: ./gradlew check  (runs configured test tasks)
- Run JVM tests only: ./gradlew jvmTest
- Run JS tests only (browser headless via Karma/ChromeHeadless): ./gradlew jsTest
- Run a single test (JVM): ./gradlew jvmTest --tests "fully.qualified.TestClassName*"  (use wildcard patterns)
- Run a single test (JS): ./gradlew jsTest --tests "fully.qualified.TestClassName*"  (Karma + ChromeHeadless)
- Publish artifacts: ./gradlew publish
- Lint/format: no repository-wide linter task detected in Gradle; kotlin.code.style=official is set in gradle.properties. If linting is needed, invoke ktlint/detekt via added Gradle plugins.

High-level architecture (big picture)
- Kotlin Multiplatform library. kotlin { jvm, js(IR) { browser + nodejs }, mingwX64("native") } in build.gradle.kts.
- Source layout:
  - src/commonMain/kotlin: platform-independent implementations (primary place for algorithms & DS)
  - src/commonTest / src/jvmTest / src/jsTest: test code using kotlin.test
  - src/jvmMain/kotlin, src/nativeMain/kotlin, src/jsMain/kotlin: platform-specific bits only
- JS tests run with Karma + ChromeHeadless (configured in build.gradle.kts testTask useKarma block).
- The Gradle Kotlin plugin and maven-publish are applied; artifacts can be published via Gradle.
- kotlin.version pinned in gradle.properties (use that value when generating build scripts).
- Extensive human-facing docs in docs/ (USAGE_EXAMPLES.md, ALGORITHM_CATALOG.md, PROJECT_ROADMAP.md) — use these as authoritative references for API examples and expected behavior.
- There are additional .github orchestrations (see .github/ITZAMNA.md and .github/agents/) that encode common assistant workflows; reuse prompts/agents where applicable.

Repository-specific conventions and patterns
- New algorithms and data structures: implement in src/commonMain/kotlin so they are available across targets.
- Tests: prefer kotlin.test and place unit tests under commonTest when platform-independent; use target-specific test modules (jvmTest/jsTest) for platform behaviour.
- Test naming: Gradle --tests accepts fully-qualified names and wildcards; use FQCN patterns when invoking individual tests programmatically.
- Native target is configured as mingwX64("native") and referenced as 'native' in sourceSets; do not assume other native targets exist.
- JS target uses the IR compiler with browser Karma configuration — generated artifacts and test behavior assume ChromeHeadless is available in CI.
- Do not assume a repository-level linter/formatter; respect kotlin.code.style=official when generating or suggesting Kotlin code.
- Use the Gradle wrapper (gradlew / gradlew.bat) for reproducible builds in CI and local dev.

Where to look first (important files)
- build.gradle.kts (project targets and test runners)
- gradle.properties (kotlin.version, code style)
- docs/USAGE_EXAMPLES.md and docs/ALGORITHM_CATALOG.md (canonical API examples and expected behaviors)
- .github/agents/ and .github/prompts/ (pre-built assistant workflows and prompts to reuse)

Notes for assistants
- Prefer platform-agnostic edits under src/commonMain/kotlin; if adding platform-specific code, place it under the appropriate sourceSet.
- When suggesting test runs or CI changes, reference the exact Gradle task (jvmTest, jsTest, check, build, publish).
- Be explicit about OS when emitting shell commands: for Windows use gradlew.bat, for POSIX use ./gradlew.

Last updated: 2026-02-15

Itzamna integration — rules and recommendations
- Itzamna metadata and short-term memory live under .itzamna/; long-term memory is MEMORY.md at the repository root. Respect append-only semantics for both.
- When creating or updating root CLI memos (AGENTS.md, GEMINI.md etc.), follow the Itzamna Protocol: prefer append-only changes, include an explicit rationale, and obtain human approval before committing changes.
- AGENTS.md is the canonical top-level agent catalogue; if present, reference it from GEMINI.md and .github/copilot-instructions.md.
- NEVER modify .context/ or .itzamna/memory.md without explicit human approval; always present proposed drafts and request confirmation (Human Gate) before writing.
- If a CLI-root file (e.g., GEMINI.md) exists and does not reference Itzamna, propose the standard append block (the Itzamna Protocol snippet) and ask the user whether to append it; do not auto-append without approval.
- When the repo requires initialization, recommend running: itzamna init (run from repository root). Provide OS-appropriate invocation instructions to the user.
- For assistants: if performing multi-step repository edits, present a concise plan, show proposed file diffs, and require explicit approval for each write that touches .context/ or .itzamna/.

Recommended quick actions (ask human before applying)
- Append Itzamna Protocol to CLI root files that lack it (copyable snippet available in .itzamna/templates/cli-compatibility.md).
- Fill .context/{project.md,tech.md,rules.md} with short drafts synthesized from docs/ and build files; show drafts and ask approval before committing.
- Populate .itzamna/memory.md initial short-term memory draft (presented as proposal) only after approval.


