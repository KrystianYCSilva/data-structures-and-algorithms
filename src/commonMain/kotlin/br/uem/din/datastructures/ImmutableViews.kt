package br.uem.din.datastructures

import br.uem.din.datastructures.queue.MutableQueue
import br.uem.din.datastructures.queue.Queue
import br.uem.din.datastructures.stack.MutableStack
import br.uem.din.datastructures.stack.Stack

/**
 * Small immutable/view helpers to allow treating mutable structures as read-only where appropriate.
 * These create lightweight read-only views (no defensive copy) to encourage functional usage patterns.
 */

fun <T> MutableStack<T>.asReadOnly(): Stack<T> = object : Stack<T> {
    override fun peek(): T? = this@asReadOnly.peek()
    override fun size(): Int = this@asReadOnly.size()
    override fun isEmpty(): Boolean = this@asReadOnly.isEmpty()
}

fun <T> MutableQueue<T>.asReadOnly(): Queue<T> = object : Queue<T> {
    override fun peek(): T? = this@asReadOnly.peek()
    override fun size(): Int = this@asReadOnly.size()
    override fun isEmpty(): Boolean = this@asReadOnly.isEmpty()
}

/**
 * Snapshot helpers that produce immutable copies of the current contents.
 * Implemented by temporarily using mutation operations and restoring the original state.
 * Returns List<T?> because stack/queue pop/dequeue return nullable values.
 */

fun <T> MutableStack<T>.snapshot(): List<T?> {
    val popped = ArrayList<T?>()
    while (!isEmpty()) {
        popped.add(pop())
    }
    // restore original order
    for (i in popped.size - 1 downTo 0) {
        val v = popped[i]
        @Suppress("UNCHECKED_CAST")
        push(v as T)
    }
    return popped.toList()
}

fun <T> MutableQueue<T>.snapshot(): List<T?> {
    val n = size()
    val list = ArrayList<T?>(n)
    for (i in 0 until n) {
        val v = dequeue()
        list.add(v)
        @Suppress("UNCHECKED_CAST")
        enqueue(v as T)
    }
    return list.toList()
}
