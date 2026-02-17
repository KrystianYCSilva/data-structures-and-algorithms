package br.uem.din.optimization

import kotlin.random.Random

/**
 * Large Neighborhood Search (LNS — Busca em Grande Vizinhança).
 *
 * Metaheurística que alterna entre destruição parcial e reconstrução da solução.
 * A cada iteração, uma fração da solução é removida (destroy) e depois reconstruída
 * (repair), explorando eficientemente grandes porções do espaço de busca.
 *
 * O operador destroy genérico é implementado como múltiplas perturbações consecutivas
 * (proporcional ao grau de destruição). O operador repair é implementado como uma
 * fase de busca local que refina a solução destruída.
 *
 * Ciclo LNS:
 * 1. **Destroy**: aplica [destroyDegree] perturbações consecutivas.
 * 2. **Repair**: refina via busca local (first-improvement).
 * 3. **Aceitação**: aceita se melhorou globalmente (critério guloso) ou com
 *    probabilidade SA-like quando [acceptanceTemp] > 0.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | maxIterations | Iterações do ciclo principal |
 * | destroyDegree | Intensidade da destruição (perturbações aplicadas) |
 * | repairSteps | Iterações da busca local de reparo |
 * | acceptanceTemp | Temperatura para aceitação SA-like (0 = guloso puro) |
 *
 * Referência: Shaw, P. "Using Constraint Programming and Local Search Methods to Solve
 *             Vehicle Routing Problems" (1998), CP-98;
 *             Pisinger, D. & Ropke, S. "A General Heuristic for Vehicle Routing Problems"
 *             (2007), Computers & Operations Research 34(8).
 *
 * @param T tipo da representação da solução.
 * @param problem o problema de otimização.
 * @param destroy operador de destruição customizado. Se null, usa perturbações consecutivas.
 * @param repair operador de reparo customizado. Se null, usa busca local first-improvement.
 * @param maxIterations número de iterações do ciclo principal.
 * @param destroyDegree intensidade da destruição (número de perturbações).
 * @param repairSteps iterações da busca local de reparo.
 * @param repairNeighbors vizinhos avaliados por passo do reparo.
 * @param acceptanceTemp temperatura para aceitação probabilística (0.0 = guloso).
 * @param coolingRate taxa de resfriamento da temperatura de aceitação.
 * @param random gerador de números aleatórios.
 * @return resultado da otimização contendo a melhor solução encontrada.
 */
public fun <T> largeNeighborhoodSearch(
    problem: OptimizationProblem<T>,
    destroy: ((T, Random) -> T)? = null,
    repair: ((T, Random) -> T)? = null,
    maxIterations: Int = 1_000,
    destroyDegree: Int = 10,
    repairSteps: Int = 100,
    repairNeighbors: Int = 10,
    acceptanceTemp: Double = 0.0,
    coolingRate: Double = 0.995,
    random: Random = Random
): OptResult<T> {
    var evaluations = 0

    fun defaultDestroy(solution: T): T {
        var result = solution
        for (d in 0 until destroyDegree) {
            result = problem.neighbor(result, random)
        }
        return result
    }

    fun defaultRepair(solution: T): T {
        var current = solution
        var currentCost = problem.evaluate(current)
        evaluations++

        for (step in 0 until repairSteps) {
            var improved = false
            for (n in 0 until repairNeighbors) {
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
        return current
    }

    var current = problem.randomSolution(random)
    current = if (repair != null) repair(current, random) else defaultRepair(current)
    var currentCost = problem.evaluate(current)
    evaluations++
    var bestSolution = problem.copy(current)
    var bestCost = currentCost
    var temperature = acceptanceTemp

    for (iter in 0 until maxIterations) {
        val destroyed = if (destroy != null) destroy(current, random) else defaultDestroy(current)
        val repaired = if (repair != null) repair(destroyed, random) else defaultRepair(destroyed)
        val repairedCost = problem.evaluate(repaired)
        evaluations++

        val accept = if (problem.isBetter(repairedCost, currentCost)) {
            true
        } else if (temperature > 1e-12) {
            val delta = if (problem.direction == OptimizationDirection.MINIMIZE)
                repairedCost - currentCost
            else
                currentCost - repairedCost
            random.nextDouble() < kotlin.math.exp(-delta / temperature)
        } else {
            false
        }

        if (accept) {
            current = repaired
            currentCost = repairedCost
        }

        if (problem.isBetter(repairedCost, bestCost)) {
            bestSolution = problem.copy(repaired)
            bestCost = repairedCost
        }

        temperature *= coolingRate
    }

    return OptResult(bestSolution, bestCost, maxIterations, evaluations)
}
