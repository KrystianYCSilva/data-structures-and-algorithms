package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class VariableNeighborhoodSearchTest {

    @Test
    fun testVNSSphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = variableNeighborhoodSearch(
            problem,
            kMax = 4,
            maxIterations = 100,
            random = Random(42)
        )
        assertTrue(result.cost < 10.0)
    }

    @Test
    fun testVNSRastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = variableNeighborhoodSearch(
            problem,
            kMax = 5,
            maxIterations = 150,
            random = Random(99)
        )
        assertTrue(result.cost < 30.0)
    }

    @Test
    fun testVNSReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = variableNeighborhoodSearch(
            problem,
            maxIterations = 10,
            random = Random(7)
        )
        assertEquals(10, result.iterations)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testVNSImprovesOverRandom() {
        val problem = Benchmarks.sphereProblem(5)
        val rng = Random(55)
        val randomCost = problem.evaluate(problem.randomSolution(rng))
        val result = variableNeighborhoodSearch(problem, random = Random(55))
        assertTrue(result.cost <= randomCost)
    }

    @Test
    fun testVNSTSP() {
        val tsp = TSPProblem.random(8, random = Random(1))
        val result = variableNeighborhoodSearch(
            tsp,
            kMax = 4,
            maxIterations = 50,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
        assertTrue(result.cost < Double.MAX_VALUE)
    }
}
