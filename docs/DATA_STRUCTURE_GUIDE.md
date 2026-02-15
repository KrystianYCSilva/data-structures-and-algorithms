---
description: "Guia de decisão para escolher a estrutura de dados correta baseado em padrões de acesso, complexidade e requisitos de performance."
---

# Guia de Escolha de Estruturas de Dados

## Como Escolher a Estrutura de Dados Correta

Este guia ajuda a escolher a estrutura mais apropriada para cada situação, baseado em padrões de acesso e requisitos de performance. Todos os exemplos utilizam a API Kotlin Multiplatform da biblioteca.

---

## 1. Estruturas Primitivas

Kotlin já fornece tipos primitivos nativos: `Int`, `Long`, `Double`, `Float`, `Char`, `Boolean`.
Não é necessário implementar — a linguagem os trata como value types no JVM.

---

## 2. Estruturas Lineares

### Tabela Comparativa

| Estrutura | Acesso | Inserção (fim) | Inserção (início) | Busca | Memória |
|-----------|--------|----------------|-------------------|-------|---------|
| **DynamicArray** | O(1) | O(1)* | O(n) | O(n) / O(log n)† | Baixo |
| **LinkedList** | O(n) | O(1) | O(1) | O(n) | Alto |
| **DoublyLinkedList** | O(n) | O(1) | O(1) | O(n) | Alto |
| **CircularLinkedList** | O(n) | O(1) | O(1) | O(n) | Alto |
| **UnrolledLinkedList** | O(n/B) | O(1) | O(n) | O(n) | Médio |
| **Stack** | O(1)‡ | O(1)* | N/A | N/A | Baixo |
| **Queue** | O(1)‡ | O(1)* | N/A | N/A | Baixo |
| **CircularQueue** | O(1)‡ | O(1) | N/A | N/A | Baixo |
| **Deque** | O(1)‡ | O(1) | O(1) | N/A | Baixo |

*: Amortizado | †: Com array ordenado + busca binária | ‡: Apenas topo/front/back | B: tamanho do bloco

---

### DynamicArray (ArrayList) - Use Quando:

✅ **INDICADO**: Acesso frequente por índice, inserções no final, cache locality, busca binária

❌ **NÃO INDICADO**: Inserções/remoções frequentes no início/meio

```kotlin
import br.uem.din.datastructures.array.DynamicArray

val array = DynamicArray<Int>()
array.add(1)
array.add(2)
array.add(3)
println(array[1])   // 2
array.removeAt(0)   // remove 1
```

> `DynamicArray<T>` é um typealias para `ArrayList<T>`, aproveitando a implementação nativa de cada plataforma.

---

### LinkedList - Use Quando:

✅ **INDICADO**: Inserções/remoções em posições arbitrárias, tamanho variável, implementar outras estruturas

❌ **NÃO INDICADO**: Acesso por índice frequente, cache locality crítica

```kotlin
import br.uem.din.datastructures.linkedlist.LinkedList

val list = LinkedList<Int>()
list.push(30)        // [30]
list.append(10)      // [30, 10]
list.append(20)      // [30, 10, 20]
println(list.pop())  // 30
```

---

### DoublyLinkedList - Use Quando:

✅ **INDICADO**: Navegação bidirecional, remoção do final em O(1)

```kotlin
import br.uem.din.datastructures.linkedlist.DoublyLinkedList

val dll = DoublyLinkedList<String>()
dll.addFirst("B")
dll.addFirst("A")
dll.addLast("C")          // [A, B, C]
println(dll.removeFirst()) // A
println(dll.removeLast())  // C
```

> **JVM**: delega para `java.util.LinkedList`. **JS/Native**: implementação manual.

---

### Stack - Use Quando:

✅ **INDICADO**: LIFO, backtracking, undo/redo, DFS, parsing de expressões, parênteses balanceados

```kotlin
import br.uem.din.datastructures.stack.ArrayStack

val stack = ArrayStack<Int>()
stack.push(1)
stack.push(2)
println(stack.peek()) // 2
println(stack.pop())  // 2
```

> **JVM**: delega para `java.util.Stack`. **JS/Native**: implementação manual com ArrayList.

---

### Queue - Use Quando:

✅ **INDICADO**: FIFO, BFS, buffer de eventos, scheduling, producer-consumer

```kotlin
import br.uem.din.datastructures.queue.ArrayQueue

val queue = ArrayQueue<Int>()
queue.enqueue(10)
queue.enqueue(20)
println(queue.peek())    // 10
println(queue.dequeue()) // 10
```

> **JVM**: delega para `java.util.ArrayDeque`. **JS/Native**: circular buffer manual.

---

### CircularQueue - Use Quando:

