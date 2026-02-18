package br.uem.din.algorithms.string

/**
 * Busca a primeira ocorrência do padrão [pattern] no texto [text] usando o algoritmo Rabin-Karp.
 *
 * Utiliza hashing rolling para comparar o hash do padrão com o hash da janela atual do texto.
 * Ideal para buscar múltiplos padrões (embora esta implementação busque um único).
 *
 * @param text o texto onde buscar.
 * @param pattern o padrão a ser encontrado.
 * @return o índice da primeira ocorrência, ou -1 se não encontrado.
 *
 * Complexidade Temporal: Médio O(n + m), Pior O(n*m) (muitas colisões de hash).
 * Complexidade Espacial: O(1)
 */
public fun rabinKarpSearch(text: String, pattern: String): Int {
    val m = pattern.length
    val n = text.length
    if (m > n) return -1
    if (m == 0) return 0

    val d = 256 // Número de caracteres no alfabeto de entrada
    val q = 101 // Um número primo

    var p = 0 // hash value for pattern
    var t = 0 // hash value for txt
    var h = 1

    // O valor de h seria "pow(d, m-1)%q"
    for (i in 0 until m - 1) {
        h = (h * d) % q
    }

    // Calcula hash inicial para pattern e primeira janela de text
    for (i in 0 until m) {
        p = (d * p + pattern[i].code) % q
        t = (d * t + text[i].code) % q
    }

    // Desliza o padrão sobre o texto
    for (i in 0..n - m) {
        // Se hash values coincidem, verifica caracteres um a um
        if (p == t) {
            var j = 0
            while (j < m) {
                if (text[i + j] != pattern[j]) break
                j++
            }
            if (j == m) return i
        }

        // Calcula hash para próxima janela de text: Remove digito lider, adiciona trailing
        if (i < n - m) {
            t = (d * (t - text[i].code * h) + text[i + m].code) % q
            // Trata valor negativo
            if (t < 0) t += q
        }
    }
    return -1
}
