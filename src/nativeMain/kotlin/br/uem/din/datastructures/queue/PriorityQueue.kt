package br.uem.din.datastructures.queue

/**
 * Implementação Native de [PriorityQueue] com binary min-heap manual.
 *
 * @param T o tipo dos elementos. Deve ser [Comparable] se nenhum [Comparator] for fornecido.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 6 — Heapsort.
 */
actual class PriorityQueue<T> actual constructor(private val comparator: Comparator<T>?) : MutableQueue<T> {
    private val heap = ArrayList<T>()

    actual override fun enqueue(element: T) {
        heap.add(element)
        siftUp(heap.size - 1)
    }

    actual override fun dequeue(): T? {
        if (isEmpty()) return null
        val result = heap[0]
        val last = heap.removeAt(heap.size - 1)
        if (heap.isNotEmpty()) {
            heap[0] = last
            siftDown(0)
        }
        return result
    }

    actual override fun peek(): T? = if (isEmpty()) null else heap[0]

    actual override val size: Int get() = heap.size

    actual override fun isEmpty(): Boolean = heap.isEmpty()

    actual override fun contains(element: T): Boolean = heap.contains(element)

    actual override fun clear() = heap.clear()

    actual override fun iterator(): Iterator<T> = heap.iterator()

    actual override fun toString(): String = heap.toString()

    @Suppress("UNCHECKED_CAST")
    private fun compare(a: T, b: T): Int {
        return if (comparator != null) {
            comparator.compare(a, b)
        } else {
            (a as Comparable<T>).compareTo(b)
        }
    }

    private fun siftUp(index: Int) {
        var k = index
        while (k > 0) {
            val parent = (k - 1) / 2
            if (compare(heap[k], heap[parent]) >= 0) break
            swap(k, parent)
            k = parent
        }
    }

    private fun siftDown(index: Int) {
        var k = index
        val half = heap.size / 2
        while (k < half) {
            var child = 2 * k + 1
            val right = child + 1
            if (right < heap.size && compare(heap[right], heap[child]) < 0) {
                child = right
            }
            if (compare(heap[k], heap[child]) <= 0) break
            swap(k, child)
            k = child
        }
    }

    private fun swap(i: Int, j: Int) {
        val temp = heap[i]
        heap[i] = heap[j]
        heap[j] = temp
    }
}
