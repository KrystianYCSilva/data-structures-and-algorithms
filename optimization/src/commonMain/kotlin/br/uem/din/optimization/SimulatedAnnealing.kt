package br.uem.din.optimization

import kotlin.math.exp
import kotlin.random.Random

/**
 * Algoritmo Simulated Annealing (Recozimento Simulado).
 *
 * Metaheurística inspirada no processo de recozimento metalúrgico. Aceita soluções
 * piores com uma probabilidade que decresce conforme a "temperatura" diminui,
 * permitindo escapar de ótimos locais.
 *
 * A probabilidade de aceitação segue a distribuição de Boltzmann:
 * P(aceitar) = exp(-Δ / T), onde Δ = |f(vizinho) - f(atual)|.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | initialTemp | Temperatura inicial (controla aceitação inicial) |
 * | coolingRate | Taxa de resfriamento geométrico (0 < α < 1) |
 * | minTemp | Temperatura mínima (critério de parada) |
 * | maxIterations | Limite de iterações por temperatura |
 *
 * Referência: Kirkpatrick, S.; Gelatt, C. D.; Vecchi, M. P.
 *             "Optimization by Simulated Annealing" (1983), Science 220(4598).
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização a ser resolvido.
 * @param initialTemp temperatura inicial.
 * @param coolingRate taxa de resfriamento (tipicamente 0.95–0.99).
 * @param minTemp temperatura mínima para parada.
 * @param maxIterationsPerTemp iterações por nível de temperatura.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> simulatedAnnealing(
    problem: OptimizationProblem<T>,
    initialTemp: Double = 1000.0,
    coolingRate: Double = 0.995,
    minTemp: Double = 1e-8,
    maxIterationsPerTemp: Int = 100,
    random: Random = Random
): OptResult<T> {
    var current = problem.randomSolution(random)
    var currentCost = problem.evaluate(current)
    var bestSolution = problem.copy(current)
    var bestCost = currentCost
    var evaluations = 1
    var iterations = 0
    var temperature = initialTemp

    while (temperature > minTemp) {
        for (i in 0 until maxIterationsPerTemp) {
            iterations++
            val neighbor = problem.neighbor(current, random)
            val neighborCost = problem.evaluate(neighbor)
            evaluations++

            val delta = if (problem.direction == OptimizationDirection.MINIMIZE)
                neighborCost - currentCost
            else
                currentCost - neighborCost

            if (delta < 0.0 || random.nextDouble() < exp(-delta / temperature)) {
                current = neighbor
                currentCost = neighborCost

                if (problem.isBetter(currentCost, bestCost)) {
                    bestSolution = problem.copy(current)
                    bestCost = currentCost
                }
            }
        }
        temperature *= coolingRate
    }

    return OptResult(bestSolution, bestCost, iterations, evaluations)
}
