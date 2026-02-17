# Algorithm & Data Structure Catalog

This catalog lists all implemented algorithms and data structures in the library, along with their complexity characteristics and status.

## Data Structures (36/36 Implemented)

### Linear
| Name | Type | Access | Search | Insert | Delete |
|------|------|--------|--------|--------|--------|
| `ArrayStack` | Stack | O(1) | O(n) | O(1) | O(1) |
| `LinkedStack` | Stack | O(1) | O(n) | O(1) | O(1) |
| `ArrayQueue` | Queue | O(1) | O(n) | O(1) | O(1) |
| `LinkedQueue` | Queue | O(1) | O(n) | O(1) | O(1) |
| `CircularQueue` | Queue | O(1) | O(n) | O(1) | O(1) |
| `Deque` | Queue (Double-ended) | O(1) | O(n) | O(1) | O(1) |
| `SinglyLinkedList` | List | O(n) | O(n) | O(1)* | O(1)* |
| `DoublyLinkedList` | List | O(n) | O(n) | O(1)* | O(1)* |
| `CircularLinkedList` | List | O(n) | O(n) | O(1)* | O(1)* |
| `UnrolledLinkedList` | List | O(n) | O(n) | O(1)* | O(1)* |
| `DynamicArray` | List | O(1) | O(n) | O(1) am. | O(n) |

### Trees
| Name | Type | Search | Insert | Delete |
|------|------|--------|--------|--------|
| `BinarySearchTree` | BST | O(h) | O(h) | O(h) |
| `AVLTree` | Balanced BST | O(log n) | O(log n) | O(log n) |
| `RedBlackTree` | Balanced BST | O(log n) | O(log n) | O(log n) |
| `SplayTree` | Self-adjusting BST | O(log n) am. | O(log n) am. | O(log n) am. |
| `Treap` | Randomized BST | O(log n) exp. | O(log n) exp. | O(log n) exp. |
| `CartesianTree` | Heap/BST | O(n) | O(n) | O(n) |
| `BTree` | Multi-way Tree | O(log n) | O(log n) | O(log n) |
| `BPlusTree` | Multi-way Tree | O(log n) | O(log n) | O(log n) |
| `Trie` | Prefix Tree | O(m) | O(m) | O(m) |
| `RadixTree` | Compressed Trie | O(m) | O(m) | O(m) |
| `SuffixTree` | String Tree | O(m) | O(m) | O(m) |

### Range Query
| Name | Type | Query | Update | Build |
|------|------|-------|--------|-------|
| `SegmentTree` | Tree | O(log n) | O(log n) | O(n) |
| `FenwickTree` | BIT | O(log n) | O(log n) | O(n log n) |

### Heaps
| Name | Type | Peek | Push | Pop |
|------|------|------|------|-----|
| `BinaryHeap` | Min/Max Heap | O(1) | O(log n) | O(log n) |
| `BinomialHeap` | Mergeable Heap | O(log n) | O(1) am. | O(log n) |
| `FibonacciHeap` | Mergeable Heap | O(1) | O(1) | O(log n) am. |
| `PriorityQueue` | Wrapper | O(1) | O(log n) | O(log n) |

### Graphs
| Name | Representation | Add Vertex | Add Edge | Remove Vertex | Remove Edge |
|------|----------------|------------|----------|---------------|-------------|
| `AdjacencyList` | List of Lists | O(1) am. | O(1) am. | O(V+E) | O(E) |
| `AdjacencyMatrix` | Matrix | O(V^2) | O(1) | O(V^2) | O(1) |
| `DirectedAcyclicGraph` | DAG | O(1) am. | O(1) am. | O(V+E) | O(E) |

### Spatial
| Name | Type | Search (Range) | Insert | Delete |
|------|------|----------------|--------|--------|
| `KDTree` | K-Dimensional Tree | O(n^(1-1/k)) | O(log n) | O(log n) |
| `QuadTree` | 2D Partitioning | O(n) | O(log n) | O(log n) |

