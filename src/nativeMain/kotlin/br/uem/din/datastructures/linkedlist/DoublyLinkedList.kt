package br.uem.din.datastructures.linkedlist

/**
 * Implementação Native de [DoublyLinkedList] com nós manuais contendo ponteiros `prev`/`next`.
 *
 * Como Kotlin/Native não possui uma lista duplamente ligada nativa, esta implementação
 * gerencia manualmente os nós com referências bidirecionais. Otimiza acesso por índice
 * percorrendo a partir da extremidade mais próxima (head ou tail).
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists.
 */
actual class DoublyLinkedList<T> : MutableLinkedList<T> {
    private class Node<T>(var value: T, var prev: Node<T>? = null, var next: Node<T>? = null)

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var _size = 0

    actual override val size: Int get() = _size

    actual override fun addFirst(element: T) {
        val newNode = Node(element)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            newNode.next = head
            head?.prev = newNode
            head = newNode
        }
        _size++
    }

    actual override fun addLast(element: T) {
        val newNode = Node(element)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            newNode.prev = tail
            tail = newNode
        }
        _size++
    }

    actual override fun removeFirst(): T? {
        if (isEmpty()) return null
        val value = head!!.value
        if (head == tail) {
            head = null
            tail = null
        } else {
            head = head?.next
            head?.prev = null
        }
        _size--
        return value
    }

    actual fun removeLast(): T? {
        if (isEmpty()) return null
        val value = tail!!.value
        if (head == tail) {
            head = null
            tail = null
        } else {
            tail = tail?.prev
            tail?.next = null
        }
        _size--
        return value
    }

    actual operator fun get(index: Int): T {
        return nodeAt(index).value
    }

    actual operator fun set(index: Int, element: T) {
        nodeAt(index).value = element
    }

    actual override fun contains(element: T): Boolean {
        for (v in this) {
            if (v == element) return true
        }
        return false
    }

    actual override fun indexOf(element: T): Int {
        var idx = 0
        for (v in this) {
            if (v == element) return idx
            idx++
        }
        return -1
    }

    actual fun removeAt(index: Int): T {
        val node = nodeAt(index)
        when (node) {
            head -> {
                head = node.next
                head?.prev = null
                if (head == null) tail = null
            }
            tail -> {
                tail = node.prev
                tail?.next = null
            }
            else -> {
                node.prev?.next = node.next
                node.next?.prev = node.prev
            }
        }
        _size--
        return node.value
    }

    actual override fun clear() {
        head = null
        tail = null
        _size = 0
    }

    actual override fun isEmpty(): Boolean = _size == 0

    actual override fun toList(): List<T> = iterator().asSequence().toList()

    actual override fun toString(): String {
        if (isEmpty()) return "[]"
        return joinToString(prefix = "[", postfix = "]")
    }

    actual override fun iterator(): Iterator<T> = object : Iterator<T> {
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
            var node = head!!
            repeat(index) { node = node.next!! }
            node
        } else {
            var node = tail!!
            repeat(_size - 1 - index) { node = node.prev!! }
            node
        }
    }
}
