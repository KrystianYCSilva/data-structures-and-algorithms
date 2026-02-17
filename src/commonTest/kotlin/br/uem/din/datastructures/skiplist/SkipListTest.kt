package br.uem.din.datastructures.skiplist

import kotlin.test.*

class SkipListTest {

    @Test
    fun testInsertAndContains() {
        val skipList = SkipList<Int>()
        skipList.insert(10)
        skipList.insert(5)
        skipList.insert(20)

        assertTrue(skipList.contains(10))
        assertTrue(skipList.contains(5))
        assertTrue(skipList.contains(20))
        assertFalse(skipList.contains(15))
        assertEquals(3, skipList.size)
    }

    @Test
    fun testLargeInsert() {
        val skipList = SkipList<Int>()
        val n = 100
        for (i in 0 until n) {
            skipList.insert(i)
        }
        assertEquals(n, skipList.size)
        assertTrue(skipList.contains(50))
        assertFalse(skipList.contains(101))
    }

    @Test
    fun testDuplicateInsert() {
        val skipList = SkipList<Int>()
        skipList.insert(5)
        skipList.insert(5)
        assertEquals(1, skipList.size)
    }

    @Test
    fun testRemove() {
        val skipList = SkipList<Int>()
        skipList.insert(1)
        skipList.insert(2)
        skipList.insert(3)
        assertTrue(skipList.remove(2))
        assertFalse(skipList.contains(2))
        assertEquals(2, skipList.size)
    }

    @Test
    fun testRemoveNonExistent() {
        val skipList = SkipList<Int>()
        skipList.insert(1)
        assertFalse(skipList.remove(99))
        assertEquals(1, skipList.size)
    }

    @Test
    fun testRemoveFromEmpty() {
        val skipList = SkipList<Int>()
        assertFalse(skipList.remove(1))
    }

    @Test
    fun testIsEmpty() {
        val skipList = SkipList<Int>()
        assertTrue(skipList.isEmpty())
        skipList.insert(1)
        assertFalse(skipList.isEmpty())
    }

    @Test
    fun testClear() {
        val skipList = SkipList<Int>()
        skipList.insert(1)
        skipList.insert(2)
        skipList.insert(3)
        skipList.clear()
        assertTrue(skipList.isEmpty())
        assertEquals(0, skipList.size)
        assertFalse(skipList.contains(1))
    }

    @Test
    fun testIterator() {
        val skipList = SkipList<Int>()
        skipList.insert(30)
        skipList.insert(10)
        skipList.insert(20)
        val collected = mutableListOf<Int>()
        for (v in skipList) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testToList() {
        val skipList = SkipList<Int>()
        skipList.insert(3)
        skipList.insert(1)
        skipList.insert(2)
        assertEquals(listOf(1, 2, 3), skipList.toList())
    }

    @Test
    fun testToString() {
        val skipList = SkipList<Int>()
        assertEquals("[]", skipList.toString())
        skipList.insert(1)
        skipList.insert(2)
        assertEquals("[1, 2]", skipList.toString())
    }

    @Test
    fun testStringType() {
        val skipList = SkipList<String>()
        skipList.insert("banana")
        skipList.insert("apple")
        skipList.insert("cherry")
        assertTrue(skipList.contains("apple"))
        assertEquals(listOf("apple", "banana", "cherry"), skipList.toList())
    }

    @Test
    fun testContainsOnEmpty() {
        val skipList = SkipList<Int>()
        assertFalse(skipList.contains(1))
    }

    @Test
    fun testMutableSkipListInterface() {
        val sl: MutableSkipList<Int> = SkipList()
        sl.insert(30)
        sl.insert(10)
        sl.insert(20)
        assertEquals(3, sl.size)
        assertTrue(sl.contains(10))
        assertFalse(sl.isEmpty())
        assertEquals(listOf(10, 20, 30), sl.toList())
        assertTrue(sl.remove(10))
        assertFalse(sl.contains(10))
        sl.clear()
        assertTrue(sl.isEmpty())
    }

    @Test
    fun testReadOnlySkipListView() {
        val sl: MutableSkipList<Int> = SkipList()
        sl.insert(5)
        sl.insert(3)
        sl.insert(7)
        val readOnly: ReadOnlySkipList<Int> = sl
        assertEquals(3, readOnly.size)
        assertTrue(readOnly.contains(5))
        assertEquals(listOf(3, 5, 7), readOnly.toList())
    }
}
