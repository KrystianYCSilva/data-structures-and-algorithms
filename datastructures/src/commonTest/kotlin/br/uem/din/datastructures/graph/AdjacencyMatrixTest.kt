package br.uem.din.datastructures.graph

import kotlin.test.*

class AdjacencyMatrixTest {

    @Test
    fun testCreateVertex() {
        val graph = AdjacencyMatrix<String>()
        val v0 = graph.createVertex("A")
        val v1 = graph.createVertex("B")
        assertEquals(0, v0.index)
        assertEquals(1, v1.index)
    }

    @Test
    fun testAddDirectedEdge() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 3.0)

        val edgesA = graph.edges(a)
        assertEquals(1, edgesA.size)
        assertEquals(b, edgesA[0].destination)
        assertEquals(3.0, edgesA[0].weight)
        assertEquals(0, graph.edges(b).size)
    }

    @Test
    fun testAddDirectedEdgeNullWeight() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, null)

        val edgesA = graph.edges(a)
        assertEquals(1, edgesA.size)
        assertNull(edgesA[0].weight)
        assertEquals(0, graph.edges(b).size)
    }

    @Test
    fun testAddUndirectedEdge() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addUndirectedEdge(a, b, 5.0)

        assertEquals(1, graph.edges(a).size)
        assertEquals(1, graph.edges(b).size)
    }

    @Test
    fun testAddEdgeWithEdgeTypeDirected() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.add(Edge(a, b, 2.0, EdgeType.DIRECTED))

        assertEquals(1, graph.edges(a).size)
        assertEquals(0, graph.edges(b).size)
    }

    @Test
    fun testAddEdgeWithEdgeTypeUndirected() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.add(Edge(a, b, 2.0, EdgeType.UNDIRECTED))

        assertEquals(1, graph.edges(a).size)
        assertEquals(1, graph.edges(b).size)
    }

    @Test
    fun testAddEdgeUndirectedNullWeight() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.add(Edge(a, b, null, EdgeType.UNDIRECTED))

        assertEquals(1, graph.edges(a).size)
        assertEquals(1, graph.edges(b).size)
        assertNull(graph.edges(a)[0].weight)
    }

    @Test
    fun testWeight() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 7.5)

        assertEquals(7.5, graph.weight(a, b))
        assertNull(graph.weight(a, c))
    }

    @Test
    fun testEdgesNullWeightVisibility() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, null)
        graph.addDirectedEdge(a, c, 1.0)

        val edges = graph.edges(a)
        assertEquals(2, edges.size)
    }

    @Test
    fun testMultipleVerticesAndEdges() {
        val graph = AdjacencyMatrix<Int>()
        val v0 = graph.createVertex(0)
        val v1 = graph.createVertex(1)
        val v2 = graph.createVertex(2)
        graph.addDirectedEdge(v0, v1, 1.0)
        graph.addDirectedEdge(v0, v2, 2.0)
        graph.addDirectedEdge(v1, v2, 3.0)

        assertEquals(2, graph.edges(v0).size)
        assertEquals(1, graph.edges(v1).size)
        assertEquals(0, graph.edges(v2).size)
    }

    @Test
    fun testToString() {
        val graph = AdjacencyMatrix<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 1.0)

        val str = graph.toString()
        assertTrue(str.contains("A"))
        assertTrue(str.contains("B"))
    }
}
