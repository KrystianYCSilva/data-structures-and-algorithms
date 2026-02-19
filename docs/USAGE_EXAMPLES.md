# Usage Examples

Exemplos atualizados com a API real da biblioteca modularizada.

## Dependencias por modulo

```kotlin
dependencies {
    implementation("br.uem.din:datastructures:0.1.0")
    implementation("br.uem.din:algorithms:0.1.0")
    implementation("br.uem.din:extensions:0.1.0")
    implementation("br.uem.din:optimization:0.1.0")
}
```

## Data Structures

### Stack (arrayStackOf)

```kotlin
import br.uem.din.datastructures.stack.arrayStackOf

fun main() {
    val stack = arrayStackOf<Int>()
    stack.push(10)
    stack.push(20)

    println(stack.peek()) // 20
    println(stack.pop())  // 20
    println(stack.size)   // 1
}
```

### Queue (arrayQueueOf)

```kotlin
import br.uem.din.datastructures.queue.arrayQueueOf

fun main() {
    val queue = arrayQueueOf<String>()
    queue.enqueue("First")
    queue.enqueue("Second")

    println(queue.dequeue()) // First
    println(queue.peek())    // Second
}
```

### Binary Search Tree

```kotlin
import br.uem.din.datastructures.tree.BinarySearchTree

fun main() {
    val bst = BinarySearchTree<Int>()
    bst.insert(50)
    bst.insert(30)
    bst.insert(70)

    println(bst.contains(30)) // true
    println(bst.remove(30))   // true
    println(bst.contains(30)) // false
}
```

### Graph (AdjacencyList)

```kotlin
import br.uem.din.datastructures.graph.AdjacencyList

fun main() {
    val graph = AdjacencyList<String>()
    val a = graph.createVertex("A")
    val b = graph.createVertex("B")

    graph.addDirectedEdge(a, b, 1.0)
    println(graph.weight(a, b)) // 1.0
}
```

## Algorithms

### Sorting (bubbleSort)

```kotlin
import br.uem.din.algorithms.sorting.bubbleSort

fun main() {
    val list = mutableListOf(5, 1, 4, 2, 8)
    bubbleSort(list)
    println(list) // [1, 2, 4, 5, 8]
}
```

### Searching (binarySearch)

```kotlin
import br.uem.din.algorithms.searching.binarySearch

fun main() {
    val sorted = listOf(1, 3, 4, 7, 9)
    println(binarySearch(sorted, 7)) // 3
}
```

### Shortest Path (Dijkstra)

```kotlin
import br.uem.din.algorithms.graph.Dijkstra
import br.uem.din.datastructures.graph.AdjacencyList

fun main() {
    val graph = AdjacencyList<String>()
    val a = graph.createVertex("A")
    val b = graph.createVertex("B")
    val c = graph.createVertex("C")

    graph.addDirectedEdge(a, b, 1.0)
    graph.addDirectedEdge(b, c, 2.0)
    graph.addDirectedEdge(a, c, 10.0)

    val distances = Dijkstra(graph).shortestPath(a)
    println(distances[c]) // 3.0
}
```

## Extensions

```kotlin
import br.uem.din.extensions.combinations
import br.uem.din.extensions.powerSet

fun main() {
    val items = listOf(1, 2, 3)
    println(items.combinations(2))
    println(items.powerSet().size) // 8
}
```

## Optimization

```kotlin
import br.uem.din.optimization.BinaryProblem
import br.uem.din.optimization.simulatedAnnealing

fun main() {
    val problem = BinaryProblem(size = 30) { bits ->
        bits.count { it }.toDouble()
    }

    val result = simulatedAnnealing(problem)
    println(result.cost)
}
```
