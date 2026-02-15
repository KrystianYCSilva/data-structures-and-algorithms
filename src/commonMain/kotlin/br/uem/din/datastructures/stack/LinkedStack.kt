package br.uem.din.datastructures.stack

import br.uem.din.datastructures.linkedlist.LinkedList

/**
 * A Stack implementation based on a Singly Linked List.
 */
class LinkedStack<T> : MutableStack<T> {
    private val list = LinkedList<T>()

    override fun push(element: T): T {
        list.push(element)
        return element
    }

    override fun pop(): T? {
        return list.pop()
    }

    override fun peek(): T? {
        return list.nodeAt(0)?.value
    }

    override fun size(): Int = list.size

    override fun isEmpty(): Boolean = list.isEmpty()
    
    override fun toString(): String = list.toString()
}
