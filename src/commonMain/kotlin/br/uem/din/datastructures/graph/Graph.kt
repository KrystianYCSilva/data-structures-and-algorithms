package br.uem.din.datastructures.graph

/**
 * Interface para representação de grafos (Graph Interface).
 *
 * Define as operações fundamentais de um grafo: criação de vértices, adição de arestas
 * (direcionadas e não-direcionadas), consulta de arestas adjacentes e pesos.
 *
 * Implementações concretas incluem [AdjacencyList] e [AdjacencyMatrix].
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.1 — Representations of graphs.
 */
interface Graph<T> {
    /**
     * Cria e retorna um novo vértice contendo o dado especificado.
     *
     * @param data o dado a ser armazenado no vértice.
     * @return o [Vertex] criado.
     */
    fun createVertex(data: T): Vertex<T>

    /**
     * Adiciona uma aresta direcionada de [source] para [destination] com peso opcional.
     *
     * @param source o vértice de origem.
     * @param destination o vértice de destino.
     * @param weight o peso da aresta, ou `null`.
     */
    fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?)

    /**
     * Adiciona uma aresta não-direcionada entre [source] e [destination] com peso opcional.
     *
     * Equivale a adicionar duas arestas direcionadas opostas.
     *
     * @param source um dos vértices.
     * @param destination o outro vértice.
     * @param weight o peso da aresta, ou `null`.
     */
    fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?)

    /**
     * Adiciona uma aresta ao grafo com base no objeto [Edge].
     *
     * O comportamento (direcionada ou não-direcionada) é determinado por [Edge.type].
     *
     * @param edge a aresta a ser adicionada.
     */
    fun add(edge: Edge<T>)

    /**
     * Retorna todas as arestas que partem do vértice especificado.
     *
     * @param source o vértice de origem.
     * @return lista de arestas adjacentes.
     */
    fun edges(source: Vertex<T>): ArrayList<Edge<T>>

    /**
     * Retorna o peso da aresta entre [source] e [destination], se existir.
     *
     * @param source o vértice de origem.
     * @param destination o vértice de destino.
     * @return o peso da aresta, ou `null` se a aresta não existir.
     */
    fun weight(source: Vertex<T>, destination: Vertex<T>): Double?
}
