package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class HillClimbingTest {

    @Test
    fun testHillClimbingSphereBestImprovement() {
        val problem = Benchmarks.sphereProblem(5)
        val result = hillClimbing(
            problem,
            strategy = HillClimbingStrategy.BEST_IMPROVEMENT,
            maxIterations = 5_000,
            neighborsPerStep = 30,
            random = Random(42)
        )
        assertTrue(result.cost < 10.0)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testHillClimbingSphereFirstImprovement() {
        val problem = Benchmarks.sphereProblem(5)
        val result = hillClimbing(
            problem,
            strategy = HillClimbingStrategy.FIRST_IMPROVEMENT,
            maxIterations = 5_000,
            neighborsPerStep = 30,
            random = Random(42)
        )
        assertTrue(result.cost < 10.0)
    }

    @Test
    fun testHillClimbingReturnsPositiveIterations() {
        val problem = Benchmarks.sphereProblem(2)
        val result = hillClimbing(problem, maxIterations = 100, random = Random(123))
        assertTrue(result.iterations > 0)
    }

    @Test
    fun testHillClimbingMinimizes() {
        val problem = Benchmarks.sphereProblem(3)
        val initial = problem.evaluate(problem.randomSolution(Random(1)))
        val result = hillClimbing(problem, maxIterations = 2_000, random = Random(1))
        assertTrue(result.cost <= initial)
    }
}
