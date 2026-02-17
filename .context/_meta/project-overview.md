---
description: Project overview, goals, scope, and current state.
---

# Project Overview

## What

Biblioteca academica em Kotlin Multiplatform (JVM/JS/Native) de estruturas de dados (36), algoritmos fundamentais (~45 planejados) e heuristicas de otimizacao (8+ planejadas), com rigor cientifico e referencias a Cormen (CLRS), Knuth (TAOCP) e papers originais.

## Why

Fornecer implementacoes portaveis, testadas e bem-documentadas de algoritmos classicos e meta-heuristicas para estudo, benchmarks e reuso em projetos academicos. O formato KMP permite execucao em JVM, browser (JS) e desktop nativo (mingwX64).

## Scope

**Dentro do escopo:**
- Estruturas de dados classicas e avancadas (lineares, arvores, grafos, heaps, spatial, probabilisticas)
- Algoritmos fundamentais (sorting, searching, graph, string matching, DP, greedy, numerical, D&C, backtracking)
- Heuristicas e meta-heuristicas de otimizacao (local search, population-based, hybrid)
- Benchmarks padronizados (TSP, funcoes continuas: Sphere, Rastrigin, Rosenbrock, Ackley, Schwefel)
- Documentacao academica com complexidades e referencias

**Fora do escopo:**
- Servicos de aplicacao ou APIs REST
- Scripts de deploy em producao
- UI/frontend

## Status

Desenvolvimento ativo — **~45% completo**.

- **Fase 1 (Estruturas de Dados):** 100% (36/36 completas). Implementacao robusta de estruturas lineares, arvores, grafos, heaps, etc.
- **Fase 2 (Algoritmos Fundamentais):** ~15% (Iniciado). Implementados apenas algoritmos basicos de ordenacao (Bubble, Insertion) e busca em grafos (BFS, DFS, Dijkstra, A*). Restante planejado.
- **Fase 3 (Heuristicas):** 0% (Planejado). Nenhuma implementacao presente no momento.
- **Metricas:** ~13.500 LOC (estimado), testes focados em estruturas de dados.
