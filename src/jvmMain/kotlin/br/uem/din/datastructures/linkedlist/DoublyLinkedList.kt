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
actual class DoublyLinkedList<T> : Iterable<T> {
    private val list = LinkedList<T>()

    actual val size: Int get() = list.size

    actual fun addFirst(element: T) {
        list.addFirst(element)
    }

    actual fun addLast(element: T) {
        list.addLast(element)
    }

    actual fun removeFirst(): T? {
        if (list.isEmpty()) return null
        return list.removeFirst()
    }

    actual fun removeLast(): T? {
        if (list.isEmpty()) return null
        return list.removeLast()
    }

    actual operator fun get(index: Int): T = list[index]

    actual operator fun set(index: Int, element: T) {
        list[index] = element
    }

    actual fun contains(element: T): Boolean = list.contains(element)

    actual fun indexOf(element: T): Int = list.indexOf(element)

    actual fun removeAt(index: Int): T = list.removeAt(index)

    actual fun clear() {
        list.clear()
    }

    actual fun isEmpty(): Boolean = list.isEmpty()

    actual fun toList(): List<T> = list.toList()

    actual override fun toString(): String = list.toString()

    actual override fun iterator(): Iterator<T> = list.iterator()
}
