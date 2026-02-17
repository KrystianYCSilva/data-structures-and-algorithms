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

    @Test
    fun testMergeExtractAll() {
        val h1 = BinomialHeap<Int>()
        h1.insert(4); h1.insert(2)
        val h2 = BinomialHeap<Int>()
        h2.insert(5); h2.insert(1); h2.insert(3)
        h1.merge(h2)
        val extracted = mutableListOf<Int>()
        while (!h1.isEmpty()) {
            extracted.add(h1.extractMin()!!)
        }
        assertEquals(listOf(1, 2, 3, 4, 5), extracted)
    }

    @Test
    fun testDuplicateElements() {
        val heap = BinomialHeap<Int>()
        heap.insert(3); heap.insert(3); heap.insert(3)
        assertEquals(3, heap.size)
        assertEquals(3, heap.extractMin())
        assertEquals(3, heap.extractMin())
        assertEquals(3, heap.extractMin())
        assertTrue(heap.isEmpty())
    }

    @Test
    fun testMergeTwoEmpty() {
        val h1 = BinomialHeap<Int>()
        val h2 = BinomialHeap<Int>()
        h1.merge(h2)
        assertTrue(h1.isEmpty())
        assertNull(h1.peek())
    }
}
