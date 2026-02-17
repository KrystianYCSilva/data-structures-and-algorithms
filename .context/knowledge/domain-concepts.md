---
description: Domain glossary and core concepts specific to this algorithms library.
---

# Domain Concepts

## Estruturas de Dados (36 implementadas)

- **Lineares**: Stack, Queue, Deque, CircularQueue, LinkedList, DoublyLinkedList, CircularLinkedList, UnrolledLinkedList, DynamicArray
- **Arvores**: BinarySearchTree, AVLTree, RedBlackTree, SplayTree, Treap, CartesianTree, BTree, BPlusTree, RadixTree, SuffixTree, Trie
- **Range Query**: SegmentTree, FenwickTree (Binary Indexed Tree)
- **Heaps**: AbstractHeap, ComparableHeapImpl, ComparatorHeapImpl, BinomialHeap, FibonacciHeap, PriorityQueue
- **Grafos**: Graph (interface), AdjacencyList, AdjacencyMatrix, DirectedAcyclicGraph, Vertex, Edge
- **Hash**: HashTable, OpenAddressingHashTable, CuckooHashTable
- **Spatial**: KDTree, QuadTree
- **Probabilisticas**: BloomFilter, SkipList
- **Outros**: BitSet, UnionFind, Multiset, ParallelArray, Matrix

## Algoritmos

### Implementados
- **Sorting**: Bubble Sort, Insertion Sort
- **Graph**: BFS, DFS, Dijkstra, A*

### Planejados
- **Sorting**: Selection, Shell, Merge, Quick, Heap, Counting, Radix, Bucket
- **Searching**: Linear, Binary, Interpolation, Ternary, Jump, Exponential
- **Graph**: Bellman-Ford, Floyd-Warshall, Kruskal, Prim
- **String Matching**: Naive, KMP, Rabin-Karp, Boyer-Moore
- **Dynamic Programming**: Fibonacci, LCS, Knapsack 0/1, Edit Distance, LIS, Rod Cutting, Matrix Chain, Coin Change
- **Greedy**: Activity Selection, Huffman Coding, Fractional Knapsack
- **Numerical**: GCD, Extended GCD, Fast Exponentiation, Sieve of Eratosthenes, Primality Test
- **Divide & Conquer**: Strassen, Closest Pair, Karatsuba, Max Subarray, Quick Select
- **Backtracking**: N-Queens, Subset Sum, Permutations, Graph Coloring

## Heuristicas e Meta-Heuristicas (Planejadas)

- **Single-Solution**: Hill Climbing, Simulated Annealing, Tabu Search, ILS (Iterated Local Search)
- **Population-Based**: Genetic Algorithm, PSO (Particle Swarm Optimization), ACO (Ant Colony Optimization)
- **Hybrid**: GRASP (Greedy Randomized Adaptive Search Procedure)
- **Planejadas (3C)**: Differential Evolution, VNS, Memetic Algorithm, LNS

## Tipos de Infraestrutura de Otimizacao (Planejados)

- **OptSolution**: Representacao de uma solucao candidata
- **OptResult**: Resultado de execucao de uma heurisitca (solucao + metricas)
- **Benchmarks TSP**: Instancias com 5, 10, 20 cidades
- **Benchmarks Continuos**: Sphere, Rastrigin, Rosenbrock, Ackley, Schwefel

## Complexidade (Big-O)

Todas as implementacoes documentam complexidade temporal e espacial no KDoc de classe, usando notacao Big-O. Tabelas Markdown no formato:

```
| Operacao | Complexidade |
|----------|-------------|
| push     | O(1) amort. |
| pop      | O(1)        |
```

## Referencias Academicas

- Cormen, T. H. et al. "Introduction to Algorithms" (CLRS) — referencia primaria
- Knuth, D. E. "The Art of Computer Programming" (TAOCP) — estruturas e sorting
- Papers originais: Dijkstra (1959), Kirkpatrick et al. (1983), Holland (1975), Kennedy & Eberhart (1995), Dorigo (1992), etc.
