package br.uem.din.algorithms.searching

import kotlin.math.sqrt
import kotlin.math.min

/**
 * Realiza uma busca por salto (Jump Search) na lista **ordenada**.
 *
 * Funciona pulando um número fixo de passos (raiz quadrada de n) e depois fazendo
 * uma busca linear no bloco onde o elemento pode estar.
 *
 * @param list a lista ordenada onde buscar.
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(√n)
 * Complexidade Espacial: O(1)
 */
public fun <T : Comparable<T>> jumpSearch(list: List<T>, target: T): Int {
    val n = list.size
    if (n == 0) return -1

    var step = sqrt(n.toDouble()).toInt()
    var prev = 0

    // Pula enquanto o elemento atual for menor que o target
    while (list[min(step, n) - 1] < target) {
        prev = step
        step += sqrt(n.toDouble()).toInt()
        if (prev >= n) return -1
    }

    // Busca linear no bloco identificado
    while (list[prev] < target) {
        prev++
        if (prev == min(step, n)) return -1
    }

    if (list[prev] == target) {
        return prev
    }

    return -1
}
