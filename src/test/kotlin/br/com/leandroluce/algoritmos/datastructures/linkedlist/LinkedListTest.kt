package br.com.leandroluce.algoritmos.datastructures.linkedlist

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LinkedListTest {

    @Test
    fun `push should add element to the front`() {
        val list = LinkedList<Int>()
        list.push(1).push(2)
        assertEquals(2, list.size)
        assertEquals("2 -> 1", list.toString())
    }

    @Test
    fun `append should add element to the end`() {
        val list = LinkedList<Int>()
        list.append(1).append(2)
        assertEquals(2, list.size)
        assertEquals("1 -> 2", list.toString())
    }

    @Test
    fun `insert should add element at a given index`() {
        val list = LinkedList<Int>()
        list.push(3).push(2).push(1)
        val middleNode = list.nodeAt(1)!!
        list.insert(4, middleNode)
        assertEquals(4, list.size)
        assertEquals("1 -> 2 -> 4 -> 3", list.toString())
    }

    @Test
    fun `pop should remove element from the front`() {
        val list = LinkedList<Int>()
        list.push(1).push(2)
        assertEquals(2, list.pop())
        assertEquals(1, list.pop())
        assertNull(list.pop())
    }

    @Test
    fun `removeLast should remove element from the end`() {
        val list = LinkedList<Int>()
        list.push(1).push(2).push(3)
        assertEquals(1, list.removeLast())
        assertEquals(2, list.size)
        assertEquals("3 -> 2", list.toString())
    }

    @Test
    fun `removeAfter should remove element after a given node`() {
        val list = LinkedList<Int>()
        list.push(1).push(2).push(3)
        val node = list.nodeAt(1)!!
        list.removeAfter(node)
        assertEquals(2, list.size)
        assertEquals("3 -> 1", list.toString())
    }
}
