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
}
