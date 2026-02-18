package br.uem.din.optimization

import kotlin.random.Random

/**
 * Variable Neighborhood Search (VNS — Busca em Vizinhança Variável).
 *
 * Metaheurística que explora sistematicamente múltiplas estruturas de vizinhança
 * de tamanho crescente. Quando a busca local estagna em uma vizinhança, o algoritmo
 * perturba a solução com uma vizinhança maior, escapando de ótimos locais.
 *
 * Ciclo VNS (Basic VNS):
 * 1. **Shaking**: perturba a solução corrente na k-ésima vizinhança.
 * 2. **Busca local**: refina a solução perturbada.
 * 3. **Move-or-not**: se melhorou, volta à vizinhança k=1; senão, k++.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | kMax | Número de estruturas de vizinhança (vizinhança k aplica k perturbações) |
 * | maxIterations | Iterações do ciclo principal |
 * | localSearchSteps | Iterações da busca local interna |
 *
 * Referência: Mladenović, N. & Hansen, P. "Variable Neighborhood Search" (1997),
 *             Computers & Operations Research 24(11).
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização.
 * @param kMax número máximo de vizinhanças (intensidade de perturbação de 1 a kMax).
 * @param maxIterations número de iterações do ciclo principal.
 * @param localSearchSteps iterações da busca local interna.
 * @param localSearchNeighbors vizinhos avaliados por passo da busca local.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> variableNeighborhoodSearch(
    problem: OptimizationProblem<T>,
    kMax: Int = 5,
    maxIterations: Int = 1_000,
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
            for (n in 0 until localSearchNeighbors) {
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

    fun shake(solution: T, k: Int): T {
        var result = solution
        for (s in 0 until k) {
            result = problem.neighbor(result, random)
        }
        return result
    }

    val (initialLocal, initialCost) = localSearch(problem.randomSolution(random))
    var bestSolution = problem.copy(initialLocal)
    var bestCost = initialCost
    var iterations = 0

    for (iter in 0 until maxIterations) {
        iterations = iter + 1
        var k = 1
        while (k <= kMax) {
            val shaken = shake(bestSolution, k)
            val (localOpt, localCost) = localSearch(shaken)

            if (problem.isBetter(localCost, bestCost)) {
                bestSolution = problem.copy(localOpt)
                bestCost = localCost
                k = 1
            } else {
                k++
            }
        }
    }

    return OptResult(bestSolution, bestCost, iterations, evaluations)
}
