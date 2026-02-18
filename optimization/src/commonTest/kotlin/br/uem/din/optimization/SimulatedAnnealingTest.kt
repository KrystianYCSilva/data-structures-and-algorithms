package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class SimulatedAnnealingTest {

    @Test
    fun testSASphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = simulatedAnnealing(
            problem,
            initialTemp = 500.0,
            coolingRate = 0.99,
            maxIterationsPerTemp = 50,
            random = Random(42)
        )
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testSARastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = simulatedAnnealing(problem, initialTemp = 1000.0, coolingRate = 0.995, random = Random(99))
        assertTrue(result.cost < 30.0)
    }

    @Test
    fun testSAReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = simulatedAnnealing(problem, random = Random(7))
        assertTrue(result.iterations > 0)
        assertTrue(result.evaluations > result.iterations)
    }

    @Test
    fun testSAImprovesOverRandom() {
        val problem = Benchmarks.sphereProblem(5)
        val rng = Random(55)
        val randomCost = problem.evaluate(problem.randomSolution(rng))
        val result = simulatedAnnealing(problem, random = Random(55))
        assertTrue(result.cost <= randomCost)
    }
}
