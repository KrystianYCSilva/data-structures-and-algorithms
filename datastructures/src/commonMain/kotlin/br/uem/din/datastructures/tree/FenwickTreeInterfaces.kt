package br.uem.din.datastructures.tree

/**
 * Interface somente-leitura para uma Árvore de Fenwick (Binary Indexed Tree).
 *
 * Define operações de consulta: soma de prefixo, soma de intervalo e consulta pontual.
 *
 * Referência: Fenwick, P. M. "A New Data Structure for Cumulative Frequency Tables" (1994).
 *
 * @see MutableFenwickTree
 */
public interface ImmutableFenwickTree {
    /**
     * Retorna a soma dos elementos no intervalo [0, index] (inclusive).
     *
     * @param index o índice final do prefixo (0-indexed, inclusive).
     * @return a soma dos elementos de 0 a [index].
     */
    public fun prefixSum(index: Int): Long

    /**
     * Retorna a soma dos elementos no intervalo [left, right] (inclusive).
     *
     * @param left índice esquerdo do intervalo (0-indexed, inclusive).
     * @param right índice direito do intervalo (0-indexed, inclusive).
     * @return a soma dos elementos no intervalo.
     */
    public fun rangeSum(left: Int, right: Int): Long

    /**
     * Retorna o valor individual na posição [index].
     *
     * @param index o índice do elemento (0-indexed).
     * @return o valor na posição [index].
     */
    public fun pointQuery(index: Int): Long
}

/**
 * Interface mutável para uma Árvore de Fenwick (Binary Indexed Tree).
 *
 * Estende [ImmutableFenwickTree] adicionando construção e atualização.
 *
 * Referência: Fenwick, P. M. "A New Data Structure for Cumulative Frequency Tables" (1994).
 *
 * @see ImmutableFenwickTree
 */
public interface MutableFenwickTree : ImmutableFenwickTree {
    /**
     * Constrói a Fenwick Tree a partir de um array de valores iniciais.
     *
     * @param values o array de valores iniciais (0-indexed).
     */
    public fun build(values: LongArray)

    /**
     * Adiciona [delta] ao valor na posição [index].
     *
     * @param index o índice do elemento (0-indexed).
     * @param delta o valor a ser adicionado.
     */
    public fun update(index: Int, delta: Long)
}
