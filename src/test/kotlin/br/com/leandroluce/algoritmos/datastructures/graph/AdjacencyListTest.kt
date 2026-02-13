package br.com.leandroluce.algoritmos.datastructures.graph

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AdjacencyListTest {

    @Test
    fun `createVertex should add a vertex to the graph`() {
        val graph = AdjacencyList<String>()
        val vertex = graph.createVertex("A")
        assertNotNull(vertex)
        assertEquals("A", vertex.data)
    }

    @Test
    fun `addDirectedEdge should add a directed edge between two vertices`() {
        val graph = AdjacencyList<String>()
        val source = graph.createVertex("A")
        val destination = graph.createVertex("B")
        graph.addDirectedEdge(source, destination, 1.0)

        val edges = graph.edges(source)
        assertEquals(1, edges.size)
        assertEquals(destination, edges[0].destination)
        assertEquals(1.0, edges[0].weight)
    }

    @Test
    fun `addUndirectedEdge should add an edge in both directions`() {
        val graph = AdjacencyList<String>()
        val source = graph.createVertex("A")
        val destination = graph.createVertex("B")
        graph.addUndirectedEdge(source, destination, 1.0)

        val sourceEdges = graph.edges(source)
        assertEquals(1, sourceEdges.size)
        assertEquals(destination, sourceEdges[0].destination)

        val destinationEdges = graph.edges(destination)
        assertEquals(1, destinationEdges.size)
        assertEquals(source, destinationEdges[0].destination)
    }
}
