package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.graph.Edge
import br.uem.din.datastructures.unionfind.UnionFind

/**
 * Algoritmo de Kruskal para Árvore Geradora Mínima (MST - Minimum Spanning Tree).
 *
 * Encontra um subconjunto de arestas que conecta todos os vértices do grafo
 * com o menor peso total possível. Funciona ordenando as arestas por peso
 * e adicionando-as à MST se não formarem ciclo (usando Union-Find).
 *
 * Complexidade Temporal: O(E log E) ou O(E log V)
 * Complexidade Espacial: O(V + E)
 *
 * @param graph o grafo a ser analisado.
 * @param vertices lista de vértices do grafo (necessária para inicializar UnionFind).
 * @return Lista de arestas que compõem a MST.
 */
public fun <T> kruskal(graph: Graph<T>, vertices: List<Vertex<T>>): List<Edge<T>> {
    val mst = mutableListOf<Edge<T>>()
    val edges = mutableListOf<Edge<T>>()
    
    // Coletar todas as arestas
    // Como o grafo pode ser direcionado ou não, para MST geralmente consideramos não-direcionado.
    // Se graph for direcionado, Kruskal trata como não-direcionado (ignorando direção ou considerando bidirecional).
    // Mas a implementação de Graph armazena arestas direcionadas.
    // Se graph.addUndirectedEdge foi usado, haverá (u,v) e (v,u).
    // Kruskal deve processar cada aresta única {u,v}.
    // Vamos usar um Set para evitar duplicatas se o grafo for não-direcionado representado como direcionado duplo.
    
    val processedEdges = mutableSetOf<Set<Vertex<T>>>()

    for (v in vertices) {
        for (edge in graph.edges(v)) {
            val edgePair = setOf(edge.source, edge.destination)
            if (processedEdges.add(edgePair)) {
                edges.add(edge)
            }
        }
    }

    // Ordenar arestas por peso crescente
    edges.sortBy { it.weight ?: Double.POSITIVE_INFINITY }

    // Inicializar Union-Find
    // UnionFind requer tamanho inteiro e índices 0..size-1.
    // Vertex tem propriedade `index`. Vamos assumir que índices são contíguos 0..V-1 ou usar o maior índice.
    val maxIndex = vertices.maxOfOrNull { it.index } ?: 0
    val uf = UnionFind(maxIndex + 1)

    for (edge in edges) {
        val u = edge.source.index
        val v = edge.destination.index

        if (uf.find(u) != uf.find(v)) {
            uf.union(u, v)
            mst.add(edge)
        }
    }

    return mst
}
