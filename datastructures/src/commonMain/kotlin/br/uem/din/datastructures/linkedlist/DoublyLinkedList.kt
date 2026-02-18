package br.uem.din.datastructures.linkedlist

/**
 * Cria uma nova instância de Lista Duplamente Ligada (Doubly Linked List).
 *
 * Cada nó possui referências para o nó anterior e o próximo, permitindo travessia bidirecional
 * e remoção eficiente de ambas as extremidades.
 *
 * Implementação:
 * - **JVM**: delega a [java.util.LinkedList]
 * - **JS/Native**: implementação manual com nós duplamente ligados
 *
 * Complexidades:
 * | Operação                        | Complexidade |
 * |---------------------------------|-------------|
 * | [addFirst] / [addLast]          | O(1)        |
 * | [removeFirst] / [removeLast]    | O(1)        |
 * | [get] / [set] / [removeAt]      | O(n)        |
 * | [contains] / [indexOf]          | O(n)        |
 * | [size] / [isEmpty]              | O(1)        |
 * | [clear]                         | O(1)        |
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists.
 *
 * @see LinkedList
 * @see CircularLinkedList
 */
public expect fun <T> doublyLinkedListOf(): IndexedMutableLinkedList<T>
