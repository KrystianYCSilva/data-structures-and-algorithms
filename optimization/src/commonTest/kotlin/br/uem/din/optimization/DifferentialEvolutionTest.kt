package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class DifferentialEvolutionTest {

    @Test
    fun testDESphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = differentialEvolution(
            problem,
            populationSize = 30,
            generations = 300,
            random = Random(42)
        )
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testDERastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = differentialEvolution(
            problem,
            populationSize = 40,
            generations = 400,
            random = Random(99)
        )
        assertTrue(result.cost < 30.0)
    }

    @Test
    fun testDEReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = differentialEvolution(
            problem,
            populationSize = 10,
            generations = 5,
            random = Random(7)
        )
        assertEquals(5, result.iterations)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testDESolutionDimensions() {
        val problem = Benchmarks.sphereProblem(8)
        val result = differentialEvolution(
            problem,
            populationSize = 15,
            generations = 10,
            random = Random(1)
        )
        assertEquals(8, result.solution.size)
    }

    @Test
    fun testDEImprovesOverRandom() {
        val problem = Benchmarks.sphereProblem(5)
        val rng = Random(55)
        val randomCost = problem.evaluate(problem.randomSolution(rng))
        val result = differentialEvolution(problem, random = Random(55))
        assertTrue(result.cost <= randomCost)
    }
}
