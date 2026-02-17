package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class LargeNeighborhoodSearchTest {

    @Test
    fun testLNSSphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = largeNeighborhoodSearch(
            problem,
            maxIterations = 200,
            destroyDegree = 8,
            random = Random(42)
        )
        assertTrue(result.cost < 10.0)
    }

    @Test
    fun testLNSRastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = largeNeighborhoodSearch(
            problem,
            maxIterations = 300,
            destroyDegree = 5,
            random = Random(99)
        )
        assertTrue(result.cost < 50.0)
    }

    @Test
    fun testLNSReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = largeNeighborhoodSearch(
            problem,
            maxIterations = 10,
            random = Random(7)
        )
        assertEquals(10, result.iterations)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testLNSWithAcceptanceTemp() {
        val problem = Benchmarks.sphereProblem(5)
        val result = largeNeighborhoodSearch(
            problem,
            maxIterations = 200,
            acceptanceTemp = 100.0,
            coolingRate = 0.99,
            random = Random(42)
        )
        assertTrue(result.cost < 20.0)
    }

    @Test
    fun testLNSImprovesOverRandom() {
        val problem = Benchmarks.sphereProblem(5)
        val rng = Random(55)
        val randomCost = problem.evaluate(problem.randomSolution(rng))
        val result = largeNeighborhoodSearch(problem, random = Random(55))
        assertTrue(result.cost <= randomCost)
    }

    @Test
    fun testLNSTSP() {
        val tsp = TSPProblem.random(8, random = Random(1))
        val result = largeNeighborhoodSearch(
            tsp,
            maxIterations = 100,
            destroyDegree = 4,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
        assertTrue(result.cost < Double.MAX_VALUE)
    }
}
