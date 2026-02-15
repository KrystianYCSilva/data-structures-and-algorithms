
## Exemplos Kotlin Multiplatform (Estruturas de Dados)

### 1. BloomFilter (Probabilistico)

```kotlin
import br.uem.din.datastructures.probabilistic.BloomFilter

fun main() {
    val bf = BloomFilter(expectedInsertions = 1000, falsePositiveProbability = 0.01)

    bf.add("leandro")
    bf.add("maria")

    println(bf.contains("leandro")) // true
    println(bf.contains("joao"))    // false
}
```

### 2. BitSet (Multiplataforma)

```kotlin
import br.uem.din.datastructures.bitset.BitSet

fun main() {
    val bitSet = BitSet(64)

    bitSet.set(10)
    bitSet.set(20)
    bitSet.clear(10)

    println(bitSet[20])      // true
    println(bitSet[10])      // false
    println(bitSet.length()) // 21
}
```

### 3. RedBlackTree (Balanceada)

```kotlin
import br.uem.din.datastructures.tree.RedBlackTree

fun main() {
    val tree = RedBlackTree<Int>()

    tree.insert(50)
    tree.insert(30)
    tree.insert(70)

    println(tree.contains(30)) // true
    tree.delete(30)
    println(tree.contains(30)) // false
}
```

### 4. SkipList (Alternativa Probabilistica)

```kotlin
import br.uem.din.datastructures.skiplist.SkipList

fun main() {
    val skipList = SkipList<String>()

    skipList.insert("Kotlin")
    skipList.insert("Java")
    skipList.insert("C++")

    println(skipList.contains("Kotlin")) // true
    skipList.remove("Java")
    println(skipList.contains("Java"))   // false
}
```

### 5. CircularLinkedList

```kotlin
import br.uem.din.datastructures.linkedlist.CircularLinkedList

fun main() {
    val list = CircularLinkedList<Int>()
    list.add(1)
    list.add(2)
    list.add(3)

    println(list) // [1, 2, 3]
}
```

### 6. SplayTree

```kotlin
import br.uem.din.datastructures.tree.SplayTree

fun main() {
    val tree = SplayTree<Int>()

    tree.insert(10)
    tree.insert(20)
    tree.insert(5)

    println(tree.find(20))     // 20 (agora na raiz)
    println(tree.contains(5))  // true
    tree.delete(10)
    println(tree.contains(10)) // false
}
```

### 7. Treap

```kotlin
import br.uem.din.datastructures.tree.Treap

fun main() {
    val treap = Treap<Int>()

    treap.insert(15)
    treap.insert(7)
    treap.insert(23)

    println(treap.contains(7))  // true
    treap.delete(7)
    println(treap.contains(7))  // false
}
```

### 8. CartesianTree

```kotlin
import br.uem.din.datastructures.tree.CartesianTree

fun main() {
    val values = intArrayOf(3, 2, 6, 1, 9)
    val tree = CartesianTree.buildFrom(values)

    println(tree.root?.value)      // 1 (minimo fica na raiz)
    println(tree.inOrderTraversal()) // [3, 2, 6, 1, 9] (preserva ordem original)
}
```

### 9. BTree

```kotlin
import br.uem.din.datastructures.tree.BTree

fun main() {
    val btree = BTree<Int>(order = 3)

    btree.insert(10)
    btree.insert(20)
    btree.insert(5)
    btree.insert(15)
    btree.insert(25)

    println(btree.search(15)) // true
    btree.delete(10)
    println(btree.search(10)) // false
}
```

### 10. BPlusTree

```kotlin
import br.uem.din.datastructures.tree.BPlusTree

fun main() {
    val bpt = BPlusTree<Int, String>(order = 4)

    bpt.insert(1, "um")
    bpt.insert(2, "dois")
    bpt.insert(3, "tres")

    println(bpt.search(2))          // "dois"
    println(bpt.rangeSearch(1, 3))  // ["um", "dois", "tres"]
}
```

### 11. RadixTree (Trie Compacta)

```kotlin
import br.uem.din.datastructures.tree.RadixTree

fun main() {
    val radix = RadixTree()

    radix.insert("romane")
    radix.insert("romanus")
    radix.insert("romulus")

    println(radix.search("romane"))        // true
    println(radix.search("rom"))           // false
    println(radix.startsWith("roman"))     // true
    radix.delete("romane")
    println(radix.search("romane"))        // false
}
```

### 12. SuffixTree

```kotlin
import br.uem.din.datastructures.tree.SuffixTree

fun main() {
    val st = SuffixTree("banana")

    println(st.contains("ana"))   // true
    println(st.contains("xyz"))   // false
    println(st.search("nan"))     // [2] (posicoes de ocorrencia)
}
```

### 13. SegmentTree

```kotlin
import br.uem.din.datastructures.tree.SegmentTree

fun main() {
    val data = intArrayOf(1, 3, 5, 7, 9, 11)
    val seg = SegmentTree(data) { a, b -> a + b }

    println(seg.query(1, 3))  // 15 (3 + 5 + 7)
    seg.update(2, 10)         // altera indice 2 para 10
    println(seg.query(1, 3))  // 20 (3 + 10 + 7)
}
```

