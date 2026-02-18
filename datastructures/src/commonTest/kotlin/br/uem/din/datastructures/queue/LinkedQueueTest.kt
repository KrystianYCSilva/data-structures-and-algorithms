package br.uem.din.datastructures.queue

import kotlin.random.Random
import kotlin.test.*

class LinkedQueueTest {

    @Test
    fun testEmptyStateBoundaryConditions() {
        val queue = LinkedQueue<Int>()

        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size)
        assertNull(queue.peek())
        assertNull(queue.dequeue())
        assertFalse(queue.contains(1))
        assertEquals(emptyList(), queue.toList())
    }

    @Test
    fun testSingleElementLifecycle() {
        val queue = LinkedQueue<Int>()

        queue.enqueue(100)
        assertEquals(1, queue.size)
        assertEquals(100, queue.peek())
        assertEquals(100, queue.dequeue())
        assertNull(queue.dequeue())
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testLargeFifoSequence() {
        val queue = LinkedQueue<Int>()
        val total = 10_000

        for (i in 0 until total) {
            queue.enqueue(i)
        }

        assertEquals(total, queue.size)

        for (i in 0 until total) {
            assertEquals(i, queue.dequeue())
        }

        assertTrue(queue.isEmpty())
    }

    @Test
    fun testReadOnlyViewAndAliasBehavior() {
        val linkedQueue = LinkedQueue<Int>()
        val alias: MutableQueue<Int> = linkedQueue

        linkedQueue.enqueue(1)
        alias.enqueue(2)
        linkedQueue.enqueue(3)

        val readOnly: Queue<Int> = linkedQueue
        val snapshot = readOnly.toList()

        assertEquals(listOf(1, 2, 3), snapshot)
        assertEquals(1, alias.dequeue())
        assertEquals(2, readOnly.size)
        assertEquals(2, readOnly.peek())
        assertEquals(listOf(1, 2, 3), snapshot)
    }

    @Test
    fun testIteratorTerminationAndNoSuchElement() {
        val queue = LinkedQueue<Int>()
        queue.enqueue(10)
        queue.enqueue(20)

        val iterator = queue.iterator()
        assertEquals(10, iterator.next())
        assertEquals(20, iterator.next())
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testRandomizedOperationsAgainstReferenceModel() {
        repeat(8) { seed ->
            val random = Random(seed + 100)
            val queue = LinkedQueue<Int>()
            val model = mutableListOf<Int>()

            repeat(800) {
                when (random.nextInt(100)) {
                    in 0..54 -> {
                        val value = random.nextInt(-1_000, 1_001)
                        queue.enqueue(value)
                        model.add(value)
                    }

                    in 55..79 -> {
                        val expected = if (model.isEmpty()) null else model.removeAt(0)
                        assertEquals(expected, queue.dequeue())
                    }

                    in 80..89 -> {
                        assertEquals(model.firstOrNull(), queue.peek())
                    }

                    else -> {
                        val candidate = random.nextInt(-1_000, 1_001)
                        assertEquals(model.contains(candidate), queue.contains(candidate))
                    }
                }

                assertEquals(model.size, queue.size)
                assertEquals(model.isEmpty(), queue.isEmpty())
                assertEquals(model.firstOrNull(), queue.peek())
            }

            while (model.isNotEmpty()) {
                assertEquals(model.removeAt(0), queue.dequeue())
            }
            assertNull(queue.dequeue())
        }
    }

    @Test
    fun testClearAndReuse() {
        val queue = LinkedQueue<Int>()

        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()

        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size)
        assertNull(queue.peek())

        queue.enqueue(9)
        queue.enqueue(10)
        assertEquals(9, queue.dequeue())
        assertEquals(10, queue.dequeue())
        assertNull(queue.dequeue())
    }
}
