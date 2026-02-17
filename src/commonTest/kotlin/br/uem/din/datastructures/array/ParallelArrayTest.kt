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

    @Test
    fun testClear() {
        val pa = ParallelArray("id", "name")
        pa.addRow(1, "Alice")
        pa.addRow(2, "Bob")
        pa.clear()
        assertTrue(pa.isEmpty())
        assertEquals(0, pa.size)
    }

    @Test
    fun testContains() {
        val pa = ParallelArray("id", "name")
        pa.addRow(1, "Alice")
        assertTrue(pa.contains(1))
        assertTrue(pa.contains("Alice"))
        assertFalse(pa.contains("Bob"))
    }

    @Test
    fun testContainsNull() {
        val pa = ParallelArray("x")
        pa.addRow(null)
        assertTrue(pa.contains(null))
    }

    @Test
    fun testEquals() {
        val a = ParallelArray("x", "y")
        val b = ParallelArray("x", "y")
        a.addRow(1, 2)
        b.addRow(1, 2)
        assertEquals(a, b)
    }

    @Test
    fun testNotEquals() {
        val a = ParallelArray("x")
        val b = ParallelArray("x")
        a.addRow(1)
        b.addRow(2)
        assertNotEquals(a, b)
    }

    @Test
    fun testNotEqualsDifferentColumns() {
        val a = ParallelArray("x")
        val b = ParallelArray("y")
        assertNotEquals(a, b)
    }

    @Test
    fun testHashCodeConsistency() {
        val a = ParallelArray("id")
        val b = ParallelArray("id")
        a.addRow(1)
        b.addRow(1)
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun testToStringEmpty() {
        val pa = ParallelArray("x")
        assertTrue(pa.toString().contains("rows=0"))
    }

    @Test
    fun testToStringWithData() {
        val pa = ParallelArray("id", "name")
        pa.addRow(1, "Alice")
        val str = pa.toString()
        assertTrue(str.contains("id=1"))
        assertTrue(str.contains("name=Alice"))
    }

    @Test
    fun testGetByColumnIndexOutOfBoundsThrows() {
        val pa = ParallelArray("x")
        pa.addRow(1)
        assertFailsWith<IndexOutOfBoundsException> {
            pa.get(0, 5)
        }
    }

    @Test
    fun testRemoveAtOutOfBoundsThrows() {
        val pa = ParallelArray("x")
        pa.addRow(1)
        assertFailsWith<IndexOutOfBoundsException> {
            pa.removeAt(5)
        }
    }

    @Test
    fun testSetOutOfBoundsThrows() {
        val pa = ParallelArray("x")
        pa.addRow(1)
        assertFailsWith<IndexOutOfBoundsException> {
            pa.set(5, "x", 99)
        }
    }

    @Test
    fun testSetInvalidColumnThrows() {
        val pa = ParallelArray("x")
        pa.addRow(1)
        assertFailsWith<IllegalArgumentException> {
            pa.set(0, "nonexistent", 99)
        }
    }

    @Test
    fun testGetColumnInvalidThrows() {
        val pa = ParallelArray("x")
        pa.addRow(1)
        assertFailsWith<IllegalArgumentException> {
            pa.getColumn("nonexistent")
        }
    }
}
