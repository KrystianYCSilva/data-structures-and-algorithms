package br.uem.din.datastructures.queue

import java.util.PriorityQueue as JavaPriorityQueue

/**
 * Implementação JVM de [PriorityQueue], delegando a [java.util.PriorityQueue].
 *
 * @param T o tipo dos elementos. Deve ser [Comparable] se nenhum [Comparator] for fornecido.
 *
 * Referência: JDK PriorityQueue — binary min-heap implementation.
 */
actual class PriorityQueue<T> actual constructor(comparator: Comparator<T>?) : MutableQueue<T> {
    private val pq = if (comparator != null) {
        JavaPriorityQueue(comparator)
    } else {
        JavaPriorityQueue()
    }

    actual override fun enqueue(element: T) {
        pq.add(element)
    }

    actual override fun dequeue(): T? = pq.poll()

    actual override fun peek(): T? = pq.peek()

    actual override val size: Int get() = pq.size

    actual override fun isEmpty(): Boolean = pq.isEmpty()

    actual override fun contains(element: T): Boolean = pq.contains(element)

    actual override fun clear() = pq.clear()

    actual override fun iterator(): Iterator<T> = pq.iterator()

    actual override fun toString(): String = pq.toString()
}
