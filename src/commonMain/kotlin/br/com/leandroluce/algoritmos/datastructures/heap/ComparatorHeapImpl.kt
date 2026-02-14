package br.com.leandroluce.algoritmos.datastructures.heap

import br.com.leandroluce.algoritmos.extensions.swap



class ComparatorHeapImpl<T>(private val comparator: Comparator<T>) : AbstractHeap<T>() {

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

    override protected fun siftDown(index: Int) {
        var parent = index
        while (true) {
            val left = leftChildIndex(parent)
            val right = rightChildIndex(parent)
            var candidate = parent
            if (left < count && comparator.compare(storage[left], storage[candidate]) < 0) {
                candidate = left
            }
            if (right < count && comparator.compare(storage[right], storage[candidate]) < 0) {
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
