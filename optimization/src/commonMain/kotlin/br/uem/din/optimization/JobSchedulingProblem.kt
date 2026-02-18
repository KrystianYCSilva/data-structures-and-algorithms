package br.uem.din.optimization

import kotlin.random.Random

/**
 * Problema de Escalonamento de Tarefas em Máquina Única (Single-Machine Scheduling).
 *
 * Dado um conjunto de [n] tarefas, cada uma com tempo de processamento [processingTimes]\[i\],
 * prazo de entrega [dueDates]\[i\] e peso/prioridade [weights]\[i\], encontrar uma
 * permutação que minimize o atraso ponderado total (weighted tardiness).
 *
 * A solução é representada como [IntArray] — uma permutação dos índices das tarefas
 * indicando a ordem de execução.
 *
 * Tardiness do job i = max(0, completionTime_i - dueDate_i).
 * Objetivo: minimizar Σ weight_i × tardiness_i.
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | processingTimes | Tempo de processamento de cada tarefa |
 * | dueDates | Prazo de entrega de cada tarefa |
 * | weights | Prioridade/peso de cada tarefa |
 *
 * Referência: Pinedo, M. L. "Scheduling: Theory, Algorithms, and Systems" (2016);
 *             Du, J. & Leung, J. Y.-T. "Minimizing Total Tardiness on One Machine
 *             is NP-Hard" (1990), Mathematics of Operations Research 15(3).
 *
 * @param processingTimes tempo de processamento de cada tarefa.
 * @param dueDates prazos de entrega.
 * @param weights prioridades.
 */
public class JobSchedulingProblem(
    public val processingTimes: IntArray,
    public val dueDates: IntArray,
    public val weights: IntArray
) : OptimizationProblem<IntArray> {

    public val n: Int = processingTimes.size

    override val direction: OptimizationDirection = OptimizationDirection.MINIMIZE

    override fun evaluate(solution: IntArray): Double {
        var time = 0
        var totalWeightedTardiness = 0.0
        for (pos in solution.indices) {
            val job = solution[pos]
            time += processingTimes[job]
            val tardiness = (time - dueDates[job]).coerceAtLeast(0)
            totalWeightedTardiness += weights[job].toDouble() * tardiness.toDouble()
        }
        return totalWeightedTardiness
    }

    override fun randomSolution(random: Random): IntArray {
        val perm = IntArray(n) { it }
        for (i in n - 1 downTo 1) {
            val j = random.nextInt(i + 1)
            val tmp = perm[i]; perm[i] = perm[j]; perm[j] = tmp
        }
        return perm
    }

    override fun neighbor(solution: IntArray, random: Random): IntArray {
        val result = solution.copyOf()
        val i = random.nextInt(n)
        var j = random.nextInt(n)
        while (j == i) j = random.nextInt(n)
        val tmp = result[i]; result[i] = result[j]; result[j] = tmp
        return result
    }

    override fun copy(solution: IntArray): IntArray = solution.copyOf()

    public companion object {
        /**
         * Gera uma instância aleatória com [n] tarefas.
         *
         * @param n número de tarefas.
         * @param maxProcessingTime tempo máximo de processamento.
         * @param random gerador de números aleatórios.
         */
        public fun random(
            n: Int,
            maxProcessingTime: Int = 20,
            random: Random = Random
        ): JobSchedulingProblem {
            val processingTimes = IntArray(n) { random.nextInt(1, maxProcessingTime + 1) }
            val totalTime = processingTimes.sum()
            val dueDates = IntArray(n) { random.nextInt(1, totalTime + 1) }
            val weights = IntArray(n) { random.nextInt(1, 11) }
            return JobSchedulingProblem(processingTimes, dueDates, weights)
        }
    }
}
