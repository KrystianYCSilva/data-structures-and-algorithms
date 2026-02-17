package br.uem.din.algorithms.searching

/**
 * Realiza uma busca linear na lista para encontrar o elemento alvo.
 *
 * @param list a lista onde buscar.
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(n)
 * Complexidade Espacial: O(1)
 */
public fun <T> linearSearch(list: List<T>, target: T): Int {
    for (i in list.indices) {
        if (list[i] == target) {
            return i
        }
    }
    return -1
}
