package br.uem.din.datastructures.queue

import java.util.ArrayDeque

actual class ArrayQueue<T> : MutableQueue<T> {
    private val deque = ArrayDeque<T>()

    actual override fun enqueue(element: T) {
        deque.addLast(element)
    }

    actual override fun dequeue(): T? {
        return deque.pollFirst()
    }

    actual override fun peek(): T? {
        return deque.peekFirst()
    }

    actual override fun size(): Int = deque.size

    actual override fun isEmpty(): Boolean = deque.isEmpty()

    actual override fun toString(): String = deque.toString()
}
