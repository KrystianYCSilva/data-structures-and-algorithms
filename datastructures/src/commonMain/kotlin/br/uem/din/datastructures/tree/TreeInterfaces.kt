package br.uem.din.datastructures.tree

/**
 * Interface somente-leitura para árvores de busca (Search Tree).
 *
 * Define as operações de consulta comuns a todas as árvores de busca:
 * verificação de pertinência, tamanho e travessia em ordem.
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 12 — Binary Search Trees.
 */
public interface SearchTree<T : Comparable<T>> : Iterable<T> {
    /**
     * Verifica se a árvore contém o elemento especificado.
     *
     * @param element o elemento a ser procurado.
     * @return `true` se o elemento existir na árvore, `false` caso contrário.
     */
    public fun contains(element: T): Boolean

    /**
     * Número de elementos armazenados na árvore.
     */
    public val size: Int

    /**
     * Verifica se a árvore está vazia.
     *
     * @return `true` se a árvore não contiver elementos.
     */
    public fun isEmpty(): Boolean

    /**
     * Retorna os elementos da árvore em ordem crescente (travessia in-order).
     *
     * @return lista ordenada dos elementos.
     */
    public fun inOrder(): List<T>
}

/**
 * Interface mutável para árvores de busca (Mutable Search Tree).
 *
 * Estende [SearchTree] adicionando operações de modificação: inserção e remoção.
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 12 — Binary Search Trees.
 */
public interface MutableSearchTree<T : Comparable<T>> : SearchTree<T> {
    /**
     * Insere um elemento na árvore.
     *
     * @param element o elemento a ser inserido.
     * @return `true` se o elemento foi inserido, `false` se já existia.
     */
    public fun insert(element: T): Boolean

    /**
     * Remove um elemento da árvore.
     *
     * @param element o elemento a ser removido.
     * @return `true` se o elemento foi removido, `false` se não existia.
     */
    public fun remove(element: T): Boolean
}
