package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class ACOTest {

    @Test
    fun testACOSmallTSP() {
        val tsp = TSPProblem.random(8, random = Random(1))
        val result = antColonyOptimization(
            tsp, antCount = 15, iterations = 100, random = Random(42)
        )
        assertTrue(result.cost > 0.0)
        assertTrue(result.cost < Double.MAX_VALUE)
        assertEquals(8, result.solution.toSet().size)
    }

    @Test
    fun testACOImprovesTour() {
        val tsp = TSPProblem.random(10, random = Random(1))
        val randomTour = tsp.randomSolution(Random(1))
        val randomCost = tsp.evaluate(randomTour)
        val result = antColonyOptimization(tsp, antCount = 20, iterations = 150, random = Random(42))
        assertTrue(result.cost <= randomCost)
    }

    @Test
    fun testACOValidPermutation() {
        val tsp = TSPProblem.random(12, random = Random(5))
        val result = antColonyOptimization(tsp, antCount = 10, iterations = 50, random = Random(42))
        assertEquals(12, result.solution.size)
        assertEquals((0 until 12).toSet(), result.solution.toSet())
    }

    @Test
    fun testACOReturnsPositiveEvaluations() {
        val tsp = TSPProblem.random(5, random = Random(1))
        val result = antColonyOptimization(tsp, antCount = 5, iterations = 10, random = Random(42))
        assertTrue(result.evaluations > 0)
    }
}
