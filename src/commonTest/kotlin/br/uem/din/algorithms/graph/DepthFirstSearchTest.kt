package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.AdjacencyList
import kotlin.test.*

class DepthFirstSearchTest {

    @Test
    fun testDfsLinearGraph() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        val dfs = DepthFirstSearch<String>()
        val result = dfs.search(graph, a)
        assertEquals(3, result.size)
        assertEquals("A", result[0].data)
        assertEquals("B", result[1].data)
        assertEquals("C", result[2].data)
    }

    @Test
    fun testDfsBranchingGraph() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(a, c, 1.0)
        graph.addDirectedEdge(b, d, 1.0)
        val dfs = DepthFirstSearch<String>()
        val result = dfs.search(graph, a)
        assertEquals(4, result.size)
        assertEquals("A", result[0].data)
    }

    @Test
    fun testDfsSingleVertex() {
        val graph = AdjacencyList<Int>()
        val v = graph.createVertex(1)
        val dfs = DepthFirstSearch<Int>()
        val result = dfs.search(graph, v)
        assertEquals(1, result.size)
        assertEquals(1, result[0].data)
    }

    @Test
    fun testDfsDisconnectedVertex() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        graph.createVertex("B")
        val dfs = DepthFirstSearch<String>()
        val result = dfs.search(graph, a)
        assertEquals(1, result.size)
    }

    @Test
    fun testDfsCycle() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        graph.addDirectedEdge(c, a, 1.0)
        val dfs = DepthFirstSearch<String>()
        val result = dfs.search(graph, a)
        assertEquals(3, result.size)
    }
}
