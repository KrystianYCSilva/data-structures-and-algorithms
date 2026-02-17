package br.uem.din.datastructures.set

import kotlin.test.*

class MultisetTest {

    @Test
    fun testAddAndCount() {
        val ms = Multiset<String>()
        ms.add("a")
        ms.add("a")
        ms.add("b")
        assertEquals(2, ms.count("a"))
        assertEquals(1, ms.count("b"))
        assertEquals(0, ms.count("c"))
    }

    @Test
    fun testAddMultipleOccurrences() {
        val ms = Multiset<String>()
        ms.add("x", 5)
        assertEquals(5, ms.count("x"))
        assertEquals(5, ms.size)
    }

    @Test
    fun testAddInvalidOccurrences() {
        val ms = Multiset<String>()
        assertFailsWith<IllegalArgumentException> {
            ms.add("a", 0)
        }
    }

    @Test
    fun testRemoveSingle() {
        val ms = Multiset<String>()
        ms.add("a", 3)
        assertTrue(ms.remove("a"))
        assertEquals(2, ms.count("a"))
        assertEquals(2, ms.size)
    }

    @Test
    fun testRemoveAll() {
        val ms = Multiset<String>()
        ms.add("a", 2)
        assertTrue(ms.remove("a", 2))
        assertEquals(0, ms.count("a"))
        assertFalse(ms.contains("a"))
    }

    @Test
    fun testRemoveMoreThanExists() {
        val ms = Multiset<String>()
        ms.add("a", 2)
        assertTrue(ms.remove("a", 10))
        assertEquals(0, ms.count("a"))
        assertEquals(0, ms.size)
    }

    @Test
    fun testRemoveMissing() {
        val ms = Multiset<String>()
        assertFalse(ms.remove("a"))
    }

    @Test
    fun testRemoveInvalidOccurrences() {
        val ms = Multiset<String>()
        ms.add("a")
        assertFailsWith<IllegalArgumentException> {
            ms.remove("a", 0)
        }
    }

    @Test
    fun testContains() {
        val ms = Multiset<String>()
        assertFalse(ms.contains("a"))
        ms.add("a")
        assertTrue(ms.contains("a"))
    }

    @Test
    fun testSize() {
        val ms = Multiset<String>()
        assertEquals(0, ms.size)
        ms.add("a", 3)
        ms.add("b", 2)
        assertEquals(5, ms.size)
    }

    @Test
    fun testDistinctElements() {
        val ms = Multiset<String>()
        ms.add("a", 3)
        ms.add("b", 2)
        ms.add("c")
        val distinct = ms.distinctElements()
        assertEquals(setOf("a", "b", "c"), distinct)
    }

    @Test
    fun testDistinctCount() {
        val ms = Multiset<String>()
        ms.add("a", 5)
        ms.add("b")
        assertEquals(2, ms.distinctCount)
    }

    @Test
    fun testIsEmpty() {
        val ms = Multiset<String>()
        assertTrue(ms.isEmpty())
        ms.add("a")
        assertFalse(ms.isEmpty())
    }

    @Test
    fun testClear() {
        val ms = Multiset<String>()
        ms.add("a", 3)
        ms.add("b", 2)
        ms.clear()
        assertTrue(ms.isEmpty())
        assertEquals(0, ms.size)
        assertEquals(0, ms.count("a"))
    }

    @Test
    fun testToString() {
        val ms = Multiset<String>()
        ms.add("x", 3)
        val str = ms.toString()
        assertTrue(str.startsWith("{"))
        assertTrue(str.endsWith("}"))
        assertTrue(str.contains("x"))
    }

    @Test
    fun testMutableMultisetInterface() {
        val ms: MutableMultiset<String> = Multiset()
        ms.add("a", 3)
        ms.add("b")
        assertEquals(4, ms.size)
        assertEquals(3, ms.count("a"))
        assertTrue(ms.contains("b"))
        assertEquals(2, ms.distinctCount)
        assertFalse(ms.isEmpty())
        ms.clear()
        assertTrue(ms.isEmpty())
    }

    @Test
    fun testReadOnlyMultisetView() {
        val ms: MutableMultiset<String> = Multiset()
        ms.add("x", 2)
        val readOnly: ImmutableMultiset<String> = ms
        assertEquals(2, readOnly.size)
        assertEquals(2, readOnly.count("x"))
        assertTrue(readOnly.contains("x"))
        assertEquals(setOf("x"), readOnly.distinctElements())
    }
}
