package br.uem.din.algorithms.searching

import kotlin.math.min

/**
 * Realiza uma busca exponencial na lista **ordenada**.
 *
 * Útil para listas ilimitadas ou muito grandes onde o tamanho é desconhecido,
 * ou quando o elemento está próximo do início.
 *
 * @param list a lista ordenada onde buscar.
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(log i), onde i é o índice do elemento buscado.
 * Complexidade Espacial: O(1)
 */
public fun <T : Comparable<T>> exponentialSearch(list: List<T>, target: T): Int {
    val n = list.size
    if (n == 0) return -1
    if (list[0] == target) return 0

    var i = 1
    while (i < n && list[i] <= target) {
        i *= 2
    }

    // Chama binary search no intervalo encontrado [i/2, min(i, n-1)]
    return binarySearchRange(list, target, i / 2, min(i, n - 1))
}

// Helper para binary search num intervalo específico
private fun <T : Comparable<T>> binarySearchRange(list: List<T>, target: T, left: Int, right: Int): Int {
    var l = left
    var r = right
    while (l <= r) {
        val mid = l + (r - l) / 2
        val cmp = list[mid].compareTo(target)
        if (cmp == 0) return mid
        if (cmp < 0) l = mid + 1
        else r = mid - 1
    }
    return -1
}
