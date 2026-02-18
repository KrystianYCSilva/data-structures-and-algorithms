package br.uem.din.datastructures.heap

import kotlin.test.*

class ComparatorHeapImplTest {

    @Test
    fun testMinHeap() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        heap.insert(3)
        heap.insert(1)
        heap.insert(2)
        assertEquals(1, heap.peek())
        assertEquals(1, heap.remove())
        assertEquals(2, heap.remove())
        assertEquals(3, heap.remove())
    }

    @Test
    fun testMaxHeap() {
        val heap = ComparatorHeapImpl(compareByDescending<Int> { it })
        heap.insert(3)
        heap.insert(1)
        heap.insert(2)
        assertEquals(3, heap.peek())
        assertEquals(3, heap.remove())
        assertEquals(2, heap.remove())
        assertEquals(1, heap.remove())
    }

    @Test
    fun testRemoveEmpty() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        assertNull(heap.remove())
    }

    @Test
    fun testRemoveByIndex() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        heap.insert(5)
        heap.insert(3)
        heap.insert(7)
        heap.insert(1)
        assertEquals(1, heap.remove(0))
    }

    @Test
    fun testRemoveByInvalidIndex() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        heap.insert(5)
        assertNull(heap.remove(99))
    }

    @Test
    fun testSizeAndIsEmpty() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        assertTrue(heap.isEmpty())
        assertEquals(0, heap.size)
        heap.insert(1)
        heap.insert(2)
        heap.insert(3)
        assertFalse(heap.isEmpty())
        assertEquals(3, heap.size)
    }

    @Test
    fun testContains() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        heap.insert(10)
        heap.insert(20)
        heap.insert(30)
        assertTrue(heap.contains(20))
        assertFalse(heap.contains(99))
    }

    @Test
    fun testClear() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        heap.insert(1)
        heap.insert(2)
        heap.clear()
        assertTrue(heap.isEmpty())
        assertEquals(0, heap.size)
    }

    @Test
    fun testPeekEmpty() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        assertNull(heap.peek())
    }

    @Test
    fun testRemoveSingleElement() {
        val heap = ComparatorHeapImpl(compareBy<Int> { it })
        heap.insert(42)
        assertEquals(42, heap.remove())
        assertTrue(heap.isEmpty())
    }
}
