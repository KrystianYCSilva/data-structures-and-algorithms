package br.uem.din.optimization

import kotlin.random.Random

/**
 * Direção de otimização.
 */
public enum class OptimizationDirection {
    MINIMIZE,
    MAXIMIZE
}

/**
 * Resultado de uma execução de heurística de otimização.
 *
 * @param T tipo da solução.
 * @param solution a melhor solução encontrada.
 * @param cost o valor da função objetivo para a melhor solução.
 * @param iterations número de iterações executadas.
 * @param evaluations número de avaliações da função objetivo.
 */
public data class OptResult<T>(
    val solution: T,
    val cost: Double,
    val iterations: Int,
    val evaluations: Int
)

/**
 * Define um problema de otimização combinatória genérico.
 *
 * O framework de heurísticas opera sobre esta interface, permitindo
 * reutilização de algoritmos para diferentes domínios (TSP, Knapsack, Scheduling, etc.).
 *
 * @param T tipo da representação da solução.
 */
public interface OptimizationProblem<T> {
    public val direction: OptimizationDirection
    public fun evaluate(solution: T): Double
    public fun randomSolution(random: Random): T
    public fun neighbor(solution: T, random: Random): T
    public fun copy(solution: T): T
}

/**
 * Verifica se [candidate] é melhor que [current] considerando a direção de otimização.
 */
public fun OptimizationProblem<*>.isBetter(candidate: Double, current: Double): Boolean =
    when (direction) {
        OptimizationDirection.MINIMIZE -> candidate < current
        OptimizationDirection.MAXIMIZE -> candidate > current
    }

/**
 * Retorna o pior valor possível para a direção de otimização.
 */
public fun OptimizationProblem<*>.worstValue(): Double =
    when (direction) {
        OptimizationDirection.MINIMIZE -> Double.POSITIVE_INFINITY
        OptimizationDirection.MAXIMIZE -> Double.NEGATIVE_INFINITY
    }

/**
 * Define um problema de otimização contínua em espaço real N-dimensional.
 *
 * @param dimensions número de dimensões.
 * @param lowerBound limite inferior do espaço de busca.
 * @param upperBound limite superior do espaço de busca.
 * @param objectiveFunction função objetivo f(x) → ℝ.
 */
public class ContinuousProblem(
    public val dimensions: Int,
    public val lowerBound: Double,
    public val upperBound: Double,
    private val objectiveFunction: (DoubleArray) -> Double,
    override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
) : OptimizationProblem<DoubleArray> {

    override fun evaluate(solution: DoubleArray): Double = objectiveFunction(solution)

    override fun randomSolution(random: Random): DoubleArray =
        DoubleArray(dimensions) { lowerBound + random.nextDouble() * (upperBound - lowerBound) }

    override fun neighbor(solution: DoubleArray, random: Random): DoubleArray {
        val result = solution.copyOf()
        val index = random.nextInt(dimensions)
        val range = (upperBound - lowerBound) * 0.1
        result[index] = (result[index] + random.nextDouble() * 2.0 * range - range)
            .coerceIn(lowerBound, upperBound)
        return result
    }

    override fun copy(solution: DoubleArray): DoubleArray = solution.copyOf()
}
