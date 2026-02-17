package br.uem.din.algorithms.graph

import br.uem.din.datastructures.graph.AdjacencyList
import br.uem.din.datastructures.graph.Vertex
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class GraphAlgorithmsTest {

    @Test
    fun testBellmanFord() {
        val graph = AdjacencyList<String>()
        val s = graph.createVertex("S")
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        // Grafo com pesos positivos
        graph.addDirectedEdge(s, a, 4.0)
        graph.addDirectedEdge(s, b, 2.0)
        graph.addDirectedEdge(a, c, 4.0)
        graph.addDirectedEdge(a, b, -1.0) // Aresta negativa segura
        graph.addDirectedEdge(b, c, 8.0)
        graph.addDirectedEdge(b, d, 5.0)
        graph.addDirectedEdge(c, d, 2.0)

        // S->B(2)
        // S->A(4) -> B(3) melhor caminho S->A->B custa 3? Não, S->B custa 2.
        // S->A(4)
        // A->B(-1) => S->A->B custa 3. S->B direto custa 2. 2 < 3.
        
        // Caminhos esperados:
        // S: 0
        // A: 4
        // B: 2 (S->B)
        // C: 8 (S->A->C)
        // D: 7 (S->B->D)
        
        // Vamos corrigir o exemplo para algo clássico do Cormen ou simples
        // S -> A (6), S -> B (7)
        // A -> C (5), A -> B (8), B -> C (-3), B -> A (-2)
        // Ops, cuidado com ciclo negativo A->B->A (-2 + 8 = 6 > 0 ok)

        val dists = bellmanFord(graph, s)
        
        assertEquals(0.0, dists[s])
        assertEquals(4.0, dists[a])
        assertEquals(2.0, dists[b])
        // S->A(4)->C(4) = 8. S->B(2)->C(8) = 10.
        // S->A(4)->B(-1) = 3 (mas S->B é 2, então não relaxa via A)
        assertEquals(8.0, dists[c]) 
        // S->B(2)->D(5) = 7. S->A(4)->C(4)->D(2) = 10.
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
        graph.addDirectedEdge(c, a, -5.0) // Ciclo A->B->C->A peso -3

        assertFailsWith<IllegalStateException> {
            bellmanFord(graph, a)
        }
    }

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

        // Exemplo simples
        // 1 -> 3 (-2) -> 4 (0) -> 2 (-1)
        // Dist 1->2 = -2 + 2 - 1 = -1
        assertEquals(-1.0, dists[v1]!![v2])
    }

    @Test
    fun testKruskal() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        // Grafo não-direcionado (adicionando bidirecional)
        // A-B (1), B-C (2), C-D (3), A-D (10), A-C (5)
        graph.addUndirectedEdge(a, b, 1.0)
        graph.addUndirectedEdge(b, c, 2.0)
        graph.addUndirectedEdge(c, d, 3.0)
        graph.addUndirectedEdge(a, d, 10.0)
        graph.addUndirectedEdge(a, c, 5.0)

        val vertices = listOf(a, b, c, d)
        val mst = kruskal(graph, vertices)

        // Esperado: A-B(1), B-C(2), C-D(3). Total peso = 6
        val totalWeight = mst.sumOf { it.weight ?: 0.0 }
        assertEquals(6.0, totalWeight)
        assertEquals(3, mst.size)
    }

    @Test
    fun testPrim() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")

        // Grafo conexo não-direcionado
        graph.addUndirectedEdge(a, b, 1.0)
        graph.addUndirectedEdge(b, c, 2.0)
        graph.addUndirectedEdge(c, d, 3.0)
        graph.addUndirectedEdge(a, d, 10.0)
        
        val mst = prim(graph, a)
        
        // Esperado: A-B(1), B-C(2), C-D(3). Total = 6
        val totalWeight = mst.sumOf { it.weight ?: 0.0 }
        assertEquals(6.0, totalWeight)
        assertEquals(3, mst.size)
    }
}
