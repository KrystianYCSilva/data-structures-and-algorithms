package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.stack.Stack
import br.uem.din.datastructures.stack.ArrayStack

class DepthFirstSearch<T> {

    fun search(graph: Graph<T>, source: Vertex<T>): ArrayList<Vertex<T>> {
        val stack: Stack<Vertex<T>> = ArrayStack()
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
