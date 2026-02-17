package br.uem.din.datastructures.graph

/**
 * Interface somente-leitura para representação de grafos (Graph Interface).
 *
 * Define as operações de consulta de um grafo: arestas adjacentes e pesos.
 * Segue o padrão Kotlin stdlib de separar interfaces imutáveis e mutáveis
 * (ex.: [List]/[MutableList]).
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.1 — Representations of graphs.
 *
 * @see MutableGraph
 */
public interface Graph<T> {
    /**
     * Retorna todas as arestas que partem do vértice especificado.
     *
     * @param source o vértice de origem.
     * @return lista de arestas adjacentes.
     */
    public fun edges(source: Vertex<T>): ArrayList<Edge<T>>

    /**
     * Retorna o peso da aresta entre [source] e [destination], se existir.
     *
     * @param source o vértice de origem.
     * @param destination o vértice de destino.
     * @return o peso da aresta, ou `null` se a aresta não existir.
     */
    public fun weight(source: Vertex<T>, destination: Vertex<T>): Double?
}

/**
 * Interface mutável para representação de grafos (Mutable Graph Interface).
 *
 * Estende [Graph] adicionando operações de modificação: criação de vértices e
 * adição de arestas (direcionadas e não-direcionadas).
 *
 * Implementações concretas incluem [AdjacencyList] e [AdjacencyMatrix].
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.1 — Representations of graphs.
 *
 * @see Graph
 */
public interface MutableGraph<T> : Graph<T> {
    /**
     * Cria e retorna um novo vértice contendo o dado especificado.
     *
     * @param data o dado a ser armazenado no vértice.
     * @return o [Vertex] criado.
     */
    public fun createVertex(data: T): Vertex<T>

    /**
     * Adiciona uma aresta direcionada de [source] para [destination] com peso opcional.
     *
     * @param source o vértice de origem.
     * @param destination o vértice de destino.
     * @param weight o peso da aresta, ou `null`.
     */
    public fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?)

    /**
     * Adiciona uma aresta não-direcionada entre [source] e [destination] com peso opcional.
     *
     * Equivale a adicionar duas arestas direcionadas opostas.
     *
     * @param source um dos vértices.
     * @param destination o outro vértice.
     * @param weight o peso da aresta, ou `null`.
     */
    public fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?)

    /**
     * Adiciona uma aresta ao grafo com base no objeto [Edge].
     *
     * O comportamento (direcionada ou não-direcionada) é determinado por [Edge.type].
     *
     * @param edge a aresta a ser adicionada.
     */
    public fun add(edge: Edge<T>)
}
