package br.uem.din.datastructures.tree

import kotlin.test.*

class AVLTreeTest {

    @Test
    fun testInsertAndContains() {
        val avl = AVLTree<Int>()
        avl.insert(5)
        avl.insert(3)
        avl.insert(7)
        assertTrue(avl.contains(3))
        assertFalse(avl.contains(99))
    }

    @Test
    fun testSize() {
        val avl = AVLTree<Int>()
        assertEquals(0, avl.size)
        avl.insert(5)
        avl.insert(3)
        avl.insert(7)
        assertEquals(3, avl.size)
    }

    @Test
    fun testIsEmpty() {
        val avl = AVLTree<Int>()
        assertTrue(avl.isEmpty())
        avl.insert(5)
        assertFalse(avl.isEmpty())
    }

    @Test
    fun testInOrder() {
        val avl = AVLTree<Int>()
        avl.insert(5)
        avl.insert(3)
        avl.insert(7)
        avl.insert(1)
        avl.insert(4)
        avl.insert(6)
        avl.insert(8)
        assertEquals(listOf(1, 3, 4, 5, 6, 7, 8), avl.inOrder())
    }

    @Test
    fun testInOrderEmpty() {
        val avl = AVLTree<Int>()
        assertEquals(emptyList(), avl.inOrder())
    }

    @Test
    fun testRemove() {
        val avl = AVLTree<Int>()
        avl.insert(5)
        avl.insert(3)
        avl.insert(7)
        avl.remove(3)
        assertFalse(avl.contains(3))
        assertEquals(2, avl.size)
    }

    @Test
    fun testRemoveNonExistent() {
        val avl = AVLTree<Int>()
        avl.insert(5)
        avl.insert(3)
        avl.insert(7)
        avl.remove(99)
        assertEquals(3, avl.size)
    }

    @Test
    fun testBalancingLeftLeft() {
        val avl = AVLTree<Int>()
        avl.insert(3)
        avl.insert(2)
        avl.insert(1)
        assertEquals(2, avl.root?.value)
    }

    @Test
    fun testBalancingRightRight() {
        val avl = AVLTree<Int>()
        avl.insert(1)
        avl.insert(2)
        avl.insert(3)
        assertEquals(2, avl.root?.value)
    }

    @Test
    fun testBalancingLeftRight() {
        val avl = AVLTree<Int>()
        avl.insert(3)
        avl.insert(1)
        avl.insert(2)
        assertEquals(2, avl.root?.value)
    }

    @Test
    fun testBalancingRightLeft() {
        val avl = AVLTree<Int>()
        avl.insert(1)
        avl.insert(3)
        avl.insert(2)
        assertEquals(2, avl.root?.value)
    }

    @Test
    fun testLargeSequential() {
        val avl = AVLTree<Int>()
        for (i in 1..20) {
            avl.insert(i)
        }
        assertEquals((1..20).toList(), avl.inOrder())
        val root = avl.root
        assertNotNull(root)
        val balanceFactor = height(root.leftChild) - height(root.rightChild)
        assertTrue(balanceFactor in -1..1)
    }

    @Test
    fun testInsertReturnValue() {
        val avl = AVLTree<Int>()
        assertTrue(avl.insert(5))
        assertTrue(avl.insert(3))
        assertFalse(avl.insert(5))
    }

    @Test
    fun testRemoveReturnValue() {
        val avl = AVLTree<Int>()
        avl.insert(5)
        avl.insert(3)
        assertTrue(avl.remove(3))
        assertFalse(avl.remove(3))
        assertFalse(avl.remove(99))
    }

    @Test
    fun testMutableSearchTreeInterface() {
        val tree: MutableSearchTree<Int> = AVLTree()
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        assertTrue(tree.contains(10))
        assertEquals(3, tree.size)
        assertFalse(tree.isEmpty())
        assertEquals(listOf(5, 10, 15), tree.inOrder())
    }

    private fun height(node: AVLNode<Int>?): Int {
        if (node == null) return 0
        return node.height
    }
}
