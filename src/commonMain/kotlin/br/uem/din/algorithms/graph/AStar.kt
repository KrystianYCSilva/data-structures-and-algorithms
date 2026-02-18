package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.queue.priorityQueueOf
import kotlin.math.abs

/**
 * Algoritmo A* (A-Star) para busca de caminho mínimo com heurística.
 *
 * Extensão do algoritmo de Dijkstra que utiliza uma função heurística para guiar
 * a busca em direção ao destino, priorizando vértices cuja soma `g(v) + h(v)` é menor,
 * onde `g(v)` é o custo real do caminho até `v` e `h(v)` é a estimativa heurística
 * até o destino.
 *
 * Esta implementação utiliza uma heurística baseada na diferença absoluta dos índices
 * dos vértices. Para garantir otimalidade, a heurística deve ser **admissível**
 * (nunca superestima o custo real).
 *
 * Complexidade: O((|V| + |E|) log |V|) no pior caso (similar a Dijkstra),
 * mas geralmente mais rápido na prática graças à heurística.
 *
 * @param T o tipo do dado armazenado nos vértices.
 * @param graph o grafo sobre o qual o algoritmo será executado.
 *
 * Referência: Hart, P. E., Nilsson, N. J., & Raphael, B. "A Formal Basis for the Heuristic
 *             Determination of Minimum Cost Paths" (1968);
 *             Russell, S. & Norvig, P. "Artificial Intelligence: A Modern Approach", Cap. 3.5.
 */
public class AStar<T>(private val graph: Graph<T>) {

    private data class QueueEntry<T>(
        val vertex: Vertex<T>,
        val priority: Double
    )

    /**
     * Calcula os caminhos mínimos de [start] até [destination] utilizando A*.
     *
     * Retorna um mapa de cada vértice explorado para o custo mínimo do caminho
     * desde [start].
     *
     * Complexidade: O((|V| + |E|) log |V|) no pior caso.
     *
     * @param start o vértice de origem.
     * @param destination o vértice de destino.
     * @return mapa de vértices para seus custos mínimos.
     */
    public fun shortestPath(start: Vertex<T>, destination: Vertex<T>): Map<Vertex<T>, Double?> {
        val costs = mutableMapOf<Vertex<T>, Double>()
        val priorityQueue = priorityQueueOf<QueueEntry<T>>(compareBy { it.priority })

        costs[start] = 0.0
        priorityQueue.enqueue(QueueEntry(start, heuristic(start, destination)))

        while (true) {
            val entry = priorityQueue.dequeue() ?: break
            val vertex = entry.vertex
            val currentCost = costs[vertex] ?: continue
            val expectedPriority = currentCost + heuristic(vertex, destination)

            if (entry.priority > expectedPriority) continue

            graph.edges(vertex).forEach { edge ->
                val weight = edge.weight ?: return@forEach
                val newCost = currentCost + weight
                val oldCost = costs[edge.destination]

                if (oldCost == null || newCost < oldCost) {
                    costs[edge.destination] = newCost
                    priorityQueue.enqueue(
                        QueueEntry(edge.destination, newCost + heuristic(edge.destination, destination))
                    )
                }
            }
        }

        return costs
    }

    /**
     * Função heurística que estima o custo restante de [source] até [destination].
     *
     * Utiliza a diferença absoluta dos índices dos vértices como estimativa.
     * Para grafos com coordenadas espaciais, deve ser substituída por uma heurística
     * mais precisa (e.g., distância euclidiana ou de Manhattan).
     *
     * @param source o vértice atual.
     * @param destination o vértice de destino.
     * @return a estimativa de custo restante.
     */
    private fun heuristic(source: Vertex<T>, destination: Vertex<T>): Double {
        return abs(source.index.toDouble() - destination.index.toDouble())
    }
}
