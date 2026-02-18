package br.uem.din.algorithms.string

/**
 * Busca a primeira ocorrência do padrão [pattern] no texto [text] usando o método Naive (Força Bruta).
 *
 * @param text o texto onde buscar.
 * @param pattern o padrão a ser encontrado.
 * @return o índice da primeira ocorrência, ou -1 se não encontrado.
 *
 * Complexidade Temporal: O((n-m+1) * m) = O(n*m)
 */
public fun naiveStringSearch(text: String, pattern: String): Int {
    val n = text.length
    val m = pattern.length
    if (m > n) return -1
    if (m == 0) return 0

    for (i in 0..n - m) {
        var j = 0
        while (j < m && text[i + j] == pattern[j]) {
            j++
        }
        if (j == m) return i
    }
    return -1
}
