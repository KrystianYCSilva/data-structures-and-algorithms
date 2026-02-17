package br.uem.din.optimization

import kotlin.math.pow
import kotlin.random.Random

/**
 * Ant Colony Optimization (ACO — Otimização por Colônia de Formigas).
 *
 * Metaheurística inspirada no comportamento de formigas que depositam feromônio
 * em trilhas para comunicar caminhos curtos. Cada formiga constrói uma solução
 * completa usando probabilidades proporcionais ao feromônio e à informação heurística
 * (inverso do custo).
 *
 * Aceita qualquer [CostMatrixProblem] — não apenas [TSPProblem] — permitindo uso
 * com qualquer problema que possua uma noção de custo entre pares de elementos
 * e soluções representadas como permutações (TSP, QAP, Flowshop, etc.).
 *
 * Regra de transição:
 *   P(i → j) ∝ τ(i,j)^α · η(i,j)^β
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | antCount | Número de formigas por iteração |
 * | iterations | Número de iterações |
 * | alpha | Peso do feromônio na decisão |
 * | beta | Peso da heurística (1/custo) na decisão |
 * | evaporationRate | Taxa de evaporação do feromônio (0.0–1.0) |
 * | q | Constante de depósito de feromônio |
 *
 * Referência: Dorigo, M. "Optimization, Learning and Natural Algorithms" (1992), PhD Thesis;
 *             Dorigo, M. & Stützle, T. "Ant Colony Optimization" (2004), MIT Press.
 *
 * @param problem problema com matriz de custos.
 * @param antCount número de formigas.
 * @param iterations número de iterações.
 * @param alpha influência do feromônio.
 * @param beta influência da heurística.
 * @param evaporationRate taxa de evaporação.
 * @param q constante de depósito.
 * @param random gerador de números aleatórios.
 * @return resultado contendo a melhor solução encontrada.
 */
public fun antColonyOptimization(
    problem: CostMatrixProblem,
    antCount: Int = 30,
    iterations: Int = 200,
    alpha: Double = 1.0,
    beta: Double = 3.0,
    evaporationRate: Double = 0.5,
    q: Double = 100.0,
    random: Random = Random
): OptResult<IntArray> {
    val n = problem.size
    var evaluations = 0
    val pheromone = Array(n) { DoubleArray(n) { 1.0 } }
    val heuristic = Array(n) { i ->
        DoubleArray(n) { j ->
            val c = problem.cost(i, j)
            if (i == j || c <= 0.0) 0.0 else 1.0 / c
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

            if (problem.isBetter(cost, bestCost)) {
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
