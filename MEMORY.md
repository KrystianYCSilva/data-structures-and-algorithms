---
description: |
  Long-term project memory: completed features, releases, hotfixes, and accumulated metrics.
  Use when: reviewing project history, understanding what was built, or onboarding new contributors.
---

# MEMORY.md - Long-Term Project History

> Historico de features, releases e hotfixes do projeto.
> Atualizado quando: feature completa, release, hotfix critico.
> Append-only: nunca apague entradas.

---

## Project

- **Nome:** algoritmos_otimizacao
- **Stack:** Kotlin 2.1.0 Multiplatform (JVM/JS/mingwX64), Gradle, kotlin.test

---

## T0: Regra de Atualizacao (OBRIGATORIA)

SEMPRE atualize este arquivo quando:
1. Feature completa (implementada e testada)
2. Release (versao publicada ou fase concluida)
3. Hotfix critico aplicado
4. Breaking change introduzido

Protocolo:
1. Proponha a atualizacao ao final da implementacao
2. Mostre o diff completo
3. AGUARDE aprovacao antes de escrever
4. NUNCA modifique este arquivo sem aprovacao

---

## Features Implementadas

### Fase 1 — Estruturas de Dados (36/36)
- **Data:** 2025-02-12
- **Arquivos:** src/commonMain/kotlin/br/uem/din/datastructures/ (stack, queue, heap, tree, graph, array, linkedlist, hash, spatial, probabilistic, skiplist, unionfind, bitset, set)
- **Testes:** 308 testes (Fases 1A-1D)
- **Notas:** Inclui lineares (5), associativas/arvores (5), balanceadas (4), e 22 estruturas adicionais KMP. expect/actual para ArrayStack, BitSet.

### Fase 2 — Algoritmos Fundamentais (~45)
- **Data:** ~2025 (Waves 1-3)
- **Arquivos:** src/commonMain/kotlin/br/uem/din/algorithms/ (sorting, graph, searching, string, dp, greedy, numerical, divideconquer, backtracking)
- **Testes:** 199 testes, 0 failures
- **Notas:** 10 sorting, 6 searching, 5 graph, 4 string matching, 8 DP, 3 greedy, 5 numerical, 5 D&C, 4 backtracking

### Fase 3A — Heuristicas Classicas (4/4)
- **Data:** 2026-02-13
- **Arquivos:** src/commonMain/kotlin/br/uem/din/algorithms/optimization/ (HillClimbing, SimulatedAnnealing, TabuSearch, GeneticAlgorithm + infraestrutura)
- **Testes:** 91 testes (inclui 32 de infraestrutura/benchmarks)
- **Notas:** OptSolution/OptResult types, RNG utils, TSP benchmarks (5/10/20 cities), Continuous benchmarks (Sphere, Rastrigin, Rosenbrock, Ackley, Schwefel)

### Fase 3B — Heuristicas Avancadas (4/4)
- **Data:** 2026-02-13
- **Arquivos:** ILS, GRASP, PSO, ACO
- **Testes:** 41 testes
- **Notas:** Completa population-based e hybrid approaches

---

## Releases

| Versao | Data | Descricao |
|--------|------|-----------|
| 1.0-SNAPSHOT | ongoing | Versao de desenvolvimento ativa |

---

## Hotfixes

| Data | Problema | Solucao |
|------|----------|---------|

---

## Metricas Acumuladas

- **LOC total:** ~13.500+ (estimado: 2.660 + 4.410 + 1.507 + 6.000 + 4.500 + 2.500)
- **Testes:** ~539+ (132 + 117 + 59 + 199 + 91 + 41, parcial — nem todas DS tem testes em commonTest)
- **Cobertura:** nao medida formalmente

---

*Ultima atualizacao: 2026-02-16.*
