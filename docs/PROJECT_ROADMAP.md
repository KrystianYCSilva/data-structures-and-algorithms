---
description: "Roadmap do projeto: estruturas de dados (36), algoritmos (46), heurísticas (12 implementadas)."
---

# Project Roadmap - Algoritmos e Otimização

## Visão Geral

**Objetivo**: Biblioteca acadêmica em Kotlin Multiplatform (JVM/JS/Native) de estruturas de dados, algoritmos clássicos, heurísticas e meta-heurísticas com rigor científico.

**Status Atual**:
- Fase 1 (Estruturas de Dados): ✅ COMPLETA (36/36)
- Fase 2 (Algoritmos Fundamentais): 🔄 87% (46/53 implementados, 9 categorias)
- Fase 3 (Heurísticas e Meta-Heurísticas): ✅ COMPLETA (12/12)

---

## Fase 1 - Estruturas de Dados ✅ COMPLETA

### Fase 1A - Estruturas Lineares ✅ (5/5)

| # | Estrutura | Referência | Status |
|---|-----------|------------|--------|
| 1 | Stack (ArrayStack, LinkedStack) | Cormen Ch. 10.1 | ✅ |
| 2 | Queue (ArrayQueue, LinkedQueue, CircularQueue, Deque) | Cormen Ch. 10.1 | ✅ |
| 3 | LinkedList (Singly, Doubly, Circular, Unrolled) | Knuth Vol 1 Sec 2.2 | ✅ |
| 4 | DynamicArray | Goodrich Ch. 7 | ✅ |
| 5 | PriorityQueue | Cormen Ch. 6.5 | ✅ |

### Fase 1B - Árvores ✅ (14/14)

| # | Estrutura | Referência | Status |
|---|-----------|------------|--------|
| 6 | BinarySearchTree | Cormen Ch. 12 | ✅ |
| 7 | AVLTree | Adelson-Velsky & Landis (1962) | ✅ |
| 8 | RedBlackTree | Cormen Ch. 13 | ✅ |
| 9 | SplayTree | Sleator & Tarjan (1985) | ✅ |
| 10 | Treap | Aragon & Seidel (1989) | ✅ |
| 11 | CartesianTree | Vuillemin (1980) | ✅ |
| 12 | BTree | Cormen Ch. 18 | ✅ |
| 13 | BPlusTree | Comer (1979) | ✅ |
| 14 | Trie | Fredkin (1960) | ✅ |
| 15 | RadixTree (Patricia) | Morrison (1968) | ✅ |
| 16 | SuffixTree | Weiner (1973) | ✅ |
| 17 | SegmentTree | Bentley (1977) | ✅ |
| 18 | FenwickTree (BIT) | Fenwick (1994) | ✅ |

### Fase 1C - Hash, Heaps, Grafos ✅ (10/10)

| # | Estrutura | Referência | Status |
|---|-----------|------------|--------|
| 19 | HashTable (Open Addressing) | Cormen Ch. 11 | ✅ |
| 20 | CuckooHashTable | Pagh & Rodler (2004) | ✅ |
| 21 | SeparateChainingHashTable | Cormen Ch. 11 | ✅ |
| 22 | BinaryHeap | Cormen Ch. 6 | ✅ |
| 23 | BinomialHeap | Cormen Ch. 19 | ✅ |
| 24 | FibonacciHeap | Fredman & Tarjan (1987) | ✅ |
| 25 | AdjacencyList | Cormen Ch. 22 | ✅ |
| 26 | AdjacencyMatrix | Cormen Ch. 22 | ✅ |
| 27 | DirectedAcyclicGraph | Cormen Ch. 22 | ✅ |

### Fase 1D - Especializadas ✅ (7/7)

| # | Estrutura | Referência | Status |
|---|-----------|------------|--------|
| 28 | BloomFilter | Bloom (1970) | ✅ |
| 29 | SkipList | Pugh (1990) | ✅ |
| 30 | BitSet | — | ✅ |
| 31 | UnionFind | Cormen Ch. 21 | ✅ |
| 32 | Multiset | — | ✅ |
| 33 | KDTree | Bentley (1975) | ✅ |
| 34 | QuadTree | Finkel & Bentley (1974) | ✅ |
| 35 | Matrix | — | ✅ |
| 36 | ParallelArray | — | ✅ |

**Total Fase 1**: 36 estruturas, multiplataforma (JVM/JS/Native).

---

## Fase 2 - Algoritmos Fundamentais 🔄 87%

### Wave 1 - Core ✅ COMPLETO

| Categoria | Algoritmos | Referência | Status |
|-----------|-----------|------------|--------|
| Sorting (10) | Bubble, Selection, Insertion, Shell, Merge, Quick, Heap, Counting, Radix, Bucket | Cormen Ch. 2, 6, 7, 8 | ✅ |
| Searching (6) | Linear, Binary, Interpolation, Ternary, Jump, Exponential | Cormen Ch. 2; Knuth Vol 3 | ✅ |
| Graph (8) | BFS, DFS, Dijkstra, A*, Bellman-Ford, Floyd-Warshall, Kruskal, Prim | Cormen Ch. 22-25 | ✅ |

### Wave 2 - Classical 🔄 PARCIAL

