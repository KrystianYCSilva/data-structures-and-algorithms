package br.uem.din.optimization

import kotlin.random.Random

/**
 * Particle Swarm Optimization (PSO — Otimizacao por Enxame de Particulas).
 *
 * Metaheuristica populacional inspirada no comportamento social de bandos de passaros.
 * Cada particula possui posicao e velocidade, atualizadas a cada iteracao com base
 * na melhor posicao pessoal (pBest) e na melhor posicao global (gBest).
 *
 * Equacoes de atualizacao:
 *   v_i(t+1) = w * v_i(t) + c1 * r1 * (pBest_i - x_i) + c2 * r2 * (gBest - x_i)
 *   x_i(t+1) = x_i(t) + v_i(t+1)
 *
 * | Parametro | Descricao |
 * |-----------|-----------|
 * | swarmSize | Numero de particulas |
 * | iterations | Numero de iteracoes |
 * | w | Inercia (controla exploracao vs exploitacao) |
 * | c1 | Coeficiente cognitivo (atracao ao pBest) |
 * | c2 | Coeficiente social (atracao ao gBest) |
 *
 * Referencia: Kennedy, J. & Eberhart, R. "Particle Swarm Optimization" (1995),
 *             Proceedings of IEEE International Conference on Neural Networks.
 *
 * @param problem o problema de otimizacao continua.
 * @param swarmSize numero de particulas no enxame.
 * @param iterations numero de iteracoes.
 * @param w fator de inercia.
 * @param c1 coeficiente cognitivo.
 * @param c2 coeficiente social.
 * @param random gerador de numeros aleatorios.
 * @return resultado da otimizacao contendo a melhor solucao encontrada.
 */
public fun particleSwarmOptimization(
    problem: ContinuousProblem,
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
