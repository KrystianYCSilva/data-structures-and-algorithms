---
description: "Guia pratico para escolher estruturas de dados da biblioteca com base em padrao de acesso e custo assintotico."
---

# Guia de Escolha de Estruturas de Dados

Este guia resume quando usar cada estrutura disponivel no modulo `:datastructures`.

## 1) Colecoes lineares

| Necessidade | Estrutura recomendada | Custo principal |
|---|---|---|
| LIFO (pilha), undo/redo, DFS | `arrayStackOf()` / `LinkedStack` | push/pop: O(1) |
| FIFO (fila), processamento em ordem | `arrayQueueOf()` / `LinkedQueue` | enqueue/dequeue: O(1) |
| Duas pontas (sliding window) | `Deque` | operacoes nas pontas: O(1) |
| Lista com append frequente e acesso por indice | `DynamicArray` | acesso: O(1), append: O(1) amort. |
| Insercao/remoção frequente no meio via iteracao | `LinkedList` / `DoublyLinkedList` | alteracao local: O(1) |

Exemplo rapido:

```kotlin
import br.uem.din.datastructures.queue.arrayQueueOf
import br.uem.din.datastructures.stack.arrayStackOf

val stack = arrayStackOf<Int>()
stack.push(1)

val queue = arrayQueueOf<Int>()
queue.enqueue(1)
```

## 2) Conjuntos e dicionarios

| Necessidade | Estrutura recomendada | Custo principal |
|---|---|---|
| Mapa chave-valor geral | `OpenAddressingHashTable` / `SeparateChainingHashTable` | get/put: O(1) amort. |
| Distribuicao com cuckoo hashing | `CuckooHashTable` | get/put: O(1) esperado |
| Contagem de frequencia | `Multiset` | add/count: O(1) amort. |
| Consulta probabilistica de pertinencia | `BloomFilter` | contains/add: O(k) |

## 3) Estruturas ordenadas

| Necessidade | Estrutura recomendada | Custo principal |
|---|---|---|
| Ordenacao com custo medio simples | `BinarySearchTree` | O(log n) medio |
| Garantia logaritmica estrita | `AVLTree` / `redBlackTreeOf()` | O(log n) |
| Acesso localidade temporal (amortizado) | `SplayTree` | O(log n) amort. |
| Dado em disco/indice com fanout | `BTree` / `BPlusTree` | O(log n) |
| Prefixo de strings/chaves | `Trie` / `RadixTree` | O(m) |

Exemplo rapido:

```kotlin
import br.uem.din.datastructures.tree.AVLTree

val tree = AVLTree<Int>()
tree.insert(10)
tree.insert(5)
tree.insert(15)
println(tree.contains(5))
```

## 4) Grafos e caminhos

| Necessidade | Estrutura recomendada |
|---|---|
| Grafo esparso | `AdjacencyList` |
| Grafo denso / acesso O(1) a aresta | `AdjacencyMatrix` |
| Grafo aciclico direcionado com restricao de ciclo | `DirectedAcyclicGraph` |

## 5) Consultas especializadas

| Necessidade | Estrutura recomendada | Custo principal |
|---|---|---|
| Prefix/range sum em array | `FenwickTree` / `SegmentTree` | query/update: O(log n) |
| Particionamento espacial | `KDTree` / `QuadTree` | busca espacial: sublinear medio |
| Componentes conectados / uniao de conjuntos | `UnionFind` | quase O(1) amort. |
| Operacoes bitwise compactas | `bitSetOf()` | set/get: O(1) |

## 6) Interface imutavel vs mutavel

A biblioteca segue padrao Kotlin stdlib:

- Interface de leitura: `Stack<T>`, `Queue<T>`, `SearchTree<T>`, etc.
- Interface mutavel: `MutableStack<T>`, `MutableQueue<T>`, `MutableSearchTree<T>`, etc.
- Views de leitura sem copia: `asReadOnly()` (live view)

Exemplo:

```kotlin
import br.uem.din.datastructures.asReadOnly
import br.uem.din.datastructures.stack.arrayStackOf

val mutable = arrayStackOf<Int>()
mutable.push(42)

val readonly = mutable.asReadOnly()
println(readonly.peek())
```

## 7) Regra de bolso

1. Se precisa de ordem por chave, use arvores balanceadas.
2. Se precisa de lookup rapido sem ordem, use hash tables.
3. Se precisa de grafo geral, comece com `AdjacencyList`.
4. Se precisa de range query, use Fenwick/SegmentTree.
5. Se precisa de operacao em bits, use `bitSetOf()`.
