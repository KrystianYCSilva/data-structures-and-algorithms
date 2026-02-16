package br.uem.din.datastructures.hash

import kotlin.test.*

class OpenAddressingHashTableTest {

    @Test
    fun testPutAndGet() {
        val table = OpenAddressingHashTable<String, Int>()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.get("a"))
        assertEquals(2, table.get("b"))
    }

    @Test
    fun testGetMissing() {
        val table = OpenAddressingHashTable<String, Int>()
        assertNull(table.get("missing"))
    }

    @Test
    fun testPutOverwrite() {
        val table = OpenAddressingHashTable<String, Int>()
        table.put("a", 1)
        table.put("a", 99)
        assertEquals(99, table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testRemove() {
        val table = OpenAddressingHashTable<String, Int>()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.remove("a"))
        assertNull(table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testRemoveMissing() {
        val table = OpenAddressingHashTable<String, Int>()
        assertNull(table.remove("missing"))
    }

    @Test
    fun testContains() {
        val table = OpenAddressingHashTable<String, Int>()
        table.put("x", 10)
        assertTrue(table.contains("x"))
        assertFalse(table.contains("y"))
    }

    @Test
    fun testSize() {
        val table = OpenAddressingHashTable<String, Int>()
        assertEquals(0, table.size)
        table.put("a", 1)
        assertEquals(1, table.size)
        table.put("b", 2)
        assertEquals(2, table.size)
        table.remove("a")
        assertEquals(1, table.size)
    }

    @Test
    fun testRehash() {
        val table = OpenAddressingHashTable<Int, Int>(initialCapacity = 4, maxLoadFactor = 0.5)
        for (i in 0 until 20) {
            table.put(i, i * 10)
        }
        assertEquals(20, table.size)
        for (i in 0 until 20) {
            assertEquals(i * 10, table.get(i))
        }
    }

    @Test
    fun testLinearProbing() {
        val table = OpenAddressingHashTable<String, Int>(
            probingStrategy = OpenAddressingHashTable.ProbingStrategy.LINEAR
        )
        table.put("key1", 1)
        table.put("key2", 2)
        assertEquals(1, table.get("key1"))
        assertEquals(2, table.get("key2"))
    }

    @Test
    fun testQuadraticProbing() {
        val table = OpenAddressingHashTable<String, Int>(
            probingStrategy = OpenAddressingHashTable.ProbingStrategy.QUADRATIC
        )
        table.put("key1", 1)
        table.put("key2", 2)
        assertEquals(1, table.get("key1"))
        assertEquals(2, table.get("key2"))
    }

    @Test
    fun testDoubleHashingProbing() {
        val table = OpenAddressingHashTable<String, Int>(
            probingStrategy = OpenAddressingHashTable.ProbingStrategy.DOUBLE_HASHING
        )
        table.put("key1", 1)
        table.put("key2", 2)
        assertEquals(1, table.get("key1"))
        assertEquals(2, table.get("key2"))
    }

    @Test
    fun testRemoveThenInsert() {
        val table = OpenAddressingHashTable<String, Int>()
        table.put("a", 1)
        table.remove("a")
        table.put("a", 2)
        assertEquals(2, table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testToString() {
        val table = OpenAddressingHashTable<String, Int>()
        table.put("key", 42)
        val str = table.toString()
        assertTrue(str.contains("key"))
        assertTrue(str.contains("42"))
    }
}
