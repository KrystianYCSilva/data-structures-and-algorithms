package br.uem.din.datastructures.heap

import kotlin.test.*

class BinomialHeapTest {

    @Test
    fun testInsertAndPeek() {
        val heap = BinomialHeap<Int>()
        heap.insert(5)
        heap.insert(3)
        heap.insert(7)
        assertEquals(3, heap.peek())
    }

    @Test
    fun testExtractMin() {
        val heap = BinomialHeap<Int>()
        heap.insert(5)
        heap.insert(3)
        heap.insert(7)
        heap.insert(1)
        assertEquals(1, heap.extractMin())
        assertEquals(3, heap.extractMin())
        assertEquals(5, heap.extractMin())
        assertEquals(7, heap.extractMin())
    }

    @Test
    fun testExtractMinEmpty() {
        val heap = BinomialHeap<Int>()
        assertNull(heap.extractMin())
    }

    @Test
    fun testSize() {
        val heap = BinomialHeap<Int>()
        assertEquals(0, heap.size)
        heap.insert(1)
        heap.insert(2)
        heap.insert(3)
        assertEquals(3, heap.size)
        heap.extractMin()
        assertEquals(2, heap.size)
    }

    @Test
    fun testIsEmpty() {
        val heap = BinomialHeap<Int>()
        assertTrue(heap.isEmpty())
        heap.insert(1)
        assertFalse(heap.isEmpty())
    }

    @Test
    fun testPeekEmpty() {
        val heap = BinomialHeap<Int>()
        assertNull(heap.peek())
    }

    @Test
    fun testMerge() {
        val heap1 = BinomialHeap<Int>()
        heap1.insert(4)
        heap1.insert(2)
        val heap2 = BinomialHeap<Int>()
        heap2.insert(5)
        heap2.insert(1)
        heap2.insert(3)
        heap1.merge(heap2)
        assertEquals(1, heap1.peek())
        assertEquals(5, heap1.size)
    }

    @Test
    fun testInsertMany() {
        val heap = BinomialHeap<Int>()
        for (i in 10 downTo 1) {
            heap.insert(i)
        }
        for (i in 1..10) {
            assertEquals(i, heap.extractMin())
        }
    }

    @Test
    fun testMergeWithEmpty() {
        val heap1 = BinomialHeap<Int>()
        heap1.insert(3)
        heap1.insert(1)
        val empty = BinomialHeap<Int>()
        heap1.merge(empty)
        assertEquals(1, heap1.peek())
        assertEquals(2, heap1.size)
    }

    @Test
    fun testExtractMinSingleElement() {
        val heap = BinomialHeap<Int>()
        heap.insert(42)
        assertEquals(42, heap.extractMin())
        assertTrue(heap.isEmpty())
    }
}