### Probabilistic & Others
| Name | Type | Operation | Complexity |
|------|------|-----------|------------|
| `BloomFilter` | Probabilistic Set | Contains | O(k) |
| `SkipList` | Probabilistic List | Search/Ins/Del | O(log n) exp. |
| `BitSet` | Bit Array | Get/Set | O(1) |
| `UnionFind` | Disjoint Set | Union/Find | O(alpha(n)) |
| `HashTable` | Hash Map | Get/Put | O(1) am. |

---

## Algorithms (46 Implemented + 12 Heuristics)

### Sorting (10/10)
- [x] **Bubble Sort**: O(n^2) — `BubbleSort.kt`
- [x] **Insertion Sort**: O(n^2) — `InsertionSort.kt`
- [x] **Selection Sort**: O(n^2) — `SelectionSort.kt`
- [x] **Shell Sort**: O(n^(3/2)) — `ShellSort.kt`
- [x] **Merge Sort**: O(n log n) — `MergeSort.kt`
- [x] **Quick Sort**: O(n log n) avg — `QuickSort.kt`
- [x] **Heap Sort**: O(n log n) — `HeapSort.kt`
- [x] **Counting Sort**: O(n + k) — `CountingSort.kt`
- [x] **Radix Sort**: O(d(n + k)) — `RadixSort.kt`
- [x] **Bucket Sort**: O(n + k) avg — `BucketSort.kt`

### Graph (8/8)
- [x] **BFS** (Breadth-First Search): O(V+E) — `BreadthFirstSearch.kt`
- [x] **DFS** (Depth-First Search): O(V+E) — `DepthFirstSearch.kt`
- [x] **Dijkstra**: O((V+E) log V) — `Dijkstra.kt`
- [x] **A*** (A-Star): O(E) — `AStar.kt`
- [x] **Bellman-Ford**: O(V*E) — `BellmanFord.kt`
- [x] **Floyd-Warshall**: O(V^3) — `FloydWarshall.kt`
- [x] **Kruskal**: O(E log E) — `Kruskal.kt`
- [x] **Prim**: O(E log V) — `Prim.kt`

### Searching (6/6)
- [x] **Linear Search**: O(n) — `LinearSearch.kt`
- [x] **Binary Search**: O(log n) — `BinarySearch.kt`
- [x] **Interpolation Search**: O(log log n) avg — `InterpolationSearch.kt`
- [x] **Ternary Search**: O(log n) — `TernarySearch.kt`
- [x] **Jump Search**: O(sqrt(n)) — `JumpSearch.kt`
- [x] **Exponential Search**: O(log n) — `ExponentialSearch.kt`

### String Matching (4/4)
- [x] **Naive**: O(nm) — `NaiveSearch.kt`
- [x] **KMP**: O(n+m) — `KMPSearch.kt`
- [x] **Rabin-Karp**: O(n+m) avg — `RabinKarpSearch.kt`
- [x] **Boyer-Moore**: O(n/m) best — `BoyerMooreSearch.kt`

### Dynamic Programming (6/8)
- [x] **Fibonacci**: O(n) — `DynamicProgramming.kt`
- [x] **LCS**: O(nm) — `DynamicProgramming.kt`
- [x] **Knapsack 0/1**: O(nW) — `DynamicProgramming.kt`
- [x] **Edit Distance**: O(nm) — `DynamicProgramming.kt`
- [x] **LIS**: O(n^2) — `DynamicProgramming.kt`
- [x] **Coin Change**: O(nS) — `DynamicProgramming.kt`
- [ ] Rod Cutting
- [ ] Matrix Chain Multiplication

### Greedy (3/3)
- [x] **Activity Selection**: O(n) — `GreedyAlgorithms.kt`
- [x] **Huffman Coding**: O(n log n) — `GreedyAlgorithms.kt`
- [x] **Fractional Knapsack**: O(n log n) — `GreedyAlgorithms.kt`

### Numerical (6/6)
- [x] **GCD**: O(log min(a,b)) — `NumericalAlgorithms.kt`
- [x] **LCM**: O(log min(a,b)) — `NumericalAlgorithms.kt`
- [x] **Extended GCD**: O(log min(a,b)) — `NumericalAlgorithms.kt`
- [x] **Modular Exponentiation**: O(log exp) — `NumericalAlgorithms.kt`
- [x] **Sieve of Eratosthenes**: O(n log log n) — `NumericalAlgorithms.kt`
- [x] **Primality Test**: O(sqrt(n)) — `NumericalAlgorithms.kt`

