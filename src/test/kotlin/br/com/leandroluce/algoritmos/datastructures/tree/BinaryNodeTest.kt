package br.com.leandroluce.algoritmos.datastructures.tree

import kotlin.test.Test
import kotlin.test.assertEquals

class BinaryNodeTest {

    @Test
    fun `traverseInOrder should traverse the tree in-order`() {
        val tree = makeBinaryTree()
        val visited = mutableListOf<Int>()
        tree.traverseInOrder { visited.add(it) }
        assertEquals(listOf(0, 1, 5, 7, 8, 9), visited)
    }

    @Test
    fun `traversePreOrder should traverse the tree pre-order`() {
        val tree = makeBinaryTree()
        val visited = mutableListOf<Int>()
        tree.traversePreOrder { visited.add(it) }
        assertEquals(listOf(7, 1, 0, 5, 9, 8), visited)
    }

    @Test
    fun `traversePostOrder should traverse the tree post-order`() {
        val tree = makeBinaryTree()
        val visited = mutableListOf<Int>()
        tree.traversePostOrder { visited.add(it) }
        assertEquals(listOf(0, 5, 1, 8, 9, 7), visited)
    }

    private fun makeBinaryTree(): BinaryNode<Int> {
        val seven = BinaryNode(7)
        val one = BinaryNode(1)
        val zero = BinaryNode(0)
        val five = BinaryNode(5)
        val nine = BinaryNode(9)
        val eight = BinaryNode(8)

        seven.leftChild = one
        one.leftChild = zero
        one.rightChild = five
        seven.rightChild = nine
        nine.leftChild = eight

        return seven
    }
}
