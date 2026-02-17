package br.uem.din.datastructures.probabilistic

import br.uem.din.datastructures.bitset.BitSet
import br.uem.din.datastructures.bitset.bitSetOf
import kotlin.math.ln
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.abs

/**
 * Filtro de Bloom (Bloom Filter) — estrutura de dados probabilística para teste de pertinência.
 *
 * Um Bloom Filter responde à pergunta "o elemento está no conjunto?" com duas possíveis respostas:
 * - **"Definitivamente não"** — sem falsos negativos
 * - **"Provavelmente sim"** — pode haver falsos positivos com probabilidade controlada
 *
 * Utiliza `k` funções de hash independentes e um bit array de tamanho `m`, calculados
 * automaticamente a partir do número esperado de inserções `n` e da taxa de falsos positivos
 * desejada `p`:
 * - `m = -(n * ln(p)) / (ln(2))²`
 * - `k = (m / n) * ln(2)`
 *
 * As `k` funções de hash são simuladas pela técnica de double hashing:
 * `h_i(x) = h1(x) + i * h2(x)`, conforme proposto por Kirsch & Mitzenmacher (2006).
 *
 * Complexidades:
 * - [add]: O(k)
 * - [contains]: O(k)
 * - Espaço: O(m) bits
 *
 * @param expectedInsertions número esperado de elementos a serem inseridos (n).
 * @param falsePositiveProbability taxa de falsos positivos desejada (padrão: 0.01 = 1%).
 *
 * Referência: Bloom, B. H. "Space/Time Trade-offs in Hash Coding with Allowable Errors" (1970);
 *             Kirsch, A. & Mitzenmacher, M. "Less Hashing, Same Performance" (2006).
 */
public class BloomFilter(private val expectedInsertions: Int, private val falsePositiveProbability: Double = 0.01) {

    private val bitSetSize: Int
    private val numHashFunctions: Int
    private val bitSet: BitSet

    init {
        bitSetSize = ceil((-expectedInsertions * ln(falsePositiveProbability)) / (ln(2.0).pow(2))).toInt()
        numHashFunctions = ceil((bitSetSize.toDouble() / expectedInsertions) * ln(2.0)).toInt()
        bitSet = bitSetOf(bitSetSize)
    }

    /**
     * Adiciona um elemento ao filtro, marcando os bits correspondentes às `k` funções de hash.
     *
     * Complexidade: O(k), onde k é o número de funções de hash.
     *
     * @param element a string a ser adicionada ao filtro.
     */
    public fun add(element: String) {
        val hash1 = element.hashCode()
        val hash2 = hash2(element)
        
        for (i in 0 until numHashFunctions) {
            val combinedHash = abs(hash1 + i * hash2)
            val index = combinedHash % bitSetSize
            bitSet.set(index)
        }
    }

    /**
     * Verifica se um elemento possivelmente pertence ao conjunto.
     *
     * Retorna `false` se qualquer bit correspondente não estiver marcado (certeza de ausência).
     * Retorna `true` se todos os bits estiverem marcados (possível falso positivo).
     *
     * Complexidade: O(k), onde k é o número de funções de hash.
     *
     * @param element a string a ser verificada.
     * @return `true` se o elemento possivelmente pertence ao conjunto, `false` se definitivamente não.
     */
    public fun contains(element: String): Boolean {
        val hash1 = element.hashCode()
        val hash2 = hash2(element)
        
        for (i in 0 until numHashFunctions) {
            val combinedHash = abs(hash1 + i * hash2)
            val index = combinedHash % bitSetSize
            if (!bitSet[index]) {
                return false
            }
        }
        return true
    }

    /**
     * Função de hash secundária baseada no algoritmo djb2 de Daniel J. Bernstein.
     *
     * Utilizada em conjunto com [String.hashCode] para a técnica de double hashing.
     *
     * @param s a string a ser hasheada.
     * @return o valor de hash calculado.
     */
    private fun hash2(s: String): Int {
        var hash = 5381
        for (char in s) {
            hash = ((hash shl 5) + hash) + char.code
        }
        return hash
    }
    
    /**
     * Retorna o tamanho do bit array interno (m).
     *
     * @return o número de bits no filtro.
     */
    public fun size(): Int = bitSetSize

    /**
     * Retorna o número de funções de hash utilizadas (k).
     *
     * @return a quantidade de funções de hash.
     */
    public fun countHashFunctions(): Int = numHashFunctions
}
