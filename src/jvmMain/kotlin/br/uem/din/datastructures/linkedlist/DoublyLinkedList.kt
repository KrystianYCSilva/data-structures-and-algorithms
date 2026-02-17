package br.uem.din.datastructures.linkedlist

import java.util.LinkedList

/**
 * Implementação JVM de [DoublyLinkedList], delegando a [java.util.LinkedList].
 *
 * No JVM, a lista duplamente ligada é implementada nativamente pelo Java Collections Framework,
 * evitando reimplementação. Todas as operações delegam diretamente à classe [java.util.LinkedList],
 * que utiliza nós com ponteiros `prev`/`next` e mantém referências à cabeça e cauda.
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: java.util.LinkedList — JDK (implementação baseada em doubly-linked list com sentinel).
 */
public actual class DoublyLinkedList<T> : MutableLinkedList<T> {
    private val list = LinkedList<T>()

    public actual override val size: Int get() = list.size

    public actual override fun addFirst(element: T) {
        list.addFirst(element)
    }

    public actual override fun addLast(element: T) {
        list.addLast(element)
    }

    public actual override fun removeFirst(): T? {
        if (list.isEmpty()) return null
        return list.removeFirst()
    }

    public actual override fun removeLast(): T? {
        if (list.isEmpty()) return null
        return list.removeLast()
    }

    public actual operator fun get(index: Int): T = list[index]

    public actual operator fun set(index: Int, element: T) {
        list[index] = element
    }

    public actual override fun contains(element: T): Boolean = list.contains(element)

    public actual override fun indexOf(element: T): Int = list.indexOf(element)

    public actual fun removeAt(index: Int): T = list.removeAt(index)

    public actual override fun clear() {
        list.clear()
    }

    public actual override fun isEmpty(): Boolean = list.isEmpty()

    public actual override fun toList(): List<T> = list.toList()

    public actual override fun toString(): String = list.toString()

    public actual override fun iterator(): Iterator<T> = list.iterator()
}
