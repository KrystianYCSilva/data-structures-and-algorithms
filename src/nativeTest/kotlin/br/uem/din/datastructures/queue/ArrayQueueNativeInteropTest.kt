package br.uem.din.datastructures.queue

import kotlin.random.Random
import kotlin.test.*

class ArrayQueueNativeInteropTest {

    @Test
    fun testNativeArrayQueueSupportsNullableElements() {
        val queue = arrayQueueOf<Int?>()

        queue.enqueue(null)
        queue.enqueue(10)
        queue.enqueue(null)

        assertNull(queue.dequeue())
        assertEquals(10, queue.dequeue())
        assertNull(queue.dequeue())
        assertNull(queue.dequeue())
    }

    @Test
    fun testNativeArrayQueueRandomizedParityWithReferenceModel() {
        val random = Random(3030)
        val queue = arrayQueueOf<Int>()
        val model = mutableListOf<Int>()

        repeat(1_500) { step ->
            when (random.nextInt(100)) {
                in 0..59 -> {
                    val value = random.nextInt(-10_000, 10_001)
                    queue.enqueue(value)
                    model.add(value)
                }

                in 60..79 -> {
                    val expected = if (model.isEmpty()) null else model.removeAt(0)
                    assertEquals(expected, queue.dequeue(), "dequeue mismatch at step=$step")
                }

                in 80..89 -> {
                    assertEquals(model.firstOrNull(), queue.peek(), "peek mismatch at step=$step")
                }

                else -> {
                    val candidate = random.nextInt(-10_000, 10_001)
                    assertEquals(model.contains(candidate), queue.contains(candidate), "contains mismatch at step=$step")
                }
            }

            assertEquals(model.size, queue.size, "size mismatch at step=$step")
            assertEquals(model.firstOrNull(), queue.peek(), "head mismatch at step=$step")
        }
    }
}
