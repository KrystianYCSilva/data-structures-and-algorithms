package br.uem.din.datastructures.queue

import br.uem.din.datastructures.heap.ComparatorHeapImpl

class PriorityQueue<T>(private val comparator: Comparator<T>) : Queue<T> {

    private val heap = ComparatorHeapImpl(comparator)

    override val count: Int
        get() = heap.count

    override fun enqueue(element: T) {
        heap.insert(element)
    }

    override fun dequeue(): T? {
        return heap.remove()
    }

    override fun peek(): T? {
        return heap.peek()
    }
}
