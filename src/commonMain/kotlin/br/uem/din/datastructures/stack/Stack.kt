package br.uem.din.datastructures.stack

interface Stack<T> {
    fun push(element: T)
    fun pop(): T?
    val count: Int
    val isEmpty: Boolean
        get() = count == 0
    fun peek(): T?
}
