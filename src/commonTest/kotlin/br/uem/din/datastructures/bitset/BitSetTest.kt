package br.uem.din.datastructures.bitset

import kotlin.test.*

class BitSetTest {
    @Test
    fun testSetAndGet() {
        val bs = BitSet(64)
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
    fun testResize() {
        val bs = BitSet(32)
        bs.set(128)
        assertTrue(bs[128])
        assertTrue(bs.size() >= 128)
    }

    @Test
    fun testLength() {
        val bs = BitSet(64)
        assertEquals(0, bs.length())
        bs.set(0)
        assertEquals(1, bs.length())
        bs.set(10)
        assertEquals(11, bs.length())
        bs.clear(10)
        assertEquals(1, bs.length())
    }
}
