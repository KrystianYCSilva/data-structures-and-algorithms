package br.com.leandroluce.algoritmos.datastructures.queue

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CircularQueueTest {

    @Test
    fun `enqueue and dequeue`() {
        val queue = CircularQueue<Int>(5)
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun `queue should wrap around`() {
        val queue = CircularQueue<Int>(3)
        assertTrue(queue.enqueue(1))
        assertTrue(queue.enqueue(2))
        assertEquals(1, queue.dequeue())
        assertTrue(queue.enqueue(3))
        assertEquals(2, queue.dequeue())
        assertEquals(3, queue.dequeue())
    }

    @Test
    fun `enqueue into a full queue`() {
        val queue = CircularQueue<Int>(3)
        assertTrue(queue.enqueue(1))
        assertTrue(queue.enqueue(2))
        assertFalse(queue.enqueue(3))
    }

    @Test
    fun `dequeue from an empty queue`() {
        val queue = CircularQueue<Int>(3)
        assertNull(queue.dequeue())
    }
}
