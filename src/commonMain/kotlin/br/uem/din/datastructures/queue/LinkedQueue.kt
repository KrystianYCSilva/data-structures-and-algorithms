package br.uem.din.datastructures.queue

import br.uem.din.datastructures.linkedlist.LinkedList

class LinkedQueue<T> : Queue<T> {
    private val list = LinkedList<T>()

    override val count: Int
        get() = list.size

    override fun enqueue(element: T) {
        list.append(element)
    }

    override fun dequeue(): T? {
        return list.pop()
    }

    override fun peek(): T? {
        return list.nodeAt(0)?.value
    }
    
    override fun toString(): String = list.toString()
}
