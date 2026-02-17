package br.uem.din.datastructures.queue

import kotlin.test.*

class PriorityQueueTest {

    @Test
    fun testMinHeapNaturalOrder() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(30)
        pq.enqueue(10)
        pq.enqueue(20)
        assertEquals(3, pq.size)
        assertEquals(10, pq.peek())
        assertEquals(10, pq.dequeue())
        assertEquals(20, pq.dequeue())
        assertEquals(30, pq.dequeue())
        assertNull(pq.dequeue())
    }

    @Test
    fun testMaxHeapWithComparator() {
        val pq = priorityQueueOf<Int>(compareByDescending { it })
        pq.enqueue(10)
        pq.enqueue(30)
        pq.enqueue(20)
        assertEquals(30, pq.dequeue())
        assertEquals(20, pq.dequeue())
        assertEquals(10, pq.dequeue())
    }

    @Test
    fun testContains() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(5)
        pq.enqueue(10)
        assertTrue(pq.contains(5))
        assertFalse(pq.contains(99))
    }

    @Test
    fun testClear() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(1)
        pq.enqueue(2)
        pq.clear()
        assertTrue(pq.isEmpty())
        assertEquals(0, pq.size)
    }

    @Test
    fun testIterator() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(3)
        pq.enqueue(1)
        pq.enqueue(2)
        val collected = mutableListOf<Int>()
        for (v in pq) collected.add(v)
        assertEquals(3, collected.size)
        assertTrue(collected.containsAll(listOf(1, 2, 3)))
    }

    @Test
    fun testStringPriorityQueue() {
        val pq = priorityQueueOf<String>()
        pq.enqueue("banana")
        pq.enqueue("apple")
        pq.enqueue("cherry")
        assertEquals("apple", pq.dequeue())
        assertEquals("banana", pq.dequeue())
        assertEquals("cherry", pq.dequeue())
    }

    @Test
    fun testPriorityQueueIsEmptyOnNew() {
        val pq = priorityQueueOf<Int>()
        assertTrue(pq.isEmpty())
        assertEquals(0, pq.size)
    }

    @Test
    fun testPriorityQueuePeekEmpty() {
        val pq = priorityQueueOf<Int>()
        assertNull(pq.peek())
    }

    @Test
    fun testPriorityQueueDequeueEmpty() {
        val pq = priorityQueueOf<Int>()
        assertNull(pq.dequeue())
    }

    @Test
    fun testPriorityQueueSizeAfterEnqueue() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(5)
        assertEquals(1, pq.size)
        assertFalse(pq.isEmpty())
        pq.enqueue(3)
        assertEquals(2, pq.size)
        pq.enqueue(7)
        assertEquals(3, pq.size)
    }

    @Test
    fun testPriorityQueuePeekDoesNotRemove() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(42)
        assertEquals(42, pq.peek())
        assertEquals(42, pq.peek())
        assertEquals(1, pq.size)
    }

    @Test
    fun testPriorityQueueContainsAfterDequeue() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(1)
        pq.enqueue(2)
        pq.dequeue()
        assertFalse(pq.contains(1))
        assertTrue(pq.contains(2))
    }

    @Test
    fun testPriorityQueueClearThenReuse() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(10)
        pq.enqueue(20)
        pq.clear()
        assertTrue(pq.isEmpty())
        assertNull(pq.peek())
        pq.enqueue(5)
        assertEquals(5, pq.peek())
        assertEquals(1, pq.size)
    }

    @Test
    fun testPriorityQueueManyElements() {
        val pq = priorityQueueOf<Int>()
        val values = (100 downTo 1).toList()
        values.forEach { pq.enqueue(it) }
        assertEquals(100, pq.size)
        for (expected in 1..100) {
            assertEquals(expected, pq.dequeue())
        }
        assertTrue(pq.isEmpty())
    }

    @Test
    fun testPriorityQueueDuplicateElements() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(5)
        pq.enqueue(5)
        pq.enqueue(5)
        assertEquals(3, pq.size)
        assertEquals(5, pq.dequeue())
        assertEquals(5, pq.dequeue())
        assertEquals(5, pq.dequeue())
        assertNull(pq.dequeue())
    }

    @Test
    fun testPriorityQueueMixedEnqueueDequeue() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(10)
        pq.enqueue(4)
        assertEquals(4, pq.dequeue())
        pq.enqueue(2)
        pq.enqueue(8)
        assertEquals(2, pq.dequeue())
        assertEquals(8, pq.dequeue())
        assertEquals(10, pq.dequeue())
        assertNull(pq.dequeue())
    }

    @Test
    fun testPriorityQueueSingleElement() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(42)
        assertEquals(42, pq.peek())
        assertTrue(pq.contains(42))
        assertEquals(42, pq.dequeue())
        assertTrue(pq.isEmpty())
    }

    @Test
    fun testPriorityQueueToString() {
        val pq = priorityQueueOf<Int>()
        pq.enqueue(1)
        val str = pq.toString()
        assertTrue(str.isNotEmpty())
    }
}
