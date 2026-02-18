package br.uem.din.optimization

import kotlin.math.abs
import kotlin.test.*

class BenchmarksTest {

    @Test
    fun testSphereGlobalMinimum() {
        assertEquals(0.0, Benchmarks.sphere(doubleArrayOf(0.0, 0.0, 0.0)), 1e-10)
    }

    @Test
    fun testSpherePositiveValue() {
        assertTrue(Benchmarks.sphere(doubleArrayOf(1.0, 2.0, 3.0)) > 0.0)
    }

    @Test
    fun testRastriginGlobalMinimum() {
        assertEquals(0.0, Benchmarks.rastrigin(doubleArrayOf(0.0, 0.0)), 1e-10)
    }

    @Test
    fun testRastriginPositive() {
        assertTrue(Benchmarks.rastrigin(doubleArrayOf(1.0, 1.0)) > 0.0)
    }

    @Test
    fun testRosenbrockGlobalMinimum() {
        assertEquals(0.0, Benchmarks.rosenbrock(doubleArrayOf(1.0, 1.0)), 1e-10)
    }

    @Test
    fun testRosenbrockPositive() {
        assertTrue(Benchmarks.rosenbrock(doubleArrayOf(0.0, 0.0)) > 0.0)
    }

    @Test
    fun testAckleyGlobalMinimum() {
        assertTrue(abs(Benchmarks.ackley(doubleArrayOf(0.0, 0.0))) < 1e-10)
    }

    @Test
    fun testAckleyPositive() {
        assertTrue(Benchmarks.ackley(doubleArrayOf(1.0, 1.0)) > 0.0)
    }

    @Test
    fun testSchwefelNonNegative() {
        assertTrue(Benchmarks.schwefel(doubleArrayOf(420.9687, 420.9687)) < 1.0)
    }

    @Test
    fun testSphereProblemFactory() {
        val p = Benchmarks.sphereProblem(3)
        assertEquals(3, p.dimensions)
        assertEquals(OptimizationDirection.MINIMIZE, p.direction)
    }
}
