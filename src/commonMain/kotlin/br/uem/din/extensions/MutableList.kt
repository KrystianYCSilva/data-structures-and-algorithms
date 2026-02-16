package br.uem.din.extensions

/**
 * Troca os elementos nas posições [index1] e [index2] desta lista mutável.
 *
 * Complexidade: O(1).
 *
 * @param T o tipo dos elementos da lista.
 * @param index1 a posição do primeiro elemento.
 * @param index2 a posição do segundo elemento.
 */
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}
