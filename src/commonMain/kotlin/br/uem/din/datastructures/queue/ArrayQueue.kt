package br.uem.din.datastructures.queue

expect class ArrayQueue<T>() : MutableQueue<T> {
    override fun enqueue(element: T)
    override fun dequeue(): T?
    override fun peek(): T?
    override fun size(): Int
    override fun isEmpty(): Boolean
    override fun toString(): String
}
