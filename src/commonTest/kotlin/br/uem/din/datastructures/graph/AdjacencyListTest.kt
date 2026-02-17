package br.uem.din.datastructures.graph

import br.uem.din.datastructures.asReadOnly
import kotlin.test.*

class AdjacencyListTest {

    @Test
    fun testCreateVertex() {
        val graph = AdjacencyList<String>()
        val v0 = graph.createVertex("A")
        val v1 = graph.createVertex("B")
        assertEquals(0, v0.index)
        assertEquals(1, v1.index)
        assertEquals("A", v0.data)
        assertEquals("B", v1.data)
    }

    @Test
    fun testAddDirectedEdge() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 3.0)

        val edgesA = graph.edges(a)
        val edgesB = graph.edges(b)
        assertEquals(1, edgesA.size)
        assertEquals(b, edgesA[0].destination)
        assertEquals(3.0, edgesA[0].weight)
        assertEquals(0, edgesB.size)
    }

    @Test
    fun testAddUndirectedEdge() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addUndirectedEdge(a, b, 5.0)

        assertEquals(1, graph.edges(a).size)
        assertEquals(1, graph.edges(b).size)
        assertEquals(b, graph.edges(a)[0].destination)
        assertEquals(a, graph.edges(b)[0].destination)
    }

    @Test
    fun testAddDirectedEdgeNullWeight() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, null)

        val edgesA = graph.edges(a)
        assertEquals(1, edgesA.size)
        assertNull(edgesA[0].weight)
        assertEquals(0, graph.edges(b).size)
    }

    @Test
    fun testAddEdgeWithEdgeTypeDirected() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.add(Edge(a, b, 2.0, EdgeType.DIRECTED))

        assertEquals(1, graph.edges(a).size)
        assertEquals(0, graph.edges(b).size)
    }

    @Test
    fun testAddEdgeWithEdgeTypeUndirected() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.add(Edge(a, b, 2.0, EdgeType.UNDIRECTED))

        assertEquals(1, graph.edges(a).size)
        assertEquals(1, graph.edges(b).size)
    }

    @Test
    fun testAddEdgeUndirectedNullWeight() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.add(Edge(a, b, null, EdgeType.UNDIRECTED))

        assertEquals(1, graph.edges(a).size)
        assertEquals(1, graph.edges(b).size)
        assertNull(graph.edges(a)[0].weight)
    }

    @Test
    fun testWeight() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 7.5)

        assertEquals(7.5, graph.weight(a, b))
        assertNull(graph.weight(a, c))
    }

    @Test
    fun testEdgesEmptyVertex() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        assertTrue(graph.edges(a).isEmpty())
    }

    @Test
    fun testMultipleEdgesFromSameVertex() {
        val graph = AdjacencyList<Int>()
        val v0 = graph.createVertex(0)
        val v1 = graph.createVertex(1)
        val v2 = graph.createVertex(2)
        val v3 = graph.createVertex(3)
        graph.addDirectedEdge(v0, v1, 1.0)
        graph.addDirectedEdge(v0, v2, 2.0)
        graph.addDirectedEdge(v0, v3, 3.0)

        val edges = graph.edges(v0)
        assertEquals(3, edges.size)
    }

    @Test
    fun testToString() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 1.0)

        val str = graph.toString()
        assertTrue(str.contains("A"))
        assertTrue(str.contains("B"))
    }

    @Test
    fun testMutableGraphInterface() {
        val graph: MutableGraph<String> = AdjacencyList()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 2.0)
        assertEquals(1, graph.edges(a).size)
        assertEquals(2.0, graph.weight(a, b))
    }

    @Test
    fun testGraphReadOnlyView() {
        val mutable: MutableGraph<String> = AdjacencyList()
        val a = mutable.createVertex("A")
        val b = mutable.createVertex("B")
        mutable.addDirectedEdge(a, b, 3.0)
        val readOnly: Graph<String> = mutable
        assertEquals(1, readOnly.edges(a).size)
        assertEquals(3.0, readOnly.weight(a, b))
    }

    @Test
    fun testSelfLoop() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        graph.addDirectedEdge(a, a, 1.0)
        val edges = graph.edges(a)
        assertEquals(1, edges.size)
        assertEquals(a, edges[0].destination)
        assertEquals(1.0, graph.weight(a, a))
    }

    @Test
    fun testParallelEdges() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(a, b, 2.0)
        assertEquals(2, graph.edges(a).size)
    }

    @Test
    fun testAsReadOnlyView() {
        val mutable: MutableGraph<String> = AdjacencyList()
        val a = mutable.createVertex("A")
        val b = mutable.createVertex("B")
        mutable.addDirectedEdge(a, b, 5.0)
        val readOnly: Graph<String> = mutable.asReadOnly()
        assertEquals(1, readOnly.edges(a).size)
        assertEquals(5.0, readOnly.weight(a, b))

        mutable.addDirectedEdge(b, a, 3.0)
        assertEquals(1, readOnly.edges(b).size)
    }

    @Test
    fun testIsolatedVertexInPopulatedGraph() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        assertTrue(graph.edges(c).isEmpty())
        assertNull(graph.weight(a, c))
    }
}
