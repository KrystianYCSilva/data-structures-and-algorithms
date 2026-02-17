package br.uem.din.optimization

import kotlin.random.Random

/**
 * Problema MAX-SAT (Maximum Satisfiability).
 *
 * Dado um conjunto de cláusulas CNF (Conjunctive Normal Form) sobre [numVariables]
 * variáveis booleanas, encontrar uma atribuição que maximize o número de cláusulas
 * satisfeitas.
 *
 * Cada cláusula é representada como [IntArray] de literais, onde:
 * - literal positivo `k` (1-indexed) → variável k é `true`
 * - literal negativo `-k` → variável k é `false`
 *
 * A solução é representada como [BooleanArray] de [numVariables] posições (0-indexed).
 *
 * | Parâmetro | Descrição |
 * |-----------|-----------|
 * | numVariables | Número de variáveis booleanas |
 * | clauses | Lista de cláusulas, cada uma como IntArray de literais |
 *
 * Referência: Garey, M. R. & Johnson, D. S. "Computers and Intractability" (1979);
 *             Papadimitriou, C. H. "Computational Complexity" (1994), Cap. 10.
 *
 * @param numVariables número de variáveis booleanas.
 * @param clauses lista de cláusulas em formato CNF (literais 1-indexed, negativos para negação).
 */
public class MaxSatProblem(
    public val numVariables: Int,
    public val clauses: List<IntArray>
) : OptimizationProblem<BooleanArray> {

    override val direction: OptimizationDirection = OptimizationDirection.MAXIMIZE

    override fun evaluate(solution: BooleanArray): Double {
        var satisfied = 0
        for (clause in clauses) {
            var clauseSatisfied = false
            for (literal in clause) {
                val varIndex = if (literal > 0) literal - 1 else -literal - 1
                val value = if (literal > 0) solution[varIndex] else !solution[varIndex]
                if (value) {
                    clauseSatisfied = true
                    break
                }
            }
            if (clauseSatisfied) satisfied++
        }
        return satisfied.toDouble()
    }

    override fun randomSolution(random: Random): BooleanArray =
        BooleanArray(numVariables) { random.nextBoolean() }

    override fun neighbor(solution: BooleanArray, random: Random): BooleanArray {
        val result = solution.copyOf()
        val index = random.nextInt(numVariables)
        result[index] = !result[index]
        return result
    }

    override fun copy(solution: BooleanArray): BooleanArray = solution.copyOf()

    public companion object {
        /**
         * Gera uma instância aleatória de 3-SAT com [numVariables] variáveis e [numClauses] cláusulas.
         *
         * Cada cláusula tem exatamente 3 literais aleatórios (possivelmente negados).
         *
         * @param numVariables número de variáveis.
         * @param numClauses número de cláusulas.
         * @param random gerador de números aleatórios.
         */
        public fun random3SAT(
            numVariables: Int,
            numClauses: Int,
            random: Random = Random
        ): MaxSatProblem {
            val clauses = List(numClauses) {
                val vars = mutableSetOf<Int>()
                while (vars.size < 3) {
                    vars.add(random.nextInt(1, numVariables + 1))
                }
                vars.map { v -> if (random.nextBoolean()) v else -v }.toIntArray()
            }
            return MaxSatProblem(numVariables, clauses)
        }
    }
}
