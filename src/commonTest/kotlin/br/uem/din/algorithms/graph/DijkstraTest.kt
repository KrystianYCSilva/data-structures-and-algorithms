package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.AdjacencyList
import kotlin.test.*

class DijkstraTest {

    @Test
    fun testShortestPathSimple() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 2.0)
        graph.addDirectedEdge(a, c, 5.0)
        val dijkstra = Dijkstra(graph)
        val costs = dijkstra.shortestPath(a)
        assertEquals(0.0, costs[a])
        assertEquals(1.0, costs[b])
        assertEquals(3.0, costs[c])
    }

    @Test
    fun testShortestPathSingleVertex() {
        val graph = AdjacencyList<Int>()
        val v = graph.createVertex(1)
        val dijkstra = Dijkstra(graph)
        val costs = dijkstra.shortestPath(v)
        assertEquals(0.0, costs[v])
        assertEquals(1, costs.size)
    }

    @Test
    fun testShortestPathDisconnected() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        graph.createVertex("B")
        val dijkstra = Dijkstra(graph)
        val costs = dijkstra.shortestPath(a)
        assertEquals(0.0, costs[a])
        assertEquals(1, costs.size)
    }

    @Test
    fun testShortestPathUndirected() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addUndirectedEdge(a, b, 2.0)
        graph.addUndirectedEdge(b, c, 3.0)
        val dijkstra = Dijkstra(graph)
        val costs = dijkstra.shortestPath(a)
        assertEquals(0.0, costs[a])
        assertEquals(2.0, costs[b])
        assertEquals(5.0, costs[c])
    }
}
