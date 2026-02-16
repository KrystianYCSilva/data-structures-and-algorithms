# AGENTS.md

Operational guide for AI coding agents working in this repository.

## Project Overview

Kotlin Multiplatform (KMP) library of data structures, algorithms, and optimization heuristics.
Group: `br.uem.din` | Kotlin 2.1.0 | Targets: JVM, JS (IR + Karma/ChromeHeadless), mingwX64 (native).

## Build & Test Commands

Use Gradle wrapper. On Windows use `gradlew.bat`, on POSIX use `./gradlew`.

```sh
# Build all targets
gradlew.bat build

# Run all tests (JVM + JS + native)
gradlew.bat check

# JVM tests only
gradlew.bat jvmTest

# JS tests only (requires ChromeHeadless)
gradlew.bat jsTest

# Single test class (JVM)
gradlew.bat jvmTest --tests "br.uem.din.datastructures.stack.StackTest"

# Single test method (JVM)
gradlew.bat jvmTest --tests "br.uem.din.datastructures.stack.StackTest.testArrayStackPushPop"

# Wildcard pattern
gradlew.bat jvmTest --tests "br.uem.din.datastructures.heap.*"

# Single test (JS — same pattern syntax)
gradlew.bat jsTest --tests "br.uem.din.datastructures.stack.StackTest"

# Publish artifacts
gradlew.bat publish
```

**Lint/Format:** No repository-wide linter task. `kotlin.code.style=official` is set in
`gradle.properties`. Follow Kotlin official style. If linting is needed, add ktlint/detekt plugins.

## Source Layout

```
src/
  commonMain/kotlin/br/uem/din/   # Platform-independent code (primary)
    algorithms/                    # Algorithm implementations (sorting/, graph/)
    datastructures/                # DS implementations (tree/, graph/, heap/, stack/, queue/, ...)
    extensions/                    # Kotlin extension functions
  commonTest/kotlin/br/uem/din/   # Platform-independent tests (kotlin.test)
  jvmMain/kotlin/                  # JVM-specific actual declarations
  jvmTest/kotlin/                  # JVM-specific tests
  jsMain/kotlin/                   # JS-specific actual declarations
  jsTest/kotlin/                   # JS-specific tests
  nativeMain/kotlin/               # mingwX64-specific actual declarations
docs/                              # ALGORITHM_CATALOG.md, USAGE_EXAMPLES.md, PROJECT_ROADMAP.md
```

## Code Style Guidelines

### Language & Formatting
- Kotlin official style (`kotlin.code.style=official`), 4-space indentation
- No trailing commas required; follow existing file conventions
- Braces on same line as declaration (K&R style)

### Package & File Organization
- Root package: `br.uem.din`
- Sub-packages mirror domain: `datastructures.stack`, `algorithms.graph`, `extensions`
- One primary class/interface per file; file name matches the type name (e.g., `ArrayStack.kt`)
- Interfaces in `*Interfaces.kt` files (e.g., `StackInterfaces.kt`, `QueueInterfaces.kt`)
- Node types in separate files (e.g., `BinaryNode.kt`, `AVLNode.kt`, `TrieNode.kt`)

### Naming Conventions
- Classes/Interfaces: `PascalCase` — `ArrayStack`, `MutableQueue`, `AbstractHeap`
- Functions: `camelCase` — `bubbleSort`, `shortestPath`, `siftDown`
- Properties/variables: `camelCase` — `leftChildIndex`, `priorityQueue`
- Constants/enums: `UPPER_SNAKE_CASE` for enum values — `VisitType.START`, `VisitType.EDGE`
- Type parameters: single uppercase letter — `<T>`
- Test classes: `{ClassUnderTest}Test` — `StackTest`, `DynamicArrayTest`
- Test methods: `test{Behavior}` — `testArrayStackPushPop`, `testLinkedStackClear`

### Imports
- Use wildcard imports for `kotlin.test.*` in test files
- Explicit imports for project-internal types (no wildcard for production code)
- Group: stdlib first, then project-internal (`br.uem.din.*`)

### Types & Generics
- Separate read-only and mutable interfaces (e.g., `Stack<T>` / `MutableStack<T>`)
- Use `expect`/`actual` for platform-specific implementations (e.g., `expect class ArrayStack<T>`)
- Prefer `Comparable<T>` bounds for sorting algorithms: `fun <T : Comparable<T>>`
- Use nullable returns (`T?`) instead of exceptions for empty-collection operations (`pop()`, `peek()`, `dequeue()`)

### Error Handling
- Return `null` for operations on empty collections (no exceptions thrown)
- No custom exception hierarchy; rely on stdlib exceptions where needed
- Validate preconditions implicitly (e.g., index bounds checked by underlying arrays)

### Documentation
- KDoc on all public types and functions (Portuguese, following existing convention)
- Include complexity table in class-level KDoc (Markdown table format)
- Include `@param`, `@return`, `@see` tags
- Reference academic sources: Cormen (CLRS), Knuth (TAOCP), original papers

### Testing
- Framework: `kotlin.test` (no Kotest, no JUnit directly)
- Place platform-independent tests in `commonTest`; platform-specific in `jvmTest`/`jsTest`
- Test naming: `test{Implementation}{Operation}` — `testArrayStackPushPop`
- Use `assertTrue`, `assertFalse`, `assertEquals`, `assertNull` from `kotlin.test`
- Each test method tests one behavior; keep tests independent

### Adding New Code
- New algorithms/data structures go in `src/commonMain/kotlin/br/uem/din/`
- Accompany with tests in `src/commonTest/kotlin/br/uem/din/`
- Update `docs/ALGORITHM_CATALOG.md` and `docs/USAGE_EXAMPLES.md` for new features
- Prefer extension functions in `extensions/` for cross-cutting utilities

## Agents Catalogue

| Agent | Purpose | Config |
|-------|---------|--------|
| kotlin-architect | Design and scaffold KMP modules | `.gemini/commands/kotlin-architect.toml` |
| kotlin-tester | Generate/run tests & property checks | `.gemini/commands/kotlin-tester.toml` |
| kotlin-release-operator | Package artifacts, Dokka, release prep | `.gemini/commands/kotlin-release-operator.toml` |

Agent stubs: `.github/agents/*.agent.md` | Prompts: `.github/prompts/*.prompt.md`

## Key References

- `.github/copilot-instructions.md` — Copilot-specific operational notes
- `.context/ai-assistant-guide.md` — runtime agent guide with skill paths
- `GEMINI.md` — Gemini CLI context
- `docs/ALGORITHM_CATALOG.md` — canonical list of implemented algorithms
- `docs/USAGE_EXAMPLES.md` — API usage examples

## Hierarchy

`AGENTS.md` (root) → `GEMINI.md` → `.github/copilot-instructions.md` → `.context/ai-assistant-guide.md`

---

## Itzamna Protocol

Este projeto utiliza o [Itzamna](https://github.com/KrystianYCSilva/itzamna) como orquestrador cognitivo.

**Antes de executar qualquer tarefa nao-trivial:**
1. Leia `.itzamna/kernel.md` (classificacao e roteamento)
2. Leia `.itzamna/memory.md` (estado atual do projeto)
3. Leia `.context/` (project.md, tech.md, rules.md)

**Ao finalizar tarefas significativas:**
- Atualize `.itzamna/memory.md` (estado atual, append-only)
- Para features/releases/hotfixes: proponha update em `MEMORY.md` (root, long-term)

Regras completas: `.itzamna/constitution.md`
Slash commands: veja `commands/` neste diretorio