✅ **INDICADO**: Buffer circular de capacidade fixa, reuso de memória

```kotlin
import br.uem.din.datastructures.queue.CircularQueue

val cq = CircularQueue<String>(capacity = 4)
cq.enqueue("A")
cq.enqueue("B")
println(cq.isFull)    // false
println(cq.dequeue())  // A
```

---

### Deque - Use Quando:

✅ **INDICADO**: Inserção/remoção em ambas as pontas, sliding window

```kotlin
import br.uem.din.datastructures.queue.Deque

val deque = Deque<Int>()
deque.enqueue(1)
deque.enqueueFront(0)
println(deque.dequeue())     // 0
println(deque.dequeueBack()) // 1
```

---

### ParallelArray - Use Quando:

✅ **INDICADO**: Dados tabulares com processamento por coluna, otimização de cache (SoA pattern)

```kotlin
import br.uem.din.datastructures.array.ParallelArray

val pa = ParallelArray("id", "name", "score")
pa.addRow(1, "Alice", 9.5)
pa.addRow(2, "Bob", 8.3)
println(pa.get(0, "name"))      // Alice
println(pa.getColumn("score"))  // [9.5, 8.3]
```

---

### BitSet - Use Quando:

✅ **INDICADO**: Conjuntos densos de inteiros, flags, filtros de bits, operações bitwise

```kotlin
import br.uem.din.datastructures.bitset.BitSet

val bs = BitSet(64)
bs.set(10)
bs.set(20)
println(bs[10])     // true
bs.clear(10)
println(bs.length()) // 21
```

> **JVM**: delega para `java.util.BitSet`. **JS**: `IntArray` (32-bit words). **Native**: `LongArray` (64-bit words).

---

## 3. Estruturas Não-Lineares

### 3A. Árvores

| Estrutura | Insert | Search | Delete | Min/Max | Ordenado |
|-----------|--------|--------|--------|---------|----------|
| **BST** | O(log n)§ | O(log n)§ | O(log n)§ | O(log n)§ | Sim |
| **AVL Tree** | O(log n) | O(log n) | O(log n) | O(log n) | Sim |
| **Red-Black Tree** | O(log n) | O(log n) | O(log n) | O(log n) | Sim |
| **Splay Tree** | O(log n)* | O(log n)* | O(log n)* | O(log n)* | Sim |
| **Treap** | O(log n)† | O(log n)† | O(log n)† | O(log n) | Sim |
| **B-Tree** | O(log n) | O(log n) | O(log n) | O(log n) | Sim |
| **Trie** | O(m) | O(m) | O(m) | N/A | Sim (lex) |

*: Amortizado | §: Caso médio, O(n) pior caso | †: Esperado | m: comprimento da string

---

### BST / AVL Tree - Use Quando:

✅ **INDICADO**: Dados ordenados com insert/search/delete O(log n), range queries, min/max

❌ **NÃO INDICADO**: Apenas lookup por chave (use HashTable), dados sem ordem natural

```kotlin
import br.uem.din.datastructures.tree.BinarySearchTree
import br.uem.din.datastructures.tree.AVLTree

val bst = BinarySearchTree<Int>()
bst.insert(50); bst.insert(30); bst.insert(70)
println(bst.contains(30)) // true

val avl = AVLTree<Int>()
for (i in 1..100) avl.insert(i)  // mantém balanceamento O(log n)
println(avl.contains(42)) // true
```

**Quando BST vs AVL?**
- **BST**: Dados inseridos aleatoriamente, implementação simples
- **AVL**: Dados podem vir ordenados (BST degenera para lista), precisa de garantia O(log n)

---

### Red-Black Tree - Use Quando:

✅ **INDICADO**: Mesmo que AVL, mas com balanceamento menos estrito (rotações menos frequentes)

```kotlin
import br.uem.din.datastructures.tree.RedBlackTree

val rbt = RedBlackTree<Int>()
rbt.insert(50); rbt.insert(30); rbt.insert(70)
println(rbt.contains(30)) // true
println(rbt.size())       // 3
```

> **JVM**: delega para `java.util.TreeSet` (internamente Red-Black). **JS/Native**: implementação manual com rotações.

---

### Splay / Treap - Use Quando:

✅ **INDICADO**: Padrões de acesso com localidade temporal (Splay), aleatoriedade desejada (Treap)

```kotlin
import br.uem.din.datastructures.tree.SplayTree
import br.uem.din.datastructures.tree.Treap

val splay = SplayTree<Int>()
splay.insert(10); splay.insert(20); splay.insert(5)

val treap = Treap<Int>()
treap.insert(15); treap.insert(7); treap.insert(23)
```

---

### B-Tree / B+ Tree - Use Quando:

