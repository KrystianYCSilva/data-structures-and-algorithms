package br.uem.din.datastructures.graph

/**
 * Aresta de um grafo (Graph Edge).
 *
 * Representa uma conexão direcionada entre dois vértices, com um peso opcional.
 * Para grafos não-direcionados, duas arestas opostas são criadas.
 *
 * @param T o tipo do dado armazenado nos vértices.
 * @property source o vértice de origem da aresta.
 * @property destination o vértice de destino da aresta.
 * @property weight o peso (custo) da aresta, ou `null` para arestas sem peso.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.1 — Representations of graphs.
 */
data class Edge<T>(
    val source: Vertex<T>,
    val destination: Vertex<T>,
    val weight: Double? = null
)
