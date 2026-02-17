package br.uem.din.algorithms.backtracking

import kotlin.math.abs

/**
 * N-Queens Problem.
 *
 * @param n tamanho do tabuleiro (n x n) e número de rainhas.
 * @return uma lista de soluções, onde cada solução é um array de inteiros
 * representando as colunas das rainhas para cada linha.
 * (índice = linha, valor = coluna).
 */
public fun nQueens(n: Int): List<IntArray> {
    val solutions = mutableListOf<IntArray>()
    solveNQueens(n, 0, IntArray(n), solutions)
    return solutions
}

private fun solveNQueens(n: Int, row: Int, cols: IntArray, solutions: MutableList<IntArray>) {
    if (row == n) {
        solutions.add(cols.copyOf())
        return
    }

    for (col in 0 until n) {
        if (isValid(row, col, cols)) {
            cols[row] = col
            solveNQueens(n, row + 1, cols, solutions)
        }
    }
}

private fun isValid(row: Int, col: Int, cols: IntArray): Boolean {
    for (prevRow in 0 until row) {
        val prevCol = cols[prevRow]
        if (prevCol == col) return false // Mesma coluna
        if (abs(prevCol - col) == abs(prevRow - row)) return false // Mesma diagonal
    }
    return true
}

/**
 * Subset Sum Problem.
 *
 * Verifica se existe um subconjunto com soma igual a target.
 *
 * @param set conjunto de números positivos.
 * @param target soma alvo.
 * @return `true` se existir tal subconjunto.
 */
public fun subsetSum(set: IntArray, target: Int): Boolean {
    return solveSubsetSum(set, set.size - 1, target)
}

private fun solveSubsetSum(set: IntArray, n: Int, target: Int): Boolean {
    if (target == 0) return true
    if (n < 0) return false
    if (set[n] > target) return solveSubsetSum(set, n - 1, target)

    return solveSubsetSum(set, n - 1, target) || solveSubsetSum(set, n - 1, target - set[n])
}

/**
 * Gerador de Permutações.
 *
 * @param nums array de números.
 * @return lista de todas as permutações possíveis.
 */
public fun permutations(nums: IntArray): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    solvePermutations(nums.toMutableList(), 0, result)
    return result
}

private fun solvePermutations(nums: MutableList<Int>, start: Int, result: MutableList<List<Int>>) {
    if (start == nums.size) {
        result.add(nums.toList())
        return
    }
    for (i in start until nums.size) {
        swap(nums, start, i)
        solvePermutations(nums, start + 1, result)
        swap(nums, start, i) // Backtrack
    }
}

private fun swap(nums: MutableList<Int>, i: Int, j: Int) {
    val temp = nums[i]
    nums[i] = nums[j]
    nums[j] = temp
}
