package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.AdjacencyList
import kotlin.test.*

class BreadthFirstSearchTest {

    @Test
    fun testBfsLinearGraph() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        val bfs = BreadthFirstSearch<String>()
        val result = bfs.search(graph, a)
        assertEquals(3, result.size)
        assertEquals("A", result[0].data)
        assertEquals("B", result[1].data)
        assertEquals("C", result[2].data)
    }

    @Test
    fun testBfsBranchingGraph() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(a, c, 1.0)
        graph.addDirectedEdge(b, d, 1.0)
        graph.addDirectedEdge(c, d, 1.0)
        val bfs = BreadthFirstSearch<String>()
        val result = bfs.search(graph, a)
        assertEquals(4, result.size)
        assertEquals("A", result[0].data)
        assertTrue(result[1].data == "B" || result[1].data == "C")
    }

    @Test
    fun testBfsSingleVertex() {
        val graph = AdjacencyList<Int>()
        val v = graph.createVertex(42)
        val bfs = BreadthFirstSearch<Int>()
        val result = bfs.search(graph, v)
        assertEquals(1, result.size)
        assertEquals(42, result[0].data)
    }

    @Test
    fun testBfsDisconnectedVertex() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        graph.createVertex("B")
        val bfs = BreadthFirstSearch<String>()
        val result = bfs.search(graph, a)
        assertEquals(1, result.size)
    }

    @Test
    fun testBfsCycle() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        graph.addDirectedEdge(c, a, 1.0)
        val bfs = BreadthFirstSearch<String>()
        val result = bfs.search(graph, a)
        assertEquals(3, result.size)
    }
}
