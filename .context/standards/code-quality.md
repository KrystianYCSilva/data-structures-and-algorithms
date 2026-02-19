---
description: Code quality standards based on actual project conventions.
---

# Code Quality

## Formatting

- `kotlin.code.style=official` (gradle.properties)
- 4-space indentation, K&R brace style
- No linter/formatter configured (sem detekt, sem ktlint)
- Sem trailing commas; seguir convencoes do arquivo existente

## Naming

- Classes/Interfaces: `PascalCase` — `ArrayStack`, `MutableQueue`, `AbstractHeap`
- Functions: `camelCase` — `bubbleSort`, `shortestPath`, `siftDown`
- Properties/variables: `camelCase` — `leftChildIndex`, `priorityQueue`
- Enum values: `UPPER_SNAKE_CASE` — `VisitType.START`, `VisitType.EDGE`
- Type parameters: single letter — `<T>`
- Test classes: `{ClassUnderTest}Test` — `StackTest`, `DynamicArrayTest`
- Test methods: `test{Impl}{Operation}` — `testArrayStackPushPop`

## File Organization

- Root package: `br.uem.din`
- Sub-packages mirror domain: `datastructures.stack`, `algorithms.graph`, `extensions`
- Each package must live in its owning module (`datastructures/`, `algorithms/`, `extensions/`, `optimization/`)
- One primary class/interface per file; filename matches type name
- Interfaces in `*Interfaces.kt` (e.g., `StackInterfaces.kt`)
- Node types in separate files (e.g., `BinaryNode.kt`, `AVLNode.kt`)

## Imports

- Wildcard `kotlin.test.*` in test files
- Explicit imports for project-internal types (no wildcard in production code)
- Grouping: stdlib first, then `br.uem.din.*`

## Documentation

- KDoc em portugues em todos os tipos e funcoes publicas
- Tabela de complexidade (Markdown) no KDoc de classe
- Tags: `@param`, `@return`, `@see`
- Referencias academicas: Cormen (CLRS), Knuth (TAOCP), papers originais

## Error Handling

- Retornar `null` para operacoes em colecoes vazias (sem excecoes)
- Sem hierarquia de excecoes customizadas
- Preconditions validadas implicitamente (bounds checked by underlying arrays)
