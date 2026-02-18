package br.uem.din.optimization

import kotlin.random.Random

/**
 * Algoritmo Genético (Genetic Algorithm — GA).
 *
 * Metaheurística populacional inspirada na evolução biológica. Mantém uma população
 * de soluções que evolui através de seleção, crossover e mutação ao longo de gerações.
 *
 * Operadores:
 * - **Seleção por torneio**: seleciona o melhor indivíduo dentre [tournamentSize] candidatos aleatórios.
 * - **Crossover**: definido pelo caller via parâmetro funcional [crossover].
 * - **Mutação**: utiliza `neighbor()` do problema como operador de mutação.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | populationSize | Tamanho da população |
 * | generations | Número de gerações |
 * | crossoverRate | Probabilidade de crossover (0.0–1.0) |
 * | mutationRate | Probabilidade de mutação (0.0–1.0) |
 * | tournamentSize | Tamanho do torneio para seleção |
 * | elitism | Número de indivíduos de elite preservados |
 *
 * Referência: Holland, J. H. "Adaptation in Natural and Artificial Systems" (1975);
 *             Goldberg, D. E. "Genetic Algorithms in Search, Optimization and Machine Learning" (1989).
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização.
 * @param crossover operador de crossover que combina dois pais em um filho.
 * @param populationSize tamanho da população.
 * @param generations número de gerações.
 * @param crossoverRate probabilidade de aplicar crossover.
 * @param mutationRate probabilidade de aplicar mutação.
 * @param tournamentSize tamanho do torneio de seleção.
 * @param elitism número de indivíduos de elite preservados entre gerações.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> geneticAlgorithm(
    problem: OptimizationProblem<T>,
    crossover: (T, T, Random) -> T,
    populationSize: Int = 100,
    generations: Int = 500,
    crossoverRate: Double = 0.8,
    mutationRate: Double = 0.1,
    tournamentSize: Int = 5,
    elitism: Int = 2,
    random: Random = Random
): OptResult<T> {
    var evaluations = 0

    fun eval(s: T): Double {
        evaluations++
        return problem.evaluate(s)
    }

    var population = List(populationSize) { problem.randomSolution(random) }
    var fitness = population.map { eval(it) }

    var bestIdx = if (problem.direction == OptimizationDirection.MINIMIZE)
        fitness.indices.minByOrNull { fitness[it] }!! else fitness.indices.maxByOrNull { fitness[it] }!!
    var bestSolution = problem.copy(population[bestIdx])
    var bestCost = fitness[bestIdx]

    fun tournamentSelect(): T {
        var best = random.nextInt(populationSize)
        for (t in 1 until tournamentSize) {
            val candidate = random.nextInt(populationSize)
            if (problem.isBetter(fitness[candidate], fitness[best])) {
                best = candidate
            }
        }
        return population[best]
    }

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
            val parent1 = tournamentSelect()
            val parent2 = tournamentSelect()

            var child = if (random.nextDouble() < crossoverRate)
                crossover(parent1, parent2, random)
            else
                problem.copy(parent1)

            if (random.nextDouble() < mutationRate) {
                child = problem.neighbor(child, random)
            }

            val childFitness = eval(child)
            nextPop.add(child)
            nextFit.add(childFitness)

            if (problem.isBetter(childFitness, bestCost)) {
                bestSolution = problem.copy(child)
                bestCost = childFitness
            }
        }

        population = nextPop
        fitness = nextFit
    }

    return OptResult(bestSolution, bestCost, generations, evaluations)
}

/**
 * Crossover de ponto único para representações [DoubleArray] (otimização contínua).
 * Combina a primeira metade de [parent1] com a segunda metade de [parent2].
 */
public fun singlePointCrossover(parent1: DoubleArray, parent2: DoubleArray, random: Random): DoubleArray {
    val n = parent1.size
    val point = random.nextInt(1, n.coerceAtLeast(2))
    return DoubleArray(n) { i -> if (i < point) parent1[i] else parent2[i] }
}

/**
 * Crossover Order (OX) para representações de permutação [IntArray] (TSP, scheduling).
 * Preserva a ordem relativa dos elementos.
 *
 * Referência: Davis, L. "Applying Adaptive Algorithms to Epistatic Domains" (1985).
 */
public fun orderCrossover(parent1: IntArray, parent2: IntArray, random: Random): IntArray {
    val n = parent1.size
    val child = IntArray(n) { -1 }
    var start = random.nextInt(n)
    var end = random.nextInt(n)
    if (start > end) { val tmp = start; start = end; end = tmp }

    val taken = HashSet<Int>(n)
    for (i in start..end) {
        child[i] = parent1[i]
        taken.add(parent1[i])
    }

    var pos = (end + 1) % n
    for (i in 0 until n) {
        val gene = parent2[(end + 1 + i) % n]
        if (gene !in taken) {
            child[pos] = gene
            pos = (pos + 1) % n
        }
    }
    return child
}

/**
 * Crossover uniforme para representações binárias [BooleanArray] (Knapsack, MAX-SAT, etc.).
 * Cada gene é herdado de um dos pais com probabilidade 50%.
 *
 * Referência: Syswerda, G. "Uniform Crossover in Genetic Algorithms" (1989),
 *             Proceedings of ICGA.
 */
public fun uniformCrossover(parent1: BooleanArray, parent2: BooleanArray, random: Random): BooleanArray {
    val n = parent1.size
    return BooleanArray(n) { i -> if (random.nextBoolean()) parent1[i] else parent2[i] }
}

/**
 * Partially Mapped Crossover (PMX) para representações de permutação [IntArray].
 * Mantém posições absolutas de um segmento do pai 1 e preenche o restante com
 * mapeamento do pai 2, preservando a viabilidade da permutação.
 *
 * Referência: Goldberg, D. E. & Lingle, R. "Alleles, Loci, and the Traveling
 *             Salesman Problem" (1985), Proceedings of ICGA.
 */
public fun pmxCrossover(parent1: IntArray, parent2: IntArray, random: Random): IntArray {
    val n = parent1.size
    val child = IntArray(n) { -1 }
    var start = random.nextInt(n)
    var end = random.nextInt(n)
    if (start > end) { val tmp = start; start = end; end = tmp }

    for (i in start..end) {
        child[i] = parent1[i]
    }

    for (i in start..end) {
        val val2 = parent2[i]
        if (val2 !in child) {
            var pos = i
            while (pos in start..end) {
                val mapped = child[pos]
                pos = parent2.indexOf(mapped)
            }
            child[pos] = val2
        }
    }

    for (i in 0 until n) {
        if (child[i] == -1) {
            child[i] = parent2[i]
        }
    }
    return child
}
