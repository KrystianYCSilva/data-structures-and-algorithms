package br.uem.din.datastructures.linkedlist

expect class DoublyLinkedList<T>() {
    fun addFirst(element: T)
    fun addLast(element: T)
    fun removeFirst(): T?
    fun removeLast(): T?
    fun size(): Int
    fun isEmpty(): Boolean
    override fun toString(): String
}
