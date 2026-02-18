package br.uem.din.datastructures.queue

import kotlin.random.Random
import kotlin.test.*

class CircularQueueTest {

    @Test
    fun testEmptyAndSingleElementBoundaries() {
        val queue = CircularQueue<Int>(3)

        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size)
        assertNull(queue.peek())
        assertNull(queue.dequeue())

        queue.enqueue(7)
        assertFalse(queue.isEmpty())
        assertEquals(1, queue.size)
        assertEquals(7, queue.peek())
        assertEquals(7, queue.dequeue())
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testZeroCapacityBehavior() {
        val queue = CircularQueue<Int>(0)

        assertTrue(queue.isEmpty())
        assertTrue(queue.isFull)
        assertFalse(queue.offer(1))
        assertFailsWith<IllegalStateException> { queue.enqueue(1) }
        assertNull(queue.dequeue())
    }

    @Test
    fun testFullQueueOfferAndEnqueueBoundaries() {
        val queue = CircularQueue<Int>(2)

        assertTrue(queue.offer(1))
        assertTrue(queue.offer(2))
        assertTrue(queue.isFull)
        assertFalse(queue.offer(3))
        assertFailsWith<IllegalStateException> { queue.enqueue(3) }

        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun testWrapAroundMaintainsFifoOrder() {
        val queue = CircularQueue<Int>(3)

        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())

        queue.enqueue(4)
        queue.enqueue(5)

        assertEquals(listOf(3, 4, 5), queue.toList())
        assertEquals(3, queue.dequeue())
        assertEquals(4, queue.dequeue())
        assertEquals(5, queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun testLargeCapacityStressWithoutOverflow() {
        val queue = CircularQueue<Int>(5_000)

        for (i in 0 until 5_000) {
            queue.enqueue(i)
        }

        assertTrue(queue.isFull)
        assertEquals(5_000, queue.size)

        for (i in 0 until 5_000) {
            assertEquals(i, queue.dequeue())
        }

        assertTrue(queue.isEmpty())
    }

    @Test
    fun testReadOnlyViewAndAliasConsistency() {
        val circularQueue = CircularQueue<Int>(5)
        val alias: MutableQueue<Int> = circularQueue

        circularQueue.enqueue(2)
        alias.enqueue(3)
        circularQueue.enqueue(4)

        val readOnly: Queue<Int> = circularQueue
        val snapshot = readOnly.toList()

        assertEquals(listOf(2, 3, 4), snapshot)
        assertEquals(2, alias.dequeue())
        assertEquals(2, readOnly.size)
        assertEquals(3, readOnly.peek())
        assertEquals(listOf(2, 3, 4), snapshot)
    }

    @Test
    fun testIteratorTerminationAndNoSuchElement() {
        val queue = CircularQueue<Int>(4)
        queue.enqueue(10)
        queue.enqueue(20)

        val iterator = queue.iterator()
        assertEquals(10, iterator.next())
        assertEquals(20, iterator.next())
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testRandomizedOperationsAgainstCapacityAwareModel() {
        repeat(8) { seed ->
            val random = Random(seed)
            val capacity = 16
            val queue = CircularQueue<Int>(capacity)
            val model = mutableListOf<Int>()

            repeat(800) {
                when (random.nextInt(100)) {
                    in 0..49 -> {
                        val value = random.nextInt(-500, 501)
                        val offered = queue.offer(value)
                        val expected = if (model.size < capacity) {
                            model.add(value)
                            true
                        } else {
                            false
                        }
                        assertEquals(expected, offered)
                    }

                    in 50..74 -> {
                        val expected = if (model.isEmpty()) null else model.removeAt(0)
                        assertEquals(expected, queue.dequeue())
                    }

                    in 75..84 -> {
                        assertEquals(model.firstOrNull(), queue.peek())
                    }

                    in 85..94 -> {
                        val candidate = random.nextInt(-500, 501)
                        assertEquals(model.contains(candidate), queue.contains(candidate))
                    }

                    else -> {
                        queue.clear()
                        model.clear()
                    }
                }

                assertEquals(model.size, queue.size)
                assertEquals(model.isEmpty(), queue.isEmpty())
                assertEquals(model.size == capacity, queue.isFull)
                assertEquals(model.firstOrNull(), queue.peek())
            }
        }
    }
}
