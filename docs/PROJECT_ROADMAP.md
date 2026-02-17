---
description: "Roadmap do projeto: estruturas de dados (36), algoritmos (24 implementados), heurísticas (planejado)."
---

# Project Roadmap - Algoritmos e Otimização

## Visão Geral

**Objetivo**: Biblioteca acadêmica em Kotlin Multiplatform (JVM/JS/Native) de estruturas de dados, algoritmos clássicos, heurísticas e meta-heurísticas com rigor científico.

**Status Atual**:
- Fase 1 (Estruturas de Dados): ✅ COMPLETA (36/36)
- Fase 2 (Algoritmos Fundamentais): 🔄 PARCIAL (24/45 implementados)
- Fase 3 (Heurísticas e Meta-Heurísticas): ⏳ PLANEJADO

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

## Fase 2 - Algoritmos Fundamentais 🔄 PARCIAL

### Wave 1 - Core ✅ IMPLEMENTADO

| Categoria | Algoritmos | Referência | Status |
|-----------|-----------|------------|--------|
| Sorting (10) | Bubble, Selection, Insertion, Shell, Merge, Quick, Heap, Counting, Radix, Bucket | Cormen Ch. 2, 6, 7, 8 | ✅ |
| Searching (6) | Linear, Binary, Interpolation, Ternary, Jump, Exponential | Cormen Ch. 2; Knuth Vol 3 | ✅ |
| Graph (8) | BFS, DFS, Dijkstra, A*, Bellman-Ford, Floyd-Warshall, Kruskal, Prim | Cormen Ch. 22-25 | ✅ |

**Total Wave 1**: 24 algoritmos implementados e testados.

### Wave 2 - Classical ⏳ PLANEJADO

| Categoria | Algoritmos | Referência | Status |
|-----------|-----------|------------|--------|
| String Matching (4) | Naive, KMP, Rabin-Karp, Boyer-Moore | Cormen Ch. 32 | ⏳ |
| Dynamic Programming (8) | Fibonacci, LCS, Knapsack 0/1, Edit Distance, LIS, Rod Cutting, Matrix Chain, Coin Change | Cormen Ch. 15 | ⏳ |
| Greedy (3) | Activity Selection, Huffman Coding, Fractional Knapsack | Cormen Ch. 16 | ⏳ |
| Numerical (5) | GCD, Extended GCD, Fast Exp, Sieve, Primality | Cormen Ch. 31 | ⏳ |

### Wave 3 - Advanced ⏳ PLANEJADO

| Categoria | Algoritmos | Referência | Status |
|-----------|-----------|------------|--------|
| Divide & Conquer (5) | Strassen, Closest Pair, Karatsuba, Max Subarray, Quick Select | Cormen Ch. 4 | ⏳ |
| Backtracking (4) | N-Queens, Subset Sum, Permutations, Graph Coloring | Cormen Ch. 34 | ⏳ |

---

## Fase 3 - Heurísticas e Meta-Heurísticas ⏳ PLANEJADO

### Phase 3A - Classical ⏳
| Algoritmo | Tipo | Referência | Status |
|-----------|------|------------|--------|
| Hill Climbing | Local Search | Russell & Norvig (2010) | ⏳ |
| Simulated Annealing | Single-Solution | Kirkpatrick et al. (1983) | ⏳ |
| Tabu Search | Single-Solution | Glover (1986) | ⏳ |
| Genetic Algorithm | Population-Based | Holland (1975) | ⏳ |

### Phase 3B - Advanced ⏳
| Algoritmo | Tipo | Referência | Status |
|-----------|------|------------|--------|
| ILS | Single-Solution | Lourenço et al. (2003) | ⏳ |
| GRASP | Hybrid | Feo & Resende (1995) | ⏳ |
| PSO | Population-Based | Kennedy & Eberhart (1995) | ⏳ |
| ACO | Population-Based | Dorigo (1992) | ⏳ |

### Phase 3C - Specialized ⏳
| Algoritmo | Tipo | Referência | Status |
|-----------|------|------------|--------|
| Differential Evolution | Population-Based | Storn & Price (1997) | ⏳ |
| VNS | Single-Solution | Mladenović & Hansen (1997) | ⏳ |
| Memetic Algorithm | Hybrid | Moscato (1989) | ⏳ |
| LNS | Single-Solution | Shaw (1998) | ⏳ |

---

## Progresso Geral

```
╔═══════════════════════════════════════════════════════════════╗
║                    PROGRESSO DO PROJETO                       ║
╠═══════════════════════════════════════════════════════════════╣
║ Fase 1 (Estruturas de Dados):    ✅ 100% (36/36)             ║
║                                                               ║
║ Fase 2 (Algoritmos Fundamentais): 🔄 53% (24/45)             ║
║   - W1 Sorting/Searching/Graph: 24 algs ✅                   ║
║   - W2 String/DP/Greedy/Numerical: 20 algs ⏳                ║
║   - W3 D&C/Backtracking: 9 algs ⏳                           ║
║                                                               ║
║ Fase 3 (Heurísticas):             ⏳ 0% (0/12)               ║
║                                                               ║
║ TOTAL: ~46% (Fase 1 completa, Fase 2 parcial)                ║
╚═══════════════════════════════════════════════════════════════╝
```

---

## Estatísticas do Projeto

```
Estruturas de Dados:      36 completas
Algoritmos:               24 implementados (10 sorting + 6 searching + 8 graph)
Heurísticas:              0 (planejado)
Arquivos fonte:           ~102 (commonMain)
Arquivos de teste:        54
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
**Próximo Marco**: Wave 2 (String Matching, DP, Greedy, Numerical) ou Phase 3A (Heurísticas)
