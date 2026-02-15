package br.uem.din.datastructures.queue

actual class PriorityQueue<T> actual constructor(private val comparator: Comparator<T>?) {
    private val heap = ArrayList<T>()

    actual fun enqueue(element: T) {
        heap.add(element)
        siftUp(heap.size - 1)
    }

    actual fun dequeue(): T? {
        if (isEmpty()) return null
        val result = heap[0]
        val last = heap.removeAt(heap.size - 1)
        if (heap.isNotEmpty()) {
            heap[0] = last
            siftDown(0)
        }
        return result
    }

    actual fun peek(): T? = if (isEmpty()) null else heap[0]
    actual fun size(): Int = heap.size
    actual fun isEmpty(): Boolean = heap.isEmpty()

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
            var right = child + 1
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
