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
 * Define um problema de otimização genérico.
 *
 * O framework de heurísticas opera sobre esta interface, permitindo
 * reutilização de algoritmos para diferentes domínios e representações
 * (vetores reais, binários, permutações, grafos, matrizes, árvores, etc.).
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
 * Problema de otimização em espaço vetorial real N-dimensional com limites.
 *
 * Interface que expõe as propriedades necessárias para algoritmos que operam
 * com aritmética vetorial (PSO, DE): dimensões e limites do espaço de busca.
 * Qualquer [OptimizationProblem]<[DoubleArray]> que exponha estas propriedades
 * pode ser otimizado por PSO e DE.
 *
 * @see ContinuousProblem implementação padrão.
 */
public interface BoundedVectorProblem : OptimizationProblem<DoubleArray> {
    public val dimensions: Int
    public val lowerBound: Double
    public val upperBound: Double
}

/**
 * Problema de otimização que possui uma matriz de custos entre pares de elementos.
 *
 * Interface para algoritmos que operam com feromônio sobre arestas (ACO).
 * Qualquer problema que possua uma noção de "custo entre i e j" e soluções
 * representadas como permutações pode ser otimizado pelo ACO.
 *
 * @see TSPProblem implementação para o Caixeiro Viajante.
 */
public interface CostMatrixProblem : OptimizationProblem<IntArray> {
    public val size: Int
    public fun cost(i: Int, j: Int): Double
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
    override val dimensions: Int,
    override val lowerBound: Double,
    override val upperBound: Double,
    private val objectiveFunction: (DoubleArray) -> Double,
    override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
) : BoundedVectorProblem {

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

/**
 * Problema de otimização binária.
 *
 * Soluções são representadas como [BooleanArray] de [size] bits.
 * Adequado para problemas como Mochila 0/1, MAX-SAT, Feature Selection, etc.
 *
 * A vizinhança é definida por bit-flip: altera um bit aleatório da solução.
 *
 * @param size número de variáveis binárias.
 * @param objectiveFunction função objetivo f(x) → ℝ.
 * @param direction direção de otimização.
 */
public open class BinaryProblem(
    public val size: Int,
    private val objectiveFunction: (BooleanArray) -> Double,
    override val direction: OptimizationDirection = OptimizationDirection.MAXIMIZE
) : OptimizationProblem<BooleanArray> {

    override fun evaluate(solution: BooleanArray): Double = objectiveFunction(solution)

    override fun randomSolution(random: Random): BooleanArray =
        BooleanArray(size) { random.nextBoolean() }

    override fun neighbor(solution: BooleanArray, random: Random): BooleanArray {
        val result = solution.copyOf()
        val index = random.nextInt(size)
        result[index] = !result[index]
        return result
    }

    override fun copy(solution: BooleanArray): BooleanArray = solution.copyOf()
}

/**
 * Problema de otimização por permutação.
 *
 * Soluções são representadas como [IntArray] contendo uma permutação de 0 até [size]-1.
 * Adequado para TSP, Job Scheduling, Flowshop, QAP, etc.
 *
 * A vizinhança é definida por swap: troca duas posições aleatórias.
 *
 * @param size número de elementos na permutação.
 * @param objectiveFunction função objetivo f(permutação) → ℝ.
 * @param direction direção de otimização.
 */
public open class PermutationProblem(
    public val size: Int,
    private val objectiveFunction: (IntArray) -> Double,
    override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
) : OptimizationProblem<IntArray> {

    override fun evaluate(solution: IntArray): Double = objectiveFunction(solution)

    override fun randomSolution(random: Random): IntArray {
        val perm = IntArray(size) { it }
        for (i in size - 1 downTo 1) {
            val j = random.nextInt(i + 1)
            val tmp = perm[i]; perm[i] = perm[j]; perm[j] = tmp
        }
        return perm
    }

    override fun neighbor(solution: IntArray, random: Random): IntArray {
        val result = solution.copyOf()
        val i = random.nextInt(size)
        var j = random.nextInt(size)
        while (j == i) j = random.nextInt(size)
        val tmp = result[i]; result[i] = result[j]; result[j] = tmp
        return result
    }

    override fun copy(solution: IntArray): IntArray = solution.copyOf()
}

/**
 * Problema de otimização com vetor de inteiros dentro de limites.
 *
 * Soluções são representadas como [IntArray] onde cada posição i ∈ [[lowerBounds]\[i\], [upperBounds]\[i\]].
 * Adequado para problemas com variáveis discretas (configuração, alocação de recursos, etc.).
 *
 * A vizinhança altera uma posição aleatória por ±1 dentro dos limites.
 *
 * @param lowerBounds limites inferiores por dimensão.
 * @param upperBounds limites superiores por dimensão.
 * @param objectiveFunction função objetivo f(x) → ℝ.
 * @param direction direção de otimização.
 */
public class IntegerProblem(
    public val lowerBounds: IntArray,
    public val upperBounds: IntArray,
    private val objectiveFunction: (IntArray) -> Double,
    override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE
) : OptimizationProblem<IntArray> {

    public val dimensions: Int = lowerBounds.size

    override fun evaluate(solution: IntArray): Double = objectiveFunction(solution)

    override fun randomSolution(random: Random): IntArray =
        IntArray(dimensions) { lowerBounds[it] + random.nextInt(upperBounds[it] - lowerBounds[it] + 1) }

    override fun neighbor(solution: IntArray, random: Random): IntArray {
        val result = solution.copyOf()
        val index = random.nextInt(dimensions)
        val delta = if (random.nextBoolean()) 1 else -1
        result[index] = (result[index] + delta).coerceIn(lowerBounds[index], upperBounds[index])
        return result
    }

    override fun copy(solution: IntArray): IntArray = solution.copyOf()
}
