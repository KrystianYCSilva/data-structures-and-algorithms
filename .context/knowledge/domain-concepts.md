---
description: Domain glossary and core concepts specific to this algorithms library.
---

# Domain Concepts

## Estruturas de Dados (36)

- Lineares: stack, queue, deque, linked lists, dynamic array
- Arvores: BST, AVL, RedBlack, Splay, Treap, BTree, BPlusTree, Trie, Radix, Suffix
- Range query: SegmentTree, FenwickTree
- Heaps: Comparable/Comparator heap, BinomialHeap, FibonacciHeap, PriorityQueue
- Grafos: AdjacencyList, AdjacencyMatrix, DirectedAcyclicGraph
- Associativas: OpenAddressing, Cuckoo, SeparateChaining, Multiset
- Espaciais/probabilisticas: KDTree, QuadTree, BloomFilter, SkipList, BitSet, UnionFind

## Algoritmos classicos (46)

- Sorting (10)
- Searching (6)
- Graph (8)
- String matching (4)
- Dynamic programming (6 de 8 planejados)
- Greedy (3)
- Numerical (6)
- Backtracking (3 de 4 planejados)

## Heuristicas/meta-heuristicas (12)

- Classical local search: Hill Climbing, Simulated Annealing, Tabu Search
- Population-based: Genetic Algorithm, PSO, ACO, Differential Evolution
- Hybrid/advanced: ILS, GRASP, VNS, Memetic Algorithm, LNS

## Framework de otimizacao

- Base: `OptimizationProblem<T>` + `OptResult<T>`
- Especializacoes: `BoundedVectorProblem` (PSO/DE), `CostMatrixProblem` (ACO)
- Modelagens: `ContinuousProblem`, `BinaryProblem`, `PermutationProblem`, `IntegerProblem`
- Problemas concretos: `KnapsackProblem`, `JobSchedulingProblem`, `MaxSatProblem`, `TSPProblem`

## Convenios de API

- Leitura/escrita separadas (`Immutable*`/`Mutable*` ou noun/`Mutable*`)
- Operacoes em colecao vazia retornam `null` quando aplicavel
- Views imutaveis via `asReadOnly()`
