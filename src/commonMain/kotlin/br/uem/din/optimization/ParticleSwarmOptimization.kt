package br.uem.din.optimization

import kotlin.random.Random

/**
 * Particle Swarm Optimization (PSO — Otimização por Enxame de Partículas).
 *
 * Metaheurística populacional inspirada no comportamento social de bandos de pássaros.
 * Cada partícula possui posição e velocidade, atualizadas a cada iteração com base
 * na melhor posição pessoal (pBest) e na melhor posição global (gBest).
 *
 * Equações de atualização:
 *   v_i(t+1) = w · v_i(t) + c1 · r1 · (pBest_i − x_i) + c2 · r2 · (gBest − x_i)
 *   x_i(t+1) = x_i(t) + v_i(t+1)
 *
 * Aceita qualquer [BoundedVectorProblem] — não apenas [ContinuousProblem] —
 * permitindo uso com representações customizadas que exponham dimensões e limites.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | swarmSize | Número de partículas |
 * | iterations | Número de iterações |
 * | w | Inércia (controla exploração vs explotação) |
 * | c1 | Coeficiente cognitivo (atração ao pBest) |
 * | c2 | Coeficiente social (atração ao gBest) |
 *
 * Referência: Kennedy, J. & Eberhart, R. "Particle Swarm Optimization" (1995),
 *             Proceedings of IEEE International Conference on Neural Networks.
 *
 * @param problem o problema de otimização com espaço vetorial limitado.
 * @param swarmSize número de partículas no enxame.
 * @param iterations número de iterações.
 * @param w fator de inércia.
 * @param c1 coeficiente cognitivo.
 * @param c2 coeficiente social.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun particleSwarmOptimization(
    problem: BoundedVectorProblem,
    swarmSize: Int = 50,
    iterations: Int = 500,
    w: Double = 0.729,
    c1: Double = 1.49445,
    c2: Double = 1.49445,
    random: Random = Random
): OptResult<DoubleArray> {
    val dim = problem.dimensions
    val lo = problem.lowerBound
    val hi = problem.upperBound
    var evaluations = 0

    val positions = Array(swarmSize) { problem.randomSolution(random) }
    val velocities = Array(swarmSize) { DoubleArray(dim) { (random.nextDouble() - 0.5) * (hi - lo) * 0.1 } }
    val pBest = Array(swarmSize) { positions[it].copyOf() }
    val pBestCost = DoubleArray(swarmSize) {
        evaluations++
        problem.evaluate(positions[it])
    }

    var gBestIdx = if (problem.direction == OptimizationDirection.MINIMIZE)
        pBestCost.indices.minByOrNull { pBestCost[it] }!! else pBestCost.indices.maxByOrNull { pBestCost[it] }!!
    var gBest = pBest[gBestIdx].copyOf()
    var gBestCost = pBestCost[gBestIdx]

    for (iter in 0 until iterations) {
        for (i in 0 until swarmSize) {
            for (d in 0 until dim) {
                val r1 = random.nextDouble()
                val r2 = random.nextDouble()
                velocities[i][d] = w * velocities[i][d] +
                    c1 * r1 * (pBest[i][d] - positions[i][d]) +
                    c2 * r2 * (gBest[d] - positions[i][d])
                positions[i][d] = (positions[i][d] + velocities[i][d]).coerceIn(lo, hi)
            }

            val cost = problem.evaluate(positions[i])
            evaluations++

            if (problem.isBetter(cost, pBestCost[i])) {
                pBest[i] = positions[i].copyOf()
                pBestCost[i] = cost

                if (problem.isBetter(cost, gBestCost)) {
                    gBest = positions[i].copyOf()
                    gBestCost = cost
                }
            }
        }
    }

    return OptResult(gBest, gBestCost, iterations, evaluations)
}
