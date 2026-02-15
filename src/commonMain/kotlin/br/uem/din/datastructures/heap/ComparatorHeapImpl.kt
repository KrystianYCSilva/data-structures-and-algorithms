package br.uem.din.datastructures.heap

import br.uem.din.extensions.swap



class ComparatorHeapImpl<T>(private val comparator: Comparator<T>) : AbstractHeap<T>() {

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
        siftDown(0)
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

    protected override fun siftDown(index: Int) {
        var parent = index
        while (true) {
            val left = leftChildIndex(parent)
            val right = rightChildIndex(parent)
            var candidate = parent
            if (left < size() && comparator.compare(storage[left], storage[candidate]) < 0) {
                candidate = left
            }
            if (right < size() && comparator.compare(storage[right], storage[candidate]) < 0) {
                candidate = right
            }
            if (candidate == parent) {
                return
            }
            storage.swap(parent, candidate)
            parent = candidate
        }
    }

    protected override fun siftUp(index: Int) {
        var child = index
        var parent = parentIndex(child)
        while (child > 0 && comparator.compare(storage[child], storage[parent]) < 0) {
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
