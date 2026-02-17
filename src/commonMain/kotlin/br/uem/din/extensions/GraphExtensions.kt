package br.uem.din.extensions

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.graph.Edge

/**
 * Extensões para grafos.
 */

/**
 * Realiza uma busca em largura (BFS) a partir de um vértice e retorna a lista de vértices visitados na ordem.
 */
public fun <T> Graph<T>.bfs(start: Vertex<T>): List<Vertex<T>> {
    val visited = mutableSetOf<Vertex<T>>()
    val queue = ArrayDeque<Vertex<T>>()
    val result = mutableListOf<Vertex<T>>()

    queue.add(start)
    visited.add(start)

    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()
        result.add(u)

        for (edge in this.edges(u)) {
            val v = edge.destination
            if (v !in visited) {
                visited.add(v)
                queue.add(v)
            }
        }
    }
    return result
}

/**
 * Realiza uma busca em profundidade (DFS) a partir de um vértice.
 */
public fun <T> Graph<T>.dfs(start: Vertex<T>): List<Vertex<T>> {
    val visited = mutableSetOf<Vertex<T>>()
    val result = mutableListOf<Vertex<T>>()
    dfsRecursive(this, start, visited, result)
    return result
}

private fun <T> dfsRecursive(graph: Graph<T>, u: Vertex<T>, visited: MutableSet<Vertex<T>>, result: MutableList<Vertex<T>>) {
    visited.add(u)
    result.add(u)
    for (edge in graph.edges(u)) {
        if (edge.destination !in visited) {
            dfsRecursive(graph, edge.destination, visited, result)
        }
    }
}
