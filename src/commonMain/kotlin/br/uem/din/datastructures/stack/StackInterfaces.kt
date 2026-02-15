package br.uem.din.datastructures.stack

/**
 * Read-only interface for a Stack (LIFO).
 */
interface Stack<T> {
    /** Looks at the object at the top of this stack without removing it. */
    fun peek(): T?

    /** Returns the number of items in the stack. */
    fun size(): Int

    /** Returns true if the stack is empty. */
    fun isEmpty(): Boolean
}

/**
 * Mutable interface for a Stack (LIFO).
 */
interface MutableStack<T> : Stack<T> {
    /** Pushes an item onto the top of this stack. */
    fun push(element: T): T

    /** Removes the object at the top of this stack and returns that object. */
    fun pop(): T?
}
