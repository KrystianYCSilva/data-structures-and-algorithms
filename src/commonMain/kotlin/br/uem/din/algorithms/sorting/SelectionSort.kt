package br.uem.din.algorithms.sorting

import br.uem.din.extensions.swap

/**
 * Ordena a lista usando o algoritmo Selection Sort.
 *
 * O Selection Sort divide a lista em duas partes: a sublista de itens já ordenados
 * e a sublista de itens restantes. O algoritmo seleciona o menor elemento da sublista
 * não ordenada e o troca com o elemento no início da sublista não ordenada.
 *
 * Complexidade Temporal:
 * - Pior caso: O(n^2)
 * - Melhor caso: O(n^2)
 * - Médio caso: O(n^2)
 *
 * Complexidade Espacial: O(1) (In-place)
 *
 * Estável: Não (por padrão, trocas podem alterar ordem relativa de iguais).
 *
 * @param T tipo dos elementos, deve ser Comparable.
 * @param list a lista mutável a ser ordenada.
 */
public fun <T : Comparable<T>> selectionSort(list: MutableList<T>) {
    val n = list.size
    for (i in 0 until n - 1) {
        var minIndex = i
        for (j in i + 1 until n) {
            if (list[j] < list[minIndex]) {
                minIndex = j
            }
        }
        if (minIndex != i) {
            list.swap(i, minIndex)
        }
    }
}
