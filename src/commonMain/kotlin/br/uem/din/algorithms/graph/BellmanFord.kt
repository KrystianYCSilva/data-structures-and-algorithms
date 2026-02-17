package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex

/**
 * Algoritmo de Bellman-Ford para caminhos mínimos de fonte única.
 *
 * Calcula os caminhos mais curtos de um vértice de origem para todos os outros vértices
 * em um grafo ponderado direcionado. Diferente de Dijkstra, suporta arestas com pesos negativos.
 * Também é capaz de detectar ciclos negativos alcançáveis a partir da origem.
 *
 * Complexidade Temporal: O(V * E)
 * Complexidade Espacial: O(V)
 *
 * @param graph o grafo a ser analisado.
 * @param source o vértice de origem.
 * @return Um mapa associando cada vértice à sua distância mínima da origem.
 * @throws IllegalStateException se um ciclo de peso negativo for detectado.
 */
public fun <T> bellmanFord(graph: Graph<T>, source: Vertex<T>): Map<Vertex<T>, Double> {
    val distances = mutableMapOf<Vertex<T>, Double>()
    val allVertices = HashSet<Vertex<T>>()

    val queue = ArrayDeque<Vertex<T>>()
    queue.add(source)
    allVertices.add(source)
    distances[source] = 0.0

    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()
        for (edge in graph.edges(u)) {
            val v = edge.destination
            if (allVertices.add(v)) {
                queue.add(v)
                distances[v] = Double.POSITIVE_INFINITY
            }
            distances.getOrPut(v) { Double.POSITIVE_INFINITY }
        }
    }

    val vertexCount = allVertices.size

    for (i in 1 until vertexCount) {
        var changed = false
        for (u in allVertices) {
            val distU = distances[u] ?: Double.POSITIVE_INFINITY
            if (distU != Double.POSITIVE_INFINITY) {
                for (edge in graph.edges(u)) {
                    val v = edge.destination
                    val weight = edge.weight ?: 1.0
                    if (distU + weight < (distances[v] ?: Double.POSITIVE_INFINITY)) {
                        distances[v] = distU + weight
                        changed = true
                    }
                }
            }
        }
        if (!changed) break
    }

    for (u in allVertices) {
        val distU = distances[u] ?: Double.POSITIVE_INFINITY
        if (distU != Double.POSITIVE_INFINITY) {
            for (edge in graph.edges(u)) {
                val v = edge.destination
                val weight = edge.weight ?: 1.0
                if (distU + weight < (distances[v] ?: Double.POSITIVE_INFINITY)) {
                    throw IllegalStateException("O grafo contém um ciclo de peso negativo alcançável a partir da origem.")
                }
            }
        }
    }

    return distances
}
