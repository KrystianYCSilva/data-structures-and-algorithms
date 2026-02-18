package br.uem.din.datastructures.hash

import kotlin.test.*

class CuckooHashTableTest {

    @Test
    fun testPutAndGet() {
        val table = CuckooHashTable<String, Int>()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.get("a"))
        assertEquals(2, table.get("b"))
    }

    @Test
    fun testGetMissing() {
        val table = CuckooHashTable<String, Int>()
        assertNull(table.get("missing"))
    }

    @Test
    fun testPutOverwrite() {
        val table = CuckooHashTable<String, Int>()
        table.put("a", 1)
        table.put("a", 99)
        assertEquals(99, table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testRemove() {
        val table = CuckooHashTable<String, Int>()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.remove("a"))
        assertNull(table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testRemoveMissing() {
        val table = CuckooHashTable<String, Int>()
        assertNull(table.remove("missing"))
    }

    @Test
    fun testContains() {
        val table = CuckooHashTable<String, Int>()
        table.put("x", 10)
        assertTrue(table.contains("x"))
        assertFalse(table.contains("y"))
    }

    @Test
    fun testSize() {
        val table = CuckooHashTable<String, Int>()
        assertEquals(0, table.size)
        table.put("a", 1)
        assertEquals(1, table.size)
        table.put("b", 2)
        assertEquals(2, table.size)
        table.remove("a")
        assertEquals(1, table.size)
    }

    @Test
    fun testRehashOnManyInsertions() {
        val table = CuckooHashTable<Int, Int>(initialCapacity = 4)
        for (i in 0 until 50) {
            table.put(i, i * 10)
        }
        assertEquals(50, table.size)
        for (i in 0 until 50) {
            assertEquals(i * 10, table.get(i))
        }
    }

    @Test
    fun testRemoveThenInsert() {
        val table = CuckooHashTable<String, Int>()
        table.put("a", 1)
        table.remove("a")
        table.put("a", 2)
        assertEquals(2, table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testToString() {
        val table = CuckooHashTable<String, Int>()
        table.put("key", 42)
        val str = table.toString()
        assertTrue(str.contains("key"))
        assertTrue(str.contains("42"))
    }

    @Test
    fun testMutableOpenHashTableInterface() {
        val table: MutableOpenHashTable<String, Int> = CuckooHashTable()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.get("a"))
        assertTrue(table.contains("b"))
        assertEquals(2, table.size)
        assertFalse(table.isEmpty())
        table.remove("a")
        assertNull(table.get("a"))
    }
}
