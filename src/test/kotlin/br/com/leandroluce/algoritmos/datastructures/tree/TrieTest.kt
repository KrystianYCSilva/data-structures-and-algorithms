package br.com.leandroluce.algoritmos.datastructures.tree

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TrieTest {

    @Test
    fun `insert and contains`() {
        val trie = Trie<Char>()
        trie.insert("kotlin".toList())
        assertTrue(trie.contains("kotlin".toList()))
        assertFalse(trie.contains("kot".toList()))
    }

    @Test
    fun `remove`() {
        val trie = Trie<Char>()
        trie.insert("kotlin".toList())
        trie.remove("kotlin".toList())
        assertFalse(trie.contains("kotlin".toList()))
    }

    @Test
    fun `collections`() {
        val trie = Trie<Char>()
        trie.insert("car".toList())
        trie.insert("card".toList())
        trie.insert("care".toList())
        trie.insert("cat".toList())

        val collections = trie.collections("ca".toList())
        assertTrue(collections.contains("car".toList()))
        assertTrue(collections.contains("card".toList()))
        assertTrue(collections.contains("care".toList()))
        assertTrue(collections.contains("cat".toList()))
    }
}
