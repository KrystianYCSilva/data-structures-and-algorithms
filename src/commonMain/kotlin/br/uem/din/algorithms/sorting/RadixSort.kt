package br.uem.din.algorithms.sorting

/**
 * Ordena a lista de inteiros não-negativos usando o algoritmo Radix Sort.
 *
 * O Radix Sort processa os elementos dígito a dígito, começando pelo dígito menos significativo
 * (LSD - Least Significant Digit). Utiliza uma versão estável do Counting Sort como sub-rotina.
 *
 * Complexidade Temporal: O(d * (n + b)), onde d é o número de dígitos, n é o número de elementos
 * e b é a base (neste caso, 10).
 * Complexidade Espacial: O(n + b).
 *
 * Estável: Sim.
 *
 * @param list a lista mutável de inteiros a ser ordenada.
 */
public fun radixSort(list: MutableList<Int>) {
    if (list.isEmpty()) return

    val max = list.maxOrNull() ?: 0
    
    // Faz o counting sort para cada dígito.
    // exp é 10^i onde i é o dígito atual sendo passado
    var exp = 1
    while (max / exp > 0) {
        countingSortByDigit(list, exp)
        exp *= 10
    }
}

private fun countingSortByDigit(list: MutableList<Int>, exp: Int) {
    val n = list.size
    val output = IntArray(n)
    val count = IntArray(10)

    // Armazena a contagem de ocorrências em count[]
    for (i in 0 until n) {
        val index = (list[i] / exp) % 10
        count[index]++
    }

    // Muda count[i] para conter a posição atual deste dígito em output[]
    for (i in 1 until 10) {
        count[i] += count[i - 1]
    }

    // Constrói o array de saída
    for (i in n - 1 downTo 0) {
        val index = (list[i] / exp) % 10
        output[count[index] - 1] = list[i]
        count[index]--
    }

    // Copia o array de saída para a lista original
    for (i in 0 until n) {
        list[i] = output[i]
    }
}
