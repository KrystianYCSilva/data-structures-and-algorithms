package br.uem.din.algorithms.sorting

/**
 * Ordenação por bolha (Bubble Sort).
 *
 * Algoritmo de ordenação por comparação que percorre a lista repetidamente,
 * comparando pares adjacentes e trocando-os se estiverem fora de ordem.
 * O maior elemento "borbulha" para o final a cada iteração.
 *
 * Complexidades:
 * - Melhor caso: O(n²) — esta implementação não possui otimização de early termination
 * - Caso médio: O(n²)
 * - Pior caso: O(n²)
 * - Espaço: O(1) — ordenação in-place
 * - Estável: Sim
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 * @param list a lista a ser ordenada in-place.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 3, Sec. 5.2.2 — Sorting by Exchanging.
 */
public fun <T : Comparable<T>> bubbleSort(list: MutableList<T>) {
    val n = list.size
    for (i in 0 until n - 1) {
        for (j in 0 until n - i - 1) {
            if (list[j] > list[j + 1]) {
                val temp = list[j]
                list[j] = list[j + 1]
                list[j + 1] = temp
            }
        }
    }
}
