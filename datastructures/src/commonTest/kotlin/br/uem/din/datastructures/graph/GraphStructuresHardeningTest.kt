package br.uem.din.datastructures.graph

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.*

class GraphStructuresHardeningTest {

    @Test
    fun testAdjacencyListAndMatrixRandomizedParityWithOracle() {
        val random = Random(20260217)
        val vertexCount = 20

        val listGraph = AdjacencyList<Int>()
        val matrixGraph = AdjacencyMatrix<Int>()

        val listVertices = List(vertexCount) { listGraph.createVertex(it) }
        val matrixVertices = List(vertexCount) { matrixGraph.createVertex(it) }

        val model = mutableMapOf<EdgeKey, Double?>()

        repeat(900) { step ->
            val source = random.nextInt(0, vertexCount)
            val destination = random.nextInt(0, vertexCount)
            val weight = randomWeight(random)

            when (random.nextInt(100)) {
                in 0..44 -> {
                    val key = EdgeKey(source, destination)
                    if (key !in model) {
                        if (random.nextBoolean()) {
                            listGraph.addDirectedEdge(listVertices[source], listVertices[destination], weight)
                            matrixGraph.addDirectedEdge(matrixVertices[source], matrixVertices[destination], weight)
                        } else {
                            listGraph.add(Edge(listVertices[source], listVertices[destination], weight, EdgeType.DIRECTED))
                            matrixGraph.add(Edge(matrixVertices[source], matrixVertices[destination], weight, EdgeType.DIRECTED))
                        }
                        model[key] = weight
                    }
                }

                in 45..74 -> {
                    if (source == destination) return@repeat
                    val direct = EdgeKey(source, destination)
                    val reverse = EdgeKey(destination, source)
                    if (direct !in model && reverse !in model) {
                        if (random.nextBoolean()) {
                            listGraph.addUndirectedEdge(listVertices[source], listVertices[destination], weight)
                            matrixGraph.addUndirectedEdge(matrixVertices[source], matrixVertices[destination], weight)
                        } else {
                            listGraph.add(Edge(listVertices[source], listVertices[destination], weight, EdgeType.UNDIRECTED))
                            matrixGraph.add(Edge(matrixVertices[source], matrixVertices[destination], weight, EdgeType.UNDIRECTED))
                        }
                        model[direct] = weight
                        model[reverse] = weight
                    }
                }

                else -> {
                    validateGraphState(listGraph, matrixGraph, listVertices, matrixVertices, model, step)
                }
            }

            if (step % 30 == 0) {
                validateGraphState(listGraph, matrixGraph, listVertices, matrixVertices, model, step)
            }
        }

        validateGraphState(listGraph, matrixGraph, listVertices, matrixVertices, model, -1)
    }

    @Test
    fun testGraphReadOnlyViewSnapshotAndLiveSemantics() {
        val mutable: MutableGraph<String> = AdjacencyList()
        val a = mutable.createVertex("A")
        val b = mutable.createVertex("B")
        val c = mutable.createVertex("C")

        mutable.addDirectedEdge(a, b, 1.0)

        val readOnly: Graph<String> = mutable.asReadOnly()
        val snapshot = readOnly.edges(a).map { it.destination.index to it.weight }

        mutable.addDirectedEdge(a, c, 2.0)

        assertEquals(listOf(b.index to 1.0), snapshot)
        assertEquals(
            setOf(b.index to 1.0, c.index to 2.0),
            readOnly.edges(a).map { it.destination.index to it.weight }.toSet()
        )
        assertEquals(2.0, readOnly.weight(a, c))
    }

