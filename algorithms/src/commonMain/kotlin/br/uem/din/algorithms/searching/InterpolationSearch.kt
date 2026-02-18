package br.uem.din.algorithms.searching

/**
 * Realiza uma busca por interpolação em uma lista de inteiros **ordenada e uniformemente distribuída**.
 *
 * Melhora a busca binária tentando "adivinhar" a posição baseada no valor dos elementos.
 *
 * @param list a lista ordenada de inteiros.
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(log log n) para dados uniformes, O(n) pior caso.
 * Complexidade Espacial: O(1)
 */
public fun interpolationSearch(list: List<Int>, target: Int): Int {
    var lo = 0
    var hi = list.size - 1

    while (lo <= hi && target >= list[lo] && target <= list[hi]) {
        if (lo == hi) {
            if (list[lo] == target) return lo
            return -1
        }

        // Fórmula da interpolação: lo + ((target - list[lo]) * (hi - lo) / (list[hi] - list[lo]))
        val pos = lo + (((target - list[lo]).toDouble() / (list[hi] - list[lo])) * (hi - lo)).toInt()

        if (list[pos] == target) {
            return pos
        }

        if (list[pos] < target) {
            lo = pos + 1
        } else {
            hi = pos - 1
        }
    }
    return -1
}