✅ **INDICADO**: Dados em disco, bancos de dados, sistemas de arquivo, índices

```kotlin
import br.uem.din.datastructures.tree.BTree
import br.uem.din.datastructures.tree.BPlusTree

val btree = BTree<Int>(order = 3)
btree.insert(10); btree.insert(20); btree.insert(5)
println(btree.search(10)) // true
```

---

### Trie / RadixTree - Use Quando:

✅ **INDICADO**: Autocomplete, prefix matching, dicionários de strings, longest common prefix

```kotlin
import br.uem.din.datastructures.tree.Trie

val trie = Trie<Char>()
trie.insert("algo".toList())
trie.insert("algorithm".toList())
println(trie.contains("algo".toList()))                     // true
println(trie.collections("al".toList()).map { it.joinToString("") }) // [algo, algorithm]
```

---

### Segment Tree / Fenwick Tree - Use Quando:

✅ **INDICADO**: Range queries (soma, min, max), atualizações pontuais, competições de programação

```kotlin
import br.uem.din.datastructures.tree.SegmentTree
import br.uem.din.datastructures.tree.FenwickTree

// SegmentTree: query(1,3) = soma do intervalo [1,3]
// FenwickTree: prefixSum(k) = soma dos primeiros k+1 elementos
```

---

### 3B. Heaps

| Estrutura | Insert | ExtractMin | Merge | DecreaseKey |
|-----------|--------|------------|-------|-------------|
| **Binary Heap** | O(log n) | O(log n) | O(n) | O(log n) |
| **Binomial Heap** | O(log n) | O(log n) | O(log n) | O(log n) |
| **Fibonacci Heap** | O(1)* | O(log n)* | O(1) | O(1)* |

*: Amortizado

### Heap / Priority Queue - Use Quando:

✅ **INDICADO**: Extrair mínimo/máximo repetidamente, Dijkstra, A*, scheduling, Top-K

❌ **NÃO INDICADO**: Busca por chave arbitrária (O(n)), dados totalmente ordenados

```kotlin
import br.uem.din.datastructures.heap.ComparableHeapImpl
import br.uem.din.datastructures.queue.PriorityQueue

val heap = ComparableHeapImpl<Int>()
heap.insert(42); heap.insert(15); heap.insert(88)
println(heap.peek())   // 15 (min-heap)
println(heap.remove())  // 15

val pq = PriorityQueue<Int>()
pq.enqueue(100); pq.enqueue(5); pq.enqueue(50)
println(pq.dequeue()) // 5
```

> **JVM PriorityQueue**: delega para `java.util.PriorityQueue`. **JS/Native**: heap manual.

---

### 3C. Grafos

| Representação | Espaço | Verificar Aresta | Listar Vizinhos |
|---------------|--------|------------------|-----------------|
| **AdjacencyList** | O(V+E) | O(grau) | O(grau) |
| **AdjacencyMatrix** | O(V²) | O(1) | O(V) |

### Graph - Use Quando:

✅ **INDICADO**: Modelagem de redes, caminhos mínimos, árvore geradora, ciclos, componentes

```kotlin
import br.uem.din.datastructures.graph.AdjacencyList

val graph = AdjacencyList<String>()
val a = graph.createVertex("A")
val b = graph.createVertex("B")
graph.addDirectedEdge(a, b, 4.0)
println(graph.weight(a, b)) // 4.0
```

**Quando List vs Matrix?**
- **List**: E << V² (esparso), maioria dos grafos reais
- **Matrix**: E ≈ V² (denso), Floyd-Warshall, verificar aresta O(1)

---

### DAG - Use Quando:

✅ **INDICADO**: Dependências (build systems, task scheduling), pipelines, ordenação topológica

```kotlin
import br.uem.din.datastructures.graph.DirectedAcyclicGraph

val dag = DirectedAcyclicGraph<String>()
dag.addVertex("A"); dag.addVertex("B"); dag.addVertex("C")
dag.addEdge("A", "B"); dag.addEdge("B", "C")
println(dag.topologicalSort()) // [A, B, C]
```

---

### 3D. Estruturas Espaciais

| Estrutura | Insert | Query (range) | Nearest | Uso |
|-----------|--------|---------------|---------|-----|
| **QuadTree** | O(log n) | O(√n + k) | N/A | 2D |
| **K-D Tree** | O(log n) | O(√n + k) | O(log n) | k-dim |

✅ **INDICADO**: Computação gráfica, geolocalização, jogos, colisão

---

## 4. Estruturas Baseadas em Hash

