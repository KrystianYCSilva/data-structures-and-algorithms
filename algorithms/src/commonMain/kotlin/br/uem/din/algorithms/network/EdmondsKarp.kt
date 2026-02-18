package br.uem.din.algorithms.network

import br.uem.din.datastructures.graph.Vertex
import kotlin.math.min

/**
 * Algoritmo de Edmonds-Karp para Fluxo Máximo.
 *
 * Implementação do método de Ford-Fulkerson usando BFS para encontrar o caminho
 * de aumento (augmenting path) mais curto em termos de número de arestas.
 *
 * @param capacity matriz de capacidades onde capacity[u][v] é a capacidade da aresta u->v.
 *                 Assume-se índices dos vértices de 0 a n-1.
 * @param source índice do vértice fonte (s).
 * @param sink índice do vértice sorvedouro (t).
 * @return o fluxo máximo possível de s para t.
 *
 * Complexidade: O(V * E^2)
 */
public fun edmondsKarp(capacity: Array<DoubleArray>, source: Int, sink: Int): Double {
    val n = capacity.size
    var maxFlow = 0.0
    
    // Residual capacity graph, initially equal to original capacity
    // We create a mutable copy to update residual capacities
    val residual = Array(n) { i -> capacity[i].copyOf() }
    
    val parent = IntArray(n) // Armazena o caminho encontrado pela BFS

    while (bfs(residual, source, sink, parent)) {
        var pathFlow = Double.POSITIVE_INFINITY
        
        // Encontrar a capacidade residual mínima ao longo do caminho encontrado
        var v = sink
        while (v != source) {
            val u = parent[v]
            pathFlow = min(pathFlow, residual[u][v])
            v = u
        }

        // Atualizar as capacidades residuais e adicionar fluxo reverso
        v = sink
        while (v != source) {
            val u = parent[v]
            residual[u][v] = residual[u][v] - pathFlow
            residual[v][u] = residual[v][u] + pathFlow
            v = u
        }

        maxFlow += pathFlow
    }

    return maxFlow
}

/**
 * BFS para encontrar caminho de aumento no grafo residual.
 */
private fun bfs(residual: Array<DoubleArray>, s: Int, t: Int, parent: IntArray): Boolean {
    val n = residual.size
    val visited = BooleanArray(n)
    val queue = ArrayDeque<Int>()

    queue.add(s)
    visited[s] = true
    parent[s] = -1

    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()

        for (v in 0 until n) {
            if (!visited[v] && residual[u][v] > 0) {
                if (v == t) {
                    parent[v] = u
                    return true
                }
                queue.add(v)
                parent[v] = u
                visited[v] = true
            }
        }
    }
    return false
}
