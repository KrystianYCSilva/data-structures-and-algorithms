package br.uem.din.datastructures.queue

import kotlin.random.Random
import kotlin.test.*

class DequeTest {

    @Test
    fun testEmptyStateBoundaryConditions() {
        val deque = Deque<Int>()

        assertTrue(deque.isEmpty())
        assertEquals(0, deque.size)
        assertNull(deque.peek())
        assertNull(deque.peekBack())
        assertNull(deque.dequeue())
        assertNull(deque.dequeueBack())
        assertFalse(deque.contains(1))
    }

    @Test
    fun testSingleElementLifecycleBothEnds() {
        val deque = Deque<Int>()

        deque.enqueueFront(42)
        assertEquals(42, deque.peek())
        assertEquals(42, deque.peekBack())
        assertEquals(42, deque.dequeueBack())
        assertTrue(deque.isEmpty())

        deque.enqueue(7)
        assertEquals(7, deque.dequeue())
        assertTrue(deque.isEmpty())
    }

    @Test
    fun testDoubleEndedOperationsPreserveOrder() {
        val deque = Deque<Int>()

        deque.enqueue(2)
        deque.enqueueFront(1)
        deque.enqueue(3)
        deque.enqueueFront(0)

        assertEquals(listOf(0, 1, 2, 3), deque.toList())
        assertEquals(0, deque.dequeue())
        assertEquals(3, deque.dequeueBack())
        assertEquals(listOf(1, 2), deque.toList())
    }

    @Test
    fun testLargeLoadWithMixedFrontBackOperations() {
        val deque = Deque<Int>()

        for (i in 0 until 5_000) {
            deque.enqueue(i)
        }

        for (i in 0 until 2_500) {
            assertEquals(i, deque.dequeue())
        }

        for (i in 1..2_500) {
            deque.enqueueFront(-i)
        }

        assertEquals(5_000, deque.size)
        assertEquals(-2_500, deque.peek())
        assertEquals(4_999, deque.peekBack())
    }

    @Test
    fun testMutableQueueViewBehavior() {
        val deque = Deque<Int>()
        val queueView: MutableQueue<Int> = deque

        queueView.enqueue(1)
        queueView.enqueue(2)
        deque.enqueueFront(0)

        assertEquals(0, queueView.dequeue())
        assertEquals(1, queueView.dequeue())
        assertEquals(2, queueView.dequeue())
        assertNull(queueView.dequeue())
    }

    @Test
    fun testReadOnlySnapshotAndAliasConsistency() {
        val deque = Deque<Int>()
        val alias: MutableQueue<Int> = deque

        deque.enqueue(10)
        alias.enqueue(20)
        deque.enqueueFront(5)

        val readOnly: Queue<Int> = deque
        val snapshot = readOnly.toList()

        assertEquals(listOf(5, 10, 20), snapshot)
        assertEquals(5, alias.dequeue())
        assertEquals(2, readOnly.size)
        assertEquals(10, readOnly.peek())
        assertEquals(listOf(5, 10, 20), snapshot)
    }

    @Test
    fun testIteratorTerminationAndNoSuchElement() {
        val deque = Deque<Int>()
        deque.enqueue(1)
        deque.enqueue(2)

        val iterator = deque.iterator()
        assertEquals(1, iterator.next())
        assertEquals(2, iterator.next())
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testRandomizedOperationsAgainstReferenceModel() {
        repeat(8) { seed ->
            val random = Random(seed + 200)
            val deque = Deque<Int>()
            val model = mutableListOf<Int>()

            repeat(1_000) {
                when (random.nextInt(100)) {
                    in 0..34 -> {
                        val value = random.nextInt(-500, 501)
                        deque.enqueue(value)
                        model.add(value)
                    }

                    in 35..59 -> {
                        val value = random.nextInt(-500, 501)
                        deque.enqueueFront(value)
                        model.add(0, value)
                    }

                    in 60..74 -> {
                        val expected = if (model.isEmpty()) null else model.removeAt(0)
                        assertEquals(expected, deque.dequeue())
                    }

                    in 75..89 -> {
                        val expected = if (model.isEmpty()) null else model.removeAt(model.lastIndex)
                        assertEquals(expected, deque.dequeueBack())
                    }

                    in 90..94 -> {
                        assertEquals(model.firstOrNull(), deque.peek())
                    }

                    in 95..97 -> {
                        assertEquals(model.lastOrNull(), deque.peekBack())
                    }

                    else -> {
                        val candidate = random.nextInt(-500, 501)
                        assertEquals(model.contains(candidate), deque.contains(candidate))
                    }
                }

                assertEquals(model.size, deque.size)
                assertEquals(model.isEmpty(), deque.isEmpty())
                assertEquals(model.firstOrNull(), deque.peek())
                assertEquals(model.lastOrNull(), deque.peekBack())
            }
        }
    }

    @Test
    fun testClearAndReuse() {
        val deque = Deque<Int>()

        deque.enqueue(1)
        deque.enqueueFront(0)
        deque.enqueue(2)
        deque.clear()

        assertTrue(deque.isEmpty())
        assertNull(deque.peek())
        assertNull(deque.peekBack())

        deque.enqueue(8)
        deque.enqueueFront(7)
        assertEquals(7, deque.dequeue())
        assertEquals(8, deque.dequeueBack())
        assertTrue(deque.isEmpty())
    }
}
