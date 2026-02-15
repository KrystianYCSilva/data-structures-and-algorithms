package br.uem.din.datastructures.stack

/**
 * A concrete implementation of [MutableStack] backed by an array (or Vector on JVM).
 */
expect class ArrayStack<T>() : MutableStack<T> {
    override fun push(element: T): T
    override fun pop(): T?
    override fun peek(): T?
    override fun size(): Int
    override fun isEmpty(): Boolean
    override fun toString(): String
}
