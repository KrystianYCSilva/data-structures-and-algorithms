package br.uem.din.datastructures.stack

import br.uem.din.datastructures.linkedlist.LinkedList

class LinkedStack<T> : Stack<T> {
    private val list = LinkedList<T>()

    override val count: Int
        get() = list.size

    override fun push(element: T) {
        list.push(element)
    }

    override fun pop(): T? {
        return list.pop()
    }

    override fun peek(): T? {
        return list.nodeAt(0)?.value
    }
    
    override fun toString(): String = list.toString()
}