### 14. FenwickTree (Binary Indexed Tree)

```kotlin
import br.uem.din.datastructures.tree.FenwickTree

fun main() {
    val fenwick = FenwickTree(6)

    fenwick.update(0, 1)
    fenwick.update(1, 3)
    fenwick.update(2, 5)
    fenwick.update(3, 7)

    println(fenwick.prefixSum(3))   // 16 (1 + 3 + 5 + 7)
    println(fenwick.rangeSum(1, 2)) // 8  (3 + 5)
}
```

### 15. BinomialHeap

```kotlin
import br.uem.din.datastructures.heap.BinomialHeap

fun main() {
    val heap = BinomialHeap<Int>()

    heap.insert(10)
    heap.insert(3)
    heap.insert(7)

    println(heap.findMin())    // 3
    println(heap.extractMin()) // 3
    println(heap.findMin())    // 7

    val other = BinomialHeap<Int>()
    other.insert(1)
    heap.merge(other)
    println(heap.findMin())    // 1
}
```

### 16. FibonacciHeap

```kotlin
import br.uem.din.datastructures.heap.FibonacciHeap

fun main() {
    val heap = FibonacciHeap<Int>()

    heap.insert(20)
    heap.insert(5)
    heap.insert(15)

    println(heap.findMin())    // 5
    heap.decreaseKey(20, 2)
    println(heap.findMin())    // 2
    println(heap.extractMin()) // 2
}
```

### 17. QuadTree

```kotlin
import br.uem.din.datastructures.spatial.QuadTree
import br.uem.din.datastructures.spatial.Point
import br.uem.din.datastructures.spatial.Rectangle

fun main() {
    val boundary = Rectangle(x = 0.0, y = 0.0, width = 100.0, height = 100.0)
    val qt = QuadTree<String>(boundary, capacity = 4)

    qt.insert(Point(10.0, 20.0, "A"))
    qt.insert(Point(50.0, 50.0, "B"))
    qt.insert(Point(80.0, 90.0, "C"))

    val range = Rectangle(x = 0.0, y = 0.0, width = 60.0, height = 60.0)
    val found = qt.query(range)
    println(found.map { it.data }) // ["A", "B"]
}
```

### 18. KDTree

```kotlin
import br.uem.din.datastructures.spatial.KDTree

fun main() {
    val points = listOf(
        doubleArrayOf(2.0, 3.0),
        doubleArrayOf(5.0, 4.0),
        doubleArrayOf(9.0, 6.0),
        doubleArrayOf(4.0, 7.0),
        doubleArrayOf(8.0, 1.0)
    )
    val kd = KDTree(points, dimensions = 2)

    val nearest = kd.nearestNeighbor(doubleArrayOf(5.0, 5.0))
    println(nearest?.toList()) // [5.0, 4.0]

    val inRange = kd.rangeSearch(doubleArrayOf(2.0, 2.0), doubleArrayOf(6.0, 5.0))
    println(inRange.size) // 2
}
```

### 19. OpenAddressingHashTable

```kotlin
import br.uem.din.datastructures.hashtable.OpenAddressingHashTable

fun main() {
    val table = OpenAddressingHashTable<String, Int>()

    table.put("alpha", 1)
    table.put("beta", 2)
    table.put("gamma", 3)

    println(table.get("beta"))      // 2
    println(table.containsKey("alpha")) // true
    table.remove("alpha")
    println(table.containsKey("alpha")) // false
    println(table.size)             // 2
}
```

### 20. CuckooHashTable

```kotlin
import br.uem.din.datastructures.hashtable.CuckooHashTable

fun main() {
    val table = CuckooHashTable<String, Int>()

    table.put("x", 10)
    table.put("y", 20)
    table.put("z", 30)

    println(table.get("y"))  // 20
    table.put("y", 25)
    println(table.get("y"))  // 25
    table.remove("z")
    println(table.size)      // 2
}
```

### 21. Multiset (Bag)

```kotlin
import br.uem.din.datastructures.set.Multiset

fun main() {
    val ms = Multiset<String>()

    ms.add("a")
    ms.add("b")
    ms.add("a")
    ms.add("a")

    println(ms.count("a"))  // 3
    println(ms.count("b"))  // 1
    println(ms.totalSize)   // 4
    ms.remove("a")
    println(ms.count("a"))  // 2
    ms.removeAll("a")
    println(ms.count("a"))  // 0
}
```

### 22. DirectedAcyclicGraph (DAG)

```kotlin
import br.uem.din.datastructures.graph.DirectedAcyclicGraph

fun main() {
    val dag = DirectedAcyclicGraph<String>()

    dag.addVertex("A")
    dag.addVertex("B")
    dag.addVertex("C")
    dag.addVertex("D")

    dag.addEdge("A", "B")
    dag.addEdge("A", "C")
    dag.addEdge("B", "D")
    dag.addEdge("C", "D")

    println(dag.topologicalSort()) // [A, B, C, D] ou [A, C, B, D]
    println(dag.hasEdge("A", "B")) // true
    println(dag.predecessors("D")) // [B, C]
}
```
