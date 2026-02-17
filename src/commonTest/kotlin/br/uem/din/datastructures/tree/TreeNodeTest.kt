package br.uem.din.datastructures.tree

import kotlin.test.*

class TreeNodeTest {

    @Test
    fun testAddChild() {
        val root = TreeNode("root")
        val child = TreeNode("child")
        assertTrue(root.add(child))
    }

    @Test
    fun testForEachDepthFirst() {
        val root = TreeNode(1)
        val child1 = TreeNode(2)
        val child2 = TreeNode(3)
        val grandchild = TreeNode(4)
        root.add(child1)
        root.add(child2)
        child1.add(grandchild)
        val visited = mutableListOf<Int>()
        root.forEachDepthFirst { visited.add(it.value) }
        assertEquals(listOf(1, 2, 4, 3), visited)
    }

    @Test
    fun testForEachLevelOrder() {
        val root = TreeNode(1)
        val child1 = TreeNode(2)
        val child2 = TreeNode(3)
        val grandchild = TreeNode(4)
        root.add(child1)
        root.add(child2)
        child1.add(grandchild)
        val visited = mutableListOf<Int>()
        root.forEachLevelOrder { visited.add(it.value) }
        assertEquals(listOf(1, 2, 3, 4), visited)
    }

    @Test
    fun testSearchFound() {
        val root = TreeNode("A")
        val b = TreeNode("B")
        val c = TreeNode("C")
        root.add(b)
        b.add(c)
        val result = root.search("C")
        assertNotNull(result)
        assertEquals("C", result.value)
    }

    @Test
    fun testSearchNotFound() {
        val root = TreeNode("A")
        root.add(TreeNode("B"))
        assertNull(root.search("Z"))
    }

    @Test
    fun testSearchRoot() {
        val root = TreeNode(42)
        val result = root.search(42)
        assertNotNull(result)
        assertEquals(42, result.value)
    }

    @Test
    fun testSingleNodeTraversal() {
        val root = TreeNode("alone")
        val depthVisited = mutableListOf<String>()
        root.forEachDepthFirst { depthVisited.add(it.value) }
        assertEquals(listOf("alone"), depthVisited)
        val levelVisited = mutableListOf<String>()
        root.forEachLevelOrder { levelVisited.add(it.value) }
        assertEquals(listOf("alone"), levelVisited)
    }
}
