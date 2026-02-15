package br.uem.din.datastructures.bitset

/**
 * Bit Array (BitSet) — array compacto de bits para manipulação eficiente de flags e conjuntos.
 *
 * Cada bit ocupa apenas 1 bit de memória (empacotado em palavras de 32 ou 64 bits),
 * em contraste com `BooleanArray` que usa 1 byte por elemento.
 * Ideal para representar conjuntos densos de inteiros, filtros de bits, e manipulação bit-a-bit.
 *
 * Implementação multiplataforma (expect/actual):
 * - **JVM**: delega para [java.util.BitSet], que usa `long[]` internamente.
 * - **JS**: implementação manual usando [IntArray] (palavras de 32 bits).
 * - **Native**: implementação manual usando [LongArray] (palavras de 64 bits).
 *
 * O BitSet cresce automaticamente quando um bit além da capacidade atual é acessado.
 *
 * Complexidades:
 * - [set], [clear], [get]: O(1)
 * - [length]: O(w) onde w é o número de palavras
 * - [isEmpty]: O(w)
 * - [size]: O(1)
 *
 * @param size a capacidade inicial em bits (padrão: 64). O BitSet pode crescer além deste valor.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 4A — Combinatorial Algorithms,
 *             Seção 7.1.3 — Bitwise Tricks and Techniques.
 *
 * @see java.util.BitSet (JVM)
 */
expect class BitSet(size: Int = 64) {

    /**
     * Define o bit no índice especificado como `true`.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based).
     */
    fun set(index: Int)

    /**
     * Define o bit no índice especificado para o valor informado.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based).
     * @param value `true` para ligar o bit, `false` para desligar.
     */
    fun set(index: Int, value: Boolean)

    /**
     * Define o bit no índice especificado como `false`.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based).
     */
    fun clear(index: Int)

    /**
     * Retorna o valor do bit no índice especificado.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based).
     * @return `true` se o bit estiver ligado, `false` caso contrário.
     */
    operator fun get(index: Int): Boolean

    /**
     * Retorna o número de bits de espaço efetivamente alocado.
     *
     * Este valor é sempre um múltiplo do tamanho da palavra interna (32 ou 64).
     *
     * Complexidade: O(1).
     *
     * @return o número total de bits alocados.
     */
    fun size(): Int

    /**
     * Retorna o "tamanho lógico": índice do bit mais alto ligado + 1.
     *
     * Retorna 0 se nenhum bit estiver ligado.
     *
     * Complexidade: O(w) onde w é o número de palavras.
     *
     * @return o tamanho lógico do BitSet.
     */
    fun length(): Int

    /**
     * Retorna `true` se nenhum bit estiver ligado neste BitSet.
     *
     * Complexidade: O(w) onde w é o número de palavras.
     *
     * @return `true` se o BitSet estiver vazio.
     */
    fun isEmpty(): Boolean
}
