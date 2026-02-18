package br.uem.din.extensions

import br.uem.din.algorithms.string.kmpSearch
import br.uem.din.algorithms.dp.editDistance
import br.uem.din.algorithms.dp.longestCommonSubsequence

/**
 * Extensões para a classe [String] integrando algoritmos clássicos.
 */

/**
 * Calcula a Distância de Levenshtein (Edit Distance) até outra string.
 *
 * @param other a string alvo.
 * @return o número mínimo de edições.
 */
public infix fun String.levenshteinTo(other: String): Int {
    return editDistance(this, other)
}

/**
 * Encontra o índice da primeira ocorrência do padrão usando KMP.
 *
 * @param pattern o padrão a ser buscado.
 * @return o índice inicial ou -1 se não encontrado.
 */
public fun String.findKMP(pattern: String): Int {
    return kmpSearch(this, pattern)
}

/**
 * Calcula o comprimento da Maior Subsequência Comum (LCS) com outra string.
 *
 * @param other a outra string.
 * @return comprimento da LCS.
 */
public infix fun String.lcsLength(other: String): Int {
    return longestCommonSubsequence(this, other)
}
