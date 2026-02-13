package br.uem.din.algoritmos.datastructures.queue

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ArrayQueueTest {

    @Test
    fun `enqueue should add element to queue`() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(1)
        assertEquals(1, queue.count)
        assertEquals(1, queue.peek())
    }

    @Test
    fun `dequeue should remove element from queue`() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        val dequeued = queue.dequeue()
        assertEquals(1, dequeued)
        assertEquals(1, queue.count)
        assertEquals(2, queue.peek())
    }

    @Test
    fun `dequeue on empty queue should return null`() {
        val queue = ArrayQueue<Int>()
        assertNull(queue.dequeue())
    }

    @Test
    fun `peek on empty queue should return null`() {
        val queue = ArrayQueue<Int>()
        assertNull(queue.peek())
    }

    @Test
    fun `peek should return element without removing it`() {
        val queue = ArrayQueue<Int>()
        queue.enqueue(1)
        assertEquals(1, queue.peek())
        assertEquals(1, queue.count)
    }
}
