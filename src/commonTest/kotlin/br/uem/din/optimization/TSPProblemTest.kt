package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class TSPProblemTest {

    @Test
    fun testTSPEvaluateTriangle() {
        val cities = listOf(
            Pair(0.0, 0.0),
            Pair(3.0, 0.0),
            Pair(0.0, 4.0)
        )
        val tsp = TSPProblem(cities)
        val tour = intArrayOf(0, 1, 2)
        val cost = tsp.evaluate(tour)
        assertEquals(3.0 + 5.0 + 4.0, cost, 1e-10)
    }

    @Test
    fun testTSPRandomSolutionIsPermutation() {
        val tsp = TSPProblem.random(20, random = Random(1))
        val solution = tsp.randomSolution(Random(42))
        assertEquals(20, solution.size)
        assertEquals((0 until 20).toSet(), solution.toSet())
    }

    @Test
    fun testTSPNeighborIsPermutation() {
        val tsp = TSPProblem.random(15, random = Random(1))
        val solution = tsp.randomSolution(Random(42))
        val neighbor = tsp.neighbor(solution, Random(99))
        assertEquals(15, neighbor.size)
        assertEquals((0 until 15).toSet(), neighbor.toSet())
    }

    @Test
    fun testTSPCopyIsIndependent() {
        val tsp = TSPProblem.random(5, random = Random(1))
        val solution = tsp.randomSolution(Random(42))
        val copy = tsp.copy(solution)
        assertContentEquals(solution, copy)
        copy[0] = -1
        assertFalse(solution[0] == -1)
    }

    @Test
    fun testTSPDirectionIsMinimize() {
        val tsp = TSPProblem.random(5, random = Random(1))
        assertEquals(OptimizationDirection.MINIMIZE, tsp.direction)
    }
}
