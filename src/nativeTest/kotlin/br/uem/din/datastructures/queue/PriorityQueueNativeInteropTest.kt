package br.uem.din.datastructures.queue

import kotlin.test.*

class PriorityQueueNativeInteropTest {

    @Test
    fun testNativeComparatorSupportsNonComparableType() {
        val queue = priorityQueueOf<Job>(compareBy<Job> { it.priority }.thenBy { it.id })

        queue.enqueue(Job("c", 3))
        queue.enqueue(Job("b", 1))
        queue.enqueue(Job("a", 1))
        queue.enqueue(Job("d", 2))

        assertEquals(Job("a", 1), queue.dequeue())
        assertEquals(Job("b", 1), queue.dequeue())
        assertEquals(Job("d", 2), queue.dequeue())
        assertEquals(Job("c", 3), queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun testNativeComparatorHandlesNullableElements() {
        val comparator = Comparator<Int?> { a, b ->
            when {
                a == b -> 0
                a == null -> -1
                b == null -> 1
                else -> a.compareTo(b)
            }
        }
        val queue = priorityQueueOf(comparator)

        queue.enqueue(3)
        queue.enqueue(null)
        queue.enqueue(1)

        assertNull(queue.dequeue())
        assertEquals(1, queue.dequeue())
        assertEquals(3, queue.dequeue())
        assertNull(queue.dequeue())
    }

    private data class Job(val id: String, val priority: Int)
}

