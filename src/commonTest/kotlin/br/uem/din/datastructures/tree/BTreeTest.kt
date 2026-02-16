package br.uem.din.datastructures.tree

import kotlin.test.*

class BTreeTest {

    @Test
    fun testInsertAndSearch() {
        val tree = BTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        assertTrue(tree.search(10))
        assertTrue(tree.search(20))
        assertTrue(tree.search(5))
        assertFalse(tree.search(99))
    }

    @Test
    fun testSize() {
        val tree = BTree<Int>()
        assertEquals(0, tree.size)
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        assertEquals(3, tree.size)
    }

    @Test
    fun testInOrder() {
        val tree = BTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        tree.insert(15)
        tree.insert(25)
        assertEquals(listOf(5, 10, 15, 20, 25), tree.inOrder())
    }

    @Test
    fun testRemove() {
        val tree = BTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        tree.remove(10)
        assertFalse(tree.search(10))
        assertEquals(2, tree.size)
    }

    @Test
    fun testRemoveNonExistent() {
        val tree = BTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.remove(99)
        assertEquals(2, tree.size)
    }

    @Test
    fun testInOrderEmpty() {
        val tree = BTree<Int>()
        assertEquals(emptyList(), tree.inOrder())
    }

    @Test
    fun testCustomDegree() {
        val tree = BTree<Int>(minimumDegree = 3)
        for (i in 1..20) tree.insert(i)
        assertEquals((1..20).toList(), tree.inOrder())
    }

    @Test
    fun testInsertMany() {
        val tree = BTree<Int>()
        for (i in 20 downTo 1) tree.insert(i)
        assertEquals((1..20).toList(), tree.inOrder())
    }

    @Test
    fun testRemoveAll() {
        val tree = BTree<Int>()
        for (i in 1..5) tree.insert(i)
        for (i in 1..5) tree.remove(i)
        assertEquals(0, tree.size)
    }

    @Test
    fun testSplitRoot() {
        val tree = BTree<Int>(minimumDegree = 2)
        tree.insert(1)
        tree.insert(2)
        tree.insert(3)
        assertTrue(tree.search(1))
        assertTrue(tree.search(2))
        assertTrue(tree.search(3))
        assertEquals(listOf(1, 2, 3), tree.inOrder())
    }
}
