package br.uem.din.optimization

import kotlin.random.Random

/**
 * Problema da Mochila 0/1 (Knapsack Problem).
 *
 * Dado um conjunto de [n] itens, cada um com peso [weights]\[i\] e valor [values]\[i\],
 * e uma mochila com capacidade máxima [capacity], selecionar um subconjunto de itens
 * que maximize o valor total sem exceder a capacidade.
 *
 * A solução é representada como [BooleanArray]: `true` se o item i está na mochila.
 *
 * Soluções inviáveis (acima da capacidade) recebem penalização proporcional ao excesso.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | weights | Peso de cada item |
 * | values | Valor de cada item |
 * | capacity | Capacidade máxima da mochila |
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 16.2 (Greedy)
 *             e Cap. 15.2 (DP). Kellerer, H. et al. "Knapsack Problems" (2004).
 *
 * @param weights pesos dos itens.
 * @param values valores dos itens.
 * @param capacity capacidade da mochila.
 */
public class KnapsackProblem(
    public val weights: IntArray,
    public val values: IntArray,
    public val capacity: Int
) : OptimizationProblem<BooleanArray> {

    public val n: Int = weights.size

    override val direction: OptimizationDirection = OptimizationDirection.MAXIMIZE

    override fun evaluate(solution: BooleanArray): Double {
        var totalWeight = 0
        var totalValue = 0
        for (i in 0 until n) {
            if (solution[i]) {
                totalWeight += weights[i]
                totalValue += values[i]
            }
        }
        return if (totalWeight <= capacity) {
            totalValue.toDouble()
        } else {
            totalValue.toDouble() - (totalWeight - capacity).toDouble() * 10.0
        }
    }

    override fun randomSolution(random: Random): BooleanArray =
        BooleanArray(n) { random.nextBoolean() }

    override fun neighbor(solution: BooleanArray, random: Random): BooleanArray {
        val result = solution.copyOf()
        val index = random.nextInt(n)
        result[index] = !result[index]
        return result
    }

    override fun copy(solution: BooleanArray): BooleanArray = solution.copyOf()

    public companion object {
        /**
         * Gera uma instância aleatória com [n] itens.
         *
         * @param n número de itens.
         * @param maxWeight peso máximo por item.
         * @param maxValue valor máximo por item.
         * @param capacityFactor fração da soma total de pesos usada como capacidade.
         * @param random gerador de números aleatórios.
         */
        public fun random(
            n: Int,
            maxWeight: Int = 50,
            maxValue: Int = 100,
            capacityFactor: Double = 0.5,
            random: Random = Random
        ): KnapsackProblem {
            val weights = IntArray(n) { random.nextInt(1, maxWeight + 1) }
            val values = IntArray(n) { random.nextInt(1, maxValue + 1) }
            val capacity = (weights.sum() * capacityFactor).toInt().coerceAtLeast(1)
            return KnapsackProblem(weights, values, capacity)
        }
    }
}
