package br.uem.din.datastructures.heap

import br.uem.din.datastructures.queue.MutableQueue

abstract class AbstractHeap<T> : MutableQueue<T> {

    abstract override fun size(): Int
    abstract override fun peek(): T?
    abstract fun insert(element: T)

    // Implement MutableQueue
    override fun enqueue(element: T) {
        insert(element)
    }

    override fun dequeue(): T? {
        return remove()
    }

    protected fun leftChildIndex(index: Int) = (2 * index) + 1
    protected fun rightChildIndex(index: Int) = (2 * index) + 2
    protected fun parentIndex(index: Int) = (index - 1) / 2

    abstract fun remove(): T?
    abstract fun remove(index: Int): T?
    protected abstract fun siftDown(index: Int)
    protected abstract fun siftUp(index: Int)
}
