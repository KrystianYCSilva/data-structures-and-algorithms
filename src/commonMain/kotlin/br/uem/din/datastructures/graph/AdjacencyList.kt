package br.uem.din.datastructures.graph

/**
 * Grafo representado por lista de adjacência (Adjacency List).
 *
 * Cada vértice mapeia para uma lista de arestas que partem dele.
 * Essa representação é eficiente em memória para grafos esparsos (|E| << |V|²).
 *
 * Complexidades:
 * - [createVertex]: O(1)
 * - [addDirectedEdge]: O(1)
 * - [edges]: O(1) (retorna a lista existente)
 * - [weight]: O(deg(v)), onde deg(v) é o grau do vértice de origem
 * - Espaço: O(|V| + |E|)
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.1 — Adjacency-list representation.
 */
class AdjacencyList<T> : Graph<T> {

    private val adjacencies: HashMap<Vertex<T>, ArrayList<Edge<T>>> = HashMap()

    /** {@inheritDoc} */
    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacencies.count(), data)
        adjacencies[vertex] = ArrayList()
        return vertex
    }

    /** {@inheritDoc} */
    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        val edge = Edge(source, destination, weight)
        adjacencies[source]?.add(edge)
    }

    /** {@inheritDoc} */
    override fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    /** {@inheritDoc} */
    override fun add(edge: Edge<T>) {
        when (edge.type) {
            EdgeType.DIRECTED -> addDirectedEdge(edge.source, edge.destination, edge.weight)
            EdgeType.UNDIRECTED -> addUndirectedEdge(edge.source, edge.destination, edge.weight)
        }
    }

    /** {@inheritDoc} */
    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> {
        return adjacencies[source] ?: ArrayList()
    }

    /** {@inheritDoc} */
    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return edges(source).firstOrNull { it.destination == destination }?.weight
    }

    /**
     * Retorna representação textual do grafo no formato `vértice -> [destinos]`.
     *
     * @return string formatada com a lista de adjacência.
     */
    override fun toString(): String {
        return buildString {
            adjacencies.forEach { (vertex, edges) ->
                val edgeString = edges.joinToString { it.destination.data.toString() }
                append("${vertex.data} -> [$edgeString]\\n")
            }
        }
    }
}
