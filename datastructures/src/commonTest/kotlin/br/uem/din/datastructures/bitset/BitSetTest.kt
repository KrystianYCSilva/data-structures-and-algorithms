package br.uem.din.datastructures.bitset

import br.uem.din.datastructures.bitset.bitSetOf
import kotlin.test.*

class BitSetTest {

    @Test
    fun testSetAndGet() {
        val bs = bitSetOf(64)
        assertFalse(bs[10])
        bs.set(10)
        assertTrue(bs[10])
        bs.clear(10)
        assertFalse(bs[10])
        bs.set(10, true)
        assertTrue(bs[10])
        bs.set(10, false)
        assertFalse(bs[10])
    }

    @Test
    fun testBit0() {
        val bs = bitSetOf()
        bs.set(0)
        assertTrue(bs[0])
        assertEquals(1, bs.length())
        assertEquals(1, bs.cardinality())
    }

    @Test
    fun testWordBoundaries32() {
        val bs = bitSetOf(64)
        bs.set(31)
        assertTrue(bs[31])
        bs.set(32)
        assertTrue(bs[32])
        assertFalse(bs[30])
        assertFalse(bs[33])
        assertEquals(2, bs.cardinality())
    }

    @Test
    fun testWordBoundaries64() {
        val bs = bitSetOf(128)
        bs.set(63)
        assertTrue(bs[63])
        bs.set(64)
        assertTrue(bs[64])
        assertFalse(bs[62])
        assertFalse(bs[65])
        assertEquals(2, bs.cardinality())
    }

    @Test
    fun testResize() {
        val bs = bitSetOf(32)
        bs.set(128)
        assertTrue(bs[128])
        assertTrue(bs.size() >= 129)
    }

    @Test
    fun testLength() {
        val bs = bitSetOf(64)
        assertEquals(0, bs.length())
        bs.set(0)
        assertEquals(1, bs.length())
        bs.set(10)
        assertEquals(11, bs.length())
        bs.clear(10)
        assertEquals(1, bs.length())
    }

    @Test
    fun testIsEmpty() {
        val bs = bitSetOf()
        assertTrue(bs.isEmpty())
        bs.set(5)
        assertFalse(bs.isEmpty())
        bs.clear(5)
        assertTrue(bs.isEmpty())
    }

    @Test
    fun testSizeAllocated() {
        val bs = bitSetOf(64)
        assertTrue(bs.size() >= 64)
        val bs2 = bitSetOf(100)
        assertTrue(bs2.size() >= 100)
    }

    @Test
    fun testCardinality() {
        val bs = bitSetOf()
        assertEquals(0, bs.cardinality())
        bs.set(1)
        bs.set(5)
        bs.set(10)
        assertEquals(3, bs.cardinality())
        bs.clear(5)
        assertEquals(2, bs.cardinality())
    }

    @Test
    fun testClearAll() {
        val bs = bitSetOf()
        bs.set(1)
        bs.set(50)
        bs.set(100)
        bs.clear()
        assertTrue(bs.isEmpty())
        assertEquals(0, bs.cardinality())
        assertEquals(0, bs.length())
    }

    @Test
    fun testNextSetBit() {
        val bs = bitSetOf()
        bs.set(3)
        bs.set(10)
        bs.set(75)
        assertEquals(3, bs.nextSetBit(0))
        assertEquals(3, bs.nextSetBit(3))
        assertEquals(10, bs.nextSetBit(4))
        assertEquals(75, bs.nextSetBit(11))
        assertEquals(-1, bs.nextSetBit(76))
    }

    @Test
    fun testIterator() {
        val bs = bitSetOf()
        bs.set(1)
        bs.set(5)
        bs.set(10)
        bs.set(100)
        assertEquals(listOf(1, 5, 10, 100), bs.toList())
    }

    @Test
    fun testIteratorEmpty() {
        val bs = bitSetOf()
        assertFalse(bs.iterator().hasNext())
        assertEquals(emptyList(), bs.toList())
    }

