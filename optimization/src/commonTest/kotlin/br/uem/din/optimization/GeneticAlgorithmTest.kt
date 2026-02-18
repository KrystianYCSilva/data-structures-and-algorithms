package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class GeneticAlgorithmTest {

    @Test
    fun testGASphereContinuous() {
        val problem = Benchmarks.sphereProblem(5)
        val result = geneticAlgorithm(
            problem,
            crossover = ::singlePointCrossover,
            populationSize = 50,
            generations = 200,
            random = Random(42)
        )
        assertTrue(result.cost < 10.0)
    }

    @Test
    fun testGATSP() {
        val tsp = TSPProblem.random(10, random = Random(1))
        val result = geneticAlgorithm(
            tsp,
            crossover = ::orderCrossover,
            populationSize = 40,
            generations = 150,
            mutationRate = 0.2,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
        assertTrue(result.cost < Double.MAX_VALUE)
        assertEquals(10, result.solution.toSet().size)
    }

    @Test
    fun testGAReturnsValidResult() {
        val problem = Benchmarks.sphereProblem(2)
        val result = geneticAlgorithm(
            problem,
            crossover = ::singlePointCrossover,
            populationSize = 20,
            generations = 10,
            random = Random(7)
        )
        assertEquals(10, result.iterations)
        assertTrue(result.evaluations > 0)
    }

    @Test
    fun testOrderCrossoverPreservesAllGenes() {
        val parent1 = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        val parent2 = intArrayOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
        val child = orderCrossover(parent1, parent2, Random(42))
        assertEquals(10, child.toSet().size)
        assertEquals((0..9).toSet(), child.toSet())
    }

    @Test
    fun testSinglePointCrossoverDimensions() {
        val p1 = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
        val p2 = doubleArrayOf(5.0, 6.0, 7.0, 8.0)
        val child = singlePointCrossover(p1, p2, Random(42))
        assertEquals(4, child.size)
    }
}
