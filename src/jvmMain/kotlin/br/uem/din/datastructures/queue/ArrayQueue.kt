package br.uem.din.datastructures.queue

import java.util.ArrayDeque

/**
 * Implementação JVM de [ArrayQueue], delegando a [java.util.ArrayDeque].
 *
 * @param T o tipo dos elementos armazenados na fila.
 *
 * Referência: JDK ArrayDeque — circular array implementation.
 */
actual class ArrayQueue<T> : MutableQueue<T> {
    private val deque = ArrayDeque<T>()

    actual override fun enqueue(element: T) {
        deque.addLast(element)
    }

    actual override fun dequeue(): T? = deque.pollFirst()

    actual override fun peek(): T? = deque.peekFirst()

    actual override fun size(): Int = deque.size

    actual override fun isEmpty(): Boolean = deque.isEmpty()

    actual override fun contains(element: T): Boolean = deque.contains(element)

    actual override fun clear() = deque.clear()

    actual override fun iterator(): Iterator<T> = deque.iterator()

    actual override fun toString(): String = deque.toString()
}
