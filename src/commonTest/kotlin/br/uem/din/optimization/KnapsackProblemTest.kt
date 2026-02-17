package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class KnapsackProblemTest {

    private fun smallKnapsack(): KnapsackProblem =
        KnapsackProblem(
            weights = intArrayOf(10, 20, 30, 40, 50),
            values = intArrayOf(60, 100, 120, 160, 200),
            capacity = 50
        )

    @Test
    fun testEvaluateFeasibleSolution() {
        val kp = smallKnapsack()
        val solution = booleanArrayOf(true, true, false, false, false)
        assertEquals(160.0, kp.evaluate(solution))
    }

    @Test
    fun testEvaluateInfeasibleSolutionPenalized() {
        val kp = smallKnapsack()
        val all = BooleanArray(5) { true }
        val cost = kp.evaluate(all)
        assertTrue(cost < kp.values.sum().toDouble())
    }

    @Test
    fun testRandomSolutionSize() {
        val kp = smallKnapsack()
        val sol = kp.randomSolution(Random(42))
        assertEquals(5, sol.size)
    }

    @Test
    fun testNeighborFlipsOneBit() {
        val kp = smallKnapsack()
        val sol = BooleanArray(5) { false }
        val neighbor = kp.neighbor(sol, Random(42))
        var diffs = 0
        for (i in sol.indices) if (sol[i] != neighbor[i]) diffs++
        assertEquals(1, diffs)
    }

    @Test
    fun testRandomFactoryGeneratesValidInstance() {
        val kp = KnapsackProblem.random(20, random = Random(42))
        assertEquals(20, kp.n)
        assertTrue(kp.capacity > 0)
    }

    @Test
    fun testHillClimbingKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = hillClimbing(kp, maxIterations = 500, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testSimulatedAnnealingKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = simulatedAnnealing(kp, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testTabuSearchKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = tabuSearch(kp, maxIterations = 500, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testGeneticAlgorithmKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = geneticAlgorithm(
            kp,
            crossover = ::uniformCrossover,
            populationSize = 30,
            generations = 100,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testILSKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = iteratedLocalSearch(kp, maxIterations = 100, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testGRASPKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = grasp(kp, maxIterations = 100, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testVNSKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = variableNeighborhoodSearch(kp, maxIterations = 50, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testMemeticAlgorithmKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = memeticAlgorithm(
            kp,
            crossover = ::uniformCrossover,
            populationSize = 20,
            generations = 50,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testLNSKnapsack() {
        val kp = KnapsackProblem.random(15, random = Random(1))
        val result = largeNeighborhoodSearch(kp, maxIterations = 100, random = Random(42))
        assertTrue(result.cost > 0.0)
    }
}
