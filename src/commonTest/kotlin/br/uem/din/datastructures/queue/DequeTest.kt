package br.uem.din.datastructures.queue

import kotlin.test.*

class DequeTest {

    @Test
    fun testEnqueueDequeue() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)
        deque.enqueue(3)
        assertEquals(1, deque.dequeue())
        assertEquals(2, deque.dequeue())
        assertEquals(3, deque.dequeue())
        assertNull(deque.dequeue())
    }

    @Test
    fun testEnqueueFront() {
        val deque = Deque<Int>()
        deque.enqueue(2)
        deque.enqueueFront(1)
        deque.enqueue(3)
        assertEquals(listOf(1, 2, 3), deque.toList())
    }

    @Test
    fun testDequeueBack() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)
        deque.enqueue(3)
        assertEquals(3, deque.dequeueBack())
        assertEquals(2, deque.dequeueBack())
        assertEquals(1, deque.dequeueBack())
        assertNull(deque.dequeueBack())
    }

    @Test
    fun testPeekAndPeekBack() {
        val deque = Deque<String>()
        assertNull(deque.peek())
        assertNull(deque.peekBack())
        deque.enqueue("a")
        deque.enqueue("b")
        assertEquals("a", deque.peek())
        assertEquals("b", deque.peekBack())
    }

    @Test
    fun testContains() {
        val deque = Deque<Int>()
        deque.enqueue(10)
        deque.enqueue(20)
        assertTrue(deque.contains(10))
        assertFalse(deque.contains(99))
    }

    @Test
    fun testClear() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)
        deque.clear()
        assertTrue(deque.isEmpty())
        assertEquals(0, deque.size)
    }

    @Test
    fun testIterator() {
        val deque = Deque<Int>()
        deque.enqueue(10)
        deque.enqueue(20)
        deque.enqueue(30)
        val collected = mutableListOf<Int>()
        for (v in deque) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testToString() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)
        assertEquals("[1, 2]", deque.toString())
    }

    @Test
    fun testAsQueue() {
        val deque = Deque<Int>()
        val queue: MutableQueue<Int> = deque
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals(1, queue.dequeue())
    }
}
