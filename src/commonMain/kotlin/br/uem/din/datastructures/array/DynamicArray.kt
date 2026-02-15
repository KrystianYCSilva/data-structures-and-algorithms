package br.uem.din.datastructures.array

/**
 * A manual implementation of a Dynamic Array (similar to ArrayList).
 * Uses a resizing strategy (typically doubling capacity) when full.
 */
class DynamicArray<T>(initialCapacity: Int = 10) {
    
    private var elements = arrayOfNulls<Any?>(if (initialCapacity > 0) initialCapacity else 10)
    var size = 0
        private set

    val capacity: Int
        get() = elements.size

    fun add(element: T) {
        if (size == elements.size) {
            resize(elements.size * 2)
        }
        elements[size++] = element
    }

    fun add(index: Int, element: T) {
        if (index < 0 || index > size) throw IndexOutOfBoundsException()
        if (size == elements.size) {
            resize(elements.size * 2)
        }
        // Shift elements right
        for (i in size downTo index + 1) {
            elements[i] = elements[i - 1]
        }
        elements[index] = element
        size++
    }

    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException()
        return elements[index] as T
    }

    @Suppress("UNCHECKED_CAST")
    fun removeAt(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException()
        val oldValue = elements[index] as T
        
        // Shift elements left
        for (i in index until size - 1) {
            elements[i] = elements[i + 1]
        }
        elements[size - 1] = null // Avoid leak
        size--
        
        // Optional: shrink if too empty
        if (size > 0 && size == elements.size / 4) {
            resize(elements.size / 2)
        }
        return oldValue
    }

    private fun resize(newCapacity: Int) {
        val newElements = arrayOfNulls<Any?>(newCapacity)
        for (i in 0 until size) {
            newElements[i] = elements[i]
        }
        elements = newElements
    }
    
    override fun toString(): String {
        val sb = StringBuilder("[")
        for (i in 0 until size) {
            sb.append(elements[i])
            if (i < size - 1) sb.append(", ")
        }
        sb.append("]")
        return sb.toString()
    }
}
