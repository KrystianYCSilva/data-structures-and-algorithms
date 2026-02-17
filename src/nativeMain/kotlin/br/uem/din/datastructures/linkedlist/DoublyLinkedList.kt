package br.uem.din.datastructures.linkedlist

/**
 * Implementacao Native da Lista Duplamente Ligada com nos manuais prev/next.
 */
public actual fun <T> doublyLinkedListOf(): IndexedMutableLinkedList<T> = NativeDoublyLinkedList()

private class NativeDoublyLinkedList<T> : IndexedMutableLinkedList<T> {
    private class Node<T>(var value: T, var prev: Node<T>? = null, var next: Node<T>? = null)

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var _size = 0

    override val size: Int get() = _size

    override fun addFirst(element: T) {
        val newNode = Node(element)
        if (isEmpty()) { head = newNode; tail = newNode }
        else { newNode.next = head; head?.prev = newNode; head = newNode }
        _size++
    }

    override fun addLast(element: T) {
        val newNode = Node(element)
        if (isEmpty()) { head = newNode; tail = newNode }
        else { tail?.next = newNode; newNode.prev = tail; tail = newNode }
        _size++
    }

    override fun removeFirst(): T? {
        if (isEmpty()) return null
        val value = head!!.value
        if (head == tail) { head = null; tail = null }
        else { head = head?.next; head?.prev = null }
        _size--
        return value
    }

    override fun removeLast(): T? {
        if (isEmpty()) return null
        val value = tail!!.value
        if (head == tail) { head = null; tail = null }
        else { tail = tail?.prev; tail?.next = null }
        _size--
        return value
    }

    override operator fun get(index: Int): T = nodeAt(index).value

    override operator fun set(index: Int, element: T) { nodeAt(index).value = element }

    override fun contains(element: T): Boolean { for (v in this) { if (v == element) return true }; return false }

    override fun indexOf(element: T): Int { var idx = 0; for (v in this) { if (v == element) return idx; idx++ }; return -1 }

    override fun removeAt(index: Int): T {
        val node = nodeAt(index)
        when (node) {
            head -> { head = node.next; head?.prev = null; if (head == null) tail = null }
            tail -> { tail = node.prev; tail?.next = null }
            else -> { node.prev?.next = node.next; node.next?.prev = node.prev }
        }
        _size--
        return node.value
    }

    override fun clear() { head = null; tail = null; _size = 0 }
    override fun isEmpty(): Boolean = _size == 0
    override fun toList(): List<T> = iterator().asSequence().toList()
    override fun toString(): String = if (isEmpty()) "[]" else joinToString(prefix = "[", postfix = "]")

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var current = head
        override fun hasNext(): Boolean = current != null
        override fun next(): T {
            val value = current?.value ?: throw NoSuchElementException()
            current = current?.next
            return value
        }
    }

    private fun nodeAt(index: Int): Node<T> {
        if (index < 0 || index >= _size) throw IndexOutOfBoundsException("Index $index, size $_size")
        return if (index < _size / 2) {
            var node = head!!; repeat(index) { node = node.next!! }; node
        } else {
            var node = tail!!; repeat(_size - 1 - index) { node = node.prev!! }; node
        }
    }
}
