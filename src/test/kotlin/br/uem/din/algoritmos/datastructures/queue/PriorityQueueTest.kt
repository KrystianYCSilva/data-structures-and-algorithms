package br.uem.din.algoritmos.datastructures.queue

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PriorityQueueTest {

    @Test
    fun `dequeue should respect priority`() {
        val queue = PriorityQueue(compareBy<Int> { it })
        queue.enqueue(5)
        queue.enqueue(1)
        queue.enqueue(3)

        assertEquals(1, queue.dequeue())
        assertEquals(3, queue.dequeue())
        assertEquals(5, queue.dequeue())
    }
    
    @Test
    fun `dequeue on empty queue should return null`() {
        val queue = PriorityQueue(compareBy<Int> { it })
        assertNull(queue.dequeue())
    }

    @Test
    fun `peek should return highest priority element without removing`() {
        val queue = PriorityQueue(compareBy<Int> { it })
        queue.enqueue(5)
        queue.enqueue(1)

        assertEquals(1, queue.peek())
        assertEquals(2, queue.count)
    }
}
