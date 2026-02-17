package br.uem.din.datastructures.linkedlist

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
}
