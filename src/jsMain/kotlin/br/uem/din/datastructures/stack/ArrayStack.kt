package br.uem.din.datastructures.stack

/**
 * Implementação JS de [ArrayStack] usando [ArrayList] como armazenamento interno.
 *
 * Armazena elementos com o topo no final do array, garantindo push/pop O(1) amortizado.
 * A iteração percorre do topo (último elemento) à base (primeiro elemento).
 *
 * @param T o tipo dos elementos armazenados na pilha.
 */
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

    actual override val size: Int get() = storage.size

    actual override fun isEmpty(): Boolean = storage.isEmpty()

    actual override fun contains(element: T): Boolean = storage.contains(element)

    actual override fun clear() = storage.clear()

    actual override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var index = storage.size - 1
        override fun hasNext(): Boolean = index >= 0
        override fun next(): T {
            if (index < 0) throw NoSuchElementException()
            return storage[index--]
        }
    }

    actual override fun toString(): String {
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
