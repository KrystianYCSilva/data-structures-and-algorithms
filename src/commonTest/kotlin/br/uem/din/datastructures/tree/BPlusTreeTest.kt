package br.uem.din.datastructures.tree

import kotlin.test.*

class BPlusTreeTest {

    @Test
    fun testInsertAndSearch() {
        val tree = BPlusTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        assertTrue(tree.contains(10))
        assertTrue(tree.contains(20))
        assertTrue(tree.contains(5))
        assertFalse(tree.contains(99))
    }

    @Test
    fun testSize() {
        val tree = BPlusTree<Int>()
        assertEquals(0, tree.size)
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        assertEquals(3, tree.size)
    }

    @Test
    fun testInOrder() {
        val tree = BPlusTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        tree.insert(15)
        tree.insert(25)
        assertEquals(listOf(5, 10, 15, 20, 25), tree.inOrder())
    }

    @Test
    fun testRemove() {
        val tree = BPlusTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        tree.remove(10)
        assertFalse(tree.contains(10))
        assertEquals(2, tree.size)
    }

    @Test
    fun testRangeSearch() {
        val tree = BPlusTree<Int>()
        for (i in 1..10) tree.insert(i)
        assertEquals(listOf(3, 4, 5, 6, 7), tree.rangeSearch(3, 7))
    }

    @Test
    fun testRangeSearchEmpty() {
        val tree = BPlusTree<Int>()
        for (i in 1..10) tree.insert(i)
        assertEquals(emptyList(), tree.rangeSearch(50, 60))
    }

    @Test
    fun testRemoveNonExistent() {
        val tree = BPlusTree<Int>()
        tree.insert(10)
        tree.insert(20)
        tree.remove(99)
        assertEquals(2, tree.size)
    }

    @Test
    fun testInOrderEmpty() {
        val tree = BPlusTree<Int>()
        assertEquals(emptyList(), tree.inOrder())
    }

    @Test
    fun testInsertDuplicate() {
        val tree = BPlusTree<Int>()
        tree.insert(5)
        tree.insert(5)
        assertEquals(1, tree.size)
    }

    @Test
    fun testInsertMany() {
        val tree = BPlusTree<Int>()
        for (i in 1..50) tree.insert(i)
        assertEquals((1..50).toList(), tree.inOrder())
    }
}
