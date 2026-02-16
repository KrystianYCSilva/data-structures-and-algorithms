package br.uem.din.datastructures.tree

import kotlin.test.*

class SplayTreeTest {

    @Test
    fun testInsertAndContains() {
        val tree = SplayTree<Int>()
        tree.insert(5)
        tree.insert(3)
        tree.insert(7)
        assertTrue(tree.contains(3))
        assertFalse(tree.contains(99))
    }

    @Test
    fun testSize() {
        val tree = SplayTree<Int>()
        assertEquals(0, tree.size)
        tree.insert(5)
        tree.insert(3)
        tree.insert(7)
        assertEquals(3, tree.size)
    }

    @Test
    fun testRemove() {
        val tree = SplayTree<Int>()
        tree.insert(5)
        tree.insert(3)
        tree.insert(7)
        tree.remove(3)
        assertFalse(tree.contains(3))
        assertEquals(2, tree.size)
    }

    @Test
    fun testRemoveNonExistent() {
        val tree = SplayTree<Int>()
        tree.insert(5)
        tree.insert(3)
        tree.insert(7)
        tree.remove(99)
        assertEquals(3, tree.size)
    }

    @Test
    fun testRemoveEmpty() {
        val tree = SplayTree<Int>()
        tree.remove(1)
        assertEquals(0, tree.size)
    }

    @Test
    fun testInOrder() {
        val tree = SplayTree<Int>()
        tree.insert(5)
        tree.insert(3)
        tree.insert(7)
        tree.insert(1)
        tree.insert(4)
        assertEquals(listOf(1, 3, 4, 5, 7), tree.inOrder())
    }

    @Test
    fun testInOrderEmpty() {
        val tree = SplayTree<Int>()
        assertEquals(emptyList(), tree.inOrder())
    }

    @Test
    fun testInsertDuplicate() {
        val tree = SplayTree<Int>()
        tree.insert(5)
        tree.insert(5)
        assertEquals(1, tree.size)
    }

    @Test
    fun testContainsSplaysToRoot() {
        val tree = SplayTree<Int>()
        tree.insert(1)
        tree.insert(2)
        tree.insert(3)
        assertTrue(tree.contains(1))
    }

    @Test
    fun testRemoveRoot() {
        val tree = SplayTree<Int>()
        tree.insert(5)
        tree.insert(3)
        tree.insert(7)
        tree.remove(5)
        assertEquals(listOf(3, 7), tree.inOrder())
    }
}
