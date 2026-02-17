package br.uem.din.datastructures.stack

/**
 * Cria uma instância Native de ArrayStack.
 */
public actual fun <T> arrayStackOf(): MutableStack<T> {
    return NativeArrayStack()
}

/**
 * Implementação Native de ArrayStack usando ArrayList.
 */
private class NativeArrayStack<T> : MutableStack<T> {
    private val storage = ArrayList<T>()

    override fun push(element: T): T {
        storage.add(element)
        return element
    }

    override fun pop(): T? {
        if (storage.isEmpty()) return null
        return storage.removeAt(storage.size - 1)
    }

    override fun peek(): T? {
        if (storage.isEmpty()) return null
        return storage[storage.size - 1]
    }

    override val size: Int get() = storage.size

    override fun isEmpty(): Boolean = storage.isEmpty()

    override fun contains(element: T): Boolean = storage.contains(element)

    override fun clear(): Unit = storage.clear()

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var index = storage.size - 1
        override fun hasNext(): Boolean = index >= 0
        override fun next(): T {
            if (index < 0) throw NoSuchElementException()
            return storage[index--]
        }
    }

    override fun toString(): String {
        if (storage.isEmpty()) return "[]"
        val sb = StringBuilder("[")
        for (i in storage.size - 1 downTo 0) {
            sb.append(storage[i])
            if (i > 0) sb.append(", ")
        }
        sb.append("]")
        return sb.toString()
    }
}
