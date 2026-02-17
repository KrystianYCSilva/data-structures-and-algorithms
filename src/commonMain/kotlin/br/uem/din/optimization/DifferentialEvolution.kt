package br.uem.din.optimization

import kotlin.random.Random

/**
 * Evolução Diferencial (Differential Evolution — DE).
 *
 * Metaheurística populacional para otimização contínua que evolui uma população
 * de vetores reais através de mutação diferencial, crossover binomial e seleção
 * gulosa. Cada indivíduo-alvo é comparado com um vetor-trial; o melhor sobrevive.
 *
 * Aceita qualquer [BoundedVectorProblem] — não apenas [ContinuousProblem] —
 * permitindo uso com representações customizadas que exponham dimensões e limites.
 *
 * Operadores:
 * - **Mutação (DE/rand/1)**: v = x_r1 + F·(x_r2 − x_r3)
 * - **Crossover binomial**: troca componentes com probabilidade CR
 * - **Seleção gulosa**: mantém o melhor entre alvo e trial
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | populationSize | Tamanho da população (NP) |
 * | generations | Número de gerações |
 * | F | Fator de escala da mutação diferencial (0.0–2.0) |
 * | CR | Taxa de crossover binomial (0.0–1.0) |
 *
 * Referência: Storn, R. & Price, K. "Differential Evolution — A Simple and Efficient
 *             Heuristic for Global Optimization over Continuous Spaces" (1997),
 *             Journal of Global Optimization 11(4).
 *
 * @param problem o problema de otimização com espaço vetorial limitado.
 * @param populationSize tamanho da população (NP ≥ 4).
 * @param generations número de gerações.
 * @param F fator de escala da mutação diferencial.
 * @param CR taxa de crossover binomial.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun differentialEvolution(
    problem: BoundedVectorProblem,
    populationSize: Int = 50,
    generations: Int = 500,
    F: Double = 0.8,
    CR: Double = 0.9,
    random: Random = Random
): OptResult<DoubleArray> {
    val dim = problem.dimensions
    val lo = problem.lowerBound
    val hi = problem.upperBound
    var evaluations = 0

    val population = Array(populationSize) { problem.randomSolution(random) }
    val fitness = DoubleArray(populationSize) {
        evaluations++
        problem.evaluate(population[it])
    }

    var bestIdx = if (problem.direction == OptimizationDirection.MINIMIZE)
        fitness.indices.minByOrNull { fitness[it] }!! else fitness.indices.maxByOrNull { fitness[it] }!!
    var bestSolution = population[bestIdx].copyOf()
    var bestCost = fitness[bestIdx]

    for (gen in 0 until generations) {
        for (i in 0 until populationSize) {
            var r1 = random.nextInt(populationSize)
            while (r1 == i) r1 = random.nextInt(populationSize)
            var r2 = random.nextInt(populationSize)
            while (r2 == i || r2 == r1) r2 = random.nextInt(populationSize)
            var r3 = random.nextInt(populationSize)
            while (r3 == i || r3 == r1 || r3 == r2) r3 = random.nextInt(populationSize)

            val jRand = random.nextInt(dim)
            val trial = DoubleArray(dim) { d ->
                if (d == jRand || random.nextDouble() < CR) {
                    (population[r1][d] + F * (population[r2][d] - population[r3][d]))
                        .coerceIn(lo, hi)
                } else {
                    population[i][d]
                }
            }

            val trialCost = problem.evaluate(trial)
            evaluations++

            if (problem.isBetter(trialCost, fitness[i]) || trialCost == fitness[i]) {
                population[i] = trial
                fitness[i] = trialCost

                if (problem.isBetter(trialCost, bestCost)) {
                    bestSolution = trial.copyOf()
                    bestCost = trialCost
                }
            }
        }
    }

    return OptResult(bestSolution, bestCost, generations, evaluations)
}
