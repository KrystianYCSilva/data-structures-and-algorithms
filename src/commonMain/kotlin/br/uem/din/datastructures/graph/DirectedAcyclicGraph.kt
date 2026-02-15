package br.uem.din.datastructures.graph

/**
 * Grafo Direcionado Acíclico — DAG (Directed Acyclic Graph).
 *
 * Um grafo direcionado que garante a ausência de ciclos por meio de detecção
 * durante a adição de arestas. Internamente utiliza uma lista de adjacência
 * e implementa algoritmos fundamentais sobre DAGs: ordenação topológica e
 * caminho mais curto via relaxação em ordem topológica.
 *
 * A detecção de ciclos utiliza DFS com classificação de vértices em três estados
 * (branco, cinza, preto), conforme Cormen Cap. 22.3. A adição de uma aresta que
 * criaria um ciclo é rejeitada com uma exceção.
 *
 * Complexidades:
 * - [addVertex]: O(1)
 * - [addEdge]: O(|V| + |E|) (inclui verificação de ciclo via DFS)
 * - [topologicalSort]: O(|V| + |E|)
 * - [shortestPath]: O(|V| + |E|)
 * - Espaço: O(|V| + |E|)
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.4 — Topological sort;
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 24.2 — Single-source shortest
 *             paths in directed acyclic graphs.
 */
class DirectedAcyclicGraph<T> {

    private val adjacencies: HashMap<Vertex<T>, ArrayList<Edge<T>>> = HashMap()
    private val vertices: ArrayList<Vertex<T>> = ArrayList()

    /**
     * Cria e retorna um novo vértice contendo o dado especificado.
     *
     * Complexidade: O(1).
     *
     * @param data o dado a ser armazenado no vértice.
     * @return o [Vertex] criado.
     */
    fun addVertex(data: T): Vertex<T> {
        val vertex = Vertex(vertices.size, data)
        vertices.add(vertex)
        adjacencies[vertex] = ArrayList()
        return vertex
    }

