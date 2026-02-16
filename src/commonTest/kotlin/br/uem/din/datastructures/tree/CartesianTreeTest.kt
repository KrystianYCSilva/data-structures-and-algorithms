package br.uem.din.datastructures.tree

import kotlin.test.*

class CartesianTreeTest {

    @Test
    fun testBuildAndInOrder() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(listOf(3, 2, 6, 1, 9))
        assertEquals(listOf(3, 2, 6, 1, 9), tree.inOrder())
    }

    @Test
    fun testMinHeapProperty() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(listOf(3, 2, 6, 1, 9))
        assertTrue(tree.isValidMinHeap())
    }

    @Test
    fun testEmptyArray() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(emptyList())
        assertNull(tree.root)
        assertEquals(emptyList(), tree.inOrder())
    }

    @Test
    fun testSingleElement() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(listOf(5))
        assertEquals(5, tree.root!!.value)
        assertEquals(listOf(5), tree.inOrder())
        assertTrue(tree.isValidMinHeap())
    }

    @Test
    fun testSortedArray() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(listOf(1, 2, 3, 4, 5))
        assertEquals(listOf(1, 2, 3, 4, 5), tree.inOrder())
        assertTrue(tree.isValidMinHeap())
    }

    @Test
    fun testReverseSorted() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(listOf(5, 4, 3, 2, 1))
        assertEquals(listOf(5, 4, 3, 2, 1), tree.inOrder())
        assertTrue(tree.isValidMinHeap())
    }

    @Test
    fun testAllSame() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(listOf(3, 3, 3))
        assertEquals(listOf(3, 3, 3), tree.inOrder())
    }

    @Test
    fun testRootIsMinimum() {
        val tree = CartesianTree<Int>()
        tree.buildFromArray(listOf(5, 3, 8, 1, 7))
        assertEquals(1, tree.root!!.value)
    }
}
