
## 5. Exemplos Kotlin Multiplatform (Novas Estruturas)

### 5.1 BloomFilter (Probabilístico)

```kotlin
import br.com.leandroluce.algoritmos.datastructures.probabilistic.BloomFilter

fun main() {
    // Bloom Filter para 1000 elementos com 1% de chance de falso positivo
    val bf = BloomFilter(expectedInsertions = 1000, falsePositiveProbability = 0.01)
    
    bf.add("leandro")
    bf.add("maria")
    
    println(bf.contains("leandro")) // true (definitivamente inserido ou falso positivo muito raro)
    println(bf.contains("joao"))    // false (definitivamente não inserido)
}
```

### 5.2 BitSet (Multiplataforma)

```kotlin
import br.com.leandroluce.algoritmos.datastructures.bitset.BitSet

fun main() {
    val bitSet = BitSet(64)
    
    bitSet.set(10)
    bitSet.set(20)
    bitSet.clear(10)
    
    println(bitSet[20]) // true
    println(bitSet[10]) // false
    println(bitSet.length()) // 21 (índice do bit mais alto + 1)
}
```

### 5.3 RedBlackTree (Balanceada)

```kotlin
import br.com.leandroluce.algoritmos.datastructures.tree.RedBlackTree

fun main() {
    val tree = RedBlackTree<Int>()
    
    // Inserção mantém a árvore balanceada (altura O(log n))
    tree.insert(50)
    tree.insert(30)
    tree.insert(70)
    
    println(tree.contains(30)) // true
}
```

### 5.4 SkipList (Alternativa Probabilística)

```kotlin
import br.com.leandroluce.algoritmos.datastructures.skiplist.SkipList

fun main() {
    val skipList = SkipList<String>()
    
    skipList.insert("Kotlin")
    skipList.insert("Java")
    skipList.insert("C++")
    
    println(skipList.contains("Kotlin")) // true
}
```

### 5.5 CircularLinkedList

```kotlin
import br.com.leandroluce.algoritmos.datastructures.linkedlist.CircularLinkedList

fun main() {
    val list = CircularLinkedList<Int>()
    list.add(1)
    list.add(2)
    list.add(3)
    
    println(list) // [1, 2, 3] (circularidade tratada no toString)
}
```
