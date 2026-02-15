package br.uem.din.datastructures.heap

import br.uem.din.extensions.swap

/**
 * Implementação de heap binário utilizando um [Comparator] externo para definir a ordenação.
 *
 * Diferentemente de [ComparableHeapImpl], esta classe aceita qualquer tipo `T` e delega
 * a comparação ao [Comparator] fornecido no construtor. Isso permite criar tanto min-heaps
 * quanto max-heaps, ou heaps com critérios de ordenação customizados.
 *
 * O heap é representado implicitamente em um [ArrayList].
 *
 * Complexidades:
 * - [insert]: O(log n)
 * - [remove]: O(log n)
 * - [peek]: O(1)
 *
 * @param T o tipo dos elementos armazenados.
 * @param comparator o comparador utilizado para ordenação dos elementos no heap.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 6 — Heapsort.
 */
class ComparatorHeapImpl<T>(private val comparator: Comparator<T>) : AbstractHeap<T>() {

    private var storage: ArrayList<T> = ArrayList()

    /** {@inheritDoc} */
    override fun size(): Int = storage.size

    /** {@inheritDoc} */
    override fun peek(): T? = storage.firstOrNull()

    /** {@inheritDoc} */
    override fun isEmpty(): Boolean = size() == 0

    /** {@inheritDoc} */
    override fun insert(element: T) {
        storage.add(element)
        siftUp(size() - 1)
    }

    /** {@inheritDoc} */
    override fun remove(): T? {
        if (isEmpty()) return null
        storage.swap(0, size() - 1)
        val removed = storage.removeAt(size() - 1)
        siftDown(0)
        return removed
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    protected override fun siftUp(index: Int) {
        var child = index
        var parent = parentIndex(child)
        while (child > 0 && comparator.compare(storage[child], storage[parent]) < 0) {
            storage.swap(child, parent)
            child = parent
            parent = parentIndex(child)
        }
    }

    override fun contains(element: T): Boolean = storage.contains(element)

    override fun clear() = storage.clear()

    override fun iterator(): Iterator<T> = storage.iterator()
}
