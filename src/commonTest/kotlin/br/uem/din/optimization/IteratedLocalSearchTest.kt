package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class IteratedLocalSearchTest {

    @Test
    fun testILSSphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = iteratedLocalSearch(problem, maxIterations = 200, random = Random(42))
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testILSRastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = iteratedLocalSearch(problem, maxIterations = 300, random = Random(99))
        assertTrue(result.cost < 30.0)
    }

    @Test
    fun testILSReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = iteratedLocalSearch(problem, maxIterations = 50, random = Random(7))
        assertEquals(50, result.iterations)
        assertTrue(result.evaluations > 50)
    }

    @Test
    fun testILSImprovesOverSingleLocalSearch() {
        val problem = Benchmarks.sphereProblem(5)
        val hcResult = hillClimbing(problem, maxIterations = 500, random = Random(42))
        val ilsResult = iteratedLocalSearch(problem, maxIterations = 100, random = Random(42))
        assertTrue(ilsResult.cost <= hcResult.cost * 2.0)
    }
}
