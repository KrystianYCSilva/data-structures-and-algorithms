package br.uem.din.optimization

import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Problema do Caixeiro Viajante (Travelling Salesman Problem — TSP).
 *
 * Representa uma instância TSP simétrica com distância euclidiana.
 * A solução é uma permutação dos índices das cidades (tour).
 *
 * Implementa [CostMatrixProblem] para compatibilidade com ACO e outros algoritmos
 * que operam sobre matrizes de custo.
 *
 * Referência: Applegate, D. L. et al. "The Traveling Salesman Problem" (2006);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 34 — NP-Completude.
 *
 * @param cities coordenadas (x, y) das cidades.
 */
public class TSPProblem(
    public val cities: List<Pair<Double, Double>>
) : CostMatrixProblem {

    override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE

    private val n: Int = cities.size

    override val size: Int get() = n

    private val distanceMatrix: Array<DoubleArray> = Array(n) { i ->
        DoubleArray(n) { j -> euclidean(cities[i], cities[j]) }
    }

    override fun cost(i: Int, j: Int): Double = distanceMatrix[i][j]

    override fun evaluate(solution: IntArray): Double {
        var total = 0.0
        for (i in 0 until solution.size - 1) {
            total += distanceMatrix[solution[i]][solution[i + 1]]
        }
        total += distanceMatrix[solution[solution.size - 1]][solution[0]]
        return total
    }

    override fun randomSolution(random: Random): IntArray {
        val perm = IntArray(n) { it }
        for (i in n - 1 downTo 1) {
            val j = random.nextInt(i + 1)
            val tmp = perm[i]; perm[i] = perm[j]; perm[j] = tmp
        }
        return perm
    }

    /**
     * Vizinhança 2-opt: reverte um segmento aleatório do tour.
     */
    override fun neighbor(solution: IntArray, random: Random): IntArray {
        val result = solution.copyOf()
        var i = random.nextInt(n)
        var j = random.nextInt(n)
        if (i > j) { val tmp = i; i = j; j = tmp }
        while (i < j) {
            val tmp = result[i]; result[i] = result[j]; result[j] = tmp
            i++; j--
        }
        return result
    }

    override fun copy(solution: IntArray): IntArray = solution.copyOf()

    public companion object {
        private fun euclidean(a: Pair<Double, Double>, b: Pair<Double, Double>): Double {
            val dx = a.first - b.first
            val dy = a.second - b.second
            return sqrt(dx * dx + dy * dy)
        }

        /**
         * Gera uma instância TSP aleatória com [n] cidades em uma área [size] x [size].
         */
        public fun random(n: Int, size: Double = 100.0, random: Random = Random): TSPProblem {
            val cities = List(n) { Pair(random.nextDouble() * size, random.nextDouble() * size) }
            return TSPProblem(cities)
        }
    }
}
