package br.uem.din.datastructures.linkedlist

import kotlin.random.Random
import kotlin.test.*

class DoublyLinkedListNativeInteropTest {

    @Test
    fun testNativeDoublyLinkedListRandomizedParityWithModel() {
        val random = Random(4043)
        val list = doublyLinkedListOf<Int>()
        val model = mutableListOf<Int>()

        repeat(1_500) { step ->
            when (random.nextInt(100)) {
                in 0..24 -> {
                    val value = random.nextInt(-5_000, 5_001)
                    list.addFirst(value)
                    model.add(0, value)
                }

                in 25..49 -> {
                    val value = random.nextInt(-5_000, 5_001)
                    list.addLast(value)
                    model.add(value)
                }

                in 50..59 -> {
                    val expected = if (model.isEmpty()) null else model.removeAt(0)
                    assertEquals(expected, list.removeFirst(), "removeFirst mismatch at step=$step")
                }

                in 60..69 -> {
                    val expected = if (model.isEmpty()) null else model.removeAt(model.lastIndex)
                    assertEquals(expected, list.removeLast(), "removeLast mismatch at step=$step")
                }

                in 70..79 -> {
                    if (model.isNotEmpty()) {
                        val index = random.nextInt(model.size)
                        val expected = model.removeAt(index)
                        assertEquals(expected, list.removeAt(index), "removeAt mismatch at step=$step")
                    }
                }

                in 80..89 -> {
                    if (model.isNotEmpty()) {
                        val index = random.nextInt(model.size)
                        val value = random.nextInt(-5_000, 5_001)
                        model[index] = value
                        list[index] = value
                        assertEquals(model[index], list[index], "set/get mismatch at step=$step")
                    }
                }

                else -> {
                    val candidate = random.nextInt(-5_000, 5_001)
                    assertEquals(model.contains(candidate), list.contains(candidate), "contains mismatch at step=$step")
                    assertEquals(model.indexOf(candidate), list.indexOf(candidate), "indexOf mismatch at step=$step")
                }
            }

            assertEquals(model.size, list.size, "size mismatch at step=$step")
            assertEquals(model, list.toList(), "toList mismatch at step=$step")
        }
    }

    @Test
    fun testNativeDoublyLinkedListSupportsNullElements() {
        val list = doublyLinkedListOf<Int?>()

        list.addFirst(null)
        list.addLast(1)
        list.addLast(null)

        assertEquals(listOf(null, 1, null), list.toList())
        assertEquals(null, list.removeFirst())
        assertEquals(null, list.removeLast())
        assertEquals(1, list.removeFirst())
        assertNull(list.removeFirst())
    }
}
