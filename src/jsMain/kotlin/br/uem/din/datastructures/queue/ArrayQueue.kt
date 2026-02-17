package br.uem.din.datastructures.queue

/**
 * Cria uma instância JS de ArrayQueue.
 */
public actual fun <T> arrayQueueOf(): MutableQueue<T> {
    return JsArrayQueue()
}

/**
 * Implementação JS de ArrayQueue com buffer circular.
 */
private class JsArrayQueue<T> : MutableQueue<T> {
    private var elements = arrayOfNulls<Any?>(16)
    private var head = 0
    private var tail = 0
    private var _count = 0

    override fun enqueue(element: T) {
        if (_count == elements.size) doubleCapacity()
        elements[tail] = element
        tail = (tail + 1) % elements.size
        _count++
    }

    @Suppress("UNCHECKED_CAST")
    override fun dequeue(): T? {
        if (_count == 0) return null
        val element = elements[head] as T
        elements[head] = null
        head = (head + 1) % elements.size
        _count--
        return element
    }

    @Suppress("UNCHECKED_CAST")
    override fun peek(): T? {
        if (_count == 0) return null
        return elements[head] as T
    }

    override val size: Int get() = _count

    override fun isEmpty(): Boolean = _count == 0

    override fun contains(element: T): Boolean {
        for (v in this) {
            if (v == element) return true
        }
        return false
    }

    override fun clear() {
        for (i in elements.indices) elements[i] = null
        head = 0
        tail = 0
        _count = 0
    }

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var index = head
        private var remaining = _count

        override fun hasNext(): Boolean = remaining > 0

        @Suppress("UNCHECKED_CAST")
        override fun next(): T {
            if (remaining <= 0) throw NoSuchElementException()
            val value = elements[index] as T
            index = (index + 1) % elements.size
            remaining--
            return value
        }
    }

    override fun toString(): String {
        if (isEmpty()) return "[]"
        return iterator().asSequence().joinToString(prefix = "[", postfix = "]")
    }

    private fun doubleCapacity() {
        val newCapacity = elements.size * 2
        val newElements = arrayOfNulls<Any?>(newCapacity)
        for (i in 0 until _count) {
            newElements[i] = elements[(head + i) % elements.size]
        }
        elements = newElements
        head = 0
        tail = _count
    }
}
