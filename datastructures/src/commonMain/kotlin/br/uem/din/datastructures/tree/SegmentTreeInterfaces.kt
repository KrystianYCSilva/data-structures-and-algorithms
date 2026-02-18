package br.uem.din.datastructures.tree

/**
 * Interface somente-leitura para uma Árvore de Segmentos.
 *
 * Define a operação de consulta de intervalo (range query).
 *
 * @param T o tipo dos elementos.
 *
 * Referência: Bentley, J. L. "Solutions to Klee's Rectangle Problems" (1977).
 *
 * @see MutableSegmentTree
 */
public interface ImmutableSegmentTree<T> {
    /**
     * Consulta o resultado da agregação no intervalo [left, right] (inclusive).
     *
     * @param left índice esquerdo do intervalo (0-indexed, inclusive).
     * @param right índice direito do intervalo (0-indexed, inclusive).
     * @return o resultado da agregação no intervalo.
     */
    public fun query(left: Int, right: Int): T
}

/**
 * Interface mutável para uma Árvore de Segmentos.
 *
 * Estende [ImmutableSegmentTree] adicionando construção e atualização.
 *
 * @param T o tipo dos elementos.
 *
 * Referência: Bentley, J. L. "Solutions to Klee's Rectangle Problems" (1977).
 *
 * @see ImmutableSegmentTree
 */
public interface MutableSegmentTree<T> : ImmutableSegmentTree<T> {
    /**
     * Constrói a árvore de segmentos a partir de uma lista de valores.
     *
     * @param data a lista de valores de entrada.
     */
    public fun build(data: List<T>)

    /**
     * Atualiza o valor na posição [index].
     *
     * @param index o índice do elemento (0-indexed).
     * @param value o novo valor.
     */
    public fun update(index: Int, value: T)

    /**
     * Atualiza todos os elementos no intervalo [left, right] com [value].
     *
     * @param left índice esquerdo do intervalo (0-indexed, inclusive).
     * @param right índice direito do intervalo (0-indexed, inclusive).
     * @param value o valor a ser aplicado ao intervalo.
     */
    public fun rangeUpdate(left: Int, right: Int, value: T)
}
