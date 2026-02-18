package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class JobSchedulingProblemTest {

    private fun smallScheduling(): JobSchedulingProblem =
        JobSchedulingProblem(
            processingTimes = intArrayOf(3, 5, 2, 7, 4),
            dueDates = intArrayOf(10, 8, 12, 15, 6),
            weights = intArrayOf(1, 2, 1, 3, 2)
        )

    @Test
    fun testEvaluateNoTardiness() {
        val sp = JobSchedulingProblem(
            processingTimes = intArrayOf(2, 3),
            dueDates = intArrayOf(10, 20),
            weights = intArrayOf(1, 1)
        )
        val sol = intArrayOf(0, 1)
        assertEquals(0.0, sp.evaluate(sol))
    }

    @Test
    fun testEvaluateWithTardiness() {
        val sp = smallScheduling()
        val sol = intArrayOf(0, 1, 2, 3, 4)
        val cost = sp.evaluate(sol)
        assertTrue(cost >= 0.0)
    }

    @Test
    fun testRandomSolutionIsValidPermutation() {
        val sp = smallScheduling()
        val sol = sp.randomSolution(Random(42))
        assertEquals(5, sol.size)
        assertEquals((0 until 5).toSet(), sol.toSet())
    }

    @Test
    fun testNeighborIsValidPermutation() {
        val sp = smallScheduling()
        val sol = intArrayOf(0, 1, 2, 3, 4)
        val neighbor = sp.neighbor(sol, Random(42))
        assertEquals((0 until 5).toSet(), neighbor.toSet())
    }

    @Test
    fun testRandomFactoryGeneratesValidInstance() {
        val sp = JobSchedulingProblem.random(10, random = Random(42))
        assertEquals(10, sp.n)
    }

    @Test
    fun testHillClimbingScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = hillClimbing(sp, maxIterations = 500, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testSimulatedAnnealingScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = simulatedAnnealing(sp, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testTabuSearchScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = tabuSearch(sp, maxIterations = 500, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testGeneticAlgorithmScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = geneticAlgorithm(
            sp,
            crossover = ::orderCrossover,
            populationSize = 30,
            generations = 100,
            random = Random(42)
        )
        assertTrue(result.cost >= 0.0)
        assertEquals((0 until 10).toSet(), result.solution.toSet())
    }

    @Test
    fun testILSScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = iteratedLocalSearch(sp, maxIterations = 100, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testGRASPScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = grasp(sp, maxIterations = 100, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testVNSScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = variableNeighborhoodSearch(sp, maxIterations = 50, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testMemeticAlgorithmScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = memeticAlgorithm(
            sp,
            crossover = ::orderCrossover,
            populationSize = 20,
            generations = 50,
            random = Random(42)
        )
        assertTrue(result.cost >= 0.0)
        assertEquals((0 until 10).toSet(), result.solution.toSet())
    }

    @Test
    fun testLNSScheduling() {
        val sp = JobSchedulingProblem.random(10, random = Random(1))
        val result = largeNeighborhoodSearch(sp, maxIterations = 100, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testACOScheduling() {
        val sp = JobSchedulingProblem.random(8, random = Random(1))
        val costMatrix = object : CostMatrixProblem {
            override val size: Int = sp.n
            override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
            override fun cost(i: Int, j: Int): Double =
                (sp.processingTimes[j] + (sp.dueDates[j] - sp.dueDates[i]).coerceAtLeast(0)).toDouble()
            override fun evaluate(solution: IntArray): Double = sp.evaluate(solution)
            override fun randomSolution(random: Random): IntArray = sp.randomSolution(random)
            override fun neighbor(solution: IntArray, random: Random): IntArray = sp.neighbor(solution, random)
            override fun copy(solution: IntArray): IntArray = solution.copyOf()
        }
        val result = antColonyOptimization(costMatrix, antCount = 10, iterations = 50, random = Random(42))
        assertTrue(result.cost >= 0.0)
        assertEquals((0 until 8).toSet(), result.solution.toSet())
    }
}
