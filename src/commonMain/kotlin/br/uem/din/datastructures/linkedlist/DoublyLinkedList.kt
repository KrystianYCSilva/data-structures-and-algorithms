package br.uem.din.datastructures.linkedlist

/**
 * Lista duplamente ligada (Doubly Linked List) — declaração `expect` para Kotlin Multiplatform.
 *
 * Cada nó possui referências para o nó anterior e o próximo, permitindo travessia bidirecional
 * e remoção eficiente de ambas as extremidades.
 *
 * Complexidades esperadas:
 * - [addFirst] / [addLast]: O(1)
 * - [removeFirst] / [removeLast]: O(1)
 * - [size]: O(1)
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists (doubly linked with sentinel).
 */
expect class DoublyLinkedList<T>() {
    /**
     * Insere um elemento no início da lista.
     *
     * Complexidade: O(1).
     *
     * @param element o valor a ser inserido.
     */
    fun addFirst(element: T)

    /**
     * Insere um elemento no final da lista.
     *
     * Complexidade: O(1).
     *
     * @param element o valor a ser inserido.
     */
    fun addLast(element: T)

    /**
     * Remove e retorna o primeiro elemento da lista.
     *
     * Complexidade: O(1).
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    fun removeFirst(): T?

    /**
     * Remove e retorna o último elemento da lista.
     *
     * Complexidade: O(1).
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    fun removeLast(): T?

    /**
     * Retorna o número de elementos na lista.
     *
     * Complexidade: O(1).
     *
     * @return a quantidade de elementos.
     */
    fun size(): Int

    /**
     * Verifica se a lista está vazia.
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    fun isEmpty(): Boolean

    /**
     * Retorna a representação textual da lista.
     *
     * @return string com os elementos da lista.
     */
    override fun toString(): String
}
