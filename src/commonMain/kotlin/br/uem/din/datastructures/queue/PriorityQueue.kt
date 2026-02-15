package br.uem.din.datastructures.queue

expect class PriorityQueue<T>(comparator: Comparator<T>? = null) {
    fun enqueue(element: T)
    fun dequeue(): T?
    fun peek(): T?
    fun size(): Int
    fun isEmpty(): Boolean
}
