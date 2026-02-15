package br.uem.din.datastructures.queue

import kotlin.test.*

class PriorityQueueTest {

    @Test
    fun testMinHeapNaturalOrder() {
        val pq = PriorityQueue<Int>()
        pq.enqueue(30)
        pq.enqueue(10)
        pq.enqueue(20)
        assertEquals(3, pq.size())
        assertEquals(10, pq.peek())
        assertEquals(10, pq.dequeue())
        assertEquals(20, pq.dequeue())
        assertEquals(30, pq.dequeue())
        assertNull(pq.dequeue())
    }

    @Test
    fun testMaxHeapWithComparator() {
        val pq = PriorityQueue<Int>(compareByDescending { it })
        pq.enqueue(10)
        pq.enqueue(30)
        pq.enqueue(20)
        assertEquals(30, pq.dequeue())
        assertEquals(20, pq.dequeue())
        assertEquals(10, pq.dequeue())
    }

    @Test
    fun testContains() {
        val pq = PriorityQueue<Int>()
        pq.enqueue(5)
        pq.enqueue(10)
        assertTrue(pq.contains(5))
        assertFalse(pq.contains(99))
    }

    @Test
    fun testClear() {
        val pq = PriorityQueue<Int>()
        pq.enqueue(1)
        pq.enqueue(2)
        pq.clear()
        assertTrue(pq.isEmpty())
        assertEquals(0, pq.size())
    }

    @Test
    fun testIterator() {
        val pq = PriorityQueue<Int>()
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
        val pq = PriorityQueue<String>()
        pq.enqueue("banana")
        pq.enqueue("apple")
        pq.enqueue("cherry")
        assertEquals("apple", pq.dequeue())
        assertEquals("banana", pq.dequeue())
        assertEquals("cherry", pq.dequeue())
    }
}
