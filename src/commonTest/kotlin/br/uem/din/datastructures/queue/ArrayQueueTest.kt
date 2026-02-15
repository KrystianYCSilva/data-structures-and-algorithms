package br.uem.din.datastructures.queue

import kotlin.test.*

class ArrayQueueTest {

    @Test
    fun testEnqueueDequeue() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals(2, queue.size())
        assertEquals(1, queue.peek())
        assertEquals(1, queue.dequeue())
        assertEquals(1, queue.size())
        assertEquals(2, queue.dequeue())
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testDequeueEmpty() {
        val queue = ArrayQueue<Int>()
        assertNull(queue.dequeue())
        assertNull(queue.peek())
    }

    @Test
    fun testContains() {
        val queue = ArrayQueue<String>()
        queue.enqueue("a")
        queue.enqueue("b")
        assertTrue(queue.contains("a"))
        assertFalse(queue.contains("z"))
    }

    @Test
    fun testClear() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()
        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size())
    }

    @Test
    fun testIterator() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(10)
        queue.enqueue(20)
        queue.enqueue(30)
        val collected = mutableListOf<Int>()
        for (v in queue) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testToList() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(listOf(1, 2, 3), queue.toList())
    }

    @Test
    fun testToString() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals("[1, 2]", queue.toString())
    }
}
