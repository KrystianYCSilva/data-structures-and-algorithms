package br.uem.din.datastructures.heap

import kotlin.test.*

class ComparableHeapImplTest {

    @Test
    fun testInsertAndPeek() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(3)
        heap.insert(1)
        heap.insert(2)
        assertEquals(1, heap.peek())
    }

    @Test
    fun testRemoveRoot() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(3)
        heap.insert(1)
        heap.insert(2)
        assertEquals(1, heap.remove())
        assertEquals(2, heap.remove())
        assertEquals(3, heap.remove())
    }

    @Test
    fun testRemoveFromEmpty() {
        val heap = ComparableHeapImpl<Int>()
        assertNull(heap.remove())
    }

    @Test
    fun testRemoveByIndex() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(5)
        heap.insert(3)
        heap.insert(7)
        heap.insert(1)
        assertEquals(1, heap.remove(0))
    }

    @Test
    fun testRemoveByInvalidIndex() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(5)
        heap.insert(3)
        assertNull(heap.remove(99))
    }

    @Test
    fun testSize() {
        val heap = ComparableHeapImpl<Int>()
        assertEquals(0, heap.size)
        heap.insert(1)
        heap.insert(2)
        heap.insert(3)
        assertEquals(3, heap.size)
        heap.remove()
        assertEquals(2, heap.size)
    }

    @Test
    fun testIsEmpty() {
        val heap = ComparableHeapImpl<Int>()
        assertTrue(heap.isEmpty())
        heap.insert(1)
        assertFalse(heap.isEmpty())
        heap.clear()
        assertTrue(heap.isEmpty())
    }

    @Test
    fun testContains() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(10)
        heap.insert(20)
        heap.insert(30)
        assertTrue(heap.contains(20))
        assertFalse(heap.contains(99))
    }

    @Test
    fun testClear() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(1)
        heap.insert(2)
        heap.insert(3)
        heap.clear()
        assertTrue(heap.isEmpty())
        assertEquals(0, heap.size)
    }

    @Test
    fun testPeekEmpty() {
        val heap = ComparableHeapImpl<Int>()
        assertNull(heap.peek())
    }

    @Test
    fun testEnqueueDequeue() {
        val heap = ComparableHeapImpl<Int>()
        heap.enqueue(5)
        heap.enqueue(1)
        heap.enqueue(3)
        assertEquals(1, heap.dequeue())
    }

    @Test
    fun testIterator() {
        val heap = ComparableHeapImpl<Int>()
        heap.insert(3)
        heap.insert(1)
        heap.insert(2)
        val elements = heap.iterator().asSequence().toList()
        assertEquals(3, elements.size)
        assertTrue(elements.contains(1))
        assertTrue(elements.contains(2))
        assertTrue(elements.contains(3))
    }
}
