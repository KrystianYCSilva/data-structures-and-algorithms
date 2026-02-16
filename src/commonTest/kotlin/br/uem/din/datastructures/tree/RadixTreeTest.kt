package br.uem.din.datastructures.tree

import kotlin.test.*

class RadixTreeTest {

    @Test
    fun testInsertAndSearch() {
        val tree = RadixTree()
        tree.insert("hello")
        assertTrue(tree.search("hello"))
        assertFalse(tree.search("world"))
    }

    @Test
    fun testSize() {
        val tree = RadixTree()
        assertEquals(0, tree.size)
        tree.insert("a")
        tree.insert("b")
        tree.insert("c")
        assertEquals(3, tree.size)
    }

    @Test
    fun testRemove() {
        val tree = RadixTree()
        tree.insert("hello")
        tree.insert("world")
        tree.remove("hello")
        assertFalse(tree.search("hello"))
        assertEquals(1, tree.size)
    }

    @Test
    fun testRemoveNonExistent() {
        val tree = RadixTree()
        tree.insert("hello")
        tree.insert("world")
        tree.remove("xyz")
        assertEquals(2, tree.size)
    }

    @Test
    fun testPrefixSearch() {
        val tree = RadixTree()
        tree.insert("test")
        tree.insert("testing")
        tree.insert("tested")
        tree.insert("tempo")
        val result = tree.prefixSearch("test").sorted()
        assertEquals(listOf("test", "tested", "testing"), result)
    }

    @Test
    fun testPrefixSearchAll() {
        val tree = RadixTree()
        tree.insert("alpha")
        tree.insert("beta")
        tree.insert("gamma")
        val result = tree.prefixSearch("")
        assertEquals(3, result.size)
    }

    @Test
    fun testPrefixSearchNoMatch() {
        val tree = RadixTree()
        tree.insert("hello")
        tree.insert("world")
        val result = tree.prefixSearch("xyz")
        assertTrue(result.isEmpty())
    }

    @Test
    fun testInsertDuplicate() {
        val tree = RadixTree()
        tree.insert("hello")
        tree.insert("hello")
        assertEquals(1, tree.size)
    }

    @Test
    fun testEmptyKey() {
        val tree = RadixTree()
        tree.insert("")
        assertTrue(tree.search(""))
        assertEquals(1, tree.size)
    }

    @Test
    fun testSplitNode() {
        val tree = RadixTree()
        tree.insert("test")
        tree.insert("team")
        assertTrue(tree.search("test"))
        assertTrue(tree.search("team"))
    }

    @Test
    fun testRemoveWithCompression() {
        val tree = RadixTree()
        tree.insert("test")
        tree.insert("testing")
        tree.remove("testing")
        assertTrue(tree.search("test"))
        assertFalse(tree.search("testing"))
    }

    @Test
    fun testInsertSharedPrefix() {
        val tree = RadixTree()
        tree.insert("abc")
        tree.insert("ab")
        assertTrue(tree.search("ab"))
        assertTrue(tree.search("abc"))
        assertEquals(2, tree.size)
    }
}
