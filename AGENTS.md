# AGENTS.md

Operational guide for AI coding agents working in this repository.

## Project Overview

Kotlin Multiplatform (KMP) library of data structures, algorithms, and optimization heuristics.
Group: `br.uem.din` | Kotlin 2.1.0 | Targets: JVM, JS (IR + Karma/ChromeHeadless), mingwX64 (native).

## Build & Test Commands

Use Gradle wrapper. On Windows use `gradlew.bat`, on POSIX use `./gradlew`.

```sh
# Build all targets (all modules)
gradlew.bat build

# Run all tests (JVM + JS + native, all modules)
gradlew.bat check

# Single module tests
gradlew.bat :datastructures:check
gradlew.bat :algorithms:check
gradlew.bat :extensions:check
gradlew.bat :optimization:check

# JVM tests only (single module)
gradlew.bat :datastructures:jvmTest

# JS tests only (requires ChromeHeadless)
gradlew.bat :datastructures:jsTest

# Single test class (JVM)
gradlew.bat :datastructures:jvmTest --tests "br.uem.din.datastructures.stack.StackTest"

# Single test method (JVM)
gradlew.bat :datastructures:jvmTest --tests "br.uem.din.datastructures.stack.StackTest.testArrayStackPushPop"

# Wildcard pattern
gradlew.bat :datastructures:jvmTest --tests "br.uem.din.datastructures.heap.*"

# Publish artifacts
gradlew.bat publish
```

**Lint/Format:** No repository-wide linter task. `kotlin.code.style=official` is set in
`gradle.properties`. Follow Kotlin official style. If linting is needed, add ktlint/detekt plugins.

## Module Structure

Multi-module Gradle project with 5 modules:

| Module | Artifact | Dependencies | Description |
|--------|----------|-------------|-------------|
| `:datastructures` | `br.uem.din:datastructures` | — | 36 data structures (stack, queue, tree, graph, heap, hash, etc.) |
| `:algorithms` | `br.uem.din:algorithms` | `datastructures` | 46 algorithms (sorting, searching, graph, string, DP, greedy, etc.) |
| `:extensions` | `br.uem.din:extensions` | `datastructures`, `algorithms` | Kotlin extension functions bridging DS and algorithms |
| `:optimization` | `br.uem.din:optimization` | — | 12 optimization heuristics + 7 problem modelings |
| `:bom` | `br.uem.din:bom` | (platform) | Bill of Materials for version alignment |

## Source Layout

```
datastructures/
  src/commonMain/kotlin/br/uem/din/
    datastructures/                # 36 DS (tree/, graph/, heap/, stack/, queue/, hash/, ...)
    extensions/                    # MutableList.swap(), GraphExtensions (DS-only deps)
  src/commonTest/                  # Platform-independent tests
  src/{jvm,js,native}Main/        # expect/actual: ArrayStack, ArrayQueue, PriorityQueue, DoublyLinkedList, RedBlackTree, BitSet
  src/{jvm,js,native}Test/        # Platform-specific interop tests

algorithms/
  src/commonMain/kotlin/br/uem/din/
    algorithms/                    # sorting/, searching/, graph/, string/, dp/, greedy/, numerical/, backtracking/, divideconquer/, geometry/, network/, combinatorics/
  src/commonTest/                  # Tests

extensions/
  src/commonMain/kotlin/br/uem/din/
    extensions/                    # CollectionExtensions, MathExtensions, StringExtensions
  src/commonTest/                  # Tests

optimization/
  src/commonMain/kotlin/br/uem/din/
    optimization/                  # 12 heuristics + 7 problem types + benchmarks
  src/commonTest/                  # Tests

bom/                               # BOM (java-platform)
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
- New data structures go in `datastructures/src/commonMain/kotlin/br/uem/din/datastructures/`
- New algorithms go in `algorithms/src/commonMain/kotlin/br/uem/din/algorithms/`
- New extensions go in `extensions/src/commonMain/kotlin/br/uem/din/extensions/`
- New heuristics go in `optimization/src/commonMain/kotlin/br/uem/din/optimization/`
- Accompany with tests in the corresponding `src/commonTest/` directory
- Update `docs/ALGORITHM_CATALOG.md` and `docs/USAGE_EXAMPLES.md` for new features

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
