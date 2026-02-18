package br.uem.din.datastructures.graph

import kotlin.test.*

class DirectedAcyclicGraphTest {

    @Test
    fun testAddVertex() {
        val dag = DirectedAcyclicGraph<String>()
        val v = dag.addVertex("A")
        assertEquals(0, v.index)
        assertEquals("A", v.data)
        assertEquals(1, dag.vertices().size)
    }

    @Test
    fun testAddEdge() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        dag.addEdge(a, b, 1.0)

        val edges = dag.edges(a)
        assertEquals(1, edges.size)
        assertEquals(b, edges[0].destination)
        assertEquals(1.0, edges[0].weight)
    }

    @Test
    fun testAddEdgeNullWeight() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        dag.addEdge(a, b)

        val edges = dag.edges(a)
        assertEquals(1, edges.size)
        assertNull(edges[0].weight)
    }

    @Test
    fun testCycleDetectionSelfLoop() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        assertFailsWith<IllegalArgumentException> {
            dag.addEdge(a, a)
        }
    }

    @Test
    fun testCycleDetectionDirectCycle() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        dag.addEdge(a, b)
        assertFailsWith<IllegalArgumentException> {
            dag.addEdge(b, a)
        }
    }

    @Test
    fun testCycleDetectionIndirectCycle() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        val c = dag.addVertex("C")
        dag.addEdge(a, b)
        dag.addEdge(b, c)
        assertFailsWith<IllegalArgumentException> {
            dag.addEdge(c, a)
        }
    }

    @Test
    fun testAddEdgeInvalidVertex() {
        val dag1 = DirectedAcyclicGraph<String>()
        val dag2 = DirectedAcyclicGraph<String>()
        val a = dag1.addVertex("A")
        val b = dag2.addVertex("B")
        assertFailsWith<IllegalArgumentException> {
            dag1.addEdge(a, b)
        }
    }

    @Test
    fun testTopologicalSort() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        val c = dag.addVertex("C")
        val d = dag.addVertex("D")
        dag.addEdge(a, b)
        dag.addEdge(a, c)
        dag.addEdge(b, d)
        dag.addEdge(c, d)

        val order = dag.topologicalSort()
        assertEquals(4, order.size)
        val indexMap = order.withIndex().associate { it.value to it.index }
        assertTrue(indexMap[a]!! < indexMap[b]!!)
        assertTrue(indexMap[a]!! < indexMap[c]!!)
        assertTrue(indexMap[b]!! < indexMap[d]!!)
        assertTrue(indexMap[c]!! < indexMap[d]!!)
    }

    @Test
    fun testTopologicalSortLinear() {
        val dag = DirectedAcyclicGraph<Int>()
        val v1 = dag.addVertex(1)
        val v2 = dag.addVertex(2)
        val v3 = dag.addVertex(3)
        dag.addEdge(v1, v2)
        dag.addEdge(v2, v3)

        val order = dag.topologicalSort()
        assertEquals(listOf(v1, v2, v3), order)
    }

    @Test
    fun testShortestPath() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        val c = dag.addVertex("C")
        val d = dag.addVertex("D")
        dag.addEdge(a, b, 1.0)
        dag.addEdge(a, c, 4.0)
        dag.addEdge(b, c, 2.0)
        dag.addEdge(b, d, 6.0)
        dag.addEdge(c, d, 1.0)

        val dist = dag.shortestPath(a)
        assertEquals(0.0, dist[a])
        assertEquals(1.0, dist[b])
        assertEquals(3.0, dist[c])
        assertEquals(4.0, dist[d])
    }

    @Test
    fun testShortestPathUnreachable() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")

        val dist = dag.shortestPath(a)
        assertEquals(0.0, dist[a])
        assertEquals(Double.POSITIVE_INFINITY, dist[b])
    }

    @Test
    fun testShortestPathNullWeightTreatedAs1() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        val c = dag.addVertex("C")
        dag.addEdge(a, b)
        dag.addEdge(b, c)

        val dist = dag.shortestPath(a)
        assertEquals(0.0, dist[a])
        assertEquals(1.0, dist[b])
        assertEquals(2.0, dist[c])
    }

    @Test
    fun testShortestPathInvalidSource() {
        val dag1 = DirectedAcyclicGraph<String>()
        val dag2 = DirectedAcyclicGraph<String>()
        val a = dag2.addVertex("A")
        assertFailsWith<IllegalArgumentException> {
            dag1.shortestPath(a)
        }
    }

    @Test
    fun testEdgesEmptyVertex() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        assertTrue(dag.edges(a).isEmpty())
    }

    @Test
    fun testVertices() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        assertEquals(listOf(a, b), dag.vertices())
    }

    @Test
    fun testToString() {
        val dag = DirectedAcyclicGraph<String>()
        val a = dag.addVertex("A")
        val b = dag.addVertex("B")
        dag.addEdge(a, b, 2.0)
        val str = dag.toString()
        assertTrue(str.contains("A"))
        assertTrue(str.contains("B"))
    }
}
