package br.uem.din.datastructures.graph

/**
 * Tipo de aresta em um grafo.
 *
 * Define se a aresta é direcionada (de origem para destino apenas) ou
 * não-direcionada (bidirecional).
 */
enum class EdgeType {
    DIRECTED,
    UNDIRECTED
}

/**
 * Aresta de um grafo (Graph Edge).
 *
 * Representa uma conexão entre dois vértices, com um peso opcional e um tipo
 * que indica se a aresta é direcionada ou não-direcionada.
 *
 * @param T o tipo do dado armazenado nos vértices.
 * @property source o vértice de origem da aresta.
 * @property destination o vértice de destino da aresta.
 * @property weight o peso (custo) da aresta, ou `null` para arestas sem peso.
 * @property type o tipo da aresta: [EdgeType.DIRECTED] ou [EdgeType.UNDIRECTED].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.1 — Representations of graphs.
 */
data class Edge<T>(
    val source: Vertex<T>,
    val destination: Vertex<T>,
    val weight: Double? = null,
    val type: EdgeType = EdgeType.DIRECTED
)