| Categoria | Algoritmos | Referência | Status |
|-----------|-----------|------------|--------|
| String Matching (4/4) | Naive, KMP, Rabin-Karp, Boyer-Moore | Cormen Ch. 32 | ✅ |
| Dynamic Programming (6/8) | Fibonacci, LCS, Knapsack 0/1, Edit Distance, LIS, Coin Change | Cormen Ch. 15 | 🔄 |
| Greedy (3/3) | Activity Selection, Huffman Coding, Fractional Knapsack | Cormen Ch. 16 | ✅ |
| Numerical (6/6) | GCD, LCM, Extended GCD, Mod Exp, Sieve, Primality | Cormen Ch. 31 | ✅ |

**Faltantes Wave 2**: Rod Cutting, Matrix Chain Multiplication (DP)

### Wave 3 - Advanced 🔄 PARCIAL

| Categoria | Algoritmos | Referência | Status |
|-----------|-----------|------------|--------|
| Backtracking (3/4) | N-Queens, Subset Sum, Permutations | Cormen Ch. 34 | 🔄 |
| Divide & Conquer (0/5) | — | Cormen Ch. 4 | ⏳ |

**Faltantes Wave 3**: Graph Coloring (Backtracking), Strassen, Closest Pair, Karatsuba, Max Subarray, Quick Select (D&C)

**Total Fase 2**: 46 algoritmos implementados de ~53 planejados.

---

## Fase 3 - Heurísticas e Meta-Heurísticas ✅ COMPLETA

### Phase 3A - Classical ✅ COMPLETA
| Algoritmo | Tipo | Referência | Testes | Status |
|-----------|------|------------|--------|--------|
| Hill Climbing | Local Search | Russell & Norvig (2010) | 4 | ✅ |
| Simulated Annealing | Single-Solution | Kirkpatrick et al. (1983) | 4 | ✅ |
| Tabu Search | Single-Solution | Glover (1986) | 4 | ✅ |
| Genetic Algorithm | Population-Based | Holland (1975) | 5 | ✅ |

### Phase 3B - Advanced ✅ COMPLETA
| Algoritmo | Tipo | Referência | Testes | Status |
|-----------|------|------------|--------|--------|
| ILS | Single-Solution | Lourenço et al. (2003) | 4 | ✅ |
| GRASP | Hybrid | Feo & Resende (1995) | 4 | ✅ |
| PSO | Population-Based | Kennedy & Eberhart (1995) | 5 | ✅ |
| ACO | Population-Based | Dorigo (1992) | 4 | ✅ |

**Infraestrutura**: OptimizationProblem<T>, BoundedVectorProblem, CostMatrixProblem, ContinuousProblem, BinaryProblem, PermutationProblem, IntegerProblem, KnapsackProblem, JobSchedulingProblem, MaxSatProblem, TSPProblem (2-opt), Benchmarks (Sphere, Rastrigin, Rosenbrock, Ackley, Schwefel), OptResult, crossover operators (singlePoint, uniform, OX, PMX).

### Phase 3C - Specialized ✅ COMPLETA
| Algoritmo | Tipo | Referência | Testes | Status |
|-----------|------|------------|--------|--------|
| Differential Evolution | Population-Based | Storn & Price (1997) | 5 | ✅ |
| VNS | Single-Solution | Mladenović & Hansen (1997) | 5 | ✅ |
| Memetic Algorithm | Hybrid | Moscato (1989) | 5 | ✅ |
| LNS | Single-Solution | Shaw (1998) | 6 | ✅ |

---

## Progresso Geral

```
╔═══════════════════════════════════════════════════════════════╗
║                    PROGRESSO DO PROJETO                       ║
╠═══════════════════════════════════════════════════════════════╣
║ Fase 1 (Estruturas de Dados):    ✅ 100% (36/36)             ║
║                                                               ║
║ Fase 2 (Algoritmos Fundamentais): 🔄 87% (46/53)             ║
║   - W1 Sorting/Searching/Graph: 24 algs ✅                   ║
║   - W2 String/DP/Greedy/Numerical: 19/20 algs 🔄             ║
║   - W3 Backtracking/D&C: 3/9 algs 🔄                        ║
║                                                               ║
║ Fase 3 (Heurísticas):             ✅ 100% (12/12)            ║
║   - 3A Classical: 4/4 ✅                                     ║
║   - 3B Advanced: 4/4 ✅                                      ║
║   - 3C Specialized: 4/4 ✅                                   ║
║                                                               ║
║ TOTAL: ~93% (Fase 1+3 completas, Fase 2 87%)                ║
╚═══════════════════════════════════════════════════════════════╝
```

---

## Estatísticas do Projeto

```
Estruturas de Dados:      36 completas
Algoritmos:               46 implementados (31 arquivos, 9 categorias)
Heurísticas:              12 implementadas (12 arquivos)
Modelagens de Problema:   7 (Continuous, Binary, Permutation, Integer, Knapsack, Scheduling, MaxSAT)
Interfaces de Abstração:  3 (OptimizationProblem<T>, BoundedVectorProblem, CostMatrixProblem)
Crossover Operators:      4 (singlePoint, uniform, OX, PMX)
Arquivos de teste:        ~72
Plataformas:              JVM, JS (IR), mingwX64 (Native)
```

---

## Documentação

| Documento | Descrição |
|-----------|-----------|
| `ALGORITHM_CATALOG.md` | Catálogo completo com status de implementação |
| `USAGE_EXAMPLES.md` | Exemplos de uso da API |
| `QA-ITERATIVE-PLAN.md` | Plano de QA iterativo (13 iterações) |

---

**Última Atualização**: 2026-02-17
**Próximo Marco**: Completar Fase 2 (D&C + DP/Backtracking restantes — em desenvolvimento pelo Gemini)