| Estrutura | Insert | Search | Delete | Uso |
|-----------|--------|--------|--------|-----|
| **HashTable** (chaining) | O(1)* | O(1)* | O(1)* | Geral |
| **OpenAddressing** | O(1)* | O(1)* | O(1)* | Cache locality |
| **CuckooHashTable** | O(1)* | O(1) worst | O(1) | Lookup garantido |
| **BloomFilter** | O(k) | O(k) | N/A | Probabilístico |

*: Amortizado

### HashTable - Use Quando:

✅ **INDICADO**: Lookup O(1) por chave, dicionários, caches, sets, contagem de frequência

❌ **NÃO INDICADO**: Dados precisam estar ordenados, range queries

```kotlin
import br.uem.din.datastructures.hash.HashTable

val ht = HashTable<String, Int>()
ht["idade"] = 25
ht["altura"] = 180
println(ht["idade"])          // 25
println(ht.containsKey("idade")) // true
```

> `HashTable<K,V>` é typealias para `HashMap<K,V>`, aproveitando implementação nativa de cada plataforma.

---

### BloomFilter - Use Quando:

✅ **INDICADO**: Teste de pertinência probabilístico, cache de URLs, detecção de duplicatas em escala

```kotlin
import br.uem.din.datastructures.probabilistic.BloomFilter

val bf = BloomFilter(expectedInsertions = 1000, falsePositiveProbability = 0.01)
bf.add("kotlin")
println(bf.contains("kotlin")) // true (ou falso positivo raro)
println(bf.contains("java"))   // false (definitivamente não está)
```

---

## 5. ADTs / Conjuntos

| Estrutura | Operação Principal | Complexidade | Uso Típico |
|-----------|-------------------|-------------|------------|
| **UnionFind** | Find / Union | O(α(n)) ≈ O(1) | Componentes conexos, Kruskal |
| **Multiset** | Add / Count | O(1) | Contagem de frequência |
| **SkipList** | Insert / Search | O(log n) esperado | Alternativa probabilística a BST |

### Union-Find - Use Quando:

✅ **INDICADO**: Componentes conexos dinâmicos, Kruskal MST, equivalência de classes

```kotlin
import br.uem.din.datastructures.unionfind.UnionFind

val uf = UnionFind(10)
uf.union(0, 1)
uf.union(2, 3)
uf.union(1, 3)
println(uf.connected(0, 3)) // true
println(uf.numberOfSets)     // 7
```

---

### Multiset - Use Quando:

✅ **INDICADO**: Contagem de frequência, histogramas, bags

```kotlin
import br.uem.din.datastructures.set.Multiset

val ms = Multiset<String>()
ms.add("a"); ms.add("a"); ms.add("b")
println(ms.count("a")) // 2
```

---

## Regras de Ouro

1. **Acesso aleatório frequente** → DynamicArray (ArrayList)
2. **Inserções/remoções frequentes** → LinkedList / DoublyLinkedList
3. **LIFO** → Stack (ArrayStack / LinkedStack)
4. **FIFO** → Queue (ArrayQueue / LinkedQueue)
5. **Lookup por chave O(1)** → HashTable / CuckooHashTable
6. **Dados ordenados + range queries** → BST / AVL / Red-Black Tree
7. **Extrair min/max repetidamente** → Heap / PriorityQueue
8. **Merge de heaps eficiente** → BinomialHeap / FibonacciHeap
9. **Prefix matching em strings** → Trie / RadixTree
10. **"Quem pertence ao mesmo grupo?"** → UnionFind
11. **Modelar relações entre entidades** → Graph (AdjacencyList / AdjacencyMatrix)
12. **Sem ciclos + ordenação de dependências** → DAG
13. **Dados espaciais 2D/kD** → QuadTree / KDTree
14. **Range queries em arrays** → SegmentTree / FenwickTree
15. **Teste de pertinência rápido** → BloomFilter
16. **Quando em dúvida e performance importa** → ArrayList (cache locality)

---

## Referências

1. Cormen, T. H. et al. (2009). *Introduction to Algorithms* (3rd ed.), Chapters 6, 10-13, 18-19, 21-22
2. Knuth, D. E. (1997-1998). *The Art of Computer Programming*, Vol 1-3
3. Sedgewick, R. & Wayne, K. (2011). *Algorithms* (4th ed.)
4. Skiena, S. S. (2020). *The Algorithm Design Manual* (3rd ed.), Chapter 3
5. Sleator, D. D. & Tarjan, R. E. (1985). Self-adjusting binary search trees. *JACM*
6. Fredman, M. L. & Tarjan, R. E. (1987). Fibonacci heaps. *JACM*
7. Pagh, R. & Rodler, F. F. (2004). Cuckoo hashing. *J. Algorithms*
8. Fenwick, P. M. (1994). A new data structure for cumulative frequency tables. *Software: P&E*

---

**Última atualização**: 2026-02-15
