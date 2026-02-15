package br.uem.din.datastructures.heap

import br.uem.din.extensions.swap

class ComparableHeapImpl<T : Comparable<T>> : AbstractHeap<T>() {

    private var storage: ArrayList<T> = ArrayList()

    override fun size(): Int = storage.size

    override fun peek(): T? = storage.firstOrNull()

    override fun isEmpty(): Boolean = size() == 0

    override fun insert(element: T) {
        storage.add(element)
        siftUp(size() - 1)
    }

    override fun remove(): T? {
        if (isEmpty()) return null
        storage.swap(0, size() - 1)
        val removed = storage.removeAt(size() - 1)
        if (size() > 0) {
            siftDown(0)
        }
        return removed
    }

    override fun remove(index: Int): T? {
        if (index >= size()) return null
        return if (index == size() - 1) {
            storage.removeAt(size() - 1)
        } else {
            storage.swap(index, size() - 1)
            val removed = storage.removeAt(size() - 1)
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
            if (left < size() && storage[left] < storage[candidate]) {
                candidate = left
            }
            if (right < size() && storage[right] < storage[candidate]) {
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
}
