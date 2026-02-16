package br.uem.din.datastructures.linkedlist

import kotlin.test.*

class CircularLinkedListTest {

    @Test
    fun testAdd() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(3, list.size)
        assertEquals("[1, 2, 3]", list.toString())
    }

    @Test
    fun testAddFirst() {
        val list = CircularLinkedList<Int>()
        list.add(2)
        list.add(3)
        list.addFirst(1)
        assertEquals("[1, 2, 3]", list.toString())
    }

    @Test
    fun testRemove() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)

        assertTrue(list.remove(2))
        assertEquals(2, list.size)
        assertEquals("[1, 3]", list.toString())

        assertTrue(list.remove(1))
        assertEquals(1, list.size)
        assertEquals("[3]", list.toString())

        assertTrue(list.remove(3))
        assertEquals(0, list.size)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testRemoveNonExistent() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        assertFalse(list.remove(99))
        assertEquals(1, list.size)
    }

    @Test
    fun testRemoveAt() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(2, list.removeAt(1))
        assertEquals("[1, 3]", list.toString())
        assertEquals(1, list.removeAt(0))
        assertEquals("[3]", list.toString())
        assertEquals(3, list.removeAt(0))
        assertTrue(list.isEmpty())
    }

    @Test
    fun testRemoveAtOutOfBounds() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        assertFailsWith<IndexOutOfBoundsException> { list.removeAt(1) }
        assertFailsWith<IndexOutOfBoundsException> { list.removeAt(-1) }
    }

    @Test
    fun testGet() {
        val list = CircularLinkedList<Int>()
        list.add(10)
        list.add(20)
        assertEquals(10, list[0])
        assertEquals(20, list[1])
        assertNull(list[2])
        assertNull(list[-1])
    }

    @Test
    fun testContains() {
        val list = CircularLinkedList<String>()
        list.add("a")
        list.add("b")
        assertTrue(list.contains("a"))
        assertTrue(list.contains("b"))
        assertFalse(list.contains("c"))
    }

    @Test
    fun testIndexOf() {
        val list = CircularLinkedList<Int>()
        list.add(10)
        list.add(20)
        list.add(30)
        assertEquals(0, list.indexOf(10))
        assertEquals(2, list.indexOf(30))
        assertEquals(-1, list.indexOf(99))
    }

    @Test
    fun testClear() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.clear()
        assertTrue(list.isEmpty())
        assertEquals(0, list.size)
        assertEquals("[]", list.toString())
    }

    @Test
    fun testToList() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(listOf(1, 2, 3), list.toList())
    }

    @Test
    fun testIterator() {
        val list = CircularLinkedList<Int>()
        list.add(10)
        list.add(20)
        list.add(30)
        val collected = mutableListOf<Int>()
        for (v in list) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testIterableExtensions() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(6, list.sumOf { it })
        assertEquals(listOf(2), list.filter { it % 2 == 0 })
    }

    @Test
    fun testEmptyList() {
        val list = CircularLinkedList<Int>()
        assertTrue(list.isEmpty())
        assertEquals("[]", list.toString())
        assertEquals(emptyList(), list.toList())
    }

    @Test
    fun testRemoveFirst() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(1, list.removeFirst())
        assertEquals(2, list.size)
        assertEquals("[2, 3]", list.toString())
    }

    @Test
    fun testRemoveFirstEmpty() {
        val list = CircularLinkedList<Int>()
        assertNull(list.removeFirst())
    }

    @Test
    fun testRemoveFirstSingleElement() {
        val list = CircularLinkedList<Int>()
        list.add(42)
        assertEquals(42, list.removeFirst())
        assertTrue(list.isEmpty())
    }

    @Test
    fun testRemoveLast() {
        val list = CircularLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(3, list.removeLast())
        assertEquals(2, list.size)
        assertEquals("[1, 2]", list.toString())
    }

    @Test
    fun testRemoveLastEmpty() {
        val list = CircularLinkedList<Int>()
        assertNull(list.removeLast())
    }

    @Test
    fun testRemoveLastSingleElement() {
        val list = CircularLinkedList<Int>()
        list.add(42)
        assertEquals(42, list.removeLast())
        assertTrue(list.isEmpty())
    }

    @Test
    fun testAddLast() {
        val list = CircularLinkedList<Int>()
        list.addLast(1)
        list.addLast(2)
        assertEquals("[1, 2]", list.toString())
    }
}
