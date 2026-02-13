package br.com.leandroluce.algoritmos.datastructures.tree

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BinarySearchTreeTest {

    @Test
    fun `insert and contains`() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.insert(1)
        bst.insert(4)
        bst.insert(6)
        bst.insert(8)

        assertTrue(bst.contains(5))
        assertTrue(bst.contains(3))
        assertTrue(bst.contains(7))
        assertTrue(bst.contains(1))
        assertTrue(bst.contains(4))
        assertTrue(bst.contains(6))
        assertTrue(bst.contains(8))
        assertFalse(bst.contains(0))
        assertFalse(bst.contains(2))
        assertFalse(bst.contains(9))
    }

    @Test
    fun `remove a leaf node`() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.remove(3)
        assertTrue(bst.contains(5))
        assertFalse(bst.contains(3))
        assertTrue(bst.contains(7))
    }

    @Test
    fun `remove a node with one child`() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.insert(1)
        bst.remove(3)
        assertTrue(bst.contains(5))
        assertFalse(bst.contains(3))
        assertTrue(bst.contains(7))
        assertTrue(bst.contains(1))
    }

    @Test
    fun `remove a node with two children`() {
        val bst = BinarySearchTree<Int>()
        bst.insert(5)
        bst.insert(3)
        bst.insert(7)
        bst.insert(1)
        bst.insert(4)
        bst.remove(3)
        assertTrue(bst.contains(5))
        assertFalse(bst.contains(3))
        assertTrue(bst.contains(7))
        assertTrue(bst.contains(1))
        assertTrue(bst.contains(4))
    }
}
