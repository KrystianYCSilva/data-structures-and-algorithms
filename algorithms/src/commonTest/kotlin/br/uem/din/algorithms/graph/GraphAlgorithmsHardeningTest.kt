package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.AdjacencyList
import br.uem.din.datastructures.graph.AdjacencyMatrix
import br.uem.din.datastructures.graph.Edge
import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.MutableGraph
import br.uem.din.datastructures.graph.Vertex
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GraphAlgorithmsHardeningTest {

    @Test
    fun testBfsAndDfsReachabilityMatchesOracleOnRandomGraphs() {
        for (factory in graphFactories()) {
            repeat(5) { seed ->
                val random = Random(seed)
                val (graph, vertices) = buildRandomDirectedGraph(
                    factory = factory,
                    random = random,
                    vertexCount = 24,
                    edgeProbability = 0.18
                ) { _, _, rnd ->
                    rnd.nextInt(1, 20).toDouble()
                }

                val source = vertices[random.nextInt(vertices.size)]
                val reachable = reachableVertices(graph, source)
                val levelByVertex = bfsLevels(graph, source)

                val bfs = BreadthFirstSearch<Int>().search(graph, source)
                assertEquals(source, bfs.first(), "BFS first vertex mismatch (${factory.name}) seed=$seed")
                assertEquals(bfs.size, bfs.toSet().size, "BFS returned duplicates (${factory.name}) seed=$seed")
                assertEquals(reachable, bfs.toSet(), "BFS reachable-set mismatch (${factory.name}) seed=$seed")

                var previousLevel = -1
                for (vertex in bfs) {
                    val currentLevel = levelByVertex[vertex]
                    assertNotNull(currentLevel, "BFS level missing (${factory.name}) seed=$seed")
                    assertTrue(
                        currentLevel >= previousLevel,
                        "BFS level order regression (${factory.name}) seed=$seed prev=$previousLevel current=$currentLevel"
                    )
                    previousLevel = currentLevel
                }

                val dfs = DepthFirstSearch<Int>().search(graph, source)
                assertEquals(source, dfs.first(), "DFS first vertex mismatch (${factory.name}) seed=$seed")
                assertEquals(dfs.size, dfs.toSet().size, "DFS returned duplicates (${factory.name}) seed=$seed")
                assertEquals(reachable, dfs.toSet(), "DFS reachable-set mismatch (${factory.name}) seed=$seed")
            }
        }
    }

    @Test
    fun testDijkstraRandomizedAgainstBellmanFordOracleForNonNegativeGraphs() {
        for (factory in graphFactories()) {
            repeat(5) { seed ->
                val random = Random(100 + seed)
                val (graph, vertices) = buildRandomDirectedGraph(
                    factory = factory,
                    random = random,
                    vertexCount = 18,
                    edgeProbability = 0.16
                ) { _, _, rnd ->
                    rnd.nextInt(0, 35).toDouble()
                }

                val source = vertices[random.nextInt(vertices.size)]
                val expected = bellmanFord(graph, source)
                val actual = Dijkstra(graph).shortestPath(source)

                assertEquals(expected.keys, actual.keys, "Dijkstra key-set mismatch (${factory.name}) seed=$seed")

                for ((vertex, expectedDistance) in expected) {
                    val actualDistance = actual[vertex]
                    assertNotNull(actualDistance, "Missing Dijkstra distance (${factory.name}) seed=$seed vertex=${vertex.index}")
                    assertEquals(
                        expectedDistance,
                        actualDistance,
                        absoluteTolerance = 1e-9,
                        message = "Distance mismatch (${factory.name}) seed=$seed vertex=${vertex.index}"
                    )
                    assertTrue(actualDistance >= 0.0, "Negative distance in non-negative graph (${factory.name}) seed=$seed")
                }
            }
        }
    }

    @Test
    fun testDijkstraIsStatelessAcrossRepeatedInvocations() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        graph.addDirectedEdge(c, d, 1.0)
        graph.addDirectedEdge(a, d, 10.0)

        val dijkstra = Dijkstra(graph)

        val fromA = dijkstra.shortestPath(a)
        val fromADistance = assertNotNull(fromA[d])
        assertEquals(3.0, fromADistance, absoluteTolerance = 1e-9)

        val fromC = dijkstra.shortestPath(c)
        assertEquals(setOf(c, d), fromC.keys)
        val fromCToCDistance = assertNotNull(fromC[c])
        assertEquals(0.0, fromCToCDistance, absoluteTolerance = 1e-9)
        val fromCToDDistance = assertNotNull(fromC[d])
        assertEquals(1.0, fromCToDDistance, absoluteTolerance = 1e-9)
        assertFalse(fromC.containsKey(a))
        assertFalse(fromC.containsKey(b))
    }

    @Test
    fun testAStarIsStatelessAcrossRepeatedInvocations() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        graph.addDirectedEdge(a, b, 1.0)
        graph.addDirectedEdge(b, c, 1.0)
        graph.addDirectedEdge(c, d, 1.0)
        graph.addDirectedEdge(a, d, 10.0)

        val aStar = AStar(graph)

        val fromAToD = aStar.shortestPath(a, d)
        val fromAToDDistance = assertNotNull(fromAToD[d])
        assertEquals(3.0, fromAToDDistance, absoluteTolerance = 1e-9)

        val fromCToD = aStar.shortestPath(c, d)
        assertEquals(setOf(c, d), fromCToD.keys)
        val fromCToDSelfDistance = assertNotNull(fromCToD[c])
        assertEquals(0.0, fromCToDSelfDistance, absoluteTolerance = 1e-9)
        val fromCToDDistance = assertNotNull(fromCToD[d])
        assertEquals(1.0, fromCToDDistance, absoluteTolerance = 1e-9)
        assertFalse(fromCToD.containsKey(a))
        assertFalse(fromCToD.containsKey(b))
    }

    @Test
    fun testAStarMatchesDijkstraForDestinationOnMonotonicForwardGraphs() {
        for (factory in graphFactories()) {
            repeat(4) { seed ->
                val random = Random(300 + seed)
                val graph = factory.factory()
                val vertices = (0 until 16).map { graph.createVertex(it) }

                for (i in 0 until vertices.size - 1) {
                    graph.addDirectedEdge(vertices[i], vertices[i + 1], 1.0)
                    for (j in i + 2 until vertices.size) {
                        if (random.nextDouble() < 0.18) {
                            val weight = (j - i).toDouble() + random.nextDouble(0.0, 3.0)
                            graph.addDirectedEdge(vertices[i], vertices[j], weight)
                        }
                    }
                }

                val source = vertices.first()
                val destination = vertices.last()

                val expected = Dijkstra(graph).shortestPath(source)[destination]
                assertNotNull(expected, "Destination should be reachable (${factory.name}) seed=$seed")

                val actual = AStar(graph).shortestPath(source, destination)[destination]
                assertNotNull(actual, "A* should find destination (${factory.name}) seed=$seed")

                assertEquals(
                    expected,
                    actual,
                    absoluteTolerance = 1e-9,
                    message = "A* vs Dijkstra mismatch (${factory.name}) seed=$seed"
                )
            }
        }
    }

    @Test
    fun testFloydWarshallMatchesRepeatedDijkstraForNonNegativeGraphs() {
        for (factory in graphFactories()) {
            repeat(4) { seed ->
                val random = Random(500 + seed)
                val (graph, vertices) = buildRandomDirectedGraph(
                    factory = factory,
                    random = random,
                    vertexCount = 10,
                    edgeProbability = 0.24
                ) { _, _, rnd ->
                    rnd.nextInt(0, 30).toDouble()
                }

                val allPairs = floydWarshall(graph, vertices)

                for (source in vertices) {
                    val dijkstra = Dijkstra(graph).shortestPath(source)
                    for (destination in vertices) {
                        val expected = dijkstra[destination] ?: Double.POSITIVE_INFINITY
                        val actual = allPairs[source]!![destination]!!

                        assertEquals(
                            expected,
                            actual,
                            absoluteTolerance = 1e-9,
                            message = "Floyd-Warshall mismatch (${factory.name}) seed=$seed source=${source.index} destination=${destination.index}"
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testKruskalAndPrimAgreeOnMstWeightForConnectedUndirectedGraphs() {
        repeat(6) { seed ->
            val random = Random(700 + seed)
            val graph = AdjacencyList<Int>()
            val vertexCount = 14
            val vertices = (0 until vertexCount).map { graph.createVertex(it) }
            val edgeSet = mutableSetOf<Pair<Int, Int>>()

            fun addUniqueUndirectedEdge(i: Int, j: Int, weight: Double) {
                val a = minOf(i, j)
                val b = maxOf(i, j)
                if (a == b) return
                if (edgeSet.add(a to b)) {
                    graph.addUndirectedEdge(vertices[a], vertices[b], weight)
                }
            }

            for (i in 1 until vertexCount) {
                val parent = random.nextInt(i)
                addUniqueUndirectedEdge(parent, i, random.nextInt(1, 50).toDouble())
            }

            for (i in 0 until vertexCount) {
                for (j in i + 1 until vertexCount) {
                    if (random.nextDouble() < 0.20) {
                        addUniqueUndirectedEdge(i, j, random.nextInt(1, 50).toDouble())
                    }
                }
            }

            val primMst = prim(graph, vertices.first())
            val kruskalMst = kruskal(graph, vertices)

            assertEquals(vertexCount - 1, primMst.size, "Prim edge count mismatch seed=$seed")
            assertEquals(vertexCount - 1, kruskalMst.size, "Kruskal edge count mismatch seed=$seed")

            val primWeight = primMst.sumOf { it.weight ?: 0.0 }
            val kruskalWeight = kruskalMst.sumOf { it.weight ?: 0.0 }

            assertEquals(kruskalWeight, primWeight, absoluteTolerance = 1e-9, message = "MST weight mismatch seed=$seed")
            assertTrue(isAcyclicTree(primMst, vertexCount), "Prim produced cycle seed=$seed")
            assertTrue(isAcyclicTree(kruskalMst, vertexCount), "Kruskal produced cycle seed=$seed")
        }
    }

    @Test
    fun testDijkstraEdgeLookupsStayLinearInVisitedVertices() {
        val random = Random(999)
        val graph = AdjacencyList<Int>()
        val vertices = (0 until 60).map { graph.createVertex(it) }

        for (i in vertices.indices) {
            for (j in vertices.indices) {
                if (i != j && random.nextDouble() < 0.30) {
                    graph.addDirectedEdge(vertices[i], vertices[j], random.nextInt(1, 20).toDouble())
                }
            }
        }

        val countingGraph = CountingGraph(graph)
        val result = Dijkstra(countingGraph).shortestPath(vertices.first())

        assertTrue(result.isNotEmpty())
        assertTrue(
            countingGraph.edgeCalls <= result.size,
            "Dijkstra edge expansions exceeded visited vertex count: calls=${countingGraph.edgeCalls}, visited=${result.size}"
        )
    }

    private data class GraphFactory(
        val name: String,
        val factory: () -> MutableGraph<Int>
    )

    private fun graphFactories(): List<GraphFactory> = listOf(
        GraphFactory("adjacency-list") { AdjacencyList() },
        GraphFactory("adjacency-matrix") { AdjacencyMatrix() }
    )

    private fun buildRandomDirectedGraph(
        factory: GraphFactory,
        random: Random,
        vertexCount: Int,
        edgeProbability: Double,
        weightBuilder: (sourceIndex: Int, destinationIndex: Int, random: Random) -> Double
    ): Pair<MutableGraph<Int>, List<Vertex<Int>>> {
        val graph = factory.factory()
        val vertices = (0 until vertexCount).map { graph.createVertex(it) }

        for (i in 0 until vertexCount) {
            for (j in 0 until vertexCount) {
                if (i == j) continue
                if (random.nextDouble() < edgeProbability) {
                    graph.addDirectedEdge(vertices[i], vertices[j], weightBuilder(i, j, random))
                }
            }
        }

        return graph to vertices
    }

    private fun reachableVertices(graph: Graph<Int>, source: Vertex<Int>): Set<Vertex<Int>> {
        val queue = ArrayDeque<Vertex<Int>>()
        val visited = mutableSetOf<Vertex<Int>>()

        queue.addLast(source)
        visited.add(source)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            for (edge in graph.edges(current)) {
                if (visited.add(edge.destination)) {
                    queue.addLast(edge.destination)
                }
            }
        }

        return visited
    }

    private fun bfsLevels(graph: Graph<Int>, source: Vertex<Int>): Map<Vertex<Int>, Int> {
        val queue = ArrayDeque<Vertex<Int>>()
        val distance = mutableMapOf<Vertex<Int>, Int>()

        queue.addLast(source)
        distance[source] = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val baseDistance = distance[current]!!
            for (edge in graph.edges(current)) {
                if (!distance.containsKey(edge.destination)) {
                    distance[edge.destination] = baseDistance + 1
                    queue.addLast(edge.destination)
                }
            }
        }

        return distance
    }

    private fun isAcyclicTree(edges: List<Edge<Int>>, vertexCount: Int): Boolean {
        if (edges.size != vertexCount - 1) return false

        val parent = IntArray(vertexCount) { it }
        val rank = IntArray(vertexCount)

        fun find(x: Int): Int {
            var current = x
            while (parent[current] != current) {
                parent[current] = parent[parent[current]]
                current = parent[current]
            }
            return current
        }

        fun union(a: Int, b: Int): Boolean {
            val rootA = find(a)
            val rootB = find(b)
            if (rootA == rootB) return false

            when {
                rank[rootA] < rank[rootB] -> parent[rootA] = rootB
                rank[rootA] > rank[rootB] -> parent[rootB] = rootA
                else -> {
                    parent[rootB] = rootA
                    rank[rootA]++
                }
            }
            return true
        }

        for (edge in edges) {
            if (!union(edge.source.index, edge.destination.index)) {
                return false
            }
        }

        return true
    }

    private class CountingGraph<T>(private val delegate: Graph<T>) : Graph<T> {
        var edgeCalls: Int = 0

        override fun edges(source: Vertex<T>): ArrayList<Edge<T>> {
            edgeCalls++
            return delegate.edges(source)
        }

        override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
            return delegate.weight(source, destination)
        }
    }
}

