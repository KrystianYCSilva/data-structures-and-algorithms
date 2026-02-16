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
    private val hasEdge = arrayListOf<ArrayList<Boolean>>()

    /** {@inheritDoc} */
    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(vertices.count(), data)
        vertices.add(vertex)
        weights.forEach { it.add(null) }
        hasEdge.forEach { it.add(false) }
        val row = ArrayList<Double?>(vertices.count())
        val edgeRow = ArrayList<Boolean>(vertices.count())
        repeat(vertices.count()) {
            row.add(null)
            edgeRow.add(false)
        }
        weights.add(row)
        hasEdge.add(edgeRow)
        return vertex
    }

    /** {@inheritDoc} */
    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        weights[source.index][destination.index] = weight
        hasEdge[source.index][destination.index] = true
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
        val edges = arrayListOf<Edge<T>>()
        (0 until weights.size).forEach { column ->
            if (hasEdge[source.index][column]) {
                edges.add(Edge(source, vertices[column], weights[source.index][column]))
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
