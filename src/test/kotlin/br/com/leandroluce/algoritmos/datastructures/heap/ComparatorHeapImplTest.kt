package br.com.leandroluce.algoritmos.datastructures.heap

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ComparatorHeapImplTest {

    @Test
    fun `insert and remove with custom comparator`() {
        val comparator = Comparator<Int> { o1, o2 -> o2.compareTo(o1) } // Max heap
        val heap = ComparatorHeapImpl(comparator)
        heap.insert(5)
        heap.insert(1)
        heap.insert(3)
        heap.insert(10)

        assertEquals(10, heap.remove())
        assertEquals(5, heap.remove())
        assertEquals(3, heap.remove())
        assertEquals(1, heap.remove())
        assertNull(heap.remove())
    }
}
