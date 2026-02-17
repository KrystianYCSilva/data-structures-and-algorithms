package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import kotlin.math.min

/**
 * Algoritmo de Floyd-Warshall para caminhos mínimos entre todos os pares.
 *
 * Calcula os caminhos mais curtos entre todos os pares de vértices em um grafo ponderado
 * (direcionado ou não). Suporta pesos negativos, mas não ciclos negativos.
 *
 * Complexidade Temporal: O(V^3)
 * Complexidade Espacial: O(V^2)
 *
 * @param graph o grafo a ser analisado.
 * @param vertices a lista de vértices do grafo (necessária pois Graph interface é read-only e não expõe lista total).
 * @return Uma matriz de distâncias (Map de Map) onde result[u][v] é a distância de u para v.
 */
public fun <T> floydWarshall(graph: Graph<T>, vertices: List<Vertex<T>>): Map<Vertex<T>, Map<Vertex<T>, Double>> {
    val dist = mutableMapOf<Vertex<T>, MutableMap<Vertex<T>, Double>>()

    // 1. Inicialização
    for (u in vertices) {
        dist[u] = mutableMapOf()
        for (v in vertices) {
            if (u == v) {
                dist[u]!![v] = 0.0
            } else {
                // Peso direto da aresta u->v ou infinito
                val w = graph.weight(u, v)
                dist[u]!![v] = w ?: Double.POSITIVE_INFINITY
            }
        }
    }

    // 2. Programação Dinâmica
    for (k in vertices) {
        for (i in vertices) {
            for (j in vertices) {
                val distIK = dist[i]!![k]!!
                val distKJ = dist[k]!![j]!!
                val currentDist = dist[i]!![j]!!

                if (distIK != Double.POSITIVE_INFINITY && distKJ != Double.POSITIVE_INFINITY) {
                    if (distIK + distKJ < currentDist) {
                        dist[i]!![j] = distIK + distKJ
                    }
                }
            }
        }
    }
    
    // Opcional: Detecção de ciclos negativos (se dist[i][i] < 0 para algum i)
    for (v in vertices) {
        if (dist[v]!![v]!! < 0.0) {
             throw IllegalStateException("Ciclo negativo detectado envolvendo o vértice ${v.data}")
        }
    }

    return dist
}
