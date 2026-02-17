package br.uem.din.datastructures.queue

import kotlin.random.Random
import kotlin.test.*

class ArrayQueueTest {

    @Test
    fun testEmptyStateBoundaryConditions() {
        val queue = arrayQueueOf<Int>()

        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size)
        assertNull(queue.peek())
        assertNull(queue.dequeue())
        assertFalse(queue.contains(1))
        assertEquals(emptyList(), queue.toList())
    }

    @Test
    fun testSingleElementLifecycle() {
        val queue = arrayQueueOf<Int>()

        queue.enqueue(42)
        assertFalse(queue.isEmpty())
        assertEquals(1, queue.size)
        assertEquals(42, queue.peek())
        assertEquals(42, queue.dequeue())
        assertNull(queue.dequeue())
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testLargeSequenceKeepsFifoOrderAfterResizes() {
        val queue = arrayQueueOf<Int>()
        val total = 20_000

        for (i in 0 until total) {
            queue.enqueue(i)
        }

        assertEquals(total, queue.size)
        assertEquals(0, queue.peek())

        for (i in 0 until total) {
            assertEquals(i, queue.dequeue())
        }

        assertTrue(queue.isEmpty())
        assertNull(queue.peek())
    }

    @Test
    fun testMutableAliasAndReadOnlySnapshotConsistency() {
        val mutableQueue = arrayQueueOf<Int>()
        val alias: MutableQueue<Int> = mutableQueue

        mutableQueue.enqueue(4)
        alias.enqueue(5)
        mutableQueue.enqueue(6)

        val readOnly: Queue<Int> = mutableQueue
        val snapshot = readOnly.toList()

        assertEquals(listOf(4, 5, 6), snapshot)
        assertEquals(4, alias.dequeue())
        assertEquals(2, readOnly.size)
        assertEquals(5, readOnly.peek())
        assertEquals(listOf(4, 5, 6), snapshot)
    }

    @Test
    fun testIteratorTerminationAndNoSuchElement() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(10)
        queue.enqueue(20)

        val iterator = queue.iterator()
        assertTrue(iterator.hasNext())
        assertEquals(10, iterator.next())
        assertTrue(iterator.hasNext())
        assertEquals(20, iterator.next())
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testRandomizedOperationsAgainstReferenceModel() {
        repeat(8) { seed ->
            val random = Random(seed)
            val queue = arrayQueueOf<Int>()
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
            assertTrue(queue.isEmpty())
        }
    }

    @Test
    fun testClearAndReuse() {
        val queue = arrayQueueOf<Int>()

        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.clear()

        assertTrue(queue.isEmpty())
        assertNull(queue.peek())

        queue.enqueue(10)
        queue.enqueue(11)
        assertEquals(10, queue.dequeue())
        assertEquals(11, queue.dequeue())
        assertNull(queue.dequeue())
    }
}
