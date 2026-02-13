package br.com.leandroluce.algoritmos.datastructures.queue

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PriorityQueueTest {

    @Test
    fun `dequeue should respect priority`() {
        val comparator = compareBy<Int> { it }
        val queue = PriorityQueue(comparator)
        queue.enqueue(5)
        queue.enqueue(1)
        queue.enqueue(3)

        assertEquals(1, queue.dequeue())
        assertEquals(3, queue.dequeue())
        assertEquals(5, queue.dequeue())
    }
    
    @Test
    fun `dequeue on empty queue should return null`() {
        val comparator = compareBy<Int> { it }
        val queue = PriorityQueue(comparator)
        assertNull(queue.dequeue())
    }

    @Test
    fun `peek should return highest priority element without removing`() {
        val comparator = compareBy<Int> { it }
        val queue = PriorityQueue(comparator)
        queue.enqueue(5)
        queue.enqueue(1)

        assertEquals(1, queue.peek())
        assertEquals(2, queue.count)
    }
}
