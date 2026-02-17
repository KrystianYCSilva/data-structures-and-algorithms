package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.AdjacencyList
import br.uem.din.datastructures.graph.Vertex
import kotlin.test.*

class GraphAlgorithmsTest {

    // ==================== Bellman-Ford ====================

    @Test
    fun testBellmanFord() {
        val graph = AdjacencyList<String>()
        val s = graph.createVertex("S")
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        graph.addDirectedEdge(s, a, 4.0)
        graph.addDirectedEdge(s, b, 2.0)
        graph.addDirectedEdge(a, c, 4.0)
        graph.addDirectedEdge(a, b, -1.0)
        graph.addDirectedEdge(b, c, 8.0)
        graph.addDirectedEdge(b, d, 5.0)
        graph.addDirectedEdge(c, d, 2.0)

        val dists = bellmanFord(graph, s)

        assertEquals(0.0, dists[s])
        assertEquals(4.0, dists[a])
        assertEquals(2.0, dists[b])
        assertEquals(8.0, dists[c])
        assertEquals(7.0, dists[d])
    }

    @Test
    fun testBellmanFordNegativeCycle() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")

        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        graph.addDirectedEdge(c, a, -5.0)

        assertFailsWith<IllegalStateException> {
            bellmanFord(graph, a)
        }
    }

    @Test
    fun testBellmanFordSingleVertex() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val dists = bellmanFord(graph, a)
        assertEquals(0.0, dists[a])
        assertEquals(1, dists.size)
    }

    @Test
    fun testBellmanFordDisconnected() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 3.0)
        val c = graph.createVertex("C")
        val dists = bellmanFord(graph, a)
        assertEquals(0.0, dists[a])
        assertEquals(3.0, dists[b])
        assertFalse(dists.containsKey(c))
    }

    // ==================== Floyd-Warshall ====================

    @Test
    fun testFloydWarshall() {
        val graph = AdjacencyList<String>()
        val v1 = graph.createVertex("1")
        val v2 = graph.createVertex("2")
        val v3 = graph.createVertex("3")
        val v4 = graph.createVertex("4")

        graph.addDirectedEdge(v1, v3, -2.0)
        graph.addDirectedEdge(v2, v1, 4.0)
        graph.addDirectedEdge(v2, v3, 3.0)
        graph.addDirectedEdge(v3, v4, 2.0)
        graph.addDirectedEdge(v4, v2, -1.0)

        val vertices = listOf(v1, v2, v3, v4)
        val dists = floydWarshall(graph, vertices)

        assertEquals(-1.0, dists[v1]!![v2])
    }

    @Test
    fun testFloydWarshallSingleVertex() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val dists = floydWarshall(graph, listOf(a))
        assertEquals(0.0, dists[a]!![a])
    }

    @Test
    fun testFloydWarshallDisconnected() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val dists = floydWarshall(graph, listOf(a, b))
        assertEquals(0.0, dists[a]!![a])
        assertEquals(0.0, dists[b]!![b])
        assertEquals(Double.POSITIVE_INFINITY, dists[a]!![b])
        assertEquals(Double.POSITIVE_INFINITY, dists[b]!![a])
    }

    @Test
    fun testFloydWarshallNegativeCycle() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, a, -5.0)
        assertFailsWith<IllegalStateException> {
            floydWarshall(graph, listOf(a, b))
        }
    }

    // ==================== Kruskal ====================

    @Test
    fun testKruskal() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        graph.addUndirectedEdge(a, b, 1.0)
        graph.addUndirectedEdge(b, c, 2.0)
        graph.addUndirectedEdge(c, d, 3.0)
        graph.addUndirectedEdge(a, d, 10.0)
        graph.addUndirectedEdge(a, c, 5.0)

        val vertices = listOf(a, b, c, d)
        val mst = kruskal(graph, vertices)

        val totalWeight = mst.sumOf { it.weight ?: 0.0 }
        assertEquals(6.0, totalWeight)
        assertEquals(3, mst.size)
    }

    @Test
    fun testKruskalSingleVertex() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val mst = kruskal(graph, listOf(a))
        assertTrue(mst.isEmpty())
    }

    @Test
    fun testKruskalTwoVertices() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addUndirectedEdge(a, b, 5.0)
        val mst = kruskal(graph, listOf(a, b))
        assertEquals(1, mst.size)
        assertEquals(5.0, mst[0].weight)
    }

    @Test
    fun testKruskalDisconnected() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        graph.addUndirectedEdge(a, b, 1.0)
        graph.addUndirectedEdge(c, d, 2.0)
        val mst = kruskal(graph, listOf(a, b, c, d))
        assertEquals(2, mst.size)
        val totalWeight = mst.sumOf { it.weight ?: 0.0 }
        assertEquals(3.0, totalWeight)
    }

    @Test
    fun testKruskalNegativeWeights() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addUndirectedEdge(a, b, -2.0)
        graph.addUndirectedEdge(b, c, 3.0)
        graph.addUndirectedEdge(a, c, 1.0)
        val mst = kruskal(graph, listOf(a, b, c))
        assertEquals(2, mst.size)
        val totalWeight = mst.sumOf { it.weight ?: 0.0 }
        assertEquals(-1.0, totalWeight)
    }

    // ==================== Prim ====================

    @Test
    fun testPrim() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        graph.addUndirectedEdge(a, b, 1.0)
        graph.addUndirectedEdge(b, c, 2.0)
        graph.addUndirectedEdge(c, d, 3.0)
        graph.addUndirectedEdge(a, d, 10.0)

        val mst = prim(graph, a)

        val totalWeight = mst.sumOf { it.weight ?: 0.0 }
        assertEquals(6.0, totalWeight)
        assertEquals(3, mst.size)
    }

    @Test
    fun testPrimSingleVertex() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val mst = prim(graph, a)
        assertTrue(mst.isEmpty())
    }

    @Test
    fun testPrimTwoVertices() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        graph.addUndirectedEdge(a, b, 7.0)
        val mst = prim(graph, a)
        assertEquals(1, mst.size)
        assertEquals(7.0, mst[0].weight)
    }

    @Test
    fun testPrimWithCycle() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addUndirectedEdge(a, b, 1.0)
        graph.addUndirectedEdge(b, c, 2.0)
        graph.addUndirectedEdge(a, c, 10.0)
        val mst = prim(graph, a)
        assertEquals(2, mst.size)
        val totalWeight = mst.sumOf { it.weight ?: 0.0 }
        assertEquals(3.0, totalWeight)
    }

    // ==================== Dijkstra extra ====================

    @Test
    fun testDijkstraWithCycle() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 2.0)
        graph.addDirectedEdge(c, a, 10.0)
        val dijkstra = Dijkstra(graph)
        val costs = dijkstra.shortestPath(a)
        assertEquals(0.0, costs[a])
        assertEquals(1.0, costs[b])
        assertEquals(3.0, costs[c])
    }

    @Test
    fun testDijkstraZeroWeightEdge() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 0.0)
        graph.addDirectedEdge(b, c, 1.0)
        val dijkstra = Dijkstra(graph)
        val costs = dijkstra.shortestPath(a)
        assertEquals(0.0, costs[a])
        assertEquals(0.0, costs[b])
        assertEquals(1.0, costs[c])
    }

    // ==================== A* extra ====================

    @Test
    fun testAStarUnreachable() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val astar = AStar(graph)
        val costs = astar.shortestPath(a, b)
        assertEquals(0.0, costs[a])
        assertNull(costs[b])
    }

    @Test
    fun testAStarWithCycle() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 2.0)
        graph.addDirectedEdge(c, a, 10.0)
        val astar = AStar(graph)
        val costs = astar.shortestPath(a, c)
        assertEquals(0.0, costs[a])
        assertEquals(3.0, costs[c])
    }
}
