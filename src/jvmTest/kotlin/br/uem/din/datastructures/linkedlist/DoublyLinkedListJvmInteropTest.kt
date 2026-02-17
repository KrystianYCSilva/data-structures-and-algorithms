package br.uem.din.datastructures.linkedlist

import java.util.LinkedList as JavaLinkedList
import kotlin.random.Random
import kotlin.test.*

class DoublyLinkedListJvmInteropTest {

    @Test
    fun testJvmDoublyLinkedListMatchesJavaLinkedListRandomized() {
        val random = Random(4041)
        val ours = doublyLinkedListOf<Int>()
        val java = JavaLinkedList<Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..24 -> {
                    val value = random.nextInt(-5_000, 5_001)
                    ours.addFirst(value)
                    java.addFirst(value)
                }

                in 25..49 -> {
                    val value = random.nextInt(-5_000, 5_001)
                    ours.addLast(value)
                    java.addLast(value)
                }

                in 50..59 -> {
                    val expected = if (java.isEmpty()) null else java.removeFirst()
                    assertEquals(expected, ours.removeFirst(), "removeFirst mismatch at step=$step")
                }

                in 60..69 -> {
                    val expected = if (java.isEmpty()) null else java.removeLast()
                    assertEquals(expected, ours.removeLast(), "removeLast mismatch at step=$step")
                }

                in 70..79 -> {
                    if (java.isNotEmpty()) {
                        val index = random.nextInt(java.size)
                        val expected = java.removeAt(index)
                        assertEquals(expected, ours.removeAt(index), "removeAt mismatch at step=$step")
                    }
                }

                in 80..89 -> {
                    if (java.isNotEmpty()) {
                        val index = random.nextInt(java.size)
                        val value = random.nextInt(-5_000, 5_001)
                        java[index] = value
                        ours[index] = value
                        assertEquals(java[index], ours[index], "set/get mismatch at step=$step")
                    }
                }

                else -> {
                    val candidate = random.nextInt(-5_000, 5_001)
                    assertEquals(java.contains(candidate), ours.contains(candidate), "contains mismatch at step=$step")
                    assertEquals(java.indexOf(candidate), ours.indexOf(candidate), "indexOf mismatch at step=$step")
                }
            }

            assertEquals(java.size, ours.size, "size mismatch at step=$step")
            assertEquals(java.toList(), ours.toList(), "toList mismatch at step=$step")
            assertEquals(java.toString(), ours.toString(), "toString mismatch at step=$step")
        }
    }

    @Test
    fun testJvmDoublyLinkedListSupportsNullElements() {
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
