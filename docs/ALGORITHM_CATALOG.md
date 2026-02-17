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
| `UnionFind` | Disjoint Set | Union/Find | O(α(n)) |
| `HashTable` | Hash Map | Get/Put | O(1) am. |

---

## Algorithms (24/24 Implemented)

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
- [x] **Jump Search**: O(√n) — `JumpSearch.kt`
- [x] **Exponential Search**: O(log n) — `ExponentialSearch.kt`

---

## Planned (Future Releases)

### String Matching
- [ ] Naive
- [ ] KMP
- [ ] Rabin-Karp
- [ ] Boyer-Moore

### Dynamic Programming
- [ ] Fibonacci
- [ ] LCS
- [ ] Knapsack 0/1
- [ ] Edit Distance
- [ ] LIS
- [ ] Rod Cutting
- [ ] Matrix Chain Multiplication
- [ ] Coin Change

### Greedy
- [ ] Activity Selection
- [ ] Huffman Coding
- [ ] Fractional Knapsack

### Numerical
- [ ] GCD / Extended GCD
- [ ] Fast Exponentiation
- [ ] Sieve of Eratosthenes
- [ ] Primality Test

### Divide & Conquer
- [ ] Strassen Matrix Multiplication
- [ ] Closest Pair of Points
- [ ] Karatsuba Multiplication
- [ ] Maximum Subarray
- [ ] Quick Select

### Backtracking
- [ ] N-Queens
- [ ] Subset Sum
- [ ] Permutations
- [ ] Graph Coloring

### Optimization Heuristics
- [ ] Hill Climbing
- [ ] Simulated Annealing
- [ ] Tabu Search
- [ ] Genetic Algorithm
- [ ] ILS (Iterated Local Search)
- [ ] GRASP
- [ ] PSO (Particle Swarm Optimization)
- [ ] ACO (Ant Colony Optimization)
- [ ] Differential Evolution
- [ ] VNS
- [ ] Memetic Algorithm
- [ ] LNS
