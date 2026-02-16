package br.uem.din.datastructures.tree

import kotlin.test.*

class BinaryNodeTest {

    @Test
    fun testTraverseInOrder() {
        val root = BinaryNode(2)
        root.leftChild = BinaryNode(1)
        root.rightChild = BinaryNode(3)
        val result = mutableListOf<Int>()
        root.traverseInOrder { result.add(it) }
        assertEquals(listOf(1, 2, 3), result)
    }

    @Test
    fun testTraversePreOrder() {
        val root = BinaryNode(2)
        root.leftChild = BinaryNode(1)
        root.rightChild = BinaryNode(3)
        val result = mutableListOf<Int>()
        root.traversePreOrder { result.add(it) }
        assertEquals(listOf(2, 1, 3), result)
    }

    @Test
    fun testTraversePostOrder() {
        val root = BinaryNode(2)
        root.leftChild = BinaryNode(1)
        root.rightChild = BinaryNode(3)
        val result = mutableListOf<Int>()
        root.traversePostOrder { result.add(it) }
        assertEquals(listOf(1, 3, 2), result)
    }

    @Test
    fun testSingleNode() {
        val node = BinaryNode(5)
        val inOrder = mutableListOf<Int>()
        val preOrder = mutableListOf<Int>()
        val postOrder = mutableListOf<Int>()
        node.traverseInOrder { inOrder.add(it) }
        node.traversePreOrder { preOrder.add(it) }
        node.traversePostOrder { postOrder.add(it) }
        assertEquals(listOf(5), inOrder)
        assertEquals(listOf(5), preOrder)
        assertEquals(listOf(5), postOrder)
    }

    @Test
    fun testDeepTree() {
        val root = BinaryNode(4)
        root.leftChild = BinaryNode(2).apply {
            leftChild = BinaryNode(1)
            rightChild = BinaryNode(3)
        }
        root.rightChild = BinaryNode(6).apply {
            leftChild = BinaryNode(5)
            rightChild = BinaryNode(7)
        }
        val result = mutableListOf<Int>()
        root.traverseInOrder { result.add(it) }
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7), result)
    }

    @Test
    fun testNullChildren() {
        val node = BinaryNode(42)
        assertNull(node.leftChild)
        assertNull(node.rightChild)
    }
}
