package br.uem.din.optimization

import kotlin.random.Random

/**
 * Algoritmo Iterated Local Search (ILS — Busca Local Iterada).
 *
 * Metaheurística que alterna entre busca local e perturbação da solução.
 * A perturbação move a solução para uma região diferente do espaço de busca,
 * permitindo explorar múltiplos ótimos locais.
 *
 * Ciclo ILS:
 * 1. Aplica busca local na solução corrente.
 * 2. Perturba a solução (multiple neighbors).
 * 3. Aplica busca local na solução perturbada.
 * 4. Aplica critério de aceitação.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | maxIterations | Iterações do ciclo principal |
 * | perturbationStrength | Número de perturbações consecutivas |
 * | localSearchSteps | Iterações da busca local interna |
 *
 * Referência: Lourenço, H. R.; Martin, O. C.; Stützle, T.
 *             "Iterated Local Search" (2003), in Handbook of Metaheuristics.
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização.
 * @param maxIterations número de iterações do ciclo principal.
 * @param perturbationStrength intensidade da perturbação (número de modificações).
 * @param localSearchSteps iterações da busca local interna.
 * @param localSearchNeighbors vizinhos avaliados por passo da busca local.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> iteratedLocalSearch(
    problem: OptimizationProblem<T>,
    maxIterations: Int = 1_000,
    perturbationStrength: Int = 5,
    localSearchSteps: Int = 200,
    localSearchNeighbors: Int = 10,
    random: Random = Random
): OptResult<T> {
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

    fun perturb(solution: T): T {
        var result = solution
        for (p in 0 until perturbationStrength) {
            result = problem.neighbor(result, random)
        }
        return result
    }

    val (initialLocal, initialCost) = localSearch(problem.randomSolution(random))
    var current = initialLocal
    var currentCost = initialCost
    var bestSolution = problem.copy(current)
    var bestCost = currentCost

    for (iter in 0 until maxIterations) {
        val perturbed = perturb(current)
        val (localOpt, localCost) = localSearch(perturbed)

        if (problem.isBetter(localCost, currentCost)) {
            current = localOpt
            currentCost = localCost
        }

        if (problem.isBetter(currentCost, bestCost)) {
            bestSolution = problem.copy(current)
            bestCost = currentCost
        }
    }

    return OptResult(bestSolution, bestCost, maxIterations, evaluations)
}
