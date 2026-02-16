package br.uem.din.datastructures.array

import kotlin.test.*

class ParallelArrayTest {

    @Test
    fun testAddRowAndGet() {
        val pa = ParallelArray("id", "name", "score")
        pa.addRow(1, "Alice", 9.5)
        pa.addRow(2, "Bob", 8.3)

        assertEquals(2, pa.size)
        assertEquals(1, pa.get(0, "id"))
        assertEquals("Alice", pa.get(0, "name"))
        assertEquals(9.5, pa.get(0, "score"))
        assertEquals("Bob", pa.get(1, "name"))
    }

    @Test
    fun testGetByColumnIndex() {
        val pa = ParallelArray("x", "y")
        pa.addRow(10, 20)

        assertEquals(10, pa.get(0, 0))
        assertEquals(20, pa.get(0, 1))
    }

    @Test
    fun testGetRow() {
        val pa = ParallelArray("a", "b", "c")
        pa.addRow(1, 2, 3)

        assertEquals(listOf(1, 2, 3), pa.getRow(0))
    }

    @Test
    fun testGetColumn() {
        val pa = ParallelArray("id", "value")
        pa.addRow(1, "x")
        pa.addRow(2, "y")
        pa.addRow(3, "z")

        assertEquals(listOf(1, 2, 3), pa.getColumn("id"))
        assertEquals(listOf("x", "y", "z"), pa.getColumn("value"))
    }

    @Test
    fun testSet() {
        val pa = ParallelArray("name")
        pa.addRow("old")
        pa.set(0, "name", "new")

        assertEquals("new", pa.get(0, "name"))
    }

    @Test
    fun testRemoveAt() {
        val pa = ParallelArray("id")
        pa.addRow(1)
        pa.addRow(2)
        pa.addRow(3)

        pa.removeAt(1)

        assertEquals(2, pa.size)
        assertEquals(1, pa.get(0, "id"))
        assertEquals(3, pa.get(1, "id"))
    }

    @Test
    fun testIsEmpty() {
        val pa = ParallelArray("x")
        assertTrue(pa.isEmpty())
        pa.addRow(1)
        assertFalse(pa.isEmpty())
    }

    @Test
    fun testColumnCount() {
        val pa = ParallelArray("a", "b", "c", "d")
        assertEquals(4, pa.columnCount)
    }

    @Test
    fun testColumnNames() {
        val pa = ParallelArray("id", "name")
        assertEquals(listOf("id", "name"), pa.columnNames)
    }

    @Test
    fun testInvalidColumnThrows() {
        val pa = ParallelArray("x")
        pa.addRow(1)
        assertFailsWith<IllegalArgumentException> {
            pa.get(0, "nonexistent")
        }
    }

    @Test
    fun testWrongValueCountThrows() {
        val pa = ParallelArray("a", "b")
        assertFailsWith<IllegalArgumentException> {
            pa.addRow(1)
        }
    }

    @Test
    fun testDuplicateColumnNamesThrows() {
        assertFailsWith<IllegalArgumentException> {
            ParallelArray("x", "x")
        }
    }

    @Test
    fun testRowOutOfBoundsThrows() {
        val pa = ParallelArray("x")
        pa.addRow(1)
        assertFailsWith<IndexOutOfBoundsException> {
            pa.get(5, "x")
        }
    }
}
