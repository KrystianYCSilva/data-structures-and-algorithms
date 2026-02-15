package br.uem.din.datastructures.queue

actual class ArrayQueue<T> : MutableQueue<T> {
    private var elements = arrayOfNulls<Any?>(16)
    private var head = 0
    private var tail = 0
    private var _count = 0

    actual override fun enqueue(element: T) {
        if (_count == elements.size) {
            doubleCapacity()
        }
        elements[tail] = element
        tail = (tail + 1) % elements.size
        _count++
    }

    @Suppress("UNCHECKED_CAST")
    actual override fun dequeue(): T? {
        if (_count == 0) return null
        val element = elements[head] as T
        elements[head] = null // Help GC
        head = (head + 1) % elements.size
        _count--
        return element
    }

    @Suppress("UNCHECKED_CAST")
    actual override fun peek(): T? {
        if (_count == 0) return null
        return elements[head] as T
    }

    actual override fun size(): Int = _count

    actual override fun isEmpty(): Boolean = _count == 0

    actual override fun toString(): String {
        if (isEmpty()) return "[]"
        val sb = StringBuilder("[")
        var current = head
        for (i in 0 until _count) {
            sb.append(elements[current])
            if (i < _count - 1) sb.append(", ")
            current = (current + 1) % elements.size
        }
        sb.append("]")
        return sb.toString()
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
