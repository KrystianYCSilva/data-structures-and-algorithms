package br.uem.din.datastructures.linkedlist

import kotlin.test.*

class UnrolledLinkedListTest {

    @Test
    fun testAddAndGet() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(3, list.size)
        assertEquals(1, list[0])
        assertEquals(2, list[1])
        assertEquals(3, list[2])
    }

    @Test
    fun testAddBeyondNodeCapacity() {
        val list = UnrolledLinkedList<Int>(2)
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        assertEquals(5, list.size)
        assertEquals(1, list[0])
        assertEquals(5, list[4])
    }

    @Test
    fun testGetOutOfBoundsThrows() {
        val list = UnrolledLinkedList<Int>()
        list.add(1)
        assertFailsWith<IndexOutOfBoundsException> { list[1] }
        assertFailsWith<IndexOutOfBoundsException> { list[-1] }
    }

    @Test
    fun testRemoveAt() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        assertEquals(2, list.removeAt(1))
        assertEquals(3, list.size)
        assertEquals(1, list[0])
        assertEquals(3, list[1])
        assertEquals(4, list[2])
    }

    @Test
    fun testRemoveAtRemovesEmptyNode() {
        val list = UnrolledLinkedList<Int>(1)
        list.add(1)
        list.add(2)
        assertEquals(1, list.removeAt(0))
        assertEquals(1, list.size)
        assertEquals(2, list[0])
    }

    @Test
    fun testContains() {
        val list = UnrolledLinkedList<String>(4)
        list.add("a")
        list.add("b")
        list.add("c")
        assertTrue(list.contains("b"))
        assertFalse(list.contains("z"))
    }

    @Test
    fun testIndexOf() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(10)
        list.add(20)
        list.add(30)
        assertEquals(0, list.indexOf(10))
        assertEquals(2, list.indexOf(30))
        assertEquals(-1, list.indexOf(99))
    }

    @Test
    fun testClear() {
        val list = UnrolledLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.clear()
        assertTrue(list.isEmpty())
        assertEquals(0, list.size)
        assertEquals("[]", list.toString())
    }

    @Test
    fun testToList() {
        val list = UnrolledLinkedList<Int>(3)
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        assertEquals(listOf(1, 2, 3, 4, 5), list.toList())
    }

    @Test
    fun testToString() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals("[1, 2, 3]", list.toString())
    }

    @Test
    fun testIterator() {
        val list = UnrolledLinkedList<Int>(2)
        list.add(10)
        list.add(20)
        list.add(30)
        val collected = mutableListOf<Int>()
        for (v in list) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testIterableExtensions() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        assertEquals(10, list.sumOf { it })
        assertEquals(listOf(2, 4), list.filter { it % 2 == 0 })
    }

    @Test
    fun testIsEmpty() {
        val list = UnrolledLinkedList<Int>()
        assertTrue(list.isEmpty())
        list.add(1)
        assertFalse(list.isEmpty())
    }

    @Test
    fun testEmptyList() {
        val list = UnrolledLinkedList<Int>()
        assertEquals("[]", list.toString())
        assertEquals(emptyList(), list.toList())
    }

    @Test
    fun testAddFirst() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(2)
        list.add(3)
        list.addFirst(1)
        assertEquals(3, list.size)
        assertEquals(1, list[0])
        assertEquals(2, list[1])
        assertEquals(3, list[2])
    }

    @Test
    fun testAddFirstOnEmpty() {
        val list = UnrolledLinkedList<Int>(4)
        list.addFirst(1)
        assertEquals(1, list.size)
        assertEquals(1, list[0])
    }

    @Test
    fun testRemoveFirst() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(1, list.removeFirst())
        assertEquals(2, list.size)
        assertEquals(2, list[0])
    }

    @Test
    fun testRemoveFirstEmpty() {
        val list = UnrolledLinkedList<Int>()
        assertNull(list.removeFirst())
    }

    @Test
    fun testRemoveLast() {
        val list = UnrolledLinkedList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals(3, list.removeLast())
        assertEquals(2, list.size)
        assertEquals(listOf(1, 2), list.toList())
    }

    @Test
    fun testRemoveLastEmpty() {
        val list = UnrolledLinkedList<Int>()
        assertNull(list.removeLast())
    }
}
