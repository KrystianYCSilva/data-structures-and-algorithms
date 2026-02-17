package br.uem.din.datastructures.queue

import java.util.ArrayDeque as JavaArrayDeque
import kotlin.random.Random
import kotlin.test.*

class ArrayQueueJvmInteropTest {

    @Test
    fun testJvmArrayQueueMatchesJavaArrayDequeRandomized() {
        val random = Random(20260217)
        val ours = arrayQueueOf<Int>()
        val java = JavaArrayDeque<Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..59 -> {
                    val value = random.nextInt(-50_000, 50_001)
                    ours.enqueue(value)
                    java.addLast(value)
                }

                in 60..79 -> {
                    assertEquals(java.pollFirst(), ours.dequeue(), "dequeue mismatch at step=$step")
                }

                in 80..89 -> {
                    assertEquals(java.peekFirst(), ours.peek(), "peek mismatch at step=$step")
                }

                else -> {
                    val candidate = random.nextInt(-50_000, 50_001)
                    assertEquals(java.contains(candidate), ours.contains(candidate), "contains mismatch at step=$step")
                }
            }

            assertEquals(java.size, ours.size, "size mismatch at step=$step")
            assertEquals(java.peekFirst(), ours.peek(), "head mismatch at step=$step")
        }

        while (java.isNotEmpty()) {
            assertEquals(java.pollFirst(), ours.dequeue())
        }
        assertNull(ours.dequeue())
    }

    @Test
    fun testJvmArrayQueueRejectsNullAtJavaBoundary() {
        val queue = arrayQueueOf<Int?>()

        assertFailsWith<NullPointerException> {
            queue.enqueue(null)
        }
    }

    @Test
    fun testJvmArrayQueueToStringParityWithJava() {
        val ours = arrayQueueOf<Int>()
        val java = JavaArrayDeque<Int>()
        val values = listOf(7, 1, 9, 2, 2, 0, -5, 12)

        values.forEach {
            ours.enqueue(it)
            java.addLast(it)
        }

        assertEquals(java.toString(), ours.toString())
    }
}
