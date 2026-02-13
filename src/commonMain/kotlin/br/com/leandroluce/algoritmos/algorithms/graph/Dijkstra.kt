package br.com.leandroluce.algoritmos.algorithms.graph

import br.com.leandroluce.algoritmos.datastructures.graph.Graph
import br.com.leandroluce.algoritmos.datastructures.graph.Vertex
import br.com.leandroluce.algoritmos.datastructures.queue.PriorityQueue

enum class VisitType {
    START,
    EDGE
}

class Dijkstra<T>(private val graph: Graph<T>) {

    private val costs = mutableMapOf<Vertex<T>, Double?>()
    private val visited = mutableSetOf<Vertex<T>>()

    fun shortestPath(start: Vertex<T>): Map<Vertex<T>, Double?> {
        val priorityQueue = PriorityQueue(compareBy<Vertex<T>> { costs[it] })
        priorityQueue.enqueue(start)
        costs[start] = 0.0

        while (true) {
            val vertex = priorityQueue.dequeue() ?: break
            if (visited.contains(vertex)) continue
            visited.add(vertex)

            val edges = graph.edges(vertex)
            edges.forEach { edge ->
                val weight = edge.weight ?: return@forEach
                if (costs[edge.destination] == null || costs[vertex]!! + weight < costs[edge.destination]!!) {
                    costs[edge.destination] = costs[vertex]!! + weight
                    priorityQueue.enqueue(edge.destination)
                }
            }
        }
        return costs
    }
}
