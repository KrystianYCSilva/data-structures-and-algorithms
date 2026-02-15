package br.uem.din.datastructures.linkedlist

/**
 * Lista duplamente ligada (Doubly Linked List) — declaração `expect` para Kotlin Multiplatform.
 *
 * Cada nó possui referências para o nó anterior e o próximo, permitindo travessia bidirecional
 * e remoção eficiente de ambas as extremidades. Implementa [Iterable] para uso idiomático
 * com `for`, `map`, `filter` e demais operações do Kotlin stdlib.
 *
 * Complexidades esperadas:
 * | Operação                        | Complexidade |
 * |---------------------------------|-------------|
 * | [addFirst] / [addLast]          | O(1)        |
 * | [removeFirst] / [removeLast]    | O(1)        |
 * | [get] / [removeAt]              | O(n)        |
 * | [contains] / [indexOf]          | O(n)        |
 * | [size] / [isEmpty]              | O(1)        |
 * | [clear]                         | O(1)        |
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists (doubly linked with sentinel).
 *
 * @see LinkedList
 * @see CircularLinkedList
 */
expect class DoublyLinkedList<T>() : Iterable<T> {
    /**
     * Número de elementos na lista.
     *
     * Complexidade: O(1).
     */
    val size: Int

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
     * Retorna o elemento na posição especificada.
     *
     * Complexidade: O(n).
     *
     * @param index a posição desejada (0-based).
     * @return o valor na posição indicada.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    operator fun get(index: Int): T

    /**
     * Substitui o valor na posição especificada.
     *
     * Complexidade: O(n).
     *
     * @param index a posição desejada (0-based).
     * @param element o novo valor.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    operator fun set(index: Int, element: T)

    /**
     * Verifica se a lista contém o elemento especificado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return `true` se encontrado, `false` caso contrário.
     */
    fun contains(element: T): Boolean

    /**
     * Retorna o índice da primeira ocorrência do valor, ou -1 se não encontrado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return o índice (0-based), ou -1.
     */
    fun indexOf(element: T): Int

    /**
     * Remove e retorna o elemento na posição especificada.
     *
     * Complexidade: O(n).
     *
     * @param index a posição do elemento a ser removido (0-based).
     * @return o valor removido.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun removeAt(index: Int): T

    /**
     * Remove todos os elementos da lista.
     *
     * Complexidade: O(1).
     */
    fun clear()

    /**
     * Verifica se a lista está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    fun isEmpty(): Boolean

    /**
     * Retorna uma cópia dos elementos como [List] imutável do Kotlin stdlib.
     *
     * Complexidade: O(n).
     *
     * @return lista imutável contendo todos os elementos na ordem de inserção.
     */
    fun toList(): List<T>

    /**
     * Retorna a representação textual da lista no formato `[v1, v2, ..., vn]`.
     *
     * @return string com os elementos da lista.
     */
    override fun toString(): String

    /**
     * Retorna um [Iterator] que percorre os elementos da lista do início ao fim.
     *
     * @return iterador sobre os elementos.
     */
    override fun iterator(): Iterator<T>
}
