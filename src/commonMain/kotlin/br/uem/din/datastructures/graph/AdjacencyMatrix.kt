package br.uem.din.datastructures.graph

/**
 * Grafo representado por matriz de adjacência (Adjacency Matrix).
 *
 * Utiliza uma matriz bidimensional de pesos onde `weights[i][j]` contém o peso
 * da aresta do vértice `i` para o vértice `j`, ou `null` se a aresta não existir.
 * Essa representação é eficiente para grafos densos (|E| ≈ |V|²).
 *
 * Complexidades:
 * - [createVertex]: O(|V|) — requer expansão de todas as linhas existentes
 * - [addDirectedEdge]: O(1)
 * - [edges]: O(|V|) — requer varredura de toda a linha
 * - [weight]: O(1) — acesso direto por índice
 * - Espaço: O(|V|²)
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.1 — Adjacency-matrix representation.
 */
class AdjacencyMatrix<T> : Graph<T> {

    private val vertices = arrayListOf<Vertex<T>>()
    private val weights = arrayListOf<ArrayList<Double?>>()

    /** {@inheritDoc} */
    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(vertices.count(), data)
        vertices.add(vertex)
        weights.forEach {
            it.add(null)
        }
        val row = ArrayList<Double?>(vertices.count())
        repeat(vertices.count()) {
            row.add(null)
        }
        weights.add(row)
        return vertex
    }

    /** {@inheritDoc} */
    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        weights[source.index][destination.index] = weight
    }

    /** {@inheritDoc} */
    override fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    /** {@inheritDoc} */
    override fun add(edge: Edge<T>) {
        when (edge.weight) {
            null -> addUndirectedEdge(edge.source, edge.destination, null)
            else -> addDirectedEdge(edge.source, edge.destination, edge.weight)
        }
    }

    /** {@inheritDoc} */
    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> {
        val edges = arrayListOf<Edge<T>>()
        (0 until weights.size).forEach { column ->
            val weight = weights[source.index][column]
            if (weight != null) {
                edges.add(Edge(source, vertices[column], weight))
            }
        }
        return edges
    }

    /** {@inheritDoc} */
    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return weights[source.index][destination.index]
    }

    /**
     * Retorna representação textual do grafo no formato `vértice -> [destinos]`.
     *
     * @return string formatada com as adjacências de cada vértice.
     */
    override fun toString(): String {
        return buildString {
            vertices.forEach { vertex ->
                val edgeString = edges(vertex).joinToString { it.destination.data.toString() }
                append("${vertex.data} -> [$edgeString]\\n")
            }
        }
    }
}
