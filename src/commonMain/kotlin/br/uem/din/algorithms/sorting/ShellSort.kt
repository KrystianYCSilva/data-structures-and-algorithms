package br.uem.din.algorithms.sorting

/**
 * Ordena a lista usando o algoritmo Shell Sort.
 *
 * O Shell Sort é uma generalização do Insertion Sort. Ele permite a troca de itens distantes.
 * A ideia é organizar a lista de elementos de modo que, começando em qualquer posição,
 * se pegarmos cada h-ésimo elemento, produziremos uma lista ordenada. Tal lista é dita
 * h-ordenada.
 *
 * Esta implementação usa a sequência de gaps de Shell (n/2, n/4, ...).
 *
 * Complexidade Temporal:
 * - Depende da sequência de gap. Para esta (Shell), pior caso é O(n^2).
 * - Sequências otimizadas (ex: Hibbard, Knuth) podem atingir O(n^(3/2)) ou O(n^(4/3)).
 *
 * Complexidade Espacial: O(1) (In-place)
 *
 * Estável: Não.
 *
 * @param T tipo dos elementos, deve ser Comparable.
 * @param list a lista mutável a ser ordenada.
 */
public fun <T : Comparable<T>> shellSort(list: MutableList<T>) {
    val n = list.size
    var gap = n / 2

    while (gap > 0) {
        for (i in gap until n) {
            val temp = list[i]
            var j = i
            while (j >= gap && list[j - gap] > temp) {
                list[j] = list[j - gap]
                j -= gap
            }
            list[j] = temp
        }
        gap /= 2
    }
}
