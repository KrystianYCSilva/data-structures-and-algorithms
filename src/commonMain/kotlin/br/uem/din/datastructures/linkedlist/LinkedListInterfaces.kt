package br.uem.din.datastructures.linkedlist

/**
 * Interface somente-leitura para uma Lista Ligada (Linked List).
 *
 * Segue o padrão do Kotlin stdlib de separar interfaces imutáveis e mutáveis
 * (ex.: [List]/[MutableList], [Set]/[MutableSet]). Implementa [Iterable] para
 * permitir uso com `for`, `map`, `filter`, etc. A iteração percorre do início ao fim.
 *
 * Define as operações comuns a todas as variantes de listas ligadas:
 * singly linked, doubly linked, circular e unrolled.
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists.
 *
 * @see MutableLinkedList
 */
interface ImmutableLinkedList<T> : Iterable<T> {
    /**
     * Número de elementos na lista.
     *
     * Complexidade: O(1).
     */
    val size: Int

    /**
     * Verifica se a lista está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    fun isEmpty(): Boolean

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
     * Retorna uma cópia dos elementos como [List] imutável do Kotlin stdlib.
     *
     * Complexidade: O(n).
     *
     * @return lista imutável contendo todos os elementos na ordem de inserção.
     */
    fun toList(): List<T>
}

/**
 * Interface mutável para uma Lista Ligada (Linked List).
 *
 * Estende [ImmutableLinkedList] adicionando operações de modificação
 * ([addFirst], [addLast], [removeFirst], [removeLast], [clear]).
 * Segue o padrão Kotlin de imutável/mutável.
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists.
 *
 * @see ImmutableLinkedList
 * @see LinkedList
 * @see DoublyLinkedList
 * @see CircularLinkedList
 * @see UnrolledLinkedList
 */
interface MutableLinkedList<T> : ImmutableLinkedList<T> {
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
     * Complexidade: O(1) para doubly/circular; O(1) amortizado para unrolled.
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
     * Complexidade: O(1) para doubly linked; O(n) para singly linked.
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    fun removeLast(): T?

    /**
     * Remove todos os elementos da lista.
     *
     * Complexidade: O(1).
     */
    fun clear()
}
