package br.uem.din.datastructures.queue

import java.util.ArrayDeque as JavaArrayDeque
import kotlin.random.Random
import kotlin.test.*

class DequeJvmInteropTest {

    @Test
    fun testDequeMatchesJavaArrayDequeForDoubleEndedOperations() {
        val random = Random(2027)
        val ours = Deque<Int>()
        val java = JavaArrayDeque<Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..34 -> {
                    val value = random.nextInt(-1_000, 1_001)
                    ours.enqueue(value)
                    java.addLast(value)
                }

                in 35..59 -> {
                    val value = random.nextInt(-1_000, 1_001)
                    ours.enqueueFront(value)
                    java.addFirst(value)
                }

                in 60..74 -> {
                    assertEquals(java.pollFirst(), ours.dequeue(), "pollFirst mismatch at step=$step")
                }

                in 75..89 -> {
                    assertEquals(java.pollLast(), ours.dequeueBack(), "pollLast mismatch at step=$step")
                }

                in 90..94 -> {
                    assertEquals(java.peekFirst(), ours.peek(), "peekFirst mismatch at step=$step")
                }

                in 95..97 -> {
                    assertEquals(java.peekLast(), ours.peekBack(), "peekLast mismatch at step=$step")
                }

                else -> {
                    val candidate = random.nextInt(-1_000, 1_001)
                    assertEquals(java.contains(candidate), ours.contains(candidate), "contains mismatch at step=$step")
                }
            }

            assertEquals(java.size, ours.size, "size mismatch at step=$step")
            assertEquals(java.peekFirst(), ours.peek(), "head mismatch at step=$step")
            assertEquals(java.peekLast(), ours.peekBack(), "tail mismatch at step=$step")
        }
    }
}
