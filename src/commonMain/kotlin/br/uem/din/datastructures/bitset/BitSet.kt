package br.uem.din.datastructures.bitset

/**
 * Cria uma nova instância de BitSet com a capacidade inicial especificada.
 *
 * @param size a capacidade inicial em bits (padrão: 64). O BitSet pode crescer além deste valor.
 * @return uma instância da interface [BitSet].
 */
public expect fun bitSetOf(size: Int = 64): BitSet

/**
 * Interface para Bit Array (BitSet) — array compacto de bits.
 *
 * Cada bit ocupa apenas 1 bit de memória.
 *
 * Implementação multiplataforma via [bitSetOf]:
 * - **JVM**: delega para [java.util.BitSet]
 * - **JS**: implementação manual usando [IntArray]
 * - **Native**: implementação manual usando [LongArray]
 *
 * @see bitSetOf
 */
public interface BitSet : Iterable<Int> {

    /**
     * Define o bit no índice especificado como `true`.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based). Deve ser >= 0.
     * @throws IllegalArgumentException se [index] for negativo.
     */
    public fun set(index: Int)

    /**
     * Define o bit no índice especificado para o valor informado.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based). Deve ser >= 0.
     * @param value `true` para ligar o bit, `false` para desligar.
     * @throws IllegalArgumentException se [index] for negativo.
     */
    public fun set(index: Int, value: Boolean)

    /**
     * Define o bit no índice especificado como `false`.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based). Deve ser >= 0.
     * @throws IllegalArgumentException se [index] for negativo.
     */
    public fun clear(index: Int)

    /**
     * Define todos os bits como `false`, efetivamente esvaziando o BitSet.
     *
     * Complexidade: O(w) onde w é o número de palavras.
     */
    public fun clear()

    /**
     * Retorna o valor do bit no índice especificado.
     *
     * Complexidade: O(1).
     *
     * @param index o índice do bit (0-based). Deve ser >= 0.
     * @return `true` se o bit estiver ligado, `false` caso contrário.
     * @throws IllegalArgumentException se [index] for negativo.
     */
    public operator fun get(index: Int): Boolean

    /**
     * Retorna o número de bits de espaço efetivamente alocado.
     */
    public fun size(): Int

    /**
     * Retorna o "tamanho lógico": índice do bit mais alto ligado + 1.
     */
    public fun length(): Int

    /**
     * Retorna `true` se nenhum bit estiver ligado.
     */
    public fun isEmpty(): Boolean

    /**
     * Retorna o número de bits ligados (population count / popcount).
     */
    public fun cardinality(): Int

    /**
     * Retorna o índice do próximo bit ligado a partir de [fromIndex].
     */
    public fun nextSetBit(fromIndex: Int): Int

    /**
     * Realiza a operação AND bit-a-bit com outro BitSet.
     * Modifica este BitSet in-place.
     */
    public fun and(other: BitSet)

    /**
     * Realiza a operação OR bit-a-bit com outro BitSet.
     * Modifica este BitSet in-place.
     */
    public fun or(other: BitSet)

    /**
     * Realiza a operação XOR bit-a-bit com outro BitSet.
     * Modifica este BitSet in-place.
     */
    public fun xor(other: BitSet)

    /**
     * Realiza a operação AND-NOT bit-a-bit com outro BitSet.
     * Modifica este BitSet in-place.
     */
    public fun andNot(other: BitSet)
}
