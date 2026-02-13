package br.com.leandroluce.algoritmos.datastructures.queue

class Deque<T> {
    private val storage = mutableListOf<T>()

    val count: Int
        get() = storage.size

    val isEmpty: Boolean
        get() = storage.isEmpty()

    fun enqueue(element: T) {
        storage.add(element)
    }

    fun enqueueFront(element: T) {
        storage.add(0, element)
    }

    fun dequeue(): T? {
        return if (isEmpty) {
            null
        } else {
            storage.removeAt(0)
        }
    }

    fun dequeueBack(): T? {
        return if (isEmpty) {
            null
        } else {
            storage.removeAt(storage.size - 1)
        }
    }

    fun peek(): T? = storage.firstOrNull()

    fun peekBack(): T? = storage.lastOrNull()
}
