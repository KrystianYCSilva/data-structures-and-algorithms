package br.uem.din.datastructures.tree

import kotlin.test.*

class TrieTest {

    @Test
    fun testInsertAndContains() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        assertTrue(trie.contains(listOf('c', 'a', 't')))
        assertFalse(trie.contains(listOf('c', 'a', 'r')))
    }

    @Test
    fun testRemove() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        trie.remove(listOf('c', 'a', 't'))
        assertFalse(trie.contains(listOf('c', 'a', 't')))
    }

    @Test
    fun testRemoveNonExistent() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        trie.remove(listOf('d', 'o', 'g'))
        assertTrue(trie.contains(listOf('c', 'a', 't')))
    }

    @Test
    fun testCollections() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        trie.insert(listOf('c', 'a', 'r'))
        trie.insert(listOf('c', 'a', 'r', 'd'))
        val result = trie.collections(listOf('c', 'a'))
        assertEquals(3, result.size)
        assertTrue(result.contains(listOf('c', 'a', 't')))
        assertTrue(result.contains(listOf('c', 'a', 'r')))
        assertTrue(result.contains(listOf('c', 'a', 'r', 'd')))
    }

    @Test
    fun testCollectionsEmpty() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        val result = trie.collections(listOf('z', 'z'))
        assertTrue(result.isEmpty())
    }

    @Test
    fun testContainsPrefix() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        assertFalse(trie.contains(listOf('c', 'a')))
    }

    @Test
    fun testEmptyList() {
        val trie = Trie<Char>()
        trie.insert(emptyList())
        assertTrue(trie.contains(emptyList()))
    }

    @Test
    fun testInsertMultiple() {
        val trie = Trie<Char>()
        trie.insert(listOf('d', 'o'))
        trie.insert(listOf('d', 'o', 'g'))
        trie.insert(listOf('d', 'o', 'n', 'e'))
        val result = trie.collections(listOf('d', 'o'))
        assertEquals(3, result.size)
        assertTrue(result.contains(listOf('d', 'o')))
        assertTrue(result.contains(listOf('d', 'o', 'g')))
        assertTrue(result.contains(listOf('d', 'o', 'n', 'e')))
    }

    @Test
    fun testRemoveCleanup() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        trie.insert(listOf('c', 'a', 'r'))
        trie.remove(listOf('c', 'a', 't'))
        val result = trie.collections(listOf('c', 'a'))
        assertEquals(1, result.size)
        assertTrue(result.contains(listOf('c', 'a', 'r')))
    }

    @Test
    fun testRemoveNonTerminating() {
        val trie = Trie<Char>()
        trie.insert(listOf('c', 'a', 't'))
        trie.remove(listOf('c', 'a'))
        assertTrue(trie.contains(listOf('c', 'a', 't')))
    }
}
