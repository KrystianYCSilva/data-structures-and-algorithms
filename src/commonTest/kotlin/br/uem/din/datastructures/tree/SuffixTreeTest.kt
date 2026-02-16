package br.uem.din.datastructures.tree

import kotlin.test.*

class SuffixTreeTest {

    @Test
    fun testSearchPattern() {
        val tree = SuffixTree()
        tree.build("banana")
        assertTrue(tree.searchPattern("ana"))
        assertFalse(tree.searchPattern("xyz"))
    }

    @Test
    fun testSearchEmpty() {
        val tree = SuffixTree()
        tree.build("hello")
        assertTrue(tree.searchPattern(""))
    }

    @Test
    fun testCountOccurrences() {
        val tree = SuffixTree()
        tree.build("banana")
        assertEquals(2, tree.countOccurrences("ana"))
    }

    @Test
    fun testCountOccurrencesZero() {
        val tree = SuffixTree()
        tree.build("hello")
        assertEquals(0, tree.countOccurrences("xyz"))
    }

    @Test
    fun testLongestRepeatedSubstring() {
        val tree = SuffixTree()
        tree.build("banana")
        assertEquals("ana", tree.longestRepeatedSubstring())
    }

    @Test
    fun testNoRepeatedSubstring() {
        val tree = SuffixTree()
        tree.build("abcdef")
        val result = tree.longestRepeatedSubstring()
        assertNotNull(result)
    }

    @Test
    fun testSingleChar() {
        val tree = SuffixTree()
        tree.build("a")
        assertTrue(tree.searchPattern("a"))
    }

    @Test
    fun testBuildAndSearch() {
        val tree = SuffixTree()
        tree.build("abcabc")
        assertTrue(tree.searchPattern("cab"))
        assertTrue(tree.searchPattern("bca"))
    }
}
