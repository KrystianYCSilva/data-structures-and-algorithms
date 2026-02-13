package br.com.leandroluce.algoritmos.datastructures.queue

interface Queue<T> {
    fun enqueue(element: T)
    fun dequeue(): T?
    val count: Int
    val isEmpty: Boolean
        get() = count == 0
    fun peek(): T?
}
