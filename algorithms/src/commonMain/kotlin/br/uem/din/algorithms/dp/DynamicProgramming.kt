package br.uem.din.algorithms.dp

import kotlin.math.max

/**
 * Problema da Maior Subsequência Comum (Longest Common Subsequence - LCS).
 *
 * @param s1 a primeira string.
 * @param s2 a segunda string.
 * @return o comprimento da maior subsequência comum.
 *
 * Complexidade: O(m*n)
 */
public fun longestCommonSubsequence(s1: String, s2: String): Int {
    val m = s1.length
    val n = s2.length
    val dp = Array(m + 1) { IntArray(n + 1) }

    for (i in 1..m) {
        for (j in 1..n) {
            if (s1[i - 1] == s2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
            } else {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
            }
        }
    }
    return dp[m][n]
}

/**
 * Problema da Mochila 0/1 (Knapsack 0/1).
 *
 * @param weights array de pesos dos itens.
 * @param values array de valores dos itens.
 * @param capacity capacidade máxima da mochila.
 * @return o valor máximo que pode ser obtido.
 *
 * Complexidade: O(n*W)
 */
public fun knapsack01(weights: IntArray, values: IntArray, capacity: Int): Int {
    val n = weights.size
    val dp = Array(n + 1) { IntArray(capacity + 1) }

    for (i in 1..n) {
        for (w in 0..capacity) {
            if (weights[i - 1] <= w) {
                dp[i][w] = max(
                    values[i - 1] + dp[i - 1][w - weights[i - 1]],
                    dp[i - 1][w]
                )
            } else {
                dp[i][w] = dp[i - 1][w]
            }
        }
    }
    return dp[n][capacity]
}

/**
 * Distância de Edição (Levenshtein Distance).
 *
 * @param s1 string de origem.
 * @param s2 string de destino.
 * @return o número mínimo de operações (inserção, remoção, substituição) para transformar s1 em s2.
 *
 * Complexidade: O(m*n)
 */
public fun editDistance(s1: String, s2: String): Int {
    val m = s1.length
    val n = s2.length
    val dp = Array(m + 1) { IntArray(n + 1) }

    for (i in 0..m) dp[i][0] = i
    for (j in 0..n) dp[0][j] = j

    for (i in 1..m) {
        for (j in 1..n) {
            if (s1[i - 1] == s2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1]
            } else {
                dp[i][j] = 1 + minOf(
                    dp[i - 1][j],    // Remove
                    dp[i][j - 1],    // Insert
                    dp[i - 1][j - 1] // Replace
                )
            }
        }
    }
    return dp[m][n]
}

/**
 * Longest Increasing Subsequence (LIS).
 *
 * @param nums array de inteiros.
 * @return o comprimento da maior subsequência crescente.
 *
 * Complexidade: O(n^2)
 */
public fun longestIncreasingSubsequence(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    val n = nums.size
    val dp = IntArray(n) { 1 }

    for (i in 1 until n) {
        for (j in 0 until i) {
            if (nums[i] > nums[j]) {
                dp[i] = max(dp[i], dp[j] + 1)
            }
        }
    }
    return dp.maxOrNull() ?: 1
}

/**
 * Sequência de Fibonacci com Memoization.
 *
 * @param n índice do termo.
 * @return o n-ésimo termo de Fibonacci.
 *
 * Complexidade: O(n)
 */
public fun fibonacciDP(n: Int): Long {
    if (n <= 1) return n.toLong()
    val dp = LongArray(n + 1)
    dp[0] = 0
    dp[1] = 1
    for (i in 2..n) {
        dp[i] = dp[i - 1] + dp[i - 2]
    }
    return dp[n]
}

/**
 * Coin Change Problem (Min coins).
 *
 * @param coins valores das moedas disponíveis.
 * @param amount valor total a ser trocado.
 * @return número mínimo de moedas, ou -1 se não for possível.
 *
 * Complexidade: O(amount * n)
 */
public fun coinChange(coins: IntArray, amount: Int): Int {
    val max = amount + 1
    val dp = IntArray(amount + 1) { max }
    dp[0] = 0

    for (i in 1..amount) {
        for (coin in coins) {
            if (coin <= i) {
                dp[i] = kotlin.math.min(dp[i], dp[i - coin] + 1)
            }
        }
    }
    return if (dp[amount] > amount) -1 else dp[amount]
}
