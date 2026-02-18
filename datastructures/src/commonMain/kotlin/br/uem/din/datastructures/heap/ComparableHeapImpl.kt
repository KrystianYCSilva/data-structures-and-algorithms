package br.uem.din.datastructures.heap

import br.uem.din.extensions.swap

/**
 * Implementação de heap binário (min-heap) para tipos que implementam [Comparable].
 *
 * Utiliza a ordenação natural dos elementos (via [Comparable.compareTo]) para manter
 * a propriedade de min-heap: o menor elemento está sempre na raiz.
 *
 * O heap é representado implicitamente em um [ArrayList], onde os índices dos filhos
 * e do pai são calculados aritmeticamente.
 *
 * Complexidades:
 * - [insert]: O(log n)
 * - [remove]: O(log n)
 * - [peek]: O(1)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 6 — Heapsort.
 */
internal class ComparableHeapImpl<T : Comparable<T>> : AbstractHeap<T>() {

    private var storage: ArrayList<T> = ArrayList()

    /** {@inheritDoc} */
    public override val size: Int get() = storage.size

    /** {@inheritDoc} */
    public override fun peek(): T? = storage.firstOrNull()

    /** {@inheritDoc} */
    public override fun isEmpty(): Boolean = size == 0

    /** {@inheritDoc} */
    public override fun insert(element: T) {
        storage.add(element)
        siftUp(size - 1)
    }

    /** {@inheritDoc} */
    public override fun remove(): T? {
        if (isEmpty()) return null
        storage.swap(0, size - 1)
        val removed = storage.removeAt(size - 1)
        if (size > 0) {
            siftDown(0)
        }
        return removed
    }

    /** {@inheritDoc} */
    public override fun remove(index: Int): T? {
        if (index >= size) return null
        return if (index == size - 1) {
            storage.removeAt(size - 1)
        } else {
            storage.swap(index, size - 1)
            val removed = storage.removeAt(size - 1)
            siftDown(index)
            siftUp(index)
            removed
        }
    }

    /** {@inheritDoc} */
    protected override fun siftDown(index: Int) {
        var parent = index
        while (true) {
            val left = leftChildIndex(parent)
            val right = rightChildIndex(parent)
            var candidate = parent
            if (left < size && storage[left] < storage[candidate]) {
                candidate = left
            }
            if (right < size && storage[right] < storage[candidate]) {
                candidate = right
            }
            if (candidate == parent) {
                return
            }
            storage.swap(parent, candidate)
            parent = candidate
        }
    }

    /** {@inheritDoc} */
    protected override fun siftUp(index: Int) {
        var child = index
        var parent = parentIndex(child)
        while (child > 0 && storage[child] < storage[parent]) {
            storage.swap(child, parent)
            child = parent
            parent = parentIndex(child)
        }
    }

    public override fun contains(element: T): Boolean = storage.contains(element)

    public override fun clear(): Unit = storage.clear()

    public override fun iterator(): Iterator<T> = storage.iterator()
}
