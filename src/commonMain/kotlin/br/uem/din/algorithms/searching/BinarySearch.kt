package br.uem.din.algorithms.searching

/**
 * Realiza uma busca binária na lista **ordenada** para encontrar o elemento alvo.
 *
 * @param list a lista ordenada onde buscar.
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(log n)
 * Complexidade Espacial: O(1)
 */
public fun <T : Comparable<T>> binarySearch(list: List<T>, target: T): Int {
    var low = 0
    var high = list.size - 1

    while (low <= high) {
        val mid = low + (high - low) / 2
        val midVal = list[mid]
        val cmp = midVal.compareTo(target)

        if (cmp < 0) {
            low = mid + 1
        } else if (cmp > 0) {
            high = mid - 1
        } else {
            return mid // Encontrado
        }
    }
    return -1 // Não encontrado
}
