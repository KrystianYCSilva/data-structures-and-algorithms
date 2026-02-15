package br.uem.din.datastructures.queue

import br.uem.din.datastructures.linkedlist.LinkedList

/**
 * A Queue implementation based on a Singly Linked List.
 */
class LinkedQueue<T> : MutableQueue<T> {
    private val list = LinkedList<T>()

    override fun enqueue(element: T) {
        list.append(element)
    }

    override fun dequeue(): T? {
        return list.pop()
    }

    override fun peek(): T? {
        return list.nodeAt(0)?.value
    }

    override fun size(): Int = list.size

    override fun isEmpty(): Boolean = list.isEmpty()
    
    override fun toString(): String = list.toString()
}
