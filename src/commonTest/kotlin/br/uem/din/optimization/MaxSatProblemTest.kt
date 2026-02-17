package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class MaxSatProblemTest {

    private fun smallSat(): MaxSatProblem = MaxSatProblem(
        numVariables = 4,
        clauses = listOf(
            intArrayOf(1, 2, -3),
            intArrayOf(-1, 3, 4),
            intArrayOf(2, -3, -4),
            intArrayOf(1, -2, 4),
            intArrayOf(-1, -2, 3)
        )
    )

    @Test
    fun testEvaluateAllTrue() {
        val sat = smallSat()
        val allTrue = BooleanArray(4) { true }
        val satisfied = sat.evaluate(allTrue)
        assertTrue(satisfied >= 1.0)
        assertTrue(satisfied <= 5.0)
    }

    @Test
    fun testEvaluateCountsCorrectly() {
        val sat = MaxSatProblem(
            numVariables = 2,
            clauses = listOf(
                intArrayOf(1, 2),
                intArrayOf(-1, -2)
            )
        )
        val tt = booleanArrayOf(true, true)
        assertEquals(1.0, sat.evaluate(tt))
        val ff = booleanArrayOf(false, false)
        assertEquals(1.0, sat.evaluate(ff))
        val tf = booleanArrayOf(true, false)
        assertEquals(2.0, sat.evaluate(tf))
    }

    @Test
    fun testRandomSolutionSize() {
        val sat = smallSat()
        val sol = sat.randomSolution(Random(42))
        assertEquals(4, sol.size)
    }

    @Test
    fun testNeighborFlipsOneBit() {
        val sat = smallSat()
        val sol = BooleanArray(4) { false }
        val neighbor = sat.neighbor(sol, Random(42))
        var diffs = 0
        for (i in sol.indices) if (sol[i] != neighbor[i]) diffs++
        assertEquals(1, diffs)
    }

    @Test
    fun testRandom3SATFactory() {
        val sat = MaxSatProblem.random3SAT(10, 30, Random(42))
        assertEquals(10, sat.numVariables)
        assertEquals(30, sat.clauses.size)
        for (clause in sat.clauses) {
            assertEquals(3, clause.size)
        }
    }

    @Test
    fun testHillClimbingMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = hillClimbing(sat, maxIterations = 500, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testSimulatedAnnealingMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = simulatedAnnealing(
            sat,
            initialTemp = 100.0,
            coolingRate = 0.95,
            minTemp = 1e-3,
            maxIterationsPerTemp = 20,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testTabuSearchMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = tabuSearch(sat, maxIterations = 500, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testGeneticAlgorithmMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = geneticAlgorithm(
            sat,
            crossover = ::uniformCrossover,
            populationSize = 30,
            generations = 100,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testILSMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = iteratedLocalSearch(sat, maxIterations = 100, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testGRASPMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = grasp(sat, maxIterations = 100, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testVNSMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = variableNeighborhoodSearch(sat, maxIterations = 50, random = Random(42))
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testMemeticAlgorithmMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = memeticAlgorithm(
            sat,
            crossover = ::uniformCrossover,
            populationSize = 20,
            generations = 50,
            random = Random(42)
        )
        assertTrue(result.cost > 0.0)
    }

    @Test
    fun testLNSMaxSat() {
        val sat = MaxSatProblem.random3SAT(15, 50, Random(1))
        val result = largeNeighborhoodSearch(sat, maxIterations = 100, random = Random(42))
        assertTrue(result.cost > 0.0)
    }
}

