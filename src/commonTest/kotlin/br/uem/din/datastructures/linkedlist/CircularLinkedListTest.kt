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
    fun testGet() {
        val list = CircularLinkedList<Int>()
        list.add(10)
        list.add(20)
        
        assertEquals(10, list[0])
        assertEquals(20, list[1])
        assertNull(list[2])
    }
}
