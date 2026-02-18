package br.uem.din.algorithms.sorting

/**
 * Ordena a lista usando o algoritmo Merge Sort.
 *
 * O Merge Sort é um algoritmo de divisão e conquista. Divide a lista em duas metades,
 * chama a si mesmo para as duas metades e depois as mescla (merge).
 *
 * Complexidade Temporal:
 * - Pior caso: O(n log n)
 * - Melhor caso: O(n log n)
 * - Médio caso: O(n log n)
 *
 * Complexidade Espacial: O(n) (Requer array auxiliar).
 *
 * Estável: Sim.
 *
 * @param T tipo dos elementos, deve ser Comparable.
 * @param list a lista mutável a ser ordenada.
 */
public fun <T : Comparable<T>> mergeSort(list: MutableList<T>) {
    if (list.size <= 1) return

    val mid = list.size / 2
    val left = list.subList(0, mid).toMutableList() // Cria cópia
    val right = list.subList(mid, list.size).toMutableList() // Cria cópia

    mergeSort(left)
    mergeSort(right)

    merge(list, left, right)
}

private fun <T : Comparable<T>> merge(result: MutableList<T>, left: List<T>, right: List<T>) {
    var i = 0
    var j = 0
    var k = 0

    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            result[k++] = left[i++]
        } else {
            result[k++] = right[j++]
        }
    }

    while (i < left.size) {
        result[k++] = left[i++]
    }

    while (j < right.size) {
        result[k++] = right[j++]
    }
}
