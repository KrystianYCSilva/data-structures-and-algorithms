package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class TabuSearchTest {

    @Test
    fun testTabuSearchSphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = tabuSearch(problem, tabuTenure = 15, maxIterations = 2_000, random = Random(42))
        assertTrue(result.cost < 10.0)
    }

    @Test
    fun testTabuSearchRastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = tabuSearch(problem, maxIterations = 3_000, random = Random(99))
        assertTrue(result.cost < 50.0)
    }

    @Test
    fun testTabuSearchReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = tabuSearch(problem, maxIterations = 100, random = Random(7))
        assertEquals(100, result.iterations)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testTabuSearchImprovesOverRandom() {
        val problem = Benchmarks.sphereProblem(5)
        val rng = Random(55)
        val randomCost = problem.evaluate(problem.randomSolution(rng))
        val result = tabuSearch(problem, random = Random(55))
        assertTrue(result.cost <= randomCost)
    }
}
