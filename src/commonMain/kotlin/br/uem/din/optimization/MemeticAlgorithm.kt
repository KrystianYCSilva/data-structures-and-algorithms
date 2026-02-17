package br.uem.din.optimization

import kotlin.random.Random

/**
 * Algoritmo Memético (Memetic Algorithm — MA).
 *
 * Metaheurística híbrida que combina um Algoritmo Genético (evolução populacional)
 * com busca local individual. Após cada geração, os indivíduos são refinados por
 * uma fase de busca local, acelerando a convergência em relação a um GA puro.
 *
 * O termo "memético" refere-se à analogia com memes (unidades de informação cultural)
 * que evoluem tanto por transmissão (crossover/mutação) quanto por aprendizado individual
 * (busca local).
 *
 * Ciclo MA:
 * 1. Inicializa população + busca local em cada indivíduo.
 * 2. Seleção por torneio.
 * 3. Crossover e mutação.
 * 4. Busca local nos descendentes.
 * 5. Seleção geracional com elitismo.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | populationSize | Tamanho da população |
 * | generations | Número de gerações |
 * | crossoverRate | Probabilidade de crossover |
 * | mutationRate | Probabilidade de mutação |
 * | localSearchSteps | Iterações da busca local por indivíduo |
 *
 * Referência: Moscato, P. "On Evolution, Search, Optimization, Genetic Algorithms and
 *             Martial Arts: Towards Memetic Algorithms" (1989), Caltech Concurrent
 *             Computation Program Report 826.
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização.
 * @param crossover operador de crossover.
 * @param populationSize tamanho da população.
 * @param generations número de gerações.
 * @param crossoverRate probabilidade de crossover.
 * @param mutationRate probabilidade de mutação.
 * @param tournamentSize tamanho do torneio de seleção.
 * @param elitism número de indivíduos de elite preservados.
 * @param localSearchSteps iterações da busca local aplicada a cada indivíduo.
 * @param localSearchNeighbors vizinhos avaliados por passo da busca local.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> memeticAlgorithm(
    problem: OptimizationProblem<T>,
    crossover: (T, T, Random) -> T,
    populationSize: Int = 50,
    generations: Int = 200,
    crossoverRate: Double = 0.8,
    mutationRate: Double = 0.1,
    tournamentSize: Int = 5,
    elitism: Int = 2,
    localSearchSteps: Int = 50,
    localSearchNeighbors: Int = 5,
    random: Random = Random
): OptResult<T> {
    var evaluations = 0

    fun eval(s: T): Double {
        evaluations++
        return problem.evaluate(s)
    }

    fun localSearch(initial: T, initialCost: Double): Pair<T, Double> {
        var current = initial
        var currentCost = initialCost
        for (step in 0 until localSearchSteps) {
            var improved = false
            for (n in 0 until localSearchNeighbors) {
                val neighbor = problem.neighbor(current, random)
                val neighborCost = eval(neighbor)
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

    fun tournamentSelect(population: List<T>, fitness: List<Double>): T {
        var best = random.nextInt(populationSize)
        for (t in 1 until tournamentSize) {
            val candidate = random.nextInt(populationSize)
            if (problem.isBetter(fitness[candidate], fitness[best])) {
                best = candidate
            }
        }
        return population[best]
    }

    var population = MutableList(populationSize) { problem.randomSolution(random) }
    var fitness = MutableList(populationSize) { eval(population[it]) }

    for (i in 0 until populationSize) {
        val (ls, lsCost) = localSearch(population[i], fitness[i])
        population[i] = ls
        fitness[i] = lsCost
    }

    var bestIdx = if (problem.direction == OptimizationDirection.MINIMIZE)
        fitness.indices.minByOrNull { fitness[it] }!! else fitness.indices.maxByOrNull { fitness[it] }!!
    var bestSolution = problem.copy(population[bestIdx])
    var bestCost = fitness[bestIdx]

    for (gen in 0 until generations) {
        val sortedIndices = fitness.indices.sortedWith(Comparator { a, b ->
            if (problem.direction == OptimizationDirection.MINIMIZE)
                fitness[a].compareTo(fitness[b])
            else
                fitness[b].compareTo(fitness[a])
        })

        val nextPop = mutableListOf<T>()
        val nextFit = mutableListOf<Double>()

        for (e in 0 until elitism.coerceAtMost(populationSize)) {
            val idx = sortedIndices[e]
            nextPop.add(problem.copy(population[idx]))
            nextFit.add(fitness[idx])
        }

        while (nextPop.size < populationSize) {
            val parent1 = tournamentSelect(population, fitness)
            val parent2 = tournamentSelect(population, fitness)

            var child = if (random.nextDouble() < crossoverRate)
                crossover(parent1, parent2, random)
            else
                problem.copy(parent1)

            if (random.nextDouble() < mutationRate) {
                child = problem.neighbor(child, random)
            }

            var childFitness = eval(child)
            val (lsChild, lsCost) = localSearch(child, childFitness)
            child = lsChild
            childFitness = lsCost

            nextPop.add(child)
            nextFit.add(childFitness)

            if (problem.isBetter(childFitness, bestCost)) {
                bestSolution = problem.copy(child)
                bestCost = childFitness
            }
        }

        population = nextPop.toMutableList()
        fitness = nextFit.toMutableList()
    }

    return OptResult(bestSolution, bestCost, generations, evaluations)
}
