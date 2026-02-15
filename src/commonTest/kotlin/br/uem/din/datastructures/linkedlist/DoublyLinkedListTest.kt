package br.uem.din.datastructures.linkedlist

import kotlin.test.*

class DoublyLinkedListTest {
    @Test
    fun testAddRemove() {
        val list = DoublyLinkedList<Int>()
        list.addFirst(1)
        list.addLast(2)
        assertEquals(2, list.size())
        assertEquals("[1, 2]", list.toString())
        assertEquals(1, list.removeFirst())
        assertEquals(2, list.removeLast())
        assertTrue(list.isEmpty())
    }
}
