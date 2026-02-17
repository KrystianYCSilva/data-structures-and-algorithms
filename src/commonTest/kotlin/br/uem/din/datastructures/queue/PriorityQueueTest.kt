package br.uem.din.datastructures.queue

import kotlin.random.Random
import kotlin.test.*

class PriorityQueueTest {

    @Test
    fun testEmptyStateBoundaryConditions() {
        val queue = priorityQueueOf<Int>()

        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size)
        assertNull(queue.peek())
        assertNull(queue.dequeue())
        assertFalse(queue.contains(1))
    }

    @Test
    fun testSingleElementLifecycle() {
        val queue = priorityQueueOf<Int>()

        queue.enqueue(42)
        assertFalse(queue.isEmpty())
        assertEquals(1, queue.size)
        assertEquals(42, queue.peek())

        assertEquals(42, queue.dequeue())
        assertNull(queue.dequeue())
        assertTrue(queue.isEmpty())
        assertEquals(0, queue.size)
    }

    @Test
    fun testLargeCollectionBoundaryAndSortedDrain() {
        val queue = priorityQueueOf<Int>()
        val total = 4096

        for (value in total downTo 1) {
            queue.enqueue(value)
        }

        assertEquals(total, queue.size)
        assertEquals(1, queue.peek())

        for (expected in 1..total) {
            assertEquals(expected, queue.dequeue())
        }

        assertTrue(queue.isEmpty())
        assertNull(queue.peek())
        assertNull(queue.dequeue())
    }

    @Test
    fun testMutableQueueUpdatesInPlaceAcrossAliases() {
        val queue = priorityQueueOf<Int>()
        val alias: MutableQueue<Int> = queue

        queue.enqueue(3)
        alias.enqueue(1)
        queue.enqueue(2)

        assertEquals(3, queue.size)
        assertEquals(1, alias.dequeue())
        assertEquals(2, queue.size)
        assertEquals(2, queue.peek())
    }

    @Test
    fun testReadOnlyViewSnapshotRemainsStableAfterMutation() {
        val mutableQueue = priorityQueueOf<Int>()
        mutableQueue.enqueue(4)
        mutableQueue.enqueue(2)
        mutableQueue.enqueue(9)

        val readOnlyView: Queue<Int> = mutableQueue
        val snapshot = readOnlyView.toList()

        mutableQueue.enqueue(1)
        assertEquals(1, readOnlyView.peek())
        assertEquals(1, mutableQueue.dequeue())

        assertEquals(listOf(2, 4, 9), snapshot.sorted())
        assertEquals(3, readOnlyView.size)
        assertEquals(2, readOnlyView.peek())
    }

    @Test
    fun testComparatorDescendingOrder() {
        val queue = priorityQueueOf<Int>(compareByDescending { it })

        queue.enqueue(10)
        queue.enqueue(40)
        queue.enqueue(20)
        queue.enqueue(30)

        assertEquals(40, queue.dequeue())
        assertEquals(30, queue.dequeue())
        assertEquals(20, queue.dequeue())
        assertEquals(10, queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun testComparatorSupportsNonComparableElements() {
        val queue = priorityQueueOf<Job>(compareBy { it.priority })

        queue.enqueue(Job("write-tests", 2))
        queue.enqueue(Job("fix-regression", 0))
        queue.enqueue(Job("publish-artifact", 1))

        assertEquals("fix-regression", queue.dequeue()?.name)
        assertEquals("publish-artifact", queue.dequeue()?.name)
        assertEquals("write-tests", queue.dequeue()?.name)
    }

    @Test
    fun testIteratorTerminatesAndThrowsAfterExhaustion() {
        val queue = priorityQueueOf<Int>()
        queue.enqueue(3)
        queue.enqueue(1)
        queue.enqueue(2)

        val iterator = queue.iterator()
        var count = 0
        while (iterator.hasNext()) {
            iterator.next()
            count++
        }

        assertEquals(3, count)
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testRandomizedOperationsAgainstReferenceModel() {
        repeat(10) { seed ->
            val random = Random(seed)
            val queue = priorityQueueOf<Int>()
            val reference = mutableListOf<Int>()

            repeat(500) {
                when (random.nextInt(100)) {
                    in 0..54 -> {
                        val value = random.nextInt(-1_000, 1_001)
                        queue.enqueue(value)
                        reference.add(value)
                    }

                    in 55..79 -> {
                        val expected = reference.minOrNull()
                        val actual = queue.dequeue()
                        assertEquals(expected, actual)
                        if (expected != null) {
                            reference.remove(expected)
                        }
                    }

                    in 80..89 -> {
                        assertEquals(reference.minOrNull(), queue.peek())
                    }

                    else -> {
                        val candidate = random.nextInt(-1_000, 1_001)
                        assertEquals(reference.contains(candidate), queue.contains(candidate))
                    }
                }

                assertEquals(reference.size, queue.size)
                assertEquals(reference.isEmpty(), queue.isEmpty())
                assertEquals(reference.minOrNull(), queue.peek())
            }

            while (reference.isNotEmpty()) {
                val expected = reference.minOrNull()
                assertEquals(expected, queue.dequeue())
                reference.remove(expected)
            }

            assertTrue(queue.isEmpty())
            assertNull(queue.dequeue())
        }
    }

    @Test
    fun testComparatorCallCountStaysWithinNLogNEnvelope() {
        val n = 2048
        val comparator = CountingComparator()
        val queue = priorityQueueOf<Int>(comparator)

        for (value in n downTo 1) {
            queue.enqueue(value)
        }

        for (expected in 1..n) {
            assertEquals(expected, queue.dequeue())
        }

        val logN = ceilLog2(n + 1).toLong()
        val upperBound = (14L * n.toLong() * logN) + (64L * n.toLong())
        assertTrue(
            comparator.comparisons <= upperBound,
            "Comparator calls should stay near O(n log n). comparisons=${comparator.comparisons}, upperBound=$upperBound"
        )
    }

    @Test
    fun testNonComparableWithoutComparatorFails() {
        val queue = priorityQueueOf<Plain>()
        queue.enqueue(Plain(1))
        assertFails { queue.enqueue(Plain(2)) }
    }

    private data class Job(val name: String, val priority: Int)

    private class Plain(@Suppress("unused") val value: Int)

    private class CountingComparator : Comparator<Int> {
        var comparisons: Long = 0

        override fun compare(a: Int, b: Int): Int {
            comparisons++
            return a.compareTo(b)
        }
    }

    private fun ceilLog2(n: Int): Int {
        require(n > 0)
        var power = 0
        var value = 1
        while (value < n) {
            value = value shl 1
            power++
        }
        return power
    }
}
