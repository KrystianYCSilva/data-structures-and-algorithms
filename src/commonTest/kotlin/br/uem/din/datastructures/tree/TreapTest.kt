package br.uem.din.datastructures.tree

import kotlin.test.*

class TreapTest {

    @Test
    fun testInsertAndContains() {
        val treap = Treap<Int>()
        treap.insert(5)
        treap.insert(3)
        treap.insert(7)
        assertTrue(treap.contains(3))
        assertFalse(treap.contains(99))
    }

    @Test
    fun testSize() {
        val treap = Treap<Int>()
        assertEquals(0, treap.size)
        treap.insert(5)
        treap.insert(3)
        treap.insert(7)
        assertEquals(3, treap.size)
    }

    @Test
    fun testRemove() {
        val treap = Treap<Int>()
        treap.insert(5)
        treap.insert(3)
        treap.insert(7)
        treap.remove(3)
        assertEquals(2, treap.size)
        assertFalse(treap.contains(3))
    }

    @Test
    fun testRemoveNonExistent() {
        val treap = Treap<Int>()
        treap.insert(5)
        treap.insert(3)
        treap.insert(7)
        treap.remove(99)
        assertEquals(3, treap.size)
    }

    @Test
    fun testRemoveEmpty() {
        val treap = Treap<Int>()
        treap.remove(1)
        assertEquals(0, treap.size)
    }

    @Test
    fun testInOrder() {
        val treap = Treap<Int>()
        treap.insert(5)
        treap.insert(3)
        treap.insert(7)
        treap.insert(1)
        treap.insert(4)
        assertEquals(listOf(1, 3, 4, 5, 7), treap.inOrder())
    }

    @Test
    fun testInOrderEmpty() {
        val treap = Treap<Int>()
        assertEquals(emptyList(), treap.inOrder())
    }

    @Test
    fun testInsertDuplicate() {
        val treap = Treap<Int>()
        treap.insert(5)
        treap.insert(5)
        assertEquals(1, treap.size)
    }

    @Test
    fun testInsertMany() {
        val treap = Treap<Int>()
        for (i in 1..100) {
            treap.insert(i)
        }
        assertEquals((1..100).toList(), treap.inOrder())
    }

    @Test
    fun testRemoveAll() {
        val treap = Treap<Int>()
        for (i in 1..5) {
            treap.insert(i)
        }
        for (i in 1..5) {
            treap.remove(i)
        }
        assertEquals(0, treap.size)
    }
}