### Backtracking (3/4)
- [x] **N-Queens**: O(n!) — `Backtracking.kt`
- [x] **Subset Sum**: O(2^n) — `Backtracking.kt`
- [x] **Permutations**: O(n!) — `Backtracking.kt`
- [ ] Graph Coloring

---

## Optimization Heuristics (12/12 Implemented)

### Classical Local Search (3/3)
- [x] **Hill Climbing** (First-Improvement / Best-Improvement): O(k × maxIter) — `HillClimbing.kt`
- [x] **Simulated Annealing**: O(maxIter × iterPerTemp) — `SimulatedAnnealing.kt`
- [x] **Tabu Search**: O(maxIter × neighbors) — `TabuSearch.kt`

### Population-Based (4/4)
- [x] **Genetic Algorithm** (Tournament, OX crossover, Elitism): O(gen × pop × eval) — `GeneticAlgorithm.kt`
- [x] **Particle Swarm Optimization**: O(iter × swarm × dim) — `ParticleSwarmOptimization.kt`
- [x] **Differential Evolution** (DE/rand/1/bin): O(gen × pop × dim) — `DifferentialEvolution.kt`
- [x] **Memetic Algorithm** (GA + Local Search): O(gen × pop × localSearch) — `MemeticAlgorithm.kt`

### Hybrid / Advanced (5/5)
- [x] **Iterated Local Search (ILS)**: O(maxIter × localSearch) — `IteratedLocalSearch.kt`
- [x] **GRASP**: O(maxIter × (construction + localSearch)) — `GRASP.kt`
- [x] **Ant Colony Optimization (ACO)**: O(iter × ants × n²) — `AntColonyOptimization.kt`
- [x] **Variable Neighborhood Search (VNS)**: O(maxIter × kMax × localSearch) — `VariableNeighborhoodSearch.kt`
- [x] **Large Neighborhood Search (LNS)**: O(maxIter × (destroy + repair)) — `LargeNeighborhoodSearch.kt`

### Infrastructure & Problem Types
- [x] `OptimizationProblem<T>` — interface genérica para qualquer representação de solução
- [x] `BoundedVectorProblem` — interface para problemas com espaço vetorial real limitado (PSO, DE)
- [x] `CostMatrixProblem` — interface para problemas com matriz de custos entre pares (ACO)
- [x] `ContinuousProblem` — espaço real N-dimensional (Benchmarks)
- [x] `BinaryProblem` — representação BooleanArray com vizinhança bit-flip (Knapsack, SAT)
- [x] `PermutationProblem` — representação IntArray permutação com vizinhança swap (TSP, Scheduling)
- [x] `IntegerProblem` — representação IntArray com limites por dimensão (configuração, alocação)
- [x] `KnapsackProblem` — Mochila 0/1 com penalização por excesso de peso
- [x] `JobSchedulingProblem` — Escalonamento em máquina única (weighted tardiness)
- [x] `MaxSatProblem` — MAX-SAT (máximo de cláusulas satisfeitas)
- [x] `TSPProblem` — Caixeiro Viajante com distância euclidiana e vizinhança 2-opt
- [x] `Benchmarks` — Sphere, Rastrigin, Rosenbrock, Ackley, Schwefel

### Crossover Operators
- [x] `singlePointCrossover` — ponto único para DoubleArray (contínuo)
- [x] `uniformCrossover` — uniforme para BooleanArray (binário)
- [x] `orderCrossover` (OX) — preserva ordem relativa para IntArray (permutação)
- [x] `pmxCrossover` (PMX) — partially mapped para IntArray (permutação)

---

## Planned (Future Releases)

### Divide & Conquer
- [ ] Strassen Matrix Multiplication
- [ ] Closest Pair of Points
- [ ] Karatsuba Multiplication
- [ ] Maximum Subarray
- [ ] Quick Select
