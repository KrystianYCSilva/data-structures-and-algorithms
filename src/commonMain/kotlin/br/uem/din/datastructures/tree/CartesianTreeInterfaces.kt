package br.uem.din.datastructures.tree

/**
 * Interface somente-leitura para uma Árvore Cartesiana.
 *
 * Define operações de consulta: travessia in-order e validação de propriedade de heap.
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Vuillemin, J. "A Unifying Look at Data Structures" (1980).
 *
 * @see MutableCartesianTree
 */
public interface ImmutableCartesianTree<T : Comparable<T>> {
    /**
     * Retorna os valores da árvore em percurso in-order.
     *
     * @return lista com os valores em ordem dos índices originais.
     */
    public fun inOrder(): List<T>

    /**
     * Verifica se a árvore satisfaz a propriedade de min-heap.
     *
     * @return `true` se a propriedade de min-heap for válida para todos os nós.
     */
    public fun isValidMinHeap(): Boolean
}

/**
 * Interface mutável para uma Árvore Cartesiana.
 *
 * Estende [ImmutableCartesianTree] adicionando a operação de construção.
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Vuillemin, J. "A Unifying Look at Data Structures" (1980).
 *
 * @see ImmutableCartesianTree
 */
public interface MutableCartesianTree<T : Comparable<T>> : ImmutableCartesianTree<T> {
    /**
     * Constrói a Árvore Cartesiana a partir de uma lista de valores.
     *
     * @param values a lista de valores a partir da qual a árvore será construída.
     */
    public fun buildFromArray(values: List<T>)
}
