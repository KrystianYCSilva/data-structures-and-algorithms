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
}
