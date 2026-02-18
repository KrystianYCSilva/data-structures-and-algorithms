package br.uem.din.datastructures.linkedlist

import kotlin.random.Random
import kotlin.test.*

class DoublyLinkedListTest {

    @Test
    fun testAddFirstAndLast() {
        val list = doublyLinkedListOf<Int>()
        list.addFirst(1)
        list.addLast(2)
        assertEquals(2, list.size)
        assertEquals("[1, 2]", list.toString())
    }

    @Test
    fun testRemoveFirstAndLast() {
        val list = doublyLinkedListOf<Int>()
        list.addFirst(1)
        list.addLast(2)
        list.addLast(3)
        assertEquals(1, list.removeFirst())
        assertEquals(3, list.removeLast())
        assertEquals(1, list.size)
        assertEquals("[2]", list.toString())
    }

    @Test
    fun testRemoveFromEmpty() {
        val list = doublyLinkedListOf<String>()
        assertNull(list.removeFirst())
        assertNull(list.removeLast())
    }

    @Test
    fun testGetAndSet() {
        val list = doublyLinkedListOf<String>()
        list.addLast("a")
        list.addLast("b")
        list.addLast("c")
        assertEquals("a", list[0])
        assertEquals("b", list[1])
        assertEquals("c", list[2])
        list[1] = "B"
        assertEquals("B", list[1])
    }

    @Test
    fun testGetOutOfBoundsThrows() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(1)
        assertFailsWith<IndexOutOfBoundsException> { list[1] }
        assertFailsWith<IndexOutOfBoundsException> { list[-1] }
    }

    @Test
    fun testContains() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(10)
        list.addLast(20)
        list.addLast(30)
        assertTrue(list.contains(20))
        assertFalse(list.contains(99))
    }

    @Test
    fun testIndexOf() {
        val list = doublyLinkedListOf<String>()
        list.addLast("x")
        list.addLast("y")
        list.addLast("z")
        assertEquals(0, list.indexOf("x"))
        assertEquals(2, list.indexOf("z"))
        assertEquals(-1, list.indexOf("w"))
    }

    @Test
    fun testRemoveAt() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        list.addLast(4)
        assertEquals(2, list.removeAt(1))
        assertEquals(3, list.size)
        assertEquals("[1, 3, 4]", list.toString())
        assertEquals(1, list.removeAt(0))
        assertEquals(4, list.removeAt(1))
        assertEquals("[3]", list.toString())
    }

    @Test
    fun testClear() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(1)
        list.addLast(2)
        list.clear()
        assertTrue(list.isEmpty())
        assertEquals(0, list.size)
        assertEquals("[]", list.toString())
    }

    @Test
    fun testToList() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        assertEquals(listOf(1, 2, 3), list.toList())
    }

    @Test
    fun testIterator() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(10)
        list.addLast(20)
        list.addLast(30)
        val collected = mutableListOf<Int>()
        for (v in list) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testIterableExtensions() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        list.addLast(4)
        assertEquals(listOf(2, 4), list.filter { it % 2 == 0 })
        assertEquals(10, list.sumOf { it })
    }

    @Test
    fun testIsEmpty() {
        val list = doublyLinkedListOf<Int>()
        assertTrue(list.isEmpty())
        list.addFirst(1)
        assertFalse(list.isEmpty())
        list.removeFirst()
        assertTrue(list.isEmpty())
    }

    @Test
    fun testIteratorTerminationAndNoSuchElement() {
        val list = doublyLinkedListOf<Int>()
        list.addLast(1)
        list.addLast(2)

        val iterator = list.iterator()
        assertEquals(1, iterator.next())
        assertEquals(2, iterator.next())
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testRandomizedOperationsAgainstReferenceModel() {
        repeat(6) { seed ->
            val random = Random(seed + 4000)
            val list = doublyLinkedListOf<Int>()
            val model = mutableListOf<Int>()

            repeat(1_500) {
                when (random.nextInt(100)) {
                    in 0..24 -> {
                        val value = random.nextInt(-1_000, 1_001)
                        list.addFirst(value)
                        model.add(0, value)
                    }

                    in 25..49 -> {
                        val value = random.nextInt(-1_000, 1_001)
                        list.addLast(value)
                        model.add(value)
                    }

                    in 50..59 -> {
                        val expected = if (model.isEmpty()) null else model.removeAt(0)
                        assertEquals(expected, list.removeFirst())
                    }

                    in 60..69 -> {
                        val expected = if (model.isEmpty()) null else model.removeAt(model.lastIndex)
                        assertEquals(expected, list.removeLast())
                    }

                    in 70..79 -> {
                        if (model.isNotEmpty()) {
                            val index = random.nextInt(model.size)
                            val expected = model.removeAt(index)
                            assertEquals(expected, list.removeAt(index))
                        }
                    }

                    in 80..89 -> {
                        if (model.isNotEmpty()) {
                            val index = random.nextInt(model.size)
                            val value = random.nextInt(-1_000, 1_001)
                            model[index] = value
                            list[index] = value
                            assertEquals(value, list[index])
                        }
                    }

                    else -> {
                        val candidate = random.nextInt(-1_000, 1_001)
                        assertEquals(model.contains(candidate), list.contains(candidate))
                        assertEquals(model.indexOf(candidate), list.indexOf(candidate))
                    }
                }

                assertEquals(model.size, list.size)
                assertEquals(model.isEmpty(), list.isEmpty())
                assertEquals(model, list.toList())

                if (model.isEmpty()) {
                    assertFailsWith<IndexOutOfBoundsException> { list[0] }
                } else {
                    val index = random.nextInt(model.size)
                    assertEquals(model[index], list[index])
                }
            }
        }
    }
}

