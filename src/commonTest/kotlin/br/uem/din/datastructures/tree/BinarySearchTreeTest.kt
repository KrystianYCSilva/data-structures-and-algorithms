package br.uem.din.datastructures.tree

import kotlin.test.*

class BinarySearchTreeTest {

    @Test
    fun testInsertAndContains() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        assertTrue(bst.contains(3))
        assertFalse(bst.contains(99))
    }

    @Test
    fun testSize() {
        val bst = BinarySearchTree<Int>()
        assertEquals(0, bst.size)
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        assertEquals(3, bst.size)
        bst.remove(3)
        assertEquals(2, bst.size)
    }

    @Test
    fun testIsEmpty() {
        val bst = BinarySearchTree<Int>()
        assertTrue(bst.isEmpty())
        bst.insert(5)
        assertFalse(bst.isEmpty())
    }

    @Test
    fun testRemove() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.insert(1)
        bst.insert(4)
        bst.remove(3)
        assertFalse(bst.contains(3))
        assertEquals(4, bst.size)
    }

    @Test
    fun testRemoveNonExistent() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.remove(99)
        assertEquals(3, bst.size)
    }

    @Test
    fun testRemoveLeaf() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.remove(3)
        assertEquals(listOf(5, 7), bst.inOrder())
    }

    @Test
    fun testRemoveNodeWithOneChild() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.insert(1)
        bst.remove(3)
        assertEquals(listOf(1, 5, 7), bst.inOrder())
    }

    @Test
    fun testRemoveNodeWithTwoChildren() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.insert(1)
        bst.insert(4)
        bst.remove(3)
        assertEquals(listOf(1, 4, 5, 7), bst.inOrder())
    }

    @Test
    fun testRemoveRoot() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.remove(5)
        assertEquals(listOf(3, 7), bst.inOrder())
    }

    @Test
    fun testInOrder() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.insert(1)
        bst.insert(4)
        bst.insert(6)
        bst.insert(8)
        assertEquals(listOf(1, 3, 4, 5, 6, 7, 8), bst.inOrder())
    }

    @Test
    fun testInOrderEmpty() {
        val bst = BinarySearchTree<Int>()
        assertEquals(emptyList(), bst.inOrder())
    }

    @Test
    fun testInsertDuplicates() {
        val bst = BinarySearchTree<Int>()
        assertTrue(bst.insert(5))
        assertFalse(bst.insert(5))
        assertFalse(bst.insert(5))
        assertEquals(1, bst.size)
        assertEquals(listOf(5), bst.inOrder())
    }

    @Test
    fun testInsertReturnValue() {
        val bst = BinarySearchTree<Int>()
        assertTrue(bst.insert(1))
        assertTrue(bst.insert(2))
        assertFalse(bst.insert(1))
    }

    @Test
    fun testRemoveReturnValue() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        assertTrue(bst.remove(3))
        assertFalse(bst.remove(3))
        assertFalse(bst.remove(99))
    }

    @Test
    fun testMutableSearchTreeInterface() {
        val tree: MutableSearchTree<Int> = BinarySearchTree()
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        assertTrue(tree.contains(10))
        assertEquals(3, tree.size)
        assertFalse(tree.isEmpty())
        assertEquals(listOf(5, 10, 15), tree.inOrder())
    }
}
