package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.AdjacencyList
import kotlin.test.*

class AStarTest {

    @Test
    fun testShortestPathSimple() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 2.0)
        graph.addDirectedEdge(a, c, 5.0)
        val astar = AStar(graph)
        val costs = astar.shortestPath(a, c)
        assertEquals(0.0, costs[a])
        assertEquals(1.0, costs[b])
        assertEquals(3.0, costs[c])
    }

    @Test
    fun testShortestPathDirectRoute() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 10.0)
        val astar = AStar(graph)
        val costs = astar.shortestPath(a, b)
        assertEquals(0.0, costs[a])
        assertEquals(10.0, costs[b])
    }

    @Test
    fun testShortestPathSameVertex() {
        val graph = AdjacencyList<Int>()
        val v = graph.createVertex(1)
        val astar = AStar(graph)
        val costs = astar.shortestPath(v, v)
        assertEquals(0.0, costs[v])
    }

    @Test
    fun testShortestPathMultipleHops() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        graph.addDirectedEdge(c, d, 1.0)
        graph.addDirectedEdge(a, d, 10.0)
        val astar = AStar(graph)
        val costs = astar.shortestPath(a, d)
        assertEquals(0.0, costs[a])
        assertEquals(3.0, costs[d])
    }
}
