package br.uem.din.algorithms.sorting

import br.uem.din.extensions.swap

/**
 * Ordena a lista usando o algoritmo Quick Sort.
 *
 * O Quick Sort é um algoritmo de divisão e conquista. Escolhe um elemento como pivô
 * e particiona o array ao redor do pivô escolhido.
 *
 * Esta implementação usa o esquema de partição de Lomuto (último elemento como pivô).
 *
 * Complexidade Temporal:
 * - Pior caso: O(n^2) (pode ocorrer se o array já estiver ordenado e o pivô for ruim)
 * - Melhor caso: O(n log n)
 * - Médio caso: O(n log n)
 *
 * Complexidade Espacial: O(log n) devido à recursão da pilha.
 *
 * Estável: Não.
 *
 * @param T tipo dos elementos, deve ser Comparable.
 * @param list a lista mutável a ser ordenada.
 */
public fun <T : Comparable<T>> quickSort(list: MutableList<T>) {
    if (list.size > 1) {
        quickSortRecursive(list, 0, list.size - 1)
    }
}

private fun <T : Comparable<T>> quickSortRecursive(list: MutableList<T>, low: Int, high: Int) {
    if (low < high) {
        val pivotIndex = partition(list, low, high)
        quickSortRecursive(list, low, pivotIndex - 1)
        quickSortRecursive(list, pivotIndex + 1, high)
    }
}

private fun <T : Comparable<T>> partition(list: MutableList<T>, low: Int, high: Int): Int {
    val pivot = list[high]
    var i = low - 1

    for (j in low until high) {
        if (list[j] <= pivot) {
            i++
            list.swap(i, j)
        }
    }
    list.swap(i + 1, high)
    return i + 1
}
