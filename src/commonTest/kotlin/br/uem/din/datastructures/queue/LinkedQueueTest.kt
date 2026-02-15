package br.uem.din.datastructures.queue

import kotlin.test.*

class LinkedQueueTest {

    @Test
    fun testEnqueueDequeue() {
        val queue = LinkedQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(3, queue.size)
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())
        assertEquals(3, queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun testContains() {
        val queue = LinkedQueue<String>()
        queue.enqueue("x")
        queue.enqueue("y")
        assertTrue(queue.contains("x"))
        assertFalse(queue.contains("z"))
    }

    @Test
    fun testClear() {
        val queue = LinkedQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testIterator() {
        val queue = LinkedQueue<Int>()
        queue.enqueue(10)
        queue.enqueue(20)
        val collected = mutableListOf<Int>()
        for (v in queue) collected.add(v)
        assertEquals(listOf(10, 20), collected)
    }

    @Test
    fun testToList() {
        val queue = LinkedQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(listOf(1, 2, 3), queue.toList())
    }
}
