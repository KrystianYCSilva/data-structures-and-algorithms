package br.uem.din.datastructures.tree

import kotlin.test.*

class RedBlackTreeTest {

    @Test
    fun testInsert() {
        val tree = redBlackTreeOf<Int>()
        assertTrue(tree.insert(10))
        assertTrue(tree.insert(5))
        assertTrue(tree.insert(15))
        assertEquals(3, tree.size)
    }

    @Test
    fun testInsertDuplicate() {
        val tree = redBlackTreeOf<Int>()
        assertTrue(tree.insert(10))
        assertFalse(tree.insert(10))
        assertEquals(1, tree.size)
    }

    @Test
    fun testContains() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        assertTrue(tree.contains(10))
        assertTrue(tree.contains(5))
        assertTrue(tree.contains(15))
        assertFalse(tree.contains(7))
    }

    @Test
    fun testRemoveLeaf() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        assertTrue(tree.remove(5))
        assertFalse(tree.contains(5))
        assertEquals(2, tree.size)
    }

    @Test
    fun testRemoveNodeWithOneChild() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        tree.insert(3)
        assertTrue(tree.remove(5))
        assertFalse(tree.contains(5))
        assertTrue(tree.contains(3))
        assertEquals(3, tree.size)
    }

    @Test
    fun testRemoveNodeWithTwoChildren() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        tree.insert(3)
        tree.insert(7)
        assertTrue(tree.remove(5))
        assertFalse(tree.contains(5))
        assertTrue(tree.contains(3))
        assertTrue(tree.contains(7))
        assertEquals(4, tree.size)
    }

    @Test
    fun testRemoveRoot() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(10)
        assertTrue(tree.remove(10))
        assertTrue(tree.isEmpty())
    }

    @Test
    fun testRemoveNonexistent() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(10)
        assertFalse(tree.remove(99))
        assertEquals(1, tree.size)
    }

    @Test
    fun testRemoveFromEmpty() {
        val tree = redBlackTreeOf<Int>()
        assertFalse(tree.remove(1))
    }

    @Test
    fun testIsEmpty() {
        val tree = redBlackTreeOf<Int>()
        assertTrue(tree.isEmpty())
        tree.insert(1)
        assertFalse(tree.isEmpty())
        tree.remove(1)
        assertTrue(tree.isEmpty())
    }

    @Test
    fun testSize() {
        val tree = redBlackTreeOf<Int>()
        assertEquals(0, tree.size)
        tree.insert(1)
        tree.insert(2)
        tree.insert(3)
        assertEquals(3, tree.size)
        tree.remove(2)
        assertEquals(2, tree.size)
    }

    @Test
    fun testInOrder() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(30)
        tree.insert(10)
        tree.insert(20)
        tree.insert(40)
        tree.insert(5)
        assertEquals(listOf(5, 10, 20, 30, 40), tree.inOrder())
    }

    @Test
    fun testInOrderEmpty() {
        val tree = redBlackTreeOf<Int>()
        assertEquals(emptyList(), tree.inOrder())
    }

    @Test
    fun testInOrderAfterRemove() {
        val tree = redBlackTreeOf<Int>()
        for (i in 1..10) tree.insert(i)
        tree.remove(5)
        tree.remove(3)
        assertEquals(listOf(1, 2, 4, 6, 7, 8, 9, 10), tree.inOrder())
    }

    @Test
    fun testManyInsertions() {
        val tree = redBlackTreeOf<Int>()
        for (i in 1..100) {
            assertTrue(tree.insert(i))
        }
        assertEquals(100, tree.size)
        for (i in 1..100) {
            assertTrue(tree.contains(i))
        }
        assertEquals((1..100).toList(), tree.inOrder())
    }

    @Test
    fun testManyRemovals() {
        val tree = redBlackTreeOf<Int>()
        for (i in 1..50) tree.insert(i)
        for (i in 1..25) assertTrue(tree.remove(i))
        assertEquals(25, tree.size)
        for (i in 1..25) assertFalse(tree.contains(i))
        for (i in 26..50) assertTrue(tree.contains(i))
    }

    @Test
    fun testInsertRemoveReinsert() {
        val tree = redBlackTreeOf<Int>()
        tree.insert(10)
        tree.remove(10)
        assertTrue(tree.insert(10))
        assertTrue(tree.contains(10))
        assertEquals(1, tree.size)
    }

    @Test
    fun testReverseOrderInsertions() {
        val tree = redBlackTreeOf<Int>()
        for (i in 20 downTo 1) tree.insert(i)
        assertEquals(20, tree.size)
        assertEquals((1..20).toList(), tree.inOrder())
    }

    @Test
    fun testStringType() {
        val tree = redBlackTreeOf<String>()
        tree.insert("banana")
        tree.insert("apple")
        tree.insert("cherry")
        assertEquals(listOf("apple", "banana", "cherry"), tree.inOrder())
        assertTrue(tree.remove("banana"))
        assertEquals(listOf("apple", "cherry"), tree.inOrder())
    }

    @Test
    fun testMutableSearchTreeInterface() {
        val tree: MutableSearchTree<Int> = redBlackTreeOf()
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        assertTrue(tree.contains(10))
        assertEquals(3, tree.size)
        assertFalse(tree.isEmpty())
        assertEquals(listOf(5, 10, 15), tree.inOrder())
    }
}
