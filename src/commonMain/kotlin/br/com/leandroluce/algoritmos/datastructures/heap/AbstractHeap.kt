package br.com.leandroluce.algoritmos.datastructures.heap

import br.com.leandroluce.algoritmos.datastructures.queue.Queue

abstract class AbstractHeap<T> : Queue<T> {

    abstract override val count: Int
    abstract override fun peek(): T?
    abstract fun insert(element: T)

    protected fun leftChildIndex(index: Int) = (2 * index) + 1
    protected fun rightChildIndex(index: Int) = (2 * index) + 2
    protected fun parentIndex(index: Int) = (index - 1) / 2

    abstract fun remove(): T?
    abstract fun remove(index: Int): T?
    protected abstract fun siftDown(index: Int)
    protected abstract fun siftUp(index: Int)
}
