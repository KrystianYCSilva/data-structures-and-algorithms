package br.com.leandroluce.algoritmos.algorithms.graph

import br.com.leandroluce.algoritmos.datastructures.graph.AdjacencyList
import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {

    @Test
    fun `shortestPath should return the shortest path from a source vertex`() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        val e = graph.createVertex("E")

        graph.addDirectedEdge(a, b, 2.0)
        graph.addDirectedEdge(a, c, 5.0)
        graph.addDirectedEdge(b, d, 4.0)
        graph.addDirectedEdge(b, e, 1.0)
        graph.addDirectedEdge(c, e, 3.0)
        graph.addDirectedEdge(d, e, 1.0)

        val dijkstra = Dijkstra(graph)
        val costs = dijkstra.shortestPath(a)

        assertEquals(0.0, costs[a])
        assertEquals(2.0, costs[b])
        assertEquals(5.0, costs[c])
        assertEquals(6.0, costs[d])
        assertEquals(3.0, costs[e])
    }
}
