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

## Algorithms (~15% Implemented)

### Sorting
- [x] **Bubble Sort**: O(n^2) - `BubbleSort.kt`
- [x] **Insertion Sort**: O(n^2) - `InsertionSort.kt`
- [ ] Selection Sort
- [ ] Shell Sort
- [ ] Merge Sort
- [ ] Quick Sort
- [ ] Heap Sort
- [ ] Counting Sort
- [ ] Radix Sort
- [ ] Bucket Sort

### Graph
- [x] **BFS** (Breadth-First Search): O(V+E) - `BreadthFirstSearch.kt`
- [x] **DFS** (Depth-First Search): O(V+E) - `DepthFirstSearch.kt`
- [x] **Dijkstra**: O((V+E) log V) - `Dijkstra.kt`
- [x] **A*** (A-Star): O(E) - `AStar.kt`
- [ ] Bellman-Ford
- [ ] Floyd-Warshall
- [ ] Kruskal
- [ ] Prim

### Searching (Planned)
- [ ] Linear Search
- [ ] Binary Search
- [ ] Interpolation Search
- [ ] Ternary Search
- [ ] Jump Search
- [ ] Exponential Search

### String Matching (Planned)
- [ ] Naive
- [ ] KMP
- [ ] Rabin-Karp
- [ ] Boyer-Moore

### Dynamic Programming (Planned)
- [ ] Fibonacci
- [ ] LCS
- [ ] Knapsack 0/1
- [ ] Edit Distance
- [ ] LIS
- [ ] Rod Cutting
- [ ] Matrix Chain Multiplication
- [ ] Coin Change

### Greedy (Planned)
- [ ] Activity Selection
- [ ] Huffman Coding
- [ ] Fractional Knapsack

### Numerical (Planned)
- [ ] GCD / Extended GCD
- [ ] Fast Exponentiation
- [ ] Sieve of Eratosthenes
- [ ] Primality Test

### Divide & Conquer (Planned)
- [ ] Strassen Matrix Multiplication
- [ ] Closest Pair of Points
- [ ] Karatsuba Multiplication
- [ ] Maximum Subarray
- [ ] Quick Select

### Backtracking (Planned)
- [ ] N-Queens
- [ ] Subset Sum
- [ ] Permutations
- [ ] Graph Coloring

---

## Optimization Heuristics (Planned)

### Classical (Planned)
- [ ] Hill Climbing
- [ ] Simulated Annealing
- [ ] Tabu Search
- [ ] Genetic Algorithm

### Advanced (Planned)
- [ ] ILS (Iterated Local Search)
- [ ] GRASP
- [ ] PSO (Particle Swarm Optimization)
- [ ] ACO (Ant Colony Optimization)
