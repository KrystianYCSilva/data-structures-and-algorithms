---
description: Architecture patterns currently used in the repository.
---

# Architecture Patterns

## 1) Layered Multi-module

```
:datastructures   (foundation)
      ^
      |
:algorithms       (classical algorithms)
      ^
      |
:extensions       (ergonomic API over DS + algorithms)

:optimization     (independent lane)

:bom              (version alignment)
```

## 2) Common-first KMP

- Implementacao primaria em `commonMain`
- Adaptacoes por plataforma em `:datastructures/src/{jvm,js,native}Main`

## 3) Factory expect/actual API

- API publica para estruturas adaptadas por plataforma via funcoes de fabrica:
  - `arrayStackOf()`
  - `arrayQueueOf()`
  - `priorityQueueOf()`
  - `bitSetOf()`
  - `redBlackTreeOf()`

## 4) Read-only / Mutable split

- Separacao consistente de interfaces de leitura e escrita
- `asReadOnly()` para views sem copia (live views)

## 5) Algorithm as top-level function or service class

- Sorting/searching majoritariamente top-level functions
- Algoritmos de grafo com classes de servico quando mantem contexto (`Dijkstra`, `AStar`, `BreadthFirstSearch`)

## 6) Optimization problem abstraction

- Heuristicas operam sobre `OptimizationProblem<T>`
- Interfaces especializadas para familias de heuristicas:
  - `BoundedVectorProblem` (PSO/DE)
  - `CostMatrixProblem` (ACO)
