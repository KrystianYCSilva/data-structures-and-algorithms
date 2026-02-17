package br.uem.din.optimization

import kotlin.random.Random

/**
 * GRASP — Greedy Randomized Adaptive Search Procedure.
 *
 * Metaheurística que combina construção gulosa randomizada com busca local.
 * Em cada iteração, uma solução é construída de forma semi-gulosa (usando lista
 * restrita de candidatos — RCL) e depois refinada via busca local.
 *
 * Fases do GRASP:
 * 1. **Construção gulosa randomizada**: gera solução inicial usando [greedyRandomized].
 * 2. **Busca local**: refina a solução com first-improvement.
 * 3. **Atualização**: mantém a melhor solução global.
 *
 * O parâmetro α controla o grau de aleatoriedade:
 * - α = 0: totalmente guloso (melhor candidato)
 * - α = 1: totalmente aleatório
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | maxIterations | Repetições do ciclo construção + busca local |
 * | alpha | Parâmetro de qualidade do RCL (0.0–1.0) |
 * | localSearchSteps | Iterações da busca local |
 *
 * Referência: Feo, T. A. & Resende, M. G. C.
 *             "Greedy Randomized Adaptive Search Procedures" (1995), Journal of Global Optimization 6.
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização.
 * @param greedyRandomized função de construção gulosa randomizada que aceita o parâmetro α.
 *        Se null, usa [OptimizationProblem.randomSolution].
 * @param maxIterations número de iterações do ciclo principal.
 * @param alpha parâmetro de aleatoriedade do RCL (0.0 = puro guloso, 1.0 = aleatório).
 * @param localSearchSteps iterações da busca local.
 * @param localSearchNeighbors vizinhos avaliados por passo.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> grasp(
    problem: OptimizationProblem<T>,
    greedyRandomized: ((Double, Random) -> T)? = null,
    maxIterations: Int = 500,
    alpha: Double = 0.3,
    localSearchSteps: Int = 300,
    localSearchNeighbors: Int = 15,
    random: Random = Random
): OptResult<T> {
    var bestSolution: T? = null
    var bestCost = problem.worstValue()
    var evaluations = 0

    fun localSearch(initial: T): Pair<T, Double> {
        var current = initial
        var currentCost = problem.evaluate(current)
        evaluations++

        for (step in 0 until localSearchSteps) {
            var improved = false
            for (k in 0 until localSearchNeighbors) {
                val neighbor = problem.neighbor(current, random)
                val neighborCost = problem.evaluate(neighbor)
                evaluations++
                if (problem.isBetter(neighborCost, currentCost)) {
                    current = neighbor
                    currentCost = neighborCost
                    improved = true
                    break
                }
            }
            if (!improved) break
        }
        return Pair(current, currentCost)
    }

    for (iter in 0 until maxIterations) {
        val constructed = greedyRandomized?.invoke(alpha, random) ?: problem.randomSolution(random)
        val (localOpt, localCost) = localSearch(constructed)

        if (problem.isBetter(localCost, bestCost)) {
            bestSolution = problem.copy(localOpt)
            bestCost = localCost
        }
    }

    return OptResult(bestSolution!!, bestCost, maxIterations, evaluations)
}
