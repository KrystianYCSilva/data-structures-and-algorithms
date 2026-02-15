package br.uem.din.datastructures.bitset

/**
 * Bit Array (BitSet) — array compacto de bits para manipulação eficiente de flags e conjuntos.
 *
 * Cada bit ocupa apenas 1 bit de memória (empacotado em palavras de 32 ou 64 bits),
 * em contraste com `BooleanArray` que usa 1 byte por elemento.
 * Ideal para representar conjuntos densos de inteiros, filtros de bits, e manipulação bit-a-bit.
 * Implementa [Iterable]<[Int]> para permitir iteração idiomática sobre os índices dos bits ligados.
 *
 * Implementação multiplataforma (expect/actual):
 * - **JVM**: delega para [java.util.BitSet], que usa `long[]` internamente.
 * - **JS**: implementação manual usando [IntArray] (palavras de 32 bits).
 * - **Native**: implementação manual usando [LongArray] (palavras de 64 bits).
 *
 * O BitSet cresce automaticamente quando um bit além da capacidade atual é acessado.
 *
 * Complexidades:
 * | Operação                          | Complexidade |
 * |-----------------------------------|-------------|
 * | [set], [clear] (single), [get]    | O(1)        |
 * | [length]                          | O(w)        |
 * | [isEmpty]                         | O(w)        |
 * | [size]                            | O(1)        |
 * | [cardinality]                     | O(w)        |
 * | [clear] (all)                     | O(w)        |
 * | [and], [or], [xor], [andNot]      | O(w)        |
 * | [iterator]                        | O(1) criação; O(n) travessia |
 *
 * onde w = número de palavras internas, n = número de bits ligados.
 *
 * @param size a capacidade inicial em bits (padrão: 64). O BitSet pode crescer além deste valor.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 4A — Combinatorial Algorithms,
 *             Seção 7.1.3 — Bitwise Tricks and Techniques.
 *
 * @see java.util.BitSet (JVM)
 */
expect class BitSet(size: Int = 64) : Iterable<Int> {

    /**
     * Define o bit no índice especificado como `true`.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based). Deve ser >= 0.
     * @throws IllegalArgumentException se [index] for negativo.
     */
    fun set(index: Int)

    /**
     * Define o bit no índice especificado para o valor informado.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based). Deve ser >= 0.
     * @param value `true` para ligar o bit, `false` para desligar.
     * @throws IllegalArgumentException se [index] for negativo.
     */
    fun set(index: Int, value: Boolean)

    /**
     * Define o bit no índice especificado como `false`.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based). Deve ser >= 0.
     */
    fun clear(index: Int)

    /**
     * Define todos os bits como `false`, efetivamente esvaziando o BitSet.
     *
     * Complexidade: O(w) onde w é o número de palavras.
     */
    fun clear()

    /**
     * Retorna o valor do bit no índice especificado.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based).
     * @return `true` se o bit estiver ligado, `false` caso contrário (incluindo índices além da capacidade).
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

    /**
     * Retorna o número de bits ligados (population count / popcount).
     *
     * Complexidade: O(w) onde w é o número de palavras.
     *
     * @return o número de bits com valor `true`.
     */
    fun cardinality(): Int

    /**
     * Retorna o índice do próximo bit ligado a partir de (inclusive) [fromIndex],
     * ou -1 se não houver mais bits ligados.
     *
     * Complexidade: O(w) no pior caso.
     *
     * @param fromIndex o índice inicial (0-based, inclusive).
     * @return índice do próximo bit ligado, ou -1.
     */
    fun nextSetBit(fromIndex: Int): Int

    /**
     * Realiza a operação AND bit-a-bit com outro BitSet (interseção de conjuntos).
     * Modifica este BitSet in-place.
     *
     * Complexidade: O(w) onde w = max(this.words, other.words).
     *
     * @param other o outro BitSet para a operação AND.
     */
    fun and(other: BitSet)

    /**
     * Realiza a operação OR bit-a-bit com outro BitSet (união de conjuntos).
     * Modifica este BitSet in-place.
     *
     * Complexidade: O(w).
     *
     * @param other o outro BitSet para a operação OR.
     */
    fun or(other: BitSet)

    /**
     * Realiza a operação XOR bit-a-bit com outro BitSet (diferença simétrica).
     * Modifica este BitSet in-place.
     *
     * Complexidade: O(w).
     *
     * @param other o outro BitSet para a operação XOR.
     */
    fun xor(other: BitSet)

    /**
     * Realiza a operação AND-NOT bit-a-bit com outro BitSet (diferença de conjuntos).
     * Remove de este BitSet todos os bits que estão ligados em [other].
     * Modifica este BitSet in-place.
     *
     * Complexidade: O(w).
     *
     * @param other o outro BitSet para a operação AND-NOT.
     */
    fun andNot(other: BitSet)

    /**
     * Retorna um [Iterator] sobre os índices de todos os bits ligados, em ordem crescente.
     *
     * Complexidade: O(1) para criação; O(n) para travessia completa, onde n = [cardinality].
     *
     * @return iterador sobre os índices dos bits ligados.
     */
    override fun iterator(): Iterator<Int>

    /**
     * Retorna representação textual no formato `{i, j, k}` com os índices dos bits ligados.
     *
     * Complexidade: O(n) onde n = [cardinality].
     *
     * @return string no formato `{índices dos bits ligados}`.
     */
    override fun toString(): String

    /**
     * Compara este BitSet com outro objeto para igualdade.
     * Dois BitSets são iguais se possuem exatamente os mesmos bits ligados.
     *
     * @param other o objeto a ser comparado.
     * @return `true` se [other] for um [BitSet] com os mesmos bits ligados.
     */
    override fun equals(other: Any?): Boolean

    /**
     * Retorna o hash code baseado nos bits ligados.
     *
     * @return o hash code deste BitSet.
     */
    override fun hashCode(): Int
}
