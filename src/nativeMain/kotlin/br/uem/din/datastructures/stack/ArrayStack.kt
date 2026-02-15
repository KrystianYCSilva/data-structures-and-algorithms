package br.uem.din.datastructures.stack

actual class ArrayStack<T> : MutableStack<T> {
    private val storage = ArrayList<T>()

    actual override fun push(element: T): T {
        storage.add(element)
        return element
    }

    actual override fun pop(): T? {
        if (storage.isEmpty()) return null
        return storage.removeAt(storage.size - 1)
    }

    actual override fun peek(): T? {
        if (storage.isEmpty()) return null
        return storage[storage.size - 1]
    }

    actual override fun size(): Int = storage.size

    actual override fun isEmpty(): Boolean = storage.isEmpty()

    actual override fun toString(): String = storage.toString()
}
