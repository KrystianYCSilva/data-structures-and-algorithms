package br.com.leandroluce.algoritmos.datastructures.queue

class CircularQueue<T>(private val size: Int) {
    private val storage: MutableList<T?> = MutableList(size) { null }
    private var readIndex = 0
    private var writeIndex = 0

    val count: Int
        get() = if (writeIndex >= readIndex) writeIndex - readIndex else writeIndex + size - readIndex

    val isFull: Boolean
        get() = count == size -1

    val isEmpty: Boolean
        get() = count == 0

    fun enqueue(element: T): Boolean {
        if (isFull) return false
        storage[writeIndex] = element
        writeIndex = (writeIndex + 1) % size
        return true
    }

    fun dequeue(): T? {
        if (isEmpty) return null
        val dequeued = storage[readIndex]
        readIndex = (readIndex + 1) % size
        return dequeued
    }

    fun peek(): T? = storage[readIndex]
}
