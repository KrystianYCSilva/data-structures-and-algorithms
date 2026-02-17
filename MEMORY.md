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

### Fase 1 — Estruturas de Dados (36/36) - COMPLETO
- **Data:** 2025-02-12
- **Arquivos:** `src/commonMain/kotlin/br/uem/din/datastructures/`
- **Categorias:**
  - **Lineares:** Stack (Array/Linked), Queue (Array/Linked/Circular/Deque), LinkedList (Singly/Doubly/Circular/Unrolled), DynamicArray
  - **Arvores:** BST, AVL, RB, Splay, Treap, Cartesian, B-Tree, B+Tree, Radix, Suffix, Trie
  - **Range Query:** SegmentTree, FenwickTree
  - **Heaps:** Binary (Min/Max), Binomial, Fibonacci, PriorityQueue
  - **Grafos:** Graph (AdjList/AdjMatrix), DAG, Vertex, Edge
  - **Spatial:** KDTree, QuadTree
  - **Probabilisticas:** BloomFilter, SkipList
  - **Outros:** BitSet, UnionFind, Set, Multiset, ParallelArray, Matrix
- **Testes:** Cobertura parcial em `src/commonTest/kotlin/br/uem/din/datastructures/`

### Fase 2 — Algoritmos Fundamentais (~15%) - EM ANDAMENTO
- **Data:** 2026-02-17 (Status Atualizado)
- **Arquivos:** `src/commonMain/kotlin/br/uem/din/algorithms/`
- **Implementados:**
  - **Sorting:** BubbleSort, InsertionSort
  - **Graph:** BFS, DFS, Dijkstra, A*
- **Planejados (Faltantes):**
  - Sorting: Selection, Shell, Merge, Quick, Heap, Counting, Radix, Bucket
  - Searching: Linear, Binary, Interpolation, Ternary, Jump, Exponential
  - Graph: Bellman-Ford, Floyd-Warshall, Kruskal, Prim
  - String Matching: Naive, KMP, Rabin-Karp, Boyer-Moore
  - DP: Fibonacci, LCS, Knapsack 0/1, Edit Distance, LIS, Rod Cutting, Matrix Chain, Coin Change
  - Greedy: Activity Selection, Huffman Coding, Fractional Knapsack
  - Numerical: GCD, Extended GCD, Fast Exponentiation, Sieve, Primality
  - D&C: Strassen, Closest Pair, Karatsuba, Max Subarray, Quick Select
  - Backtracking: N-Queens, Subset Sum, Permutations, Graph Coloring

### Fase 3 — Heuristicas e Otimizacao (0%) - PLANEJADO
- **Status:** Nenhuma implementacao encontrada no codebase atual.
- **Planejados:**
  - Classicas: Hill Climbing, Simulated Annealing, Tabu Search, Genetic Algorithm
  - Avancadas: ILS, GRASP, PSO, ACO
  - Benchmarks: TSP, Continuous Functions (Sphere, Rastrigin, etc.)

---

## Releases

| Versao | Data | Descricao |
|--------|------|-----------|
| 1.0-SNAPSHOT | ongoing | Versao de desenvolvimento ativa |

---

## Hotfixes

| Data | Problema | Solucao |
|------|----------|---------|
| 2026-02-17 | Discrepancia Documentacao vs Codebase | Atualizacao de MEMORY.md e .context/ para refletir estado real (DS completa, Algos inicial, Heuristicas pendente) |

---

## Metricas Atuais

- **LOC total:** ~13.500+ (DS majoritariamente)
- **Testes:** Focados em Estruturas de Dados (`datastructures/`)
- **Cobertura:** nao medida formalmente

---

*Ultima atualizacao: 2026-02-17.*
