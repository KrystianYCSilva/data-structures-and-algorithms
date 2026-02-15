package br.uem.din.datastructures.linkedlist

class CircularLinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    var size = 0
        private set

    fun isEmpty() = size == 0

    fun add(value: T) {
        val newNode = Node(value)
        if (isEmpty()) {
            head = newNode
            tail = newNode
            newNode.next = head // Points to itself
        } else {
            tail?.next = newNode
            tail = newNode
            tail?.next = head // Points back to head
        }
        size++
    }

    fun remove(value: T): Boolean {
        if (isEmpty()) return false

        var current = head
        var prev: Node<T>? = tail

        do {
            if (current?.value == value) {
                if (current == head && current == tail) { // Only one node
                    head = null
                    tail = null
                } else if (current == head) {
                    head = head?.next
                    tail?.next = head
                } else if (current == tail) {
                    tail = prev
                    tail?.next = head
                } else {
                    prev?.next = current?.next
                }
                size--
                return true
            }
            prev = current
            current = current?.next
        } while (current != head)

        return false
    }

    operator fun get(index: Int): T? {
        if (isEmpty() || index < 0 || index >= size) return null
        var current = head
        repeat(index) {
            current = current?.next
        }
        return current?.value
    }

    override fun toString(): String {
        if (isEmpty()) return "[]"
        val sb = StringBuilder("[")
        var current = head
        do {
            sb.append(current?.value)
            current = current?.next
            if (current != head) {
                sb.append(", ")
            }
        } while (current != head)
        sb.append("]")
        return sb.toString()
    }
}
