package br.uem.din.datastructures.queue

import java.util.PriorityQueue as JavaPriorityQueue

/**
 * Cria uma instância JVM de PriorityQueue delegando para java.util.PriorityQueue.
 */
public actual fun <T> priorityQueueOf(comparator: Comparator<T>?): MutableQueue<T> {
    return JvmPriorityQueue(comparator)
}

/**
 * Wrapper privado para java.util.PriorityQueue.
 */
private class JvmPriorityQueue<T>(comparator: Comparator<T>?) : MutableQueue<T> {
    private val pq = if (comparator != null) {
        JavaPriorityQueue(comparator)
    } else {
        JavaPriorityQueue()
    }

    override fun enqueue(element: T) {
        pq.add(element)
    }

    override fun dequeue(): T? = pq.poll()

    override fun peek(): T? = pq.peek()

    override val size: Int get() = pq.size

    override fun isEmpty(): Boolean = pq.isEmpty()

    override fun contains(element: T): Boolean = pq.contains(element)

    override fun clear(): Unit = pq.clear()

    override fun iterator(): Iterator<T> = pq.iterator()

    override fun toString(): String = pq.toString()
}
