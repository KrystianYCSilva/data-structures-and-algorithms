package br.uem.din.datastructures.queue

/**
 * Read-only interface for a Queue (FIFO).
 */
interface Queue<T> {
    /** Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty. */
    fun peek(): T?

    /** Returns the number of elements in this queue. */
    fun size(): Int

    /** Returns true if this queue contains no elements. */
    fun isEmpty(): Boolean
}

/**
 * Mutable interface for a Queue (FIFO).
 */
interface MutableQueue<T> : Queue<T> {
    /** Inserts the specified element into this queue. */
    fun enqueue(element: T)

    /** Retrieves and removes the head of this queue, or returns null if this queue is empty. */
    fun dequeue(): T?
}
