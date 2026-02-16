package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.queue.PriorityQueue

/**
 * Tipo de visita durante a travessia de um grafo.
 *
 * @property START indica o vértice inicial da travessia.
 * @property EDGE indica que o vértice foi alcançado por uma aresta.
 */
enum class VisitType {
    START,
    EDGE
}

/**
 * Algoritmo de Dijkstra para caminhos mínimos de fonte única (Single-Source Shortest Paths).
 *
 * Calcula a menor distância do vértice de origem para todos os vértices alcançáveis
 * em um grafo com pesos **não-negativos**. Utiliza uma [PriorityQueue] (min-heap)
 * para selecionar o próximo vértice de menor custo a cada iteração.
 *
 * Complexidade: O((|V| + |E|) log |V|) com heap binário.
 *
 * **Restrição:** todos os pesos das arestas devem ser ≥ 0. Para grafos com pesos
 * negativos, utilize o algoritmo de Bellman-Ford.
 *
 * @param T o tipo do dado armazenado nos vértices.
 * @param graph o grafo sobre o qual o algoritmo será executado.
 *
 * Referência: Dijkstra, E. W. "A Note on Two Problems in Connexion with Graphs" (1959);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 24.3 — Dijkstra's Algorithm.
 */
class Dijkstra<T>(private val graph: Graph<T>) {

    private val costs = mutableMapOf<Vertex<T>, Double?>()
    private val visited = mutableSetOf<Vertex<T>>()

    /**
     * Calcula os caminhos mínimos a partir do vértice de origem [start].
     *
     * Retorna um mapa de cada vértice alcançável para o custo mínimo do caminho
     * desde [start]. Vértices não alcançáveis não estarão no mapa.
     *
     * Complexidade: O((|V| + |E|) log |V|).
     *
     * @param start o vértice de origem.
     * @return mapa de vértices para seus custos mínimos.
     */
    fun shortestPath(start: Vertex<T>): Map<Vertex<T>, Double?> {
        val priorityQueue = PriorityQueue(compareBy<Vertex<T>> { costs[it] })
        priorityQueue.enqueue(start)
        costs[start] = 0.0

        while (true) {
            val vertex = priorityQueue.dequeue() ?: break
            if (visited.contains(vertex)) continue
            visited.add(vertex)

            val edges = graph.edges(vertex)
            edges.forEach { edge ->
                val weight = edge.weight ?: return@forEach
                if (costs[edge.destination] == null || costs[vertex]!! + weight < costs[edge.destination]!!) {
                    costs[edge.destination] = costs[vertex]!! + weight
                    priorityQueue.enqueue(edge.destination)
                }
            }
        }
        return costs
    }
}
