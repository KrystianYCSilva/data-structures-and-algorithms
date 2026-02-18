package br.uem.din.datastructures.heap

import kotlin.test.*

class FibonacciHeapTest {

    @Test
    fun testInsertAndPeek() {
        val heap = FibonacciHeap<Int>()
        heap.insert(5)
        heap.insert(3)
        heap.insert(7)
        assertEquals(3, heap.peek())
    }

    @Test
    fun testExtractMin() {
        val heap = FibonacciHeap<Int>()
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
        val heap = FibonacciHeap<Int>()
        assertNull(heap.extractMin())
    }

    @Test
    fun testSize() {
        val heap = FibonacciHeap<Int>()
        assertEquals(0, heap.size)
        heap.insert(1)
        heap.insert(2)
        heap.insert(3)
        assertEquals(3, heap.size)
    }

    @Test
    fun testIsEmpty() {
        val heap = FibonacciHeap<Int>()
        assertTrue(heap.isEmpty())
        heap.insert(1)
        assertFalse(heap.isEmpty())
    }

    @Test
    fun testPeekEmpty() {
        val heap = FibonacciHeap<Int>()
        assertNull(heap.peek())
    }

    @Test
    fun testMerge() {
        val heap1 = FibonacciHeap<Int>()
        heap1.insert(4)
        heap1.insert(2)
        val heap2 = FibonacciHeap<Int>()
        heap2.insert(5)
        heap2.insert(1)
        heap2.insert(3)
        heap1.merge(heap2)
        assertEquals(1, heap1.peek())
        assertEquals(5, heap1.size)
    }

    @Test
    fun testDecreaseKey() {
        val heap = FibonacciHeap<Int>()
        heap.insert(10)
        heap.insert(20)
        val node = heap.insert(30)
        heap.decreaseKey(node, 5)
        assertEquals(5, heap.peek())
    }

    @Test
    fun testExtractMinSingleElement() {
        val heap = FibonacciHeap<Int>()
        heap.insert(42)
        assertEquals(42, heap.extractMin())
        assertTrue(heap.isEmpty())
    }

    @Test
    fun testInsertMany() {
        val heap = FibonacciHeap<Int>()
        for (i in 10 downTo 1) {
            heap.insert(i)
        }
        for (i in 1..10) {
            assertEquals(i, heap.extractMin())
        }
    }
}
