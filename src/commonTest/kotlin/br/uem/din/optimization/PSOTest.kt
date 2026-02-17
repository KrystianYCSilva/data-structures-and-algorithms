package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class PSOTest {

    @Test
    fun testPSOSphere() {
        val problem = Benchmarks.sphereProblem(5)
        val result = particleSwarmOptimization(
            problem, swarmSize = 30, iterations = 300, random = Random(42)
        )
        assertTrue(result.cost < 1.0)
    }

    @Test
    fun testPSORastrigin() {
        val problem = Benchmarks.rastriginProblem(3)
        val result = particleSwarmOptimization(
            problem, swarmSize = 40, iterations = 500, random = Random(99)
        )
        assertTrue(result.cost < 20.0)
    }

    @Test
    fun testPSOAckley() {
        val problem = Benchmarks.ackleyProblem(3)
        val result = particleSwarmOptimization(
            problem, swarmSize = 40, iterations = 500, random = Random(42)
        )
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testPSOReturnsValidDimensions() {
        val problem = Benchmarks.sphereProblem(10)
        val result = particleSwarmOptimization(
            problem, swarmSize = 20, iterations = 50, random = Random(7)
        )
        assertEquals(10, result.solution.size)
    }

    @Test
    fun testPSOEvaluationCount() {
        val problem = Benchmarks.sphereProblem(2)
        val result = particleSwarmOptimization(
            problem, swarmSize = 10, iterations = 20, random = Random(7)
        )
        assertEquals(10 + 10 * 20, result.evaluations)
    }
}
