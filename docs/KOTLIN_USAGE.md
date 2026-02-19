# Kotlin Usage Guide (KMP)

Guia rapido com exemplos idiomaticos para uso em projetos Kotlin Multiplatform.

## 1) Dependencias recomendadas

```kotlin
dependencies {
    implementation(platform("br.uem.din:bom:0.1.0"))
    implementation("br.uem.din:datastructures")
    implementation("br.uem.din:algorithms")
    implementation("br.uem.din:extensions")
    implementation("br.uem.din:optimization")
}
```

## 2) Estruturas de dados

### Stack e Queue

```kotlin
import br.uem.din.datastructures.queue.arrayQueueOf
import br.uem.din.datastructures.stack.arrayStackOf

fun basicCollections() {
    val stack = arrayStackOf<Int>()
    stack.push(1)
    stack.push(2)

    val queue = arrayQueueOf<Int>()
    queue.enqueue(10)
    queue.enqueue(20)

    println(stack.pop())   // 2
    println(queue.dequeue()) // 10
}
```

### SearchTree + view imutavel

```kotlin
import br.uem.din.datastructures.asReadOnly
import br.uem.din.datastructures.tree.AVLTree

fun immutableViewSample() {
    val tree = AVLTree<Int>()
    tree.insert(4)
    tree.insert(2)
    tree.insert(6)

    val view = tree.asReadOnly()
    println(view.inOrder()) // [2, 4, 6]
}
```

### BitSet

```kotlin
import br.uem.din.datastructures.bitset.bitSetOf

fun bitSetSample() {
    val bits = bitSetOf(64)
    bits.set(3)
    bits.set(10)
    println(bits[3])          // true
    println(bits.cardinality()) // 2
}
```

## 3) Algoritmos

### Sorting + Searching

```kotlin
import br.uem.din.algorithms.searching.binarySearch
import br.uem.din.algorithms.sorting.quickSort

fun algorithmSample() {
    val data = mutableListOf(9, 2, 7, 1, 5)
    quickSort(data)
    val index = binarySearch(data, 7)
    println(index) // 3
}
```

### Grafos + Dijkstra

```kotlin
import br.uem.din.algorithms.graph.Dijkstra
import br.uem.din.datastructures.graph.AdjacencyList

fun graphSample() {
    val graph = AdjacencyList<String>()
    val a = graph.createVertex("A")
    val b = graph.createVertex("B")
    val c = graph.createVertex("C")

    graph.addDirectedEdge(a, b, 2.0)
    graph.addDirectedEdge(b, c, 3.0)
    graph.addDirectedEdge(a, c, 10.0)

    val result = Dijkstra(graph).shortestPath(a)
    println(result[c]) // 5.0
}
```

## 4) Extensions

```kotlin
import br.uem.din.extensions.gcd
import br.uem.din.extensions.lcsLength
import br.uem.din.extensions.powerSet

fun extensionSample() {
    println(48 gcd 18)                  // 6
    println("abcdef" lcsLength "azced") // 3
    println(listOf(1, 2, 3).powerSet().size) // 8
}
```

## 5) Otimizacao

```kotlin
import br.uem.din.optimization.BinaryProblem
import br.uem.din.optimization.geneticAlgorithm

fun optimizationSample() {
    val problem = BinaryProblem(size = 50) { x ->
        x.count { it }.toDouble()
    }

    val best = geneticAlgorithm(problem)
    println(best.cost)
}
```

## 6) Comandos uteis

```bash
./gradlew :datastructures:check
./gradlew :algorithms:check
./gradlew :extensions:check
./gradlew :optimization:check
./gradlew check
```
