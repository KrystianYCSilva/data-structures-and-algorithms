package br.uem.din.datastructures.linkedlist

data class DoublyNode<T>(var value: T, var prev: DoublyNode<T>? = null, var next: DoublyNode<T>? = null) {
    override fun toString(): String = value.toString()
}

class DoublyLinkedList<T> {
    private var head: DoublyNode<T>? = null
    private var tail: DoublyNode<T>? = null
    var size = 0
        private set

    fun isEmpty() = size == 0

    fun addFirst(value: T) {
        val newNode = DoublyNode(value)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            newNode.next = head
            head?.prev = newNode
            head = newNode
        }
        size++
    }

    fun addLast(value: T) {
        val newNode = DoublyNode(value)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            newNode.prev = tail
            tail = newNode
        }
        size++
    }

    fun removeFirst(): T? {
        if (isEmpty()) return null
        val value = head?.value
        if (head == tail) {
            head = null
            tail = null
        } else {
            head = head?.next
            head?.prev = null
        }
        size--
        return value
    }

    fun removeLast(): T? {
        if (isEmpty()) return null
        val value = tail?.value
        if (head == tail) {
            head = null
            tail = null
        } else {
            tail = tail?.prev
            tail?.next = null
        }
        size--
        return value
    }
    
    // Additional methods (get, remove(val), etc.) can be added
    
    override fun toString(): String {
        if (isEmpty()) return "[]"
        val sb = StringBuilder("[")
        var current = head
        while (current != null) {
            sb.append(current.value)
            if (current.next != null) sb.append(", ")
            current = current.next
        }
        sb.append("]")
        return sb.toString()
    }
}
