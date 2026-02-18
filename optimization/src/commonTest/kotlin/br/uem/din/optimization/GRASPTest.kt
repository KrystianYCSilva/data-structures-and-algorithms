package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class GRASPTest {

    @Test
    fun testGRASPSphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = grasp(problem, maxIterations = 100, random = Random(42))
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testGRASPRastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = grasp(problem, maxIterations = 200, random = Random(99))
        assertTrue(result.cost < 30.0)
    }

    @Test
    fun testGRASPReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = grasp(problem, maxIterations = 20, random = Random(7))
        assertEquals(20, result.iterations)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testGRASPWithCustomConstructor() {
        val problem = Benchmarks.sphereProblem(3)
        val result = grasp(
            problem,
            greedyRandomized = { _, rng -> problem.randomSolution(rng) },
            maxIterations = 50,
            random = Random(42)
        )
        assertTrue(result.cost < 20.0)
    }
}
