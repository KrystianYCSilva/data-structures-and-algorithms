package br.uem.din.datastructures.queue

import java.util.PriorityQueue as JavaPriorityQueue
import java.util.Comparator

actual class PriorityQueue<T> actual constructor(comparator: Comparator<T>?) {
    private val pq = if (comparator != null) {
        JavaPriorityQueue(comparator)
    } else {
        JavaPriorityQueue()
    }

    actual fun enqueue(element: T) {
        pq.add(element)
    }

    actual fun dequeue(): T? = pq.poll()
    actual fun peek(): T? = pq.peek()
    actual fun size(): Int = pq.size
    actual fun isEmpty(): Boolean = pq.isEmpty()
}
