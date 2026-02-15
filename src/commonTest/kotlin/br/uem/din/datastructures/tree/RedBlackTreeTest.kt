package br.uem.din.datastructures.tree

import kotlin.test.*

class RedBlackTreeTest {
    @Test
    fun testInsertAndContains() {
        val tree = RedBlackTree<Int>()
        assertTrue(tree.isEmpty())
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        assertEquals(3, tree.size())
        assertTrue(tree.contains(10))
        assertTrue(tree.contains(5))
        assertTrue(tree.contains(15))
        assertFalse(tree.contains(20))
    }
}
