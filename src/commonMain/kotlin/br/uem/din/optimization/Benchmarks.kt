package br.uem.din.optimization

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sqrt

/**
 * Funções de benchmark clássicas para otimização contínua.
 *
 * Referência: Jamil, M. & Yang, X.-S. "A Literature Survey of Benchmark Functions
 * For Global Optimization Problems" (2013).
 */
public object Benchmarks {

    /**
     * Função Sphere: f(x) = Σ xᵢ².
     * Mínimo global: f(0,...,0) = 0.
     */
    public fun sphere(x: DoubleArray): Double = x.sumOf { it * it }

    /**
     * Função de Rastrigin: f(x) = 10n + Σ [xᵢ² - 10·cos(2π·xᵢ)].
     * Mínimo global: f(0,...,0) = 0.
     * Altamente multimodal.
     *
     * Referência: Rastrigin, L. A. (1974).
     */
    public fun rastrigin(x: DoubleArray): Double {
        val n = x.size
        return 10.0 * n + x.sumOf { it * it - 10.0 * cos(2.0 * PI * it) }
    }

    /**
     * Função de Rosenbrock (Banana): f(x) = Σ [100·(xᵢ₊₁ - xᵢ²)² + (1 - xᵢ)²].
     * Mínimo global: f(1,...,1) = 0.
     * Vale estreito e curvo.
     *
     * Referência: Rosenbrock, H. H. (1960).
     */
    public fun rosenbrock(x: DoubleArray): Double {
        var sum = 0.0
        for (i in 0 until x.size - 1) {
            val xi = x[i]
            val xi1 = x[i + 1]
            sum += 100.0 * (xi1 - xi * xi) * (xi1 - xi * xi) + (1.0 - xi) * (1.0 - xi)
        }
        return sum
    }

    /**
     * Função de Ackley: f(x) = -20·exp(-0.2·√(Σxᵢ²/n)) - exp(Σcos(2π·xᵢ)/n) + 20 + e.
     * Mínimo global: f(0,...,0) = 0.
     * Quase plana com muitos mínimos locais.
     *
     * Referência: Ackley, D. H. (1987).
     */
    public fun ackley(x: DoubleArray): Double {
        val n = x.size.toDouble()
        val sumSq = x.sumOf { it * it }
        val sumCos = x.sumOf { cos(2.0 * PI * it) }
        return -20.0 * exp(-0.2 * sqrt(sumSq / n)) - exp(sumCos / n) + 20.0 + kotlin.math.E
    }

    /**
     * Função de Schwefel: f(x) = 418.9829·n - Σ xᵢ·sin(√|xᵢ|).
     * Mínimo global: f(420.9687,...,420.9687) ≈ 0.
     * Mínimo global geometricamente distante do segundo melhor.
     *
     * Referência: Schwefel, H.-P. (1981).
     */
    public fun schwefel(x: DoubleArray): Double {
        val n = x.size
        return 418.9829 * n - x.sumOf { it * kotlin.math.sin(sqrt(abs(it))) }
    }

    /**
     * Cria um [ContinuousProblem] de Sphere com [dimensions] dimensões.
     */
    public fun sphereProblem(dimensions: Int): ContinuousProblem =
        ContinuousProblem(dimensions, -5.12, 5.12, ::sphere)

    /**
     * Cria um [ContinuousProblem] de Rastrigin com [dimensions] dimensões.
     */
    public fun rastriginProblem(dimensions: Int): ContinuousProblem =
        ContinuousProblem(dimensions, -5.12, 5.12, ::rastrigin)

    /**
     * Cria um [ContinuousProblem] de Rosenbrock com [dimensions] dimensões.
     */
    public fun rosenbrockProblem(dimensions: Int): ContinuousProblem =
        ContinuousProblem(dimensions, -5.0, 10.0, ::rosenbrock)

    /**
     * Cria um [ContinuousProblem] de Ackley com [dimensions] dimensões.
     */
    public fun ackleyProblem(dimensions: Int): ContinuousProblem =
        ContinuousProblem(dimensions, -32.768, 32.768, ::ackley)

    /**
     * Cria um [ContinuousProblem] de Schwefel com [dimensions] dimensões.
     */
    public fun schwefelProblem(dimensions: Int): ContinuousProblem =
        ContinuousProblem(dimensions, -500.0, 500.0, ::schwefel)
}
