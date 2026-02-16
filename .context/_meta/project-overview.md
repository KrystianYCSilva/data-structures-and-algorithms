---
description: Project overview, goals, scope, and current state.
---

# Project Overview

## What

Biblioteca academica em Kotlin Multiplatform (JVM/JS/Native) de estruturas de dados (36), algoritmos fundamentais (~45) e heuristicas de otimizacao (8+), com rigor cientifico e referencias a Cormen (CLRS), Knuth (TAOCP) e papers originais.

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

Desenvolvimento ativo — 89% completo.
- Fase 1 (Estruturas de Dados): 36/36 completas
- Fase 2 (Algoritmos Fundamentais): ~45 algoritmos completos, 199 testes
- Fase 3A (Heuristicas Classicas): 4/4 (HC, SA, TS, GA) — 91 testes
- Fase 3B (Heuristicas Avancadas): 4/4 (ILS, GRASP, PSO, ACO) — 41 testes
- Fase 3C (Especializadas): planejada (DE, VNS, Memetic, LNS)
- Metricas: ~21.500 LOC, ~639 testes, 0 failures
