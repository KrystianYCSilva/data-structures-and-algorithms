package br.com.leandroluce.algoritmos.algorithms.graph

import br.com.leandroluce.algoritmos.datastructures.graph.Graph
import br.com.leandroluce.algoritmos.datastructures.graph.Vertex
import br.com.leandroluce.algoritmos.datastructures.queue.Queue
import br.com.leandroluce.algoritmos.datastructures.queue.ArrayQueue

class BreadthFirstSearch<T> {

    fun search(graph: Graph<T>, source: Vertex<T>): ArrayList<Vertex<T>> {
        val queue: Queue<Vertex<T>> = ArrayQueue()
        val enqueued = mutableSetOf<Vertex<T>>()
        val visited = ArrayList<Vertex<T>>()

        queue.enqueue(source)
        enqueued.add(source)

        while (true) {
            val vertex = queue.dequeue() ?: break
            visited.add(vertex)
            val neighbors = graph.edges(vertex)
            neighbors.forEach { edge ->
                if (!enqueued.contains(edge.destination)) {
                    queue.enqueue(edge.destination)
                    enqueued.add(edge.destination)
                }
            }
        }
        return visited
    }
}
