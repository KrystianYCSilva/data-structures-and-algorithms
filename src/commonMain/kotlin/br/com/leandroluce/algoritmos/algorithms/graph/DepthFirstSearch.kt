package br.com.leandroluce.algoritmos.algorithms.graph

import br.com.leandroluce.algoritmos.datastructures.graph.Graph
import br.com.leandroluce.algoritmos.datastructures.graph.Vertex
import br.com.leandroluce.algoritmos.datastructures.stack.Stack
import br.com.leandroluce.algoritmos.datastructures.stack.StackImpl

class DepthFirstSearch<T> {

    fun search(graph: Graph<T>, source: Vertex<T>): ArrayList<Vertex<T>> {
        val stack: Stack<Vertex<T>> = StackImpl()
        val pushed = mutableSetOf<Vertex<T>>()
        val visited = ArrayList<Vertex<T>>()

        stack.push(source)
        pushed.add(source)
        visited.add(source)

        outer@ while (true) {
            val vertex = stack.peek() ?: break
            val neighbors = graph.edges(vertex)
            for (edge in neighbors) {
                if (!pushed.contains(edge.destination)) {
                    stack.push(edge.destination)
                    pushed.add(edge.destination)
                    visited.add(edge.destination)
                    continue@outer
                }
            }
            stack.pop()
        }
        return visited
    }
}
