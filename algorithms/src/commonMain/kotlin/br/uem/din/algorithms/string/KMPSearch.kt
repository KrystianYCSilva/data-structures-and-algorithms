package br.uem.din.algorithms.string

/**
 * Busca a primeira ocorrência do padrão [pattern] no texto [text] usando o algoritmo KMP (Knuth-Morris-Pratt).
 *
 * Utiliza um array de prefixos (LPS - Longest Prefix Suffix) para evitar comparações redundantes
 * após uma falha de correspondência.
 *
 * @param text o texto onde buscar.
 * @param pattern o padrão a ser encontrado.
 * @return o índice da primeira ocorrência, ou -1 se não encontrado.
 *
 * Complexidade Temporal: O(n + m)
 * Complexidade Espacial: O(m)
 */
public fun kmpSearch(text: String, pattern: String): Int {
    val n = text.length
    val m = pattern.length
    if (m == 0) return 0
    if (m > n) return -1

    val lps = computeLPSArray(pattern)
    var i = 0 // índice para text
    var j = 0 // índice para pattern

    while (i < n) {
        if (pattern[j] == text[i]) {
            j++
            i++
        }
        if (j == m) {
            return i - j // Encontrado
        } else if (i < n && pattern[j] != text[i]) {
            if (j != 0) {
                j = lps[j - 1]
            } else {
                i++
            }
        }
    }
    return -1
}

private fun computeLPSArray(pattern: String): IntArray {
    val m = pattern.length
    val lps = IntArray(m)
    var len = 0
    var i = 1
    lps[0] = 0

    while (i < m) {
        if (pattern[i] == pattern[len]) {
            len++
            lps[i] = len
            i++
        } else {
            if (len != 0) {
                len = lps[len - 1]
            } else {
                lps[i] = 0
                i++
            }
        }
    }
    return lps
}
