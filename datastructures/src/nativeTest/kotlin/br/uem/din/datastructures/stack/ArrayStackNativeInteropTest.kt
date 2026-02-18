package br.uem.din.datastructures.stack

import kotlin.random.Random
import kotlin.test.*

class ArrayStackNativeInteropTest {

    @Test
    fun testNativeArrayStackSupportsNullableElements() {
        val stack = arrayStackOf<Int?>()

        stack.push(null)
        stack.push(10)
        stack.push(null)

        assertNull(stack.pop())
        assertEquals(10, stack.pop())
        assertNull(stack.pop())
        assertNull(stack.pop())
    }

    @Test
    fun testNativeArrayStackRandomizedParityWithModel() {
        val random = Random(3032)
        val stack = arrayStackOf<Int>()
        val model = mutableListOf<Int>()

        repeat(1_500) { step ->
            when (random.nextInt(100)) {
                in 0..59 -> {
                    val value = random.nextInt(-10_000, 10_001)
                    stack.push(value)
                    model.add(value)
                }

                in 60..79 -> {
                    val expected = if (model.isEmpty()) null else model.removeAt(model.lastIndex)
                    assertEquals(expected, stack.pop(), "pop mismatch at step=$step")
                }

                in 80..89 -> {
                    assertEquals(model.lastOrNull(), stack.peek(), "peek mismatch at step=$step")
                }

                else -> {
                    val candidate = random.nextInt(-10_000, 10_001)
                    assertEquals(model.contains(candidate), stack.contains(candidate), "contains mismatch at step=$step")
                }
            }

            assertEquals(model.size, stack.size, "size mismatch at step=$step")
            assertEquals(model.lastOrNull(), stack.peek(), "head mismatch at step=$step")
            assertEquals(model.asReversed(), stack.toList(), "iteration/order mismatch at step=$step")
        }
    }
}
