package br.uem.din.datastructures.linkedlist

import kotlin.test.*

class LinkedListTest {

    @Test
    fun testAddFirstAndLast() {
        val list = LinkedList<Int>()
        list.addFirst(2)
        list.addFirst(1)
        list.addLast(3)
        assertEquals(3, list.size)
        assertEquals("[1, 2, 3]", list.toString())
    }

    @Test
    fun testPushAndPop() {
        val list = LinkedList<Int>()
        list.push(1).push(2).push(3)
        assertEquals(3, list.pop())
        assertEquals(2, list.pop())
        assertEquals(1, list.pop())
        assertNull(list.pop())
    }

    @Test
    fun testAppend() {
        val list = LinkedList<String>()
        list.append("a").append("b").append("c")
        assertEquals("[a, b, c]", list.toString())
    }

    @Test
    fun testGetAndSet() {
        val list = LinkedList<Int>()
        list.addLast(10)
        list.addLast(20)
        list.addLast(30)
        assertEquals(10, list[0])
        assertEquals(20, list[1])
        assertEquals(30, list[2])
        list[1] = 99
        assertEquals(99, list[1])
    }

    @Test
    fun testGetOutOfBoundsThrows() {
        val list = LinkedList<Int>()
        list.addLast(1)
        assertFailsWith<IndexOutOfBoundsException> { list[1] }
        assertFailsWith<IndexOutOfBoundsException> { list[-1] }
    }

    @Test
    fun testContains() {
        val list = LinkedList<String>()
        list.addLast("hello")
        list.addLast("world")
        assertTrue(list.contains("hello"))
        assertTrue(list.contains("world"))
        assertFalse(list.contains("foo"))
    }

    @Test
    fun testIndexOf() {
        val list = LinkedList<Int>()
        list.addLast(5)
        list.addLast(10)
        list.addLast(15)
        assertEquals(0, list.indexOf(5))
        assertEquals(2, list.indexOf(15))
        assertEquals(-1, list.indexOf(99))
    }

    @Test
    fun testInsertAt() {
        val list = LinkedList<Int>()
        list.addLast(1)
        list.addLast(3)
        list.insertAt(1, 2)
        assertEquals("[1, 2, 3]", list.toString())
        list.insertAt(0, 0)
        assertEquals("[0, 1, 2, 3]", list.toString())
        list.insertAt(4, 4)
        assertEquals("[0, 1, 2, 3, 4]", list.toString())
    }

    @Test
    fun testRemoveAt() {
        val list = LinkedList<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        list.addLast(4)
        assertEquals(2, list.removeAt(1))
        assertEquals("[1, 3, 4]", list.toString())
        assertEquals(1, list.removeAt(0))
        assertEquals(4, list.removeAt(1))
        assertEquals("[3]", list.toString())
    }

    @Test
    fun testRemoveFirst() {
        val list = LinkedList<Int>()
        assertNull(list.removeFirst())
        list.addLast(1)
        list.addLast(2)
        assertEquals(1, list.removeFirst())
        assertEquals(1, list.size)
    }

    @Test
    fun testRemoveLast() {
        val list = LinkedList<Int>()
        assertNull(list.removeLast())
        list.addLast(1)
        list.addLast(2)
        assertEquals(2, list.removeLast())
        assertEquals(1, list.size)
    }

    @Test
    fun testRemoveAfterBugfix() {
        val list = LinkedList<Int>()
        list.addLast(1)
        list.addLast(2)
        val node = list.nodeAt(1)!!
        assertNull(list.removeAfter(node))
        assertEquals(2, list.size)
    }

    @Test
    fun testNodeAtNegativeIndex() {
        val list = LinkedList<Int>()
        list.addLast(1)
        assertNull(list.nodeAt(-1))
    }

    @Test
    fun testClear() {
        val list = LinkedList<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        list.clear()
        assertTrue(list.isEmpty())
        assertEquals(0, list.size)
        assertEquals("[]", list.toString())
    }

    @Test
    fun testReverse() {
        val list = LinkedList<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        list.reverse()
        assertEquals("[3, 2, 1]", list.toString())
        assertEquals(3, list[0])
        assertEquals(1, list[2])
    }

    @Test
    fun testToList() {
        val list = LinkedList<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        assertEquals(listOf(1, 2, 3), list.toList())
    }

    @Test
    fun testIterator() {
        val list = LinkedList<Int>()
        list.addLast(10)
        list.addLast(20)
        list.addLast(30)
        val collected = mutableListOf<Int>()
        for (v in list) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testIterableExtensions() {
        val list = LinkedList<Int>()
        list.addLast(1)
        list.addLast(2)
        list.addLast(3)
        list.addLast(4)
        assertEquals(listOf(2, 4), list.filter { it % 2 == 0 })
        assertEquals(10, list.sumOf { it })
        assertEquals(listOf(2, 4, 6, 8), list.map { it * 2 })
    }

    @Test
    fun testEmptyListToString() {
        val list = LinkedList<Int>()
        assertEquals("[]", list.toString())
    }

    @Test
    fun testIsEmpty() {
        val list = LinkedList<Int>()
        assertTrue(list.isEmpty())
        list.addFirst(1)
        assertFalse(list.isEmpty())
        list.removeFirst()
        assertTrue(list.isEmpty())
    }
}
