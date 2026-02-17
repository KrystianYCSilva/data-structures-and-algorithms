package br.uem.din.datastructures.linkedlist

import java.util.LinkedList

/**
 * Implementação JVM da Lista Duplamente Ligada, delegando a [java.util.LinkedList].
 */
public actual fun <T> doublyLinkedListOf(): IndexedMutableLinkedList<T> = JvmDoublyLinkedList()

private class JvmDoublyLinkedList<T> : IndexedMutableLinkedList<T> {
    private val list = LinkedList<T>()

    override val size: Int get() = list.size
    override fun addFirst(element: T) { list.addFirst(element) }
    override fun addLast(element: T) { list.addLast(element) }
    override fun removeFirst(): T? = if (list.isEmpty()) null else list.removeFirst()
    override fun removeLast(): T? = if (list.isEmpty()) null else list.removeLast()
    override operator fun get(index: Int): T = list[index]
    override operator fun set(index: Int, element: T) { list[index] = element }
    override fun contains(element: T): Boolean = list.contains(element)
    override fun indexOf(element: T): Int = list.indexOf(element)
    override fun removeAt(index: Int): T = list.removeAt(index)
    override fun clear() { list.clear() }
    override fun isEmpty(): Boolean = list.isEmpty()
    override fun toList(): List<T> = list.toList()
    override fun toString(): String = list.toString()
    override fun iterator(): Iterator<T> = list.iterator()
}
