package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.stack.MutableStack
import br.uem.din.datastructures.stack.arrayStackOf

/**
 * Busca em profundidade (Depth-First Search — DFS).
 *
 * Algoritmo de travessia de grafos que explora tão profundamente quanto possível
 * ao longo de cada ramo antes de retroceder (backtracking), utilizando uma pilha (LIFO).
 *
 * Esta implementação é iterativa (não recursiva), utilizando uma pilha explícita.
 *
 * Complexidade: O(|V| + |E|), onde |V| é o número de vértices e |E| o número de arestas.
 * Espaço: O(|V|) para a pilha e o conjunto de visitados.
 *
 * @param T o tipo do dado armazenado nos vértices.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22.3 — Depth-First Search.
 */
public class DepthFirstSearch<T> {

    /**
     * Executa a busca em profundidade a partir do vértice [source].
     *
     * Retorna a lista de vértices na ordem em que foram visitados.
     *
     * Complexidade: O(|V| + |E|).
     *
     * @param graph o grafo a ser percorrido.
     * @param source o vértice de origem da busca.
     * @return lista de vértices na ordem de visitação (DFS order).
     */
    public fun search(graph: Graph<T>, source: Vertex<T>): ArrayList<Vertex<T>> {
        val stack: MutableStack<Vertex<T>> = arrayStackOf()
        val pushed = mutableSetOf<Vertex<T>>()
        val visited = ArrayList<Vertex<T>>()

        stack.push(source)
        pushed.add(source)
        visited.add(source)

        while (!stack.isEmpty()) {
            val vertex = stack.peek() ?: break
            var foundUnvisited = false
            val neighbors = graph.edges(vertex)
            for (edge in neighbors) {
                if (!pushed.contains(edge.destination)) {
                    stack.push(edge.destination)
                    pushed.add(edge.destination)
                    visited.add(edge.destination)
                    foundUnvisited = true
                    break
                }
            }
            if (!foundUnvisited) {
                stack.pop()
            }
        }
        return visited
    }
}
