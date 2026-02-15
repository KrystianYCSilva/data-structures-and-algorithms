package br.uem.din.datastructures.queue

import kotlin.test.*

class CircularQueueTest {

    @Test
    fun testBasicEnqueueDequeue() {
        val queue = CircularQueue<Int>(3)
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(3, queue.size())
        assertTrue(queue.isFull)
        assertEquals(1, queue.dequeue())
        assertFalse(queue.isFull)
        queue.enqueue(4)
        assertEquals(3, queue.size())
    }

    @Test
    fun testPeekEmpty() {
        val queue = CircularQueue<Int>(3)
        assertNull(queue.peek())
    }

    @Test
    fun testFullThrows() {
        val queue = CircularQueue<Int>(2)
        queue.enqueue(1)
        queue.enqueue(2)
        assertTrue(queue.isFull)
        assertFailsWith<IllegalStateException> { queue.enqueue(3) }
    }

    @Test
    fun testOffer() {
        val queue = CircularQueue<Int>(2)
        assertTrue(queue.offer(1))
        assertTrue(queue.offer(2))
        assertFalse(queue.offer(3))
    }

    @Test
    fun testContains() {
        val queue = CircularQueue<String>(5)
        queue.enqueue("a")
        queue.enqueue("b")
        assertTrue(queue.contains("a"))
        assertFalse(queue.contains("z"))
    }

    @Test
    fun testClear() {
        val queue = CircularQueue<Int>(5)
        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()
        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size())
        assertNull(queue.peek())
    }

    @Test
    fun testIterator() {
        val queue = CircularQueue<Int>(5)
        queue.enqueue(10)
        queue.enqueue(20)
        queue.enqueue(30)
        val collected = mutableListOf<Int>()
        for (v in queue) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testToString() {
        val queue = CircularQueue<Int>(5)
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals("[1, 2]", queue.toString())
    }

    @Test
    fun testWrapAround() {
        val queue = CircularQueue<Int>(3)
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())
        queue.enqueue(4)
        queue.enqueue(5)
        assertEquals(listOf(3, 4, 5), queue.toList())
    }
}
