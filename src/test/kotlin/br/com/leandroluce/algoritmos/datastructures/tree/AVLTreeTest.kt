package br.com.leandroluce.algoritmos.datastructures.tree

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AVLTreeTest {

    @Test
    fun `insert and contains`() {
        val avl = AVLTree<Int>()
        avl.insert(15)
        avl.insert(10)
        avl.insert(20)
        avl.insert(5)
        avl.insert(12)
        avl.insert(18)
        avl.insert(22)

        assertTrue(avl.contains(15))
        assertTrue(avl.contains(10))
        assertTrue(avl.contains(20))
        assertTrue(avl.contains(5))
        assertTrue(avl.contains(12))
        assertTrue(avl.contains(18))
        assertTrue(avl.contains(22))
        assertFalse(avl.contains(0))
        assertFalse(avl.contains(17))
        assertFalse(avl.contains(25))
    }

    @Test
    fun `remove a leaf node`() {
        val avl = AVLTree<Int>()
        avl.insert(15)
        avl.insert(10)
        avl.insert(20)
        avl.remove(10)
        assertTrue(avl.contains(15))
        assertFalse(avl.contains(10))
        assertTrue(avl.contains(20))
    }

    @Test
    fun `remove a node with one child`() {
        val avl = AVLTree<Int>()
        avl.insert(15)
        avl.insert(10)
        avl.insert(20)
        avl.insert(5)
        avl.remove(10)
        assertTrue(avl.contains(15))
        assertFalse(avl.contains(10))
        assertTrue(avl.contains(20))
        assertTrue(avl.contains(5))
    }

    @Test
    fun `remove a node with two children`() {
        val avl = AVLTree<Int>()
        avl.insert(15)
        avl.insert(10)
        avl.insert(20)
        avl.insert(5)
        avl.insert(12)
        avl.remove(10)
        assertTrue(avl.contains(15))
        assertFalse(avl.contains(10))
        assertTrue(avl.contains(20))
        assertTrue(avl.contains(5))
        assertTrue(avl.contains(12))
    }

    @Test
    fun `balance after insertion`() {
        val avl = AVLTree<Int>()
        avl.insert(1)
        avl.insert(2)
        avl.insert(3)
        assertEquals(2, avl.root?.value)
        assertEquals(1, avl.root?.leftChild?.value)
        assertEquals(3, avl.root?.rightChild?.value)
    }

    @Test
    fun `balance after removal`() {
        val avl = AVLTree<Int>()
        avl.insert(1)
        avl.insert(2)
        avl.insert(3)
        avl.insert(4)
        avl.remove(1)
        assertEquals(3, avl.root?.value)
        assertEquals(2, avl.root?.leftChild?.value)
        assertEquals(4, avl.root?.rightChild?.value)
    }
}
