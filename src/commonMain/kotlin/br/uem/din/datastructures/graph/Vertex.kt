package br.uem.din.datastructures.graph

/**
 * Vértice de um grafo (Graph Vertex).
 *
 * Representa um nó no grafo, identificado por um [index] único (atribuído automaticamente
 * durante a criação via [Graph.createVertex]) e contendo um dado genérico [data].
 *
 * @param T o tipo do dado armazenado no vértice.
 * @property index índice único do vértice no grafo (usado como identificador interno).
 * @property data o dado associado ao vértice.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 22 — Elementary Graph Algorithms.
 */
data class Vertex<T>(val index: Int, val data: T)
