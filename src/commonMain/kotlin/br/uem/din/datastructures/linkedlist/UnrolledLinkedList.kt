package br.uem.din.datastructures.linkedlist

class UnrolledLinkedList<T>(val nodeCapacity: Int = 16) {
    
    private inner class UnrolledNode(var next: UnrolledNode? = null) {
        val elements = arrayOfNulls<Any?>(nodeCapacity)
        var count = 0
        
        fun isFull() = count == nodeCapacity
        
        fun add(element: T) {
            elements[count++] = element
        }
    }

    private var head: UnrolledNode? = null
    private var tail: UnrolledNode? = null
    var size = 0
        private set

    fun add(element: T) {
        if (head == null) {
            head = UnrolledNode()
            tail = head
        }
        
        if (tail!!.isFull()) {
            val newNode = UnrolledNode()
            tail!!.next = newNode
            tail = newNode
        }
        
        tail!!.add(element)
        size++
    }

    operator fun get(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException()
        var node = head
        var idx = index
        while (node != null) {
            if (idx < node.count) {
                @Suppress("UNCHECKED_CAST")
                return node.elements[idx] as T
            }
            idx -= node.count
            node = node.next
        }
        throw IndexOutOfBoundsException()
    }
    
    fun isEmpty() = size == 0
}
