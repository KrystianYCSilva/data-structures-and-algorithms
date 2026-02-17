package br.uem.din.algorithms.searching

/**
 * Realiza uma busca ternária na lista **ordenada** para encontrar o elemento alvo.
 *
 * Similar à busca binária, mas divide o array em três partes em vez de duas.
 *
 * @param list a lista ordenada onde buscar.
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(log_3 n)
 * Complexidade Espacial: O(1) (versão iterativa)
 */
public fun <T : Comparable<T>> ternarySearch(list: List<T>, target: T): Int {
    var left = 0
    var right = list.size - 1

    while (right >= left) {
        val mid1 = left + (right - left) / 3
        val mid2 = right - (right - left) / 3

        if (list[mid1] == target) {
            return mid1
        }
        if (list[mid2] == target) {
            return mid2
        }

        if (target < list[mid1]) {
            // O alvo está no primeiro terço
            right = mid1 - 1
        } else if (target > list[mid2]) {
            // O alvo está no terceiro terço
            left = mid2 + 1
        } else {
            // O alvo está no terço do meio
            left = mid1 + 1
            right = mid2 - 1
        }
    }
    return -1
}
