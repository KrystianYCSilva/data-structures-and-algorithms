package br.uem.din.datastructures.linkedlist

import java.util.LinkedList

actual class DoublyLinkedList<T> {
    private val list = LinkedList<T>()

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

    actual fun size(): Int = list.size

    actual fun isEmpty(): Boolean = list.isEmpty()

    actual override fun toString(): String = list.toString()
}
