package br.uem.din.datastructures.heap

import br.uem.din.extensions.swap



class ComparableHeapImpl<T : Comparable<T>> : AbstractHeap<T>() {

    private var storage: ArrayList<T> = ArrayList()

    override val count: Int
        get() = storage.size

    override fun peek(): T? = storage.firstOrNull()

    override fun insert(element: T) {
        storage.add(element)
        siftUp(count - 1)
    }

    override fun remove(): T? {
        if (isEmpty) return null
        storage.swap(0, count - 1)
        val removed = storage.removeAt(count - 1)
        siftDown(0)
        return removed
    }

    override fun remove(index: Int): T? {
        if (index >= count) return null
        return if (index == count - 1) {
            storage.removeAt(count - 1)
        } else {
            storage.swap(index, count - 1)
            val removed = storage.removeAt(count - 1)
            siftDown(index)
            siftUp(index)
            removed
        }
    }

    override fun siftDown(index: Int) {
        var parent = index
        while (true) {
            val left = leftChildIndex(parent)
            val right = rightChildIndex(parent)
            var candidate = parent
            if (left < count && storage[left] < storage[candidate]) {
                candidate = left
            }
            if (right < count && storage[right] < storage[candidate]) {
                candidate = right
            }
            if (candidate == parent) {
                return
            }
            storage.swap(parent, candidate)
            parent = candidate
        }
    }

    override protected fun siftUp(index: Int) {
        var child = index
        var parent = parentIndex(child)
        while (child > 0 && storage[child] < storage[parent]) {
            storage.swap(child, parent)
            child = parent
            parent = parentIndex(child)
        }
    }

    override fun enqueue(element: T) {
        insert(element)
    }

    override fun dequeue(): T? {
        return remove()
    }
}
