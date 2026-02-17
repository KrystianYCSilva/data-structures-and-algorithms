package br.uem.din.datastructures.stack

import java.util.ArrayDeque as JavaArrayDeque
import kotlin.random.Random
import kotlin.test.*

class ArrayStackJvmInteropTest {

    @Test
    fun testJvmArrayStackMatchesJavaArrayDequeRandomized() {
        val random = Random(2028)
        val ours = arrayStackOf<Int>()
        val java = JavaArrayDeque<Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..59 -> {
                    val value = random.nextInt(-20_000, 20_001)
                    ours.push(value)
                    java.push(value)
                }

                in 60..79 -> {
                    val expected = if (java.isEmpty()) null else java.pop()
                    assertEquals(expected, ours.pop(), "pop mismatch at step=$step")
                }

                in 80..89 -> {
                    assertEquals(java.peek(), ours.peek(), "peek mismatch at step=$step")
                }

                else -> {
                    val candidate = random.nextInt(-20_000, 20_001)
                    assertEquals(java.contains(candidate), ours.contains(candidate), "contains mismatch at step=$step")
                }
            }

            assertEquals(java.size, ours.size, "size mismatch at step=$step")
            assertEquals(java.peek(), ours.peek(), "head mismatch at step=$step")
            assertEquals(java.toList().toString(), ours.toString(), "toString mismatch at step=$step")
        }
    }

    @Test
    fun testJvmArrayStackRejectsNullAtJavaBoundary() {
        val stack = arrayStackOf<Int?>()

        assertFailsWith<NullPointerException> {
            stack.push(null)
        }
    }
}
