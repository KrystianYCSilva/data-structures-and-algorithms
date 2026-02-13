package br.com.leandroluce.algoritmos.datastructures.tree

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TreeNodeTest {

    @Test
    fun `forEachDepthFirst should traverse the tree in depth-first order`() {
        val tree = makeTree()
        val visited = mutableListOf<Int>()
        tree.forEachDepthFirst { visited.add(it.value) }
        assertEquals(listOf(1, 2, 5, 6, 3, 4), visited)
    }

    @Test
    fun `forEachLevelOrder should traverse the tree in level-order`() {
        val tree = makeTree()
        val visited = mutableListOf<Int>()
        tree.forEachLevelOrder { visited.add(it.value) }
        assertEquals(listOf(1, 2, 3, 4, 5, 6), visited)
    }

    @Test
    fun `search should find a node in the tree`() {
        val tree = makeTree()
        val foundNode = tree.search(6)
        assertNotNull(foundNode)
        assertEquals(6, foundNode.value)
    }

    @Test
    fun `search should return null if node is not found`() {
        val tree = makeTree()
        val foundNode = tree.search(7)
        assertNull(foundNode)
    }

    private fun makeTree(): TreeNode<Int> {
        val tree = TreeNode(1)

        val child1 = TreeNode(2)
        val child2 = TreeNode(3)
        val child3 = TreeNode(4)

        tree.add(child1)
        tree.add(child2)
        tree.add(child3)

        val grandChild1 = TreeNode(5)
        val grandChild2 = TreeNode(6)
        child1.add(grandChild1)
        child1.add(grandChild2)

        return tree
    }
}
