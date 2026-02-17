package br.uem.din.algorithms.sorting

import br.uem.din.extensions.swap

/**
 * Ordena a lista usando o algoritmo Heap Sort.
 *
 * O Heap Sort utiliza uma estrutura de Heap Binário (Max-Heap) para ordenar os elementos.
 * Primeiro constrói um Max-Heap a partir dos dados e depois extrai o maior elemento (raiz)
 * repetidamente, colocando-o no final da lista.
 *
 * Complexidade Temporal:
 * - Pior caso: O(n log n)
 * - Melhor caso: O(n log n)
 * - Médio caso: O(n log n)
 *
 * Complexidade Espacial: O(1) (In-place).
 *
 * Estável: Não.
 *
 * @param T tipo dos elementos, deve ser Comparable.
 * @param list a lista mutável a ser ordenada.
 */
public fun <T : Comparable<T>> heapSort(list: MutableList<T>) {
    val n = list.size

    // Constrói o heap (rearranja o array)
    for (i in n / 2 - 1 downTo 0) {
        heapify(list, n, i)
    }

    // Extrai um por um os elementos do heap
    for (i in n - 1 downTo 0) {
        // Move a raiz atual para o final
        list.swap(0, i)

        // Chama max heapify no heap reduzido
        heapify(list, i, 0)
    }
}

private fun <T : Comparable<T>> heapify(list: MutableList<T>, n: Int, i: Int) {
    var largest = i
    val left = 2 * i + 1
    val right = 2 * i + 2

    // Se filho esquerdo é maior que a raiz
    if (left < n && list[left] > list[largest]) {
        largest = left
    }

    // Se filho direito é maior que o maior até agora
    if (right < n && list[right] > list[largest]) {
        largest = right
    }

    // Se o maior não é a raiz
    if (largest != i) {
        list.swap(i, largest)

        // Recursivamente heapify a subárvore afetada
        heapify(list, n, largest)
    }
}
