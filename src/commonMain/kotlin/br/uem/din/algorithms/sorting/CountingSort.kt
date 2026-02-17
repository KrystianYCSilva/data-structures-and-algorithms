package br.uem.din.algorithms.sorting

/**
 * Ordena a lista de inteiros não-negativos usando o algoritmo Counting Sort.
 *
 * O Counting Sort é um algoritmo de ordenação de inteiros eficiente quando a variação
 * das chaves (k) não é significativamente maior que o número de elementos (n).
 * Funciona contando o número de objetos com valores de chave distintos.
 *
 * Nota: Esta implementação assume inteiros não-negativos.
 *
 * Complexidade Temporal: O(n + k), onde n é o número de elementos e k é o intervalo da entrada.
 * Complexidade Espacial: O(n + k).
 *
 * Estável: Sim.
 *
 * @param list a lista mutável de inteiros a ser ordenada.
 */
public fun countingSort(list: MutableList<Int>) {
    if (list.isEmpty()) return

    val max = list.maxOrNull() ?: 0
    val min = list.minOrNull() ?: 0

    if (min < 0) {
        throw IllegalArgumentException("Counting Sort implementation supports non-negative integers only.")
    }

    val count = IntArray(max + 1)
    val output = IntArray(list.size)

    // Armazena a contagem de cada caractere
    for (i in list.indices) {
        count[list[i]]++
    }

    // Altera count[i] para que count[i] contenha a posição real
    // deste dígito no array de saída
    for (i in 1..max) {
        count[i] += count[i - 1]
    }

    // Constrói o array de saída
    // Percorre de trás para frente para manter a estabilidade
    for (i in list.size - 1 downTo 0) {
        output[count[list[i]] - 1] = list[i]
        count[list[i]]--
    }

    // Copia o array de saída para a lista original
    for (i in list.indices) {
        list[i] = output[i]
    }
}
