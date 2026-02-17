package br.uem.din.datastructures.queue

import kotlin.test.*

class ArrayQueueTest {

    @Test
    fun testArrayQueueEnqueueDequeue() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals(2, queue.size)
        assertEquals(1, queue.peek())
        assertEquals(1, queue.dequeue())
        assertEquals(1, queue.size)
        assertEquals(2, queue.dequeue())
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testArrayQueueDequeueEmpty() {
        val queue = arrayQueueOf<Int>()
        assertNull(queue.dequeue())
        assertNull(queue.peek())
    }

    @Test
    fun testArrayQueueContains() {
        val queue = arrayQueueOf<String>()
        queue.enqueue("a")
        queue.enqueue("b")
        assertTrue(queue.contains("a"))
        assertFalse(queue.contains("z"))
    }

    @Test
    fun testArrayQueueClear() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()
        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size)
    }

    @Test
    fun testArrayQueueIterator() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(10)
        queue.enqueue(20)
        queue.enqueue(30)
        val collected = mutableListOf<Int>()
        for (v in queue) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testArrayQueueToList() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(listOf(1, 2, 3), queue.toList())
    }

    @Test
    fun testArrayQueueToString() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals("[1, 2]", queue.toString())
    }

    @Test
    fun testArrayQueueIsEmpty() {
        val queue = arrayQueueOf<Int>()
        assertTrue(queue.isEmpty())
        queue.enqueue(1)
        assertFalse(queue.isEmpty())
        queue.dequeue()
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testArrayQueueSize() {
        val queue = arrayQueueOf<Int>()
        assertEquals(0, queue.size)
        queue.enqueue(1)
        assertEquals(1, queue.size)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(3, queue.size)
        queue.dequeue()
        assertEquals(2, queue.size)
    }

    @Test
    fun testArrayQueuePeekDoesNotRemove() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(42)
        assertEquals(42, queue.peek())
        assertEquals(42, queue.peek())
        assertEquals(1, queue.size)
    }

    @Test
    fun testArrayQueueFifoOrder() {
        val queue = arrayQueueOf<String>()
        queue.enqueue("first")
        queue.enqueue("second")
        queue.enqueue("third")
        assertEquals("first", queue.dequeue())
        assertEquals("second", queue.dequeue())
        assertEquals("third", queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun testArrayQueueContainsAfterDequeue() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.dequeue()
        assertFalse(queue.contains(1))
        assertTrue(queue.contains(2))
        assertTrue(queue.contains(3))
    }

    @Test
    fun testArrayQueueClearThenReuse() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()
        assertNull(queue.peek())
        assertNull(queue.dequeue())
        queue.enqueue(10)
        assertEquals(10, queue.peek())
        assertEquals(1, queue.size)
    }

    @Test
    fun testArrayQueueManyElements() {
        val queue = arrayQueueOf<Int>()
        for (i in 0 until 1000) {
            queue.enqueue(i)
        }
        assertEquals(1000, queue.size)
        for (i in 0 until 1000) {
            assertEquals(i, queue.dequeue())
        }
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testArrayQueueIteratorAfterDequeue() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.dequeue()
        val collected = mutableListOf<Int>()
        for (v in queue) collected.add(v)
        assertEquals(listOf(2, 3), collected)
    }
}
