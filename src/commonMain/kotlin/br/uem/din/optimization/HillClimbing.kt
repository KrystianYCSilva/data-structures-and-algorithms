package br.uem.din.optimization

import kotlin.random.Random

/**
 * Estratégia de exploração da vizinhança para Hill Climbing.
 */
public enum class HillClimbingStrategy {
    FIRST_IMPROVEMENT,
    BEST_IMPROVEMENT
}

/**
 * Algoritmo Hill Climbing (Subida de Encosta).
 *
 * Busca local iterativa que, a cada passo, move-se para o melhor vizinho que
 * melhore a solução corrente. Termina quando nenhum vizinho é melhor (ótimo local).
 *
 * Suporta duas estratégias:
 * - **First-Improvement**: aceita o primeiro vizinho melhor encontrado.
 * - **Best-Improvement** (Steepest Ascent): avalia todos os vizinhos e move-se para o melhor.
 *
 * | Operação | Complexidade |
 * |----------|-------------|
 * | Iteração | O(k) onde k = neighborsPerStep |
 * | Total | O(maxIterations × k) |
 *
 * Referência: Russell, S. & Norvig, P. "Artificial Intelligence: A Modern Approach" (2010), Cap. 4.1.
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização a ser resolvido.
 * @param strategy estratégia de seleção de vizinhos.
 * @param maxIterations número máximo de iterações sem melhoria.
 * @param neighborsPerStep número de vizinhos avaliados por iteração.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> hillClimbing(
    problem: OptimizationProblem<T>,
    strategy: HillClimbingStrategy = HillClimbingStrategy.BEST_IMPROVEMENT,
    maxIterations: Int = 10_000,
    neighborsPerStep: Int = 20,
    random: Random = Random
): OptResult<T> {
    var current = problem.randomSolution(random)
    var currentCost = problem.evaluate(current)
    var evaluations = 1
    var iterations = 0

    for (iter in 0 until maxIterations) {
        iterations = iter + 1
        var improved = false
        var bestNeighbor = current
        var bestNeighborCost = currentCost

        for (k in 0 until neighborsPerStep) {
            val neighbor = problem.neighbor(current, random)
            val neighborCost = problem.evaluate(neighbor)
            evaluations++

            if (problem.isBetter(neighborCost, bestNeighborCost)) {
                bestNeighbor = neighbor
                bestNeighborCost = neighborCost
                improved = true
                if (strategy == HillClimbingStrategy.FIRST_IMPROVEMENT) break
            }
        }

        if (!improved) break
        current = bestNeighbor
        currentCost = bestNeighborCost
    }

    return OptResult(current, currentCost, iterations, evaluations)
}