    @Test
    fun testToString() {
        val bs = bitSetOf()
        assertEquals("{}", bs.toString())
        bs.set(1)
        bs.set(5)
        bs.set(10)
        assertEquals("{1, 5, 10}", bs.toString())
    }

    @Test
    fun testEquals() {
        val a = bitSetOf()
        val b = bitSetOf()
        assertEquals(a, b)
        a.set(10)
        b.set(10)
        assertEquals(a, b)
        b.set(20)
        assertNotEquals(a, b)
    }

    @Test
    fun testHashCodeConsistency() {
        val a = bitSetOf()
        val b = bitSetOf()
        a.set(5)
        a.set(100)
        b.set(5)
        b.set(100)
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun testAnd() {
        val a = bitSetOf()
        val b = bitSetOf()
        a.set(1); a.set(2); a.set(3)
        b.set(2); b.set(3); b.set(4)
        a.and(b)
        assertFalse(a[1])
        assertTrue(a[2])
        assertTrue(a[3])
        assertFalse(a[4])
    }

    @Test
    fun testOr() {
        val a = bitSetOf()
        val b = bitSetOf()
        a.set(1); a.set(2)
        b.set(3); b.set(4)
        a.or(b)
        assertTrue(a[1])
        assertTrue(a[2])
        assertTrue(a[3])
        assertTrue(a[4])
    }

    @Test
    fun testXor() {
        val a = bitSetOf()
        val b = bitSetOf()
        a.set(1); a.set(2); a.set(3)
        b.set(2); b.set(3); b.set(4)
        a.xor(b)
        assertTrue(a[1])
        assertFalse(a[2])
        assertFalse(a[3])
        assertTrue(a[4])
    }

    @Test
    fun testAndNot() {
        val a = bitSetOf()
        val b = bitSetOf()
        a.set(1); a.set(2); a.set(3)
        b.set(2); b.set(3)
        a.andNot(b)
        assertTrue(a[1])
        assertFalse(a[2])
        assertFalse(a[3])
    }

    @Test
    fun testNegativeIndexSetThrows() {
        val bs = bitSetOf()
        assertFailsWith<IllegalArgumentException> { bs.set(-1) }
    }

    @Test
    fun testNegativeIndexClearThrows() {
        val bs = bitSetOf()
        assertFailsWith<IllegalArgumentException> { bs.clear(-1) }
    }

    @Test
    fun testNegativeIndexGetThrows() {
        val bs = bitSetOf()
        assertFailsWith<IllegalArgumentException> { bs[-1] }
    }

    @Test
    fun testNegativeIndexSetValueThrows() {
        val bs = bitSetOf()
        assertFailsWith<IllegalArgumentException> { bs.set(-1, true) }
    }

    @Test
    fun testGetBeyondCapacity() {
        val bs = bitSetOf(32)
        assertFalse(bs[1000])
    }

    @Test
    fun testMultipleBitsSameWord() {
        val bs = bitSetOf()
        bs.set(0); bs.set(1); bs.set(2); bs.set(3)
        assertEquals(4, bs.cardinality())
        assertTrue(bs[0])
        assertTrue(bs[3])
        bs.clear(1)
        assertEquals(3, bs.cardinality())
        assertFalse(bs[1])
    }

    @Test
    fun testLargeIndex() {
        val bs = bitSetOf()
        bs.set(10000)
        assertTrue(bs[10000])
        assertFalse(bs[9999])
        assertEquals(1, bs.cardinality())
    }

    @Test
    fun testIterableExtensions() {
        val bs = bitSetOf()
        bs.set(2); bs.set(4); bs.set(6)
        assertEquals(12, bs.sumOf { it })
        assertEquals(listOf(2, 4, 6), bs.filter { it > 0 })
        assertTrue(bs.any { it == 4 })
    }

    @Test
    fun testDefaultConstructor() {
        val bs = bitSetOf()
        assertTrue(bs.isEmpty())
        assertTrue(bs.size() >= 64)
    }
}
