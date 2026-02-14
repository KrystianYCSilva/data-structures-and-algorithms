package br.com.leandroluce.algoritmos.datastructures.heap

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ComparableHeapImplTest {

    @Test
    fun `insert and remove`() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(5)
        heap.insert(1)
        heap.insert(3)
        heap.insert(10)

        assertEquals(1, heap.remove())
        assertEquals(3, heap.remove())
        assertEquals(5, heap.remove())
        assertEquals(10, heap.remove())
        assertNull(heap.remove())
    }

    @Test
    fun `remove at index`() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(5)
        heap.insert(1)
        heap.insert(3)
        heap.insert(10)

        assertEquals(3, heap.remove(1))
        assertEquals(3, heap.count)
    }
}
