package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.graph.Edge
import br.uem.din.datastructures.queue.priorityQueueOf

/**
 * Algoritmo de Prim para Árvore Geradora Mínima (MST - Minimum Spanning Tree).
 *
 * Constrói a MST partindo de um vértice arbitrário e expandindo para o vizinho mais próximo.
 * Utiliza uma Fila de Prioridade para selecionar a aresta de menor peso que conecta
 * um vértice da MST a um vértice fora dela.
 *
 * Complexidade Temporal: O(E log V) usando Priority Queue binária.
 * Complexidade Espacial: O(V + E)
 *
 * @param graph o grafo a ser analisado.
 * @param startVertex o vértice inicial.
 * @return Lista de arestas que compõem a MST (conectadas a partir de startVertex).
 */
public fun <T> prim(graph: Graph<T>, startVertex: Vertex<T>): List<Edge<T>> {
    val mst = mutableListOf<Edge<T>>()
    val visited = mutableSetOf<Vertex<T>>()
    
    // Priority Queue armazena arestas, ordenadas pelo peso
    // Usamos Comparator para ordenar Edge por peso
    val pq = priorityQueueOf<Edge<T>>(Comparator { e1, e2 ->
        val w1 = e1.weight ?: Double.POSITIVE_INFINITY
        val w2 = e2.weight ?: Double.POSITIVE_INFINITY
        w1.compareTo(w2)
    })

    // Começa com o vértice inicial
    visited.add(startVertex)
    for (edge in graph.edges(startVertex)) {
        pq.enqueue(edge)
    }

    while (!pq.isEmpty()) {
        val edge = pq.dequeue() ?: break
        val v = edge.destination

        // Se o destino já foi visitado, ignora (aresta interna ou ciclo)
        if (v in visited) continue

        // Adiciona à MST
        mst.add(edge)
        visited.add(v)

        // Adiciona arestas do novo vértice à PQ
        for (nextEdge in graph.edges(v)) {
            if (nextEdge.destination !in visited) {
                pq.enqueue(nextEdge)
            }
        }
    }

    return mst
}

