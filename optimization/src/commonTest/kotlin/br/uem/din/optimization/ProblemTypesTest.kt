package br.uem.din.optimization

import kotlin.random.Random
import kotlin.test.*

class ProblemTypesTest {

    @Test
    fun testBinaryProblemRandomSolution() {
        val bp = BinaryProblem(10, { x -> x.count { it }.toDouble() })
        val sol = bp.randomSolution(Random(42))
        assertEquals(10, sol.size)
    }

    @Test
    fun testBinaryProblemNeighborFlipsOneBit() {
        val bp = BinaryProblem(10, { 0.0 })
        val sol = BooleanArray(10) { false }
        val neighbor = bp.neighbor(sol, Random(42))
        var diffs = 0
        for (i in sol.indices) if (sol[i] != neighbor[i]) diffs++
        assertEquals(1, diffs)
    }

    @Test
    fun testBinaryProblemCopy() {
        val bp = BinaryProblem(5, { 0.0 })
        val sol = booleanArrayOf(true, false, true, false, true)
        val copy = bp.copy(sol)
        assertContentEquals(sol, copy)
        copy[0] = false
        assertTrue(sol[0])
    }

    @Test
    fun testBinaryProblemWithHillClimbing() {
        val bp = BinaryProblem(20, { x -> x.count { it }.toDouble() })
        val result = hillClimbing(bp, maxIterations = 200, random = Random(42))
        assertTrue(result.cost > 10.0)
    }

    @Test
    fun testPermutationProblemRandomSolution() {
        val pp = PermutationProblem(8, { 0.0 })
        val sol = pp.randomSolution(Random(42))
        assertEquals(8, sol.size)
        assertEquals((0 until 8).toSet(), sol.toSet())
    }

    @Test
    fun testPermutationProblemNeighborSwaps() {
        val pp = PermutationProblem(8, { 0.0 })
        val sol = IntArray(8) { it }
        val neighbor = pp.neighbor(sol, Random(42))
        assertEquals((0 until 8).toSet(), neighbor.toSet())
        var diffs = 0
        for (i in sol.indices) if (sol[i] != neighbor[i]) diffs++
        assertEquals(2, diffs)
    }

    @Test
    fun testPermutationProblemCopy() {
        val pp = PermutationProblem(5, { 0.0 })
        val sol = intArrayOf(4, 3, 2, 1, 0)
        val copy = pp.copy(sol)
        assertContentEquals(sol, copy)
        copy[0] = 0
        assertEquals(4, sol[0])
    }

    @Test
    fun testPermutationProblemWithSA() {
        val pp = PermutationProblem(10, { perm ->
            var cost = 0.0
            for (i in perm.indices) cost += kotlin.math.abs(perm[i] - i).toDouble()
            cost
        })
        val result = simulatedAnnealing(pp, random = Random(42))
        assertTrue(result.cost >= 0.0)
    }

    @Test
    fun testIntegerProblemRandomSolution() {
        val ip = IntegerProblem(
            lowerBounds = intArrayOf(0, 0, 0),
            upperBounds = intArrayOf(10, 10, 10),
            objectiveFunction = { x -> x.sum().toDouble() }
        )
        val sol = ip.randomSolution(Random(42))
        assertEquals(3, sol.size)
        for (i in sol.indices) {
            assertTrue(sol[i] in 0..10)
        }
    }

    @Test
    fun testIntegerProblemNeighborWithinBounds() {
        val ip = IntegerProblem(
            lowerBounds = intArrayOf(0, 0),
            upperBounds = intArrayOf(5, 5),
            objectiveFunction = { 0.0 }
        )
        val sol = intArrayOf(0, 0)
        repeat(50) {
            val neighbor = ip.neighbor(sol, Random(it))
            for (i in neighbor.indices) {
                assertTrue(neighbor[i] in 0..5)
            }
        }
    }

    @Test
    fun testIntegerProblemWithTabuSearch() {
        val ip = IntegerProblem(
            lowerBounds = IntArray(5) { 0 },
            upperBounds = IntArray(5) { 20 },
            objectiveFunction = { x -> x.sumOf { (it - 10) * (it - 10) }.toDouble() }
        )
        val result = tabuSearch(ip, maxIterations = 500, random = Random(42))
        assertTrue(result.cost < 50.0)
    }

    @Test
    fun testUniformCrossoverPreservesSize() {
        val p1 = BooleanArray(10) { true }
        val p2 = BooleanArray(10) { false }
        val child = uniformCrossover(p1, p2, Random(42))
        assertEquals(10, child.size)
    }

