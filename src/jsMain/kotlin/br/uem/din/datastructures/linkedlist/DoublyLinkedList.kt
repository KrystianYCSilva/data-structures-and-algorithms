package br.uem.din.datastructures.linkedlist

actual class DoublyLinkedList<T> {
    private class Node<T>(val value: T, var prev: Node<T>? = null, var next: Node<T>? = null)

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var count = 0

    actual fun addFirst(element: T) {
        val newNode = Node(element)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            newNode.next = head
            head?.prev = newNode
            head = newNode
        }
        count++
    }

    actual fun addLast(element: T) {
        val newNode = Node(element)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            newNode.prev = tail
            tail = newNode
        }
        count++
    }

    actual fun removeFirst(): T? {
        if (isEmpty()) return null
        val value = head?.value
        if (head == tail) {
            head = null
            tail = null
        } else {
            head = head?.next
            head?.prev = null
        }
        count--
        return value
    }

    actual fun removeLast(): T? {
        if (isEmpty()) return null
        val value = tail?.value
        if (head == tail) {
            head = null
            tail = null
        } else {
            tail = tail?.prev
            tail?.next = null
        }
        count--
        return value
    }

    actual fun size(): Int = count
    actual fun isEmpty(): Boolean = count == 0

    actual override fun toString(): String {
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
