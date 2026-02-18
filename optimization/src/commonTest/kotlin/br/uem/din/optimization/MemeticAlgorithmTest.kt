package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class MemeticAlgorithmTest {

    @Test
    fun testMASphereContinuous() {
        val problem = Benchmarks.sphereProblem(5)
        val result = memeticAlgorithm(
            problem,
            crossover = ::singlePointCrossover,
            populationSize = 30,
            generations = 100,
            random = Random(42)
        )
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testMATSP() {
        val tsp = TSPProblem.random(10, random = Random(1))
        val result = memeticAlgorithm(
            tsp,
            crossover = ::orderCrossover,
            populationSize = 20,
            generations = 50,
            mutationRate = 0.2,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
        assertTrue(result.cost < Double.MAX_VALUE)
        assertEquals(10, result.solution.toSet().size)
    }

    @Test
    fun testMAReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = memeticAlgorithm(
            problem,
            crossover = ::singlePointCrossover,
            populationSize = 10,
            generations = 5,
            random = Random(7)
        )
        assertEquals(5, result.iterations)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testMABetterThanPureGA() {
        val problem = Benchmarks.sphereProblem(5)
        val gaResult = geneticAlgorithm(
            problem,
            crossover = ::singlePointCrossover,
            populationSize = 30,
            generations = 50,
            random = Random(42)
        )
        val maResult = memeticAlgorithm(
            problem,
            crossover = ::singlePointCrossover,
            populationSize = 30,
            generations = 50,
            random = Random(42)
        )
        assertTrue(maResult.cost <= gaResult.cost)
    }

    @Test
    fun testMAImprovesOverRandom() {
        val problem = Benchmarks.sphereProblem(5)
        val rng = Random(55)
        val randomCost = problem.evaluate(problem.randomSolution(rng))
        val result = memeticAlgorithm(
            problem,
            crossover = ::singlePointCrossover,
            random = Random(55)
        )
        assertTrue(result.cost <= randomCost)
    }
}
