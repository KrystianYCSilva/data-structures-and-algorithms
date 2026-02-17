package br.uem.din.datastructures.hash

import kotlin.test.*

class SeparateChainingHashTableTest {

    @Test
    fun testPutAndGet() {
        val table = SeparateChainingHashTable<String, Int>()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.get("a"))
        assertEquals(2, table.get("b"))
    }

    @Test
    fun testGetMissing() {
        val table = SeparateChainingHashTable<String, Int>()
        assertNull(table.get("missing"))
    }

    @Test
    fun testPutOverwrite() {
        val table = SeparateChainingHashTable<String, Int>()
        table.put("a", 1)
        table.put("a", 99)
        assertEquals(99, table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testRemove() {
        val table = SeparateChainingHashTable<String, Int>()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.remove("a"))
        assertNull(table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testRemoveMissing() {
        val table = SeparateChainingHashTable<String, Int>()
        assertNull(table.remove("missing"))
    }

    @Test
    fun testContains() {
        val table = SeparateChainingHashTable<String, Int>()
        table.put("x", 10)
        assertTrue(table.contains("x"))
        assertFalse(table.contains("y"))
    }

    @Test
    fun testSize() {
        val table = SeparateChainingHashTable<String, Int>()
        assertEquals(0, table.size)
        table.put("a", 1)
        assertEquals(1, table.size)
        table.put("b", 2)
        assertEquals(2, table.size)
        table.remove("a")
        assertEquals(1, table.size)
    }

    @Test
    fun testIsEmpty() {
        val table = SeparateChainingHashTable<String, Int>()
        assertTrue(table.isEmpty())
        table.put("a", 1)
        assertFalse(table.isEmpty())
    }

    @Test
    fun testRehash() {
        val table = SeparateChainingHashTable<Int, Int>(initialCapacity = 4, maxLoadFactor = 0.5)
        for (i in 0 until 20) {
            table.put(i, i * 10)
        }
        assertEquals(20, table.size)
        for (i in 0 until 20) {
            assertEquals(i * 10, table.get(i))
        }
    }

    @Test
    fun testRemoveThenInsert() {
        val table = SeparateChainingHashTable<String, Int>()
        table.put("a", 1)
        table.remove("a")
        table.put("a", 2)
        assertEquals(2, table.get("a"))
        assertEquals(1, table.size)
    }

    @Test
    fun testToString() {
        val table = SeparateChainingHashTable<String, Int>()
        table.put("key", 42)
        val str = table.toString()
        assertTrue(str.contains("key"))
        assertTrue(str.contains("42"))
    }

    @Test
    fun testMutableOpenHashTableInterface() {
        val table: MutableOpenHashTable<String, Int> = SeparateChainingHashTable()
        table.put("a", 1)
        table.put("b", 2)
        assertEquals(1, table.get("a"))
        assertTrue(table.contains("b"))
        assertEquals(2, table.size)
        assertFalse(table.isEmpty())
        table.remove("a")
        assertNull(table.get("a"))
    }

    @Test
    fun testManyCollisions() {
        val table = SeparateChainingHashTable<Int, String>(initialCapacity = 2)
        for (i in 0 until 50) {
            table.put(i, "val$i")
        }
        assertEquals(50, table.size)
        for (i in 0 until 50) {
            assertEquals("val$i", table.get(i))
        }
    }

    @Test
    fun testRemoveFromChain() {
        val table = SeparateChainingHashTable<Int, String>(initialCapacity = 1)
        table.put(0, "zero")
        table.put(1, "one")
        table.put(2, "two")
        assertEquals("one", table.remove(1))
        assertNull(table.get(1))
        assertEquals("zero", table.get(0))
        assertEquals("two", table.get(2))
    }
}
