package br.com.leandroluce.algoritmos.datastructures.queue

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DequeTest {

    @Test
    fun `enqueue and dequeue should work like a normal queue`() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)
        assertEquals(1, deque.dequeue())
        assertEquals(2, deque.dequeue())
    }

    @Test
    fun `enqueueFront should add to the front`() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueueFront(2)
        assertEquals(2, deque.peek())
        assertEquals(2, deque.count)
    }

    @Test
    fun `dequeueBack should remove from the back`() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)
        assertEquals(2, deque.dequeueBack())
        assertEquals(1, deque.count)
    }

    @Test
    fun `peekBack should return last element without removing`() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)
        assertEquals(2, deque.peekBack())
        assertEquals(2, deque.count)
    }

    @Test
    fun `dequeue on empty deque should return null`() {
        val deque = Deque<Int>()
        assertNull(deque.dequeue())
    }

    @Test
    fun `dequeueBack on empty deque should return null`() {
        val deque = Deque<Int>()
        assertNull(deque.dequeueBack())
    }
}
