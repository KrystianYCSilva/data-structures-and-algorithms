package br.uem.din.datastructures.array

import kotlin.test.*

class MatrixTest {

    @Test
    fun testCreateAndAccess() {
        val m = Matrix(2, 3) { r, c -> r * 3 + c }
        assertEquals(0, m[0, 0])
        assertEquals(1, m[0, 1])
        assertEquals(5, m[1, 2])
        assertEquals(2, m.rows)
        assertEquals(3, m.cols)
        assertEquals(6, m.size)
    }

    @Test
    fun testSet() {
        val m = Matrix(2, 2) { _, _ -> 0 }
        m[0, 1] = 42
        assertEquals(42, m[0, 1])
    }

    @Test
    fun testGetRow() {
        val m = Matrix(2, 3) { r, c -> r * 10 + c }
        assertEquals(listOf(0, 1, 2), m.getRow(0))
        assertEquals(listOf(10, 11, 12), m.getRow(1))
    }

    @Test
    fun testGetColumn() {
        val m = Matrix(3, 2) { r, c -> r * 10 + c }
        assertEquals(listOf(0, 10, 20), m.getColumn(0))
        assertEquals(listOf(1, 11, 21), m.getColumn(1))
    }

    @Test
    fun testTranspose() {
        val m = Matrix(2, 3) { r, c -> r * 3 + c }
        val t = m.transpose()
        assertEquals(3, t.rows)
        assertEquals(2, t.cols)
        assertEquals(m[0, 1], t[1, 0])
        assertEquals(m[1, 2], t[2, 1])
    }

    @Test
    fun testMap() {
        val m = Matrix(2, 2) { r, c -> r + c }
        val doubled = m.map { it * 2 }
        assertEquals(0, doubled[0, 0])
        assertEquals(2, doubled[0, 1])
        assertEquals(4, doubled[1, 1])
    }

    @Test
    fun testIdentity() {
        val id = Matrix.identity(3, 0, 1)
        assertEquals(1, id[0, 0])
        assertEquals(0, id[0, 1])
        assertEquals(1, id[1, 1])
        assertEquals(1, id[2, 2])
        assertEquals(0, id[2, 0])
    }

    @Test
    fun testFill() {
        val m = Matrix.fill(2, 3, "x")
        assertEquals("x", m[0, 0])
        assertEquals("x", m[1, 2])
    }

    @Test
    fun testEquals() {
        val a = Matrix(2, 2) { r, c -> r + c }
        val b = Matrix(2, 2) { r, c -> r + c }
        assertEquals(a, b)
    }

    @Test
    fun testOutOfBoundsThrows() {
        val m = Matrix(2, 2) { _, _ -> 0 }
        assertFailsWith<IndexOutOfBoundsException> { m[2, 0] }
        assertFailsWith<IndexOutOfBoundsException> { m[0, 2] }
        assertFailsWith<IndexOutOfBoundsException> { m[-1, 0] }
    }

    @Test
    fun testForEach() {
        val m = Matrix(2, 2) { r, c -> r * 2 + c }
        var sum = 0
        m.forEach { _, _, value -> sum += value }
        assertEquals(0 + 1 + 2 + 3, sum)
    }

    @Test
    fun testToString() {
        val m = Matrix(2, 2) { r, c -> r * 2 + c }
        val str = m.toString()
        assertTrue(str.contains("[0, 1]"))
        assertTrue(str.contains("[2, 3]"))
    }
}
