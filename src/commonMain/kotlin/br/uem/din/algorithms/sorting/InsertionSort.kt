package br.uem.din.algorithms.sorting

/**
 * Ordenação por inserção (Insertion Sort).
 *
 * Algoritmo de ordenação que constrói a sequência ordenada um elemento por vez,
 * inserindo cada novo elemento na posição correta dentro da porção já ordenada.
 * Eficiente para listas pequenas ou quase ordenadas.
 *
 * Complexidades:
 * - Melhor caso: O(n) — quando a lista já está ordenada
 * - Caso médio: O(n²)
 * - Pior caso: O(n²) — quando a lista está em ordem reversa
 * - Espaço: O(1) — ordenação in-place
 * - Estável: Sim
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 * @param list a lista a ser ordenada in-place.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 2.1 — Insertion Sort;
 *             Knuth, D. E. "The Art of Computer Programming", Vol. 3, Sec. 5.2.1 — Sorting by Insertion.
 */
fun <T : Comparable<T>> insertionSort(list: MutableList<T>) {
    for (i in 1 until list.size) {
        val key = list[i]
        var j = i - 1
        while (j >= 0 && list[j] > key) {
            list[j + 1] = list[j]
            j--
        }
        list[j + 1] = key
    }
}
