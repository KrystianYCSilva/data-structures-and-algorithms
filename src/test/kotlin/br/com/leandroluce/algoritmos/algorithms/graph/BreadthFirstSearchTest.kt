package br.com.leandroluce.algoritmos.algorithms.graph

import br.com.leandroluce.algoritmos.datastructures.graph.AdjacencyList
import kotlin.test.Test
import kotlin.test.assertEquals

class BreadthFirstSearchTest {

    @Test
    fun `bfs should return vertices in breadth-first order`() {
        val graph = AdjacencyList<String>()

        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        val e = graph.createVertex("E")
        val f = graph.createVertex("F")

        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(a, c, 1.0)
        graph.addDirectedEdge(b, d, 1.0)
        graph.addDirectedEdge(c, e, 1.0)
        graph.addDirectedEdge(d, f, 1.0)

        val bfs = BreadthFirstSearch<String>()
        val visited = bfs.search(graph, a)

        val expected = listOf(a, b, c, d, e, f)
        assertEquals(expected, visited)
    }
}
