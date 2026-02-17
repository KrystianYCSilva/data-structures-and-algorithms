package br.uem.din.optimization

import kotlin.math.pow
import kotlin.random.Random

/**
 * Ant Colony Optimization (ACO) para o Problema do Caixeiro Viajante.
 *
 * Metaheuristica inspirada no comportamento de formigas que depositam feromonio
 * em trilhas para comunicar caminhos curtos. Cada formiga constroi uma solucao
 * completa usando probabilidades proporcionais ao feromonio e a informacao heuristica
 * (inverso da distancia).
 *
 * Regra de transicao:
 *   P(i -> j) proporcional a tau(i,j)^alpha * eta(i,j)^beta
 *
 * | Parametro | Descricao |
 * |-----------|-----------|
 * | antCount | Numero de formigas por iteracao |
 * | iterations | Numero de iteracoes |
 * | alpha | Peso do feromonio na decisao |
 * | beta | Peso da heuristica (1/distancia) na decisao |
 * | evaporationRate | Taxa de evaporacao do feromonio (0.0-1.0) |
 * | q | Constante de deposito de feromonio |
 *
 * Referencia: Dorigo, M. "Optimization, Learning and Natural Algorithms" (1992), PhD Thesis;
 *             Dorigo, M. & Stutzle, T. "Ant Colony Optimization" (2004), MIT Press.
 *
 * @param problem instancia do TSP.
 * @param antCount numero de formigas.
 * @param iterations numero de iteracoes.
 * @param alpha influencia do feromonio.
 * @param beta influencia da heuristica.
 * @param evaporationRate taxa de evaporacao.
 * @param q constante de deposito.
 * @param random gerador de numeros aleatorios.
 * @return resultado contendo o melhor tour encontrado.
 */
public fun antColonyOptimization(
    problem: TSPProblem,
    antCount: Int = 30,
    iterations: Int = 200,
    alpha: Double = 1.0,
    beta: Double = 3.0,
    evaporationRate: Double = 0.5,
    q: Double = 100.0,
    random: Random = Random
): OptResult<IntArray> {
    val n = problem.cities.size
    var evaluations = 0
    val pheromone = Array(n) { DoubleArray(n) { 1.0 } }
    val heuristic = Array(n) { i ->
        DoubleArray(n) { j ->
            val dist = problem.evaluate(intArrayOf(i, j)).let {
                if (i == j) Double.MAX_VALUE
                else {
                    val dx = problem.cities[i].first - problem.cities[j].first
                    val dy = problem.cities[i].second - problem.cities[j].second
                    kotlin.math.sqrt(dx * dx + dy * dy)
                }
            }
            if (dist > 0.0) 1.0 / dist else 0.0
        }
    }

    var bestTour = problem.randomSolution(random)
    var bestCost = problem.evaluate(bestTour)
    evaluations++

    for (iter in 0 until iterations) {
        val tours = Array(antCount) { IntArray(0) }
        val costs = DoubleArray(antCount)

        for (ant in 0 until antCount) {
            val tour = IntArray(n)
            val visited = BooleanArray(n)
            tour[0] = random.nextInt(n)
            visited[tour[0]] = true

            for (step in 1 until n) {
                val current = tour[step - 1]
                var totalProb = 0.0
                for (j in 0 until n) {
                    if (!visited[j]) {
                        totalProb += pheromone[current][j].pow(alpha) * heuristic[current][j].pow(beta)
                    }
                }

                var threshold = random.nextDouble() * totalProb
                var next = -1
                for (j in 0 until n) {
                    if (!visited[j]) {
                        threshold -= pheromone[current][j].pow(alpha) * heuristic[current][j].pow(beta)
                        if (threshold <= 0.0) {
                            next = j
                            break
                        }
                    }
                }
                if (next == -1) {
                    for (j in 0 until n) {
                        if (!visited[j]) { next = j; break }
                    }
                }

                tour[step] = next
                visited[next] = true
            }

            val cost = problem.evaluate(tour)
            evaluations++
            tours[ant] = tour
            costs[ant] = cost

            if (cost < bestCost) {
                bestTour = tour.copyOf()
                bestCost = cost
            }
        }

        for (i in 0 until n) {
            for (j in 0 until n) {
                pheromone[i][j] *= (1.0 - evaporationRate)
            }
        }

        for (ant in 0 until antCount) {
            val deposit = q / costs[ant]
            val tour = tours[ant]
            for (step in 0 until n - 1) {
                pheromone[tour[step]][tour[step + 1]] += deposit
                pheromone[tour[step + 1]][tour[step]] += deposit
            }
            pheromone[tour[n - 1]][tour[0]] += deposit
            pheromone[tour[0]][tour[n - 1]] += deposit
        }
    }

    return OptResult(bestTour, bestCost, iterations, evaluations)
}
