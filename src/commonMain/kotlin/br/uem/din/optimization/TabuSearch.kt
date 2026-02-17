package br.uem.din.optimization

import kotlin.random.Random

/**
 * Algoritmo Tabu Search (Busca Tabu).
 *
 * Metaheurística de busca local que utiliza uma lista tabu (memória de curto prazo)
 * para evitar retorno a soluções recentemente visitadas, possibilitando escapar
 * de ótimos locais.
 *
 * A cada iteração, o melhor vizinho não-tabu é selecionado, mesmo que piore a solução.
 * Um critério de aspiração permite aceitar movimentos tabu se produzirem a melhor
 * solução global encontrada.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | tabuTenure | Duração de permanência na lista tabu |
 * | maxIterations | Número máximo de iterações |
 * | neighborsPerStep | Vizinhos avaliados por iteração |
 *
 * Referência: Glover, F. "Future Paths for Integer Programming and Links to
 *             Artificial Intelligence" (1986), Computers & Operations Research 13(5).
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização a ser resolvido.
 * @param tabuTenure número de iterações que uma solução permanece tabu.
 * @param maxIterations número máximo de iterações.
 * @param neighborsPerStep número de vizinhos avaliados por iteração.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> tabuSearch(
    problem: OptimizationProblem<T>,
    tabuTenure: Int = 20,
    maxIterations: Int = 5_000,
    neighborsPerStep: Int = 30,
    random: Random = Random
): OptResult<T> {
    var current = problem.randomSolution(random)
    var currentCost = problem.evaluate(current)
    var bestSolution = problem.copy(current)
    var bestCost = currentCost
    var evaluations = 1

    val tabuList = ArrayDeque<Int>()

    for (iter in 0 until maxIterations) {
        var bestNeighbor: T? = null
        var bestNeighborCost = problem.worstValue()
        var bestNeighborHash = 0

        for (k in 0 until neighborsPerStep) {
            val neighbor = problem.neighbor(current, random)
            val neighborCost = problem.evaluate(neighbor)
            evaluations++
            val hash = neighbor.hashCode()

            val isTabu = hash in tabuList
            val aspirationMet = problem.isBetter(neighborCost, bestCost)

            if ((!isTabu || aspirationMet) && problem.isBetter(neighborCost, bestNeighborCost)) {
                bestNeighbor = neighbor
                bestNeighborCost = neighborCost
                bestNeighborHash = hash
            }
        }

        if (bestNeighbor == null) break

        current = bestNeighbor
        currentCost = bestNeighborCost

        tabuList.addLast(bestNeighborHash)
        if (tabuList.size > tabuTenure) tabuList.removeFirst()

        if (problem.isBetter(currentCost, bestCost)) {
            bestSolution = problem.copy(current)
            bestCost = currentCost
        }
    }

    return OptResult(bestSolution, bestCost, maxIterations, evaluations)
}