    @Test
    fun testDAGRandomizedAcyclicInvariantTopologicalAndShortestPath() {
        val random = Random(20260218)
        val vertexCount = 18
        val dag = DirectedAcyclicGraph<Int>()
        val vertices = List(vertexCount) { dag.addVertex(it) }

        val rankOrder = (0 until vertexCount).shuffled(random)
        val rank = rankOrder.withIndex().associate { (pos, index) -> index to pos }
        val model = mutableMapOf<EdgeKey, Double?>()

        repeat(700) {
            val a = random.nextInt(0, vertexCount)
            val b = random.nextInt(0, vertexCount)
            if (a == b) return@repeat

            val sourceIndex: Int
            val destinationIndex: Int
            if (rank[a]!! < rank[b]!!) {
                sourceIndex = a
                destinationIndex = b
            } else {
                sourceIndex = b
                destinationIndex = a
            }

            val key = EdgeKey(sourceIndex, destinationIndex)
            if (key in model) return@repeat

            val weight = if (random.nextInt(5) == 0) null else random.nextInt(1, 16).toDouble()
            if (random.nextBoolean()) {
                dag.addEdge(vertices[sourceIndex], vertices[destinationIndex], weight)
            } else {
                dag.addDirectedEdge(vertices[sourceIndex], vertices[destinationIndex], weight)
            }
            model[key] = weight
        }

        val topological = dag.topologicalSort()
        val indexInOrder = topological.withIndex().associate { (position, vertex) -> vertex.index to position }

        for ((key, _) in model) {
            assertTrue(
                indexInOrder[key.from]!! < indexInOrder[key.to]!!,
                "topological order violation for edge ${key.from} -> ${key.to}"
            )
        }

        val source = vertices[rankOrder.first()]
        val expectedDist = shortestPathOracle(source.index, topological, model)
        val actualDist = dag.shortestPath(source)

        for (vertex in vertices) {
            assertEquals(
                expectedDist[vertex.index],
                actualDist[vertex],
                "shortest path mismatch for vertex=${vertex.index}"
            )
        }

        val sampleEdge = model.keys.firstOrNull() ?: run {
            val s = vertices[rankOrder[0]]
            val d = vertices[rankOrder[1]]
            dag.addEdge(s, d, 1.0)
            EdgeKey(s.index, d.index)
        }

        assertFailsWith<IllegalArgumentException> {
            dag.addEdge(vertices[sampleEdge.to], vertices[sampleEdge.from], 1.0)
        }

        assertFailsWith<UnsupportedOperationException> {
            dag.addUndirectedEdge(vertices[0], vertices[1], 1.0)
        }
    }

    private fun validateGraphState(
        listGraph: AdjacencyList<Int>,
        matrixGraph: AdjacencyMatrix<Int>,
        listVertices: List<Vertex<Int>>,
        matrixVertices: List<Vertex<Int>>,
        model: Map<EdgeKey, Double?>,
        step: Int
    ) {
        val vertexCount = listVertices.size

        for (source in 0 until vertexCount) {
            val expectedOutgoing = model.entries
                .filter { it.key.from == source }
                .map { it.key.to to it.value }
                .sortedBy { it.first }

            val listOutgoing = listGraph.edges(listVertices[source])
                .map { it.destination.index to it.weight }
                .sortedBy { it.first }

            val matrixOutgoing = matrixGraph.edges(matrixVertices[source])
                .map { it.destination.index to it.weight }
                .sortedBy { it.first }

            assertEquals(expectedOutgoing, listOutgoing, "adjacency list mismatch at source=$source step=$step")
            assertEquals(expectedOutgoing, matrixOutgoing, "adjacency matrix mismatch at source=$source step=$step")

            for (destination in 0 until vertexCount) {
                val expectedWeight = model[EdgeKey(source, destination)]
                assertEquals(
                    expectedWeight,
                    listGraph.weight(listVertices[source], listVertices[destination]),
                    "list weight mismatch at $source->$destination step=$step"
                )
                assertEquals(
                    expectedWeight,
                    matrixGraph.weight(matrixVertices[source], matrixVertices[destination]),
                    "matrix weight mismatch at $source->$destination step=$step"
                )
            }
        }
    }

    private fun shortestPathOracle(
        source: Int,
        topologicalOrder: List<Vertex<Int>>,
        edges: Map<EdgeKey, Double?>
    ): Map<Int, Double> {
        val dist = mutableMapOf<Int, Double>()
        for (vertex in topologicalOrder) {
            dist[vertex.index] = Double.POSITIVE_INFINITY
        }
        dist[source] = 0.0

        for (vertex in topologicalOrder) {
            val current = dist[vertex.index] ?: Double.POSITIVE_INFINITY
            if (current == Double.POSITIVE_INFINITY) continue

            for ((key, weight) in edges) {
                if (key.from != vertex.index) continue
                val candidate = current + (weight ?: 1.0)
                if (candidate < (dist[key.to] ?: Double.POSITIVE_INFINITY)) {
                    dist[key.to] = candidate
                }
            }
        }

        return dist
    }

    private fun randomWeight(random: Random): Double? {
        return if (random.nextInt(5) == 0) null else random.nextInt(-20, 21).toDouble()
    }

    private data class EdgeKey(val from: Int, val to: Int)
}

