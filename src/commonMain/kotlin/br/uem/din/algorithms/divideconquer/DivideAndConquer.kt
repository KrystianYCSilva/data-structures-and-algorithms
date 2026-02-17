package br.uem.din.algorithms.divideconquer

import kotlin.math.max

/**
 * Maximum Subarray Problem (Kadane's Algorithm - versão otimizada O(n) que é DP/Greedy,
 * mas clássico problema de D&C também (O(n log n)). Aqui implementamos Kadane que é O(n).
 *
 * @param nums array de entrada.
 * @return a soma máxima de um subarray contíguo.
 */
public fun maxSubarray(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    var maxSoFar = nums[0]
    var maxEndingHere = nums[0]

    for (i in 1 until nums.size) {
        maxEndingHere = max(nums[i], maxEndingHere + nums[i])
        maxSoFar = max(maxSoFar, maxEndingHere)
    }
    return maxSoFar
}

/**
 * Karatsuba Algorithm para multiplicação rápida de inteiros grandes.
 *
 * (Implementação simulada usando Long para demonstração, pois BigInteger não está disponível em Common).
 * Na prática, seria usado com arrays de dígitos ou BigInt customizado.
 *
 * @param x primeiro número.
 * @param y segundo número.
 * @return produto x * y.
 */
public fun karatsuba(x: Long, y: Long): Long {
    if (x < 10 || y < 10) return x * y

    val n = max(x.toString().length, y.toString().length)
    val m = n / 2

    val powerOf10 = pow10(m)

    val a = x / powerOf10
    val b = x % powerOf10
    val c = y / powerOf10
    val d = y % powerOf10

    val ac = karatsuba(a, c)
    val bd = karatsuba(b, d)
    val ad_bc = karatsuba(a + b, c + d) - ac - bd

    return ac * pow10(2 * m) + ad_bc * powerOf10 + bd
}

private fun pow10(exp: Int): Long {
    var res = 1L
    repeat(exp) { res *= 10 }
    return res
}

/**
 * Quick Select - Encontra o k-ésimo menor elemento.
 *
 * @param list lista de elementos.
 * @param k posição (1-based).
 * @return o k-ésimo menor elemento.
 */
public fun <T : Comparable<T>> quickSelect(list: MutableList<T>, k: Int): T {
    if (k < 1 || k > list.size) throw IllegalArgumentException("k out of bounds")
    return select(list, 0, list.size - 1, k - 1)
}

private fun <T : Comparable<T>> select(list: MutableList<T>, left: Int, right: Int, k: Int): T {
    if (left == right) return list[left]

    val pivotIndex = partition(list, left, right)

    return when {
        k == pivotIndex -> list[k]
        k < pivotIndex -> select(list, left, pivotIndex - 1, k)
        else -> select(list, pivotIndex + 1, right, k)
    }
}

private fun <T : Comparable<T>> partition(list: MutableList<T>, left: Int, right: Int): Int {
    val pivot = list[right]
    var i = left
    for (j in left until right) {
        if (list[j] <= pivot) {
            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
            i++
        }
    }
    val temp = list[i]
    list[i] = list[right]
    list[right] = temp
    return i
}
