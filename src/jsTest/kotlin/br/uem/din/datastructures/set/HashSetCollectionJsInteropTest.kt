package br.uem.din.datastructures.set

import kotlin.random.Random
import kotlin.test.*

class HashSetCollectionJsInteropTest {

    @Test
    fun testJsTypealiasSupportsNullableElements() {
        val set = HashSetCollection<String?>()

        set.add(null)
        set.add("x")

        assertTrue(set.contains(null))
        assertTrue(set.contains("x"))
        assertEquals(2, set.size)
    }

    @Test
    fun testJsTypealiasRandomizedParityWithMutableSet() {
        val random = Random(20260220)
        val set = HashSetCollection<Int>()
        val model = mutableSetOf<Int>()

        repeat(1_600) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val value = random.nextInt(-350, 351)
                    assertEquals(model.add(value), set.add(value), "add mismatch at step=$step")
                }

                in 55..79 -> {
                    val value = random.nextInt(-350, 351)
                    assertEquals(model.remove(value), set.remove(value), "remove mismatch at step=$step")
                }

                in 80..94 -> {
                    val value = random.nextInt(-350, 351)
                    assertEquals(model.contains(value), set.contains(value), "contains mismatch at step=$step")
                }

                else -> {
                    set.clear()
                    model.clear()
                }
            }

            assertEquals(model.size, set.size, "size mismatch at step=$step")
            assertEquals(model, set, "set snapshot mismatch at step=$step")
        }
    }
}
