package br.uem.din.datastructures.queue

import java.util.PriorityQueue as JavaPriorityQueue
import kotlin.math.absoluteValue
import kotlin.random.Random
import kotlin.test.*

class PriorityQueueJvmInteropTest {

    @Test
    fun testJvmWrapperMatchesJavaPriorityQueueNaturalOrder() {
        val random = Random(20260217)
        val wrappedQueue = priorityQueueOf<Int>()
        val javaQueue = JavaPriorityQueue<Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..64 -> {
                    val value = random.nextInt(-50_000, 50_001)
                    wrappedQueue.enqueue(value)
                    javaQueue.add(value)
                }

                in 65..84 -> {
                    assertEquals(javaQueue.peek(), wrappedQueue.peek(), "peek mismatch at step=$step")
                }

                else -> {
                    assertEquals(javaQueue.poll(), wrappedQueue.dequeue(), "poll mismatch at step=$step")
                }
            }

            assertEquals(javaQueue.size, wrappedQueue.size, "size mismatch at step=$step")
            assertEquals(javaQueue.peek(), wrappedQueue.peek(), "head mismatch at step=$step")
        }

        while (javaQueue.isNotEmpty()) {
            assertEquals(javaQueue.poll(), wrappedQueue.dequeue())
        }

        assertNull(wrappedQueue.dequeue())
    }

    @Test
    fun testJvmWrapperMatchesJavaPriorityQueueWithComparator() {
        val comparator = Comparator<Int> { a, b ->
            val absCompare = a.absoluteValue.compareTo(b.absoluteValue)
            if (absCompare != 0) absCompare else a.compareTo(b)
        }
        val random = Random(77)
        val wrappedQueue = priorityQueueOf(comparator)
        val javaQueue = JavaPriorityQueue(comparator)

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..64 -> {
                    val value = random.nextInt(-10_000, 10_001)
                    wrappedQueue.enqueue(value)
                    javaQueue.add(value)
                }

                in 65..84 -> {
                    assertEquals(javaQueue.peek(), wrappedQueue.peek(), "peek mismatch at step=$step")
                }

                else -> {
                    assertEquals(javaQueue.poll(), wrappedQueue.dequeue(), "poll mismatch at step=$step")
                }
            }

            assertEquals(javaQueue.size, wrappedQueue.size, "size mismatch at step=$step")
        }

        while (javaQueue.isNotEmpty()) {
            assertEquals(javaQueue.poll(), wrappedQueue.dequeue())
        }

        assertNull(wrappedQueue.dequeue())
    }

    @Test
    fun testJvmRejectsNullElementsAtJavaBoundary() {
        val nullSafeComparator = Comparator<Int?> { a, b ->
            when {
                a == b -> 0
                a == null -> -1
                b == null -> 1
                else -> a.compareTo(b)
            }
        }
        val queue = priorityQueueOf(nullSafeComparator)

        assertFailsWith<NullPointerException> {
            queue.enqueue(null)
        }
    }

    @Test
    fun testJvmThrowsClassCastForNonComparableWithoutComparator() {
        val queue = priorityQueueOf<Plain>()
        queue.enqueue(Plain(1))

        assertFailsWith<ClassCastException> {
            queue.enqueue(Plain(2))
        }
    }

    @Test
    fun testJvmWrapperToStringMatchesJavaPriorityQueue() {
        val values = listOf(7, 1, 9, 2, 2, 0, -5, 12)
        val wrappedQueue = priorityQueueOf<Int>()
        val javaQueue = JavaPriorityQueue<Int>()

        values.forEach {
            wrappedQueue.enqueue(it)
            javaQueue.add(it)
        }

        assertEquals(javaQueue.toString(), wrappedQueue.toString())
    }

    private class Plain(@Suppress("unused") val value: Int)
}

