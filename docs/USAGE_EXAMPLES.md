# Usage Examples

Examples of how to use the data structures and algorithms in this library.

## Data Structures

### Stack (ArrayStack)

```kotlin
import br.uem.din.datastructures.stack.ArrayStack

fun main() {
    val stack = ArrayStack<Int>()
    stack.push(10)
    stack.push(20)
    
    println(stack.peek()) // 20
    println(stack.pop())  // 20
    println(stack.size()) // 1
}
```

### Queue (LinkedQueue)

```kotlin
import br.uem.din.datastructures.queue.LinkedQueue

fun main() {
    val queue = LinkedQueue<String>()
    queue.enqueue("First")
    queue.enqueue("Second")
    
    println(queue.dequeue()) // "First"
    println(queue.isEmpty()) // false
}
```

### Binary Search Tree (BST)

```kotlin
import br.uem.din.datastructures.tree.BinarySearchTree

fun main() {
    val bst = BinarySearchTree<Int>()
    bst.insert(50)
    bst.insert(30)
    bst.insert(70)
    
    println(bst.search(30)) // true
    println(bst.search(99)) // false
    
    bst.delete(30)
    println(bst.search(30)) // false
}
```

### Graph (AdjacencyList)

```kotlin
import br.uem.din.datastructures.graph.AdjacencyList
import br.uem.din.datastructures.graph.Vertex

fun main() {
    val graph = AdjacencyList<String>()
    val vA = Vertex("A")
    val vB = Vertex("B")
    
    graph.addVertex(vA)
    graph.addVertex(vB)
    graph.addEdge(vA, vB, weight = 1.0)
    
    println(graph.getNeighbors(vA).size) // 1
}
```

---

## Algorithms

### Sorting (Bubble Sort)

```kotlin
import br.uem.din.algorithms.sorting.bubbleSort

fun main() {
    val list = mutableListOf(5, 1, 4, 2, 8)
    bubbleSort(list)
    println(list) // [1, 2, 4, 5, 8]
}
```

### Graph Search (Dijkstra)

```kotlin
import br.uem.din.algorithms.graph.dijkstra
import br.uem.din.datastructures.graph.AdjacencyList
import br.uem.din.datastructures.graph.Vertex

fun main() {
    val graph = AdjacencyList<String>()
    val vA = Vertex("A")
    val vB = Vertex("B")
    val vC = Vertex("C")

    graph.addVertex(vA); graph.addVertex(vB); graph.addVertex(vC)
    graph.addEdge(vA, vB, 1.0)
    graph.addEdge(vB, vC, 2.0)
    graph.addEdge(vA, vC, 10.0)

    val distances = dijkstra(graph, vA)
    println(distances[vC]) // 3.0 (A->B->C)
}
```

### Graph Search (BFS)

```kotlin
import br.uem.din.algorithms.graph.breadthFirstSearch
import br.uem.din.datastructures.graph.AdjacencyList
import br.uem.din.datastructures.graph.Vertex

fun main() {
    val graph = AdjacencyList<String>()
    // ... setup graph ...
    val visitedOrder = breadthFirstSearch(graph, startNode)
}
```

---

> **Note:** Algorithms for Searching, String Matching, DP, Greedy, etc., are currently planned and not yet implemented.
