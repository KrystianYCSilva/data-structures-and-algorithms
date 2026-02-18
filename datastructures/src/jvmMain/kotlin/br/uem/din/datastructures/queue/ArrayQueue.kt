package br.uem.din.datastructures.queue

import java.util.ArrayDeque

/**
 * Cria uma instância JVM de ArrayQueue delegando para java.util.ArrayDeque.
 */
public actual fun <T> arrayQueueOf(): MutableQueue<T> {
    return JvmArrayQueue()
}

/**
 * Wrapper privado para java.util.ArrayDeque.
 */
private class JvmArrayQueue<T> : MutableQueue<T> {
    private val deque = ArrayDeque<T>()

    override fun enqueue(element: T) {
        deque.addLast(element)
    }

    override fun dequeue(): T? = deque.pollFirst()

    override fun peek(): T? = deque.peekFirst()

    override val size: Int get() = deque.size

    override fun isEmpty(): Boolean = deque.isEmpty()

    override fun contains(element: T): Boolean = deque.contains(element)

    override fun clear(): Unit = deque.clear()

    override fun iterator(): Iterator<T> = deque.iterator()

    override fun toString(): String = deque.toString()
}