    @Test
    fun testUniformCrossoverMixesGenes() {
        val p1 = BooleanArray(100) { true }
        val p2 = BooleanArray(100) { false }
        val child = uniformCrossover(p1, p2, Random(42))
        val trueCount = child.count { it }
        assertTrue(trueCount > 10)
        assertTrue(trueCount < 90)
    }

    @Test
    fun testPmxCrossoverPreservesPermutation() {
        val p1 = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        val p2 = intArrayOf(7, 6, 5, 4, 3, 2, 1, 0)
        val child = pmxCrossover(p1, p2, Random(42))
        assertEquals(8, child.size)
        assertEquals((0..7).toSet(), child.toSet())
    }

    @Test
    fun testPmxCrossoverDifferentSeeds() {
        val p1 = intArrayOf(0, 1, 2, 3, 4)
        val p2 = intArrayOf(4, 3, 2, 1, 0)
        repeat(10) { seed ->
            val child = pmxCrossover(p1, p2, Random(seed))
            assertEquals((0..4).toSet(), child.toSet())
        }
    }

    @Test
    fun testBoundedVectorProblemWithPSO() {
        val custom = object : BoundedVectorProblem {
            override val dimensions: Int = 3
            override val lowerBound: Double = -10.0
            override val upperBound: Double = 10.0
            override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
            override fun evaluate(solution: DoubleArray): Double = solution.sumOf { it * it }
            override fun randomSolution(random: Random): DoubleArray =
                DoubleArray(dimensions) { lowerBound + random.nextDouble() * (upperBound - lowerBound) }
            override fun neighbor(solution: DoubleArray, random: Random): DoubleArray {
                val r = solution.copyOf()
                val idx = random.nextInt(dimensions)
                r[idx] = (r[idx] + random.nextDouble() * 2.0 - 1.0).coerceIn(lowerBound, upperBound)
                return r
            }
            override fun copy(solution: DoubleArray): DoubleArray = solution.copyOf()
        }
        val result = particleSwarmOptimization(custom, swarmSize = 20, iterations = 100, random = Random(42))
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testBoundedVectorProblemWithDE() {
        val custom = object : BoundedVectorProblem {
            override val dimensions: Int = 3
            override val lowerBound: Double = -10.0
            override val upperBound: Double = 10.0
            override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
            override fun evaluate(solution: DoubleArray): Double = solution.sumOf { it * it }
            override fun randomSolution(random: Random): DoubleArray =
                DoubleArray(dimensions) { lowerBound + random.nextDouble() * (upperBound - lowerBound) }
            override fun neighbor(solution: DoubleArray, random: Random): DoubleArray {
                val r = solution.copyOf()
                val idx = random.nextInt(dimensions)
                r[idx] = (r[idx] + random.nextDouble() * 2.0 - 1.0).coerceIn(lowerBound, upperBound)
                return r
            }
            override fun copy(solution: DoubleArray): DoubleArray = solution.copyOf()
        }
        val result = differentialEvolution(custom, populationSize = 20, generations = 100, random = Random(42))
        assertTrue(result.cost < 5.0)
    }

    @Test
    fun testCostMatrixProblemWithACO() {
        val matrix = arrayOf(
            doubleArrayOf(0.0, 10.0, 15.0, 20.0),
            doubleArrayOf(10.0, 0.0, 35.0, 25.0),
            doubleArrayOf(15.0, 35.0, 0.0, 30.0),
            doubleArrayOf(20.0, 25.0, 30.0, 0.0)
        )
        val custom = object : CostMatrixProblem {
            override val size: Int = 4
            override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
            override fun cost(i: Int, j: Int): Double = matrix[i][j]
            override fun evaluate(solution: IntArray): Double {
                var total = 0.0
                for (k in 0 until solution.size - 1) total += matrix[solution[k]][solution[k + 1]]
                total += matrix[solution.last()][solution.first()]
                return total
            }
            override fun randomSolution(random: Random): IntArray {
                val perm = IntArray(4) { it }
                for (i in 3 downTo 1) {
                    val j = random.nextInt(i + 1)
                    val tmp = perm[i]; perm[i] = perm[j]; perm[j] = tmp
                }
                return perm
            }
            override fun neighbor(solution: IntArray, random: Random): IntArray {
                val r = solution.copyOf()
                val a = random.nextInt(4)
                var b = random.nextInt(4)
                while (b == a) b = random.nextInt(4)
                val tmp = r[a]; r[a] = r[b]; r[b] = tmp
                return r
            }
            override fun copy(solution: IntArray): IntArray = solution.copyOf()
        }
        val result = antColonyOptimization(custom, antCount = 10, iterations = 50, random = Random(42))
        assertTrue(result.cost > 0.0)
        assertEquals((0..3).toSet(), result.solution.toSet())
    }
}
