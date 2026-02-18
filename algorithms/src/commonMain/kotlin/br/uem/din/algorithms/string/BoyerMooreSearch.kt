package br.uem.din.algorithms.string

import kotlin.math.max

/**
 * Busca a primeira ocorrência do padrão [pattern] no texto [text] usando o algoritmo Boyer-Moore.
 *
 * Utiliza a heurística de "Bad Character" para pular caracteres que não coincidem.
 * (Implementação simplificada apenas com Bad Character Heuristic, conhecida como Boyer-Moore-Horspool
 * ou apenas Bad Character Rule, que já oferece excelente performance na prática).
 *
 * @param text o texto onde buscar.
 * @param pattern o padrão a ser encontrado.
 * @return o índice da primeira ocorrência, ou -1 se não encontrado.
 *
 * Complexidade Temporal: Melhor O(n/m), Pior O(n*m).
 * Complexidade Espacial: O(sigma) onde sigma é o tamanho do alfabeto.
 */
public fun boyerMooreSearch(text: String, pattern: String): Int {
    val m = pattern.length
    val n = text.length
    if (m == 0) return 0
    if (m > n) return -1

    val badChar = IntArray(256) { -1 }

    // Preenche tabela de bad character
    for (i in 0 until m) {
        val charCode = pattern[i].code
        if (charCode < 256) {
            badChar[charCode] = i
        }
    }

    var s = 0 // s é o shift do padrão em relação ao texto
    while (s <= (n - m)) {
        var j = m - 1

        // Reduz j enquanto caracteres coincidem
        while (j >= 0 && pattern[j] == text[s + j]) {
            j--
        }

        if (j < 0) {
            return s // Padrão encontrado na posição s
            // Se quiséssemos encontrar todas, faríamos s += (if (s+m < n) m-badChar[text[s+m]] else 1)
        } else {
            // Pula baseado no bad character da posição de falha no texto
            val charCode = text[s + j].code
            val bcValue = if (charCode < 256) badChar[charCode] else -1
            s += max(1, j - bcValue)
        }
    }
    return -1
}