    /**
     * Adiciona uma aresta direcionada de [source] para [destination] com peso opcional.
     *
     * Antes da inserção, verifica se a aresta criaria um ciclo no grafo. Se um ciclo
     * for detectado, a aresta não é adicionada e uma [IllegalArgumentException] é lançada.
     *
     * A detecção de ciclo é realizada por DFS a partir de [destination], verificando
     * se [source] é alcançável — o que indicaria que a nova aresta fecharia um ciclo.
     *
     * Complexidade: O(|V| + |E|) devido à verificação de ciclo.
     *
     * @param source o vértice de origem.
     * @param destination o vértice de destino.
     * @param weight o peso da aresta, ou `null` para arestas sem peso.
     * @throws IllegalArgumentException se a aresta criar um ciclo no grafo.
     * @throws IllegalArgumentException se o vértice não pertencer a este grafo.
     */
    fun addEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double? = null) {
        require(adjacencies.containsKey(source)) { "Vértice de origem não pertence ao grafo: $source" }
        require(adjacencies.containsKey(destination)) { "Vértice de destino não pertence ao grafo: $destination" }

        if (source == destination) {
            throw IllegalArgumentException("Self-loop detectado: aresta de $source para si mesmo criaria um ciclo.")
        }

        if (wouldCreateCycle(source, destination)) {
            throw IllegalArgumentException(
                "Aresta de ${source.data} para ${destination.data} criaria um ciclo no DAG."
            )
        }

        adjacencies[source]?.add(Edge(source, destination, weight))
    }

    /**
     * Retorna todas as arestas que partem do vértice especificado.
     *
     * @param source o vértice de origem.
     * @return lista de arestas adjacentes.
     */
    fun edges(source: Vertex<T>): List<Edge<T>> {
        return adjacencies[source] ?: emptyList()
    }

    /**
     * Retorna todos os vértices do grafo.
     *
     * @return lista de vértices.
     */
    fun vertices(): List<Vertex<T>> = vertices.toList()

    /**
     * Retorna uma ordenação topológica dos vértices do DAG.
     *
     * A ordenação topológica é uma ordenação linear dos vértices tal que, para toda
     * aresta (u, v), u aparece antes de v na ordenação. É calculada via DFS com
     * inserção em pilha ao término da exploração de cada vértice.
     *
     * Complexidade: O(|V| + |E|).
     *
     * @return lista de vértices em ordem topológica.
     *
     * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.4 — Topological sort.
     */
    fun topologicalSort(): List<Vertex<T>> {
        val visited = HashSet<Vertex<T>>()
        val stack = ArrayDeque<Vertex<T>>()

        for (vertex in vertices) {
            if (vertex !in visited) {
                topologicalDfs(vertex, visited, stack)
            }
        }

        return stack.toList()
    }

    /**
     * Calcula os caminhos mais curtos a partir de um vértice fonte usando relaxação
     * em ordem topológica.
     *
     * Este algoritmo é específico para DAGs e mais eficiente que Dijkstra para esta
     * classe de grafos, pois processa cada vértice e aresta exatamente uma vez na
     * ordem topológica. Arestas sem peso são tratadas como peso 1.0.
     *
     * Complexidade: O(|V| + |E|).
     *
     * @param source o vértice fonte.
     * @return mapa de cada vértice para a distância mínima a partir de [source].
     *         Vértices inalcançáveis possuem distância [Double.POSITIVE_INFINITY].
     *
     * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 24.2 —
     *             Single-source shortest paths in directed acyclic graphs.
     */
    fun shortestPath(source: Vertex<T>): Map<Vertex<T>, Double> {
        require(adjacencies.containsKey(source)) { "Vértice fonte não pertence ao grafo: $source" }

        val topOrder = topologicalSort()
        val dist = HashMap<Vertex<T>, Double>()

        for (v in vertices) {
            dist[v] = Double.POSITIVE_INFINITY
        }
        dist[source] = 0.0

        for (u in topOrder) {
            if (dist[u]!! < Double.POSITIVE_INFINITY) {
                for (edge in edges(u)) {
                    val w = edge.weight ?: 1.0
                    val newDist = dist[u]!! + w
                    if (newDist < dist[edge.destination]!!) {
                        dist[edge.destination] = newDist
                    }
                }
            }
        }

        return dist
    }

    /**
     * Verifica se adicionar uma aresta de [source] para [destination] criaria um ciclo.
     *
     * Realiza uma DFS a partir de [destination] para verificar se [source] é alcançável.
     * Se for, a nova aresta (source -> destination) fecharia um ciclo.
     *
     * @param source o vértice de origem da aresta proposta.
     * @param destination o vértice de destino da aresta proposta.
     * @return `true` se a aresta criaria um ciclo, `false` caso contrário.
     */
    private fun wouldCreateCycle(source: Vertex<T>, destination: Vertex<T>): Boolean {
        val visited = HashSet<Vertex<T>>()
        return isReachable(destination, source, visited)
    }

    /**
     * Verifica se [target] é alcançável a partir de [current] via DFS.
     *
     * @param current o vértice de partida da busca.
     * @param target o vértice alvo.
     * @param visited conjunto de vértices já visitados.
     * @return `true` se [target] for alcançável a partir de [current].
     */
    private fun isReachable(current: Vertex<T>, target: Vertex<T>, visited: HashSet<Vertex<T>>): Boolean {
        if (current == target) return true
        visited.add(current)
        for (edge in edges(current)) {
            if (edge.destination !in visited) {
                if (isReachable(edge.destination, target, visited)) return true
            }
        }
        return false
    }

    /**
     * DFS auxiliar para a ordenação topológica. Insere o vértice na pilha (frente do deque)
     * após a exploração completa de todos os seus adjacentes.
     *
     * @param vertex o vértice sendo explorado.
     * @param visited conjunto de vértices já visitados.
     * @param stack pilha de resultado da ordenação topológica.
     */
    private fun topologicalDfs(vertex: Vertex<T>, visited: HashSet<Vertex<T>>, stack: ArrayDeque<Vertex<T>>) {
        visited.add(vertex)
        for (edge in edges(vertex)) {
            if (edge.destination !in visited) {
                topologicalDfs(edge.destination, visited, stack)
            }
        }
        stack.addFirst(vertex)
    }

    /**
     * Retorna representação textual do DAG no formato `vértice -> [destinos]`.
     *
     * @return string formatada com a lista de adjacência.
     */
    override fun toString(): String {
        return buildString {
            adjacencies.forEach { (vertex, edges) ->
                val edgeString = edges.joinToString { "${it.destination.data}(${it.weight ?: "1.0"})" }
                append("${vertex.data} -> [$edgeString]\n")
            }
        }
    }
}
