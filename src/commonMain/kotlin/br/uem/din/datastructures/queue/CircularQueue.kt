package br.uem.din.datastructures.queue

class CircularQueue<T>(private val capacity: Int) : MutableQueue<T> {
    private val storage: MutableList<T?> = MutableList(capacity) { null }
    private var readIndex = 0
    private var writeIndex = 0

    // Internal count calculation
    private val internalCount: Int
        get() = if (writeIndex >= readIndex) writeIndex - readIndex else writeIndex + capacity - readIndex

    val isFull: Boolean
        get() = internalCount == capacity - 1

    override fun size(): Int = internalCount

    override fun isEmpty(): Boolean = internalCount == 0

    override fun enqueue(element: T) {
        if (!offer(element)) {
            throw IllegalStateException("Queue is full")
        }
    }

    fun offer(element: T): Boolean {
        if (isFull) return false
        storage[writeIndex] = element
        writeIndex = (writeIndex + 1) % capacity
        return true
    }

    override fun dequeue(): T? {
        if (isEmpty()) return null
        val dequeued = storage[readIndex]
        storage[readIndex] = null // Help GC
        readIndex = (readIndex + 1) % capacity
        return dequeued
    }

    override fun peek(): T? = storage[readIndex]
}
