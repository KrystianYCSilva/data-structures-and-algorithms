package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.queue.MutableQueue
import br.uem.din.datastructures.queue.ArrayQueue

/**
 * Busca em largura (Breadth-First Search — BFS).
 *
 * Algoritmo de travessia de grafos que explora todos os vizinhos de um vértice
 * antes de avançar para os vizinhos dos vizinhos, utilizando uma fila (FIFO).
 * Produz uma ordenação por níveis de distância a partir do vértice de origem.
 *
 * Complexidade: O(|V| + |E|), onde |V| é o número de vértices e |E| o número de arestas.
 * Espaço: O(|V|) para a fila e o conjunto de visitados.
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.2 — Breadth-First Search.
 */
class BreadthFirstSearch<T> {

    /**
     * Executa a busca em largura a partir do vértice [source].
     *
     * Retorna a lista de vértices na ordem em que foram visitados.
     *
     * Complexidade: O(|V| + |E|).
     *
     * @param graph o grafo a ser percorrido.
     * @param source o vértice de origem da busca.
     * @return lista de vértices na ordem de visitação (BFS order).
     */
    fun search(graph: Graph<T>, source: Vertex<T>): ArrayList<Vertex<T>> {
        val queue: MutableQueue<Vertex<T>> = ArrayQueue()
        val enqueued = mutableSetOf<Vertex<T>>()
        val visited = ArrayList<Vertex<T>>()

        queue.enqueue(source)
        enqueued.add(source)

        while (true) {
            val vertex = queue.dequeue() ?: break
            visited.add(vertex)
            val neighbors = graph.edges(vertex)
            neighbors.forEach { edge ->
                if (!enqueued.contains(edge.destination)) {
                    queue.enqueue(edge.destination)
                    enqueued.add(edge.destination)
                }
            }
        }
        return visited
    }
}
