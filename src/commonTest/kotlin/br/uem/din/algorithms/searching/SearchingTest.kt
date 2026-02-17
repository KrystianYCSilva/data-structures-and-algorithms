package br.uem.din.algorithms.searching

import kotlin.test.Test
import kotlin.test.assertEquals

class SearchingTest {

    private val sortedInts = listOf(2, 5, 8, 12, 16, 23, 38, 56, 72, 91)
    private val unsortedInts = listOf(42, 1, 99, 5, 12)

    @Test
    fun testLinearSearch() {
        assertEquals(3, linearSearch(unsortedInts, 5))
        assertEquals(-1, linearSearch(unsortedInts, 100))
    }

    @Test
    fun testBinarySearch() {
        assertEquals(5, binarySearch(sortedInts, 23))
        assertEquals(0, binarySearch(sortedInts, 2))
        assertEquals(9, binarySearch(sortedInts, 91))
        assertEquals(-1, binarySearch(sortedInts, 100))
    }

    @Test
    fun testTernarySearch() {
        assertEquals(5, ternarySearch(sortedInts, 23))
        assertEquals(0, ternarySearch(sortedInts, 2))
        assertEquals(9, ternarySearch(sortedInts, 91))
        assertEquals(-1, ternarySearch(sortedInts, 4))
    }

    @Test
    fun testJumpSearch() {
        assertEquals(5, jumpSearch(sortedInts, 23))
        assertEquals(0, jumpSearch(sortedInts, 2))
        assertEquals(9, jumpSearch(sortedInts, 91))
        assertEquals(-1, jumpSearch(sortedInts, 4))
    }

    @Test
    fun testExponentialSearch() {
        assertEquals(5, exponentialSearch(sortedInts, 23))
        assertEquals(0, exponentialSearch(sortedInts, 2))
        assertEquals(9, exponentialSearch(sortedInts, 91))
        assertEquals(-1, exponentialSearch(sortedInts, 4))
    }

    @Test
    fun testInterpolationSearch() {
        // Interpolation works best on uniform data, but should work on sorted generally
        assertEquals(5, interpolationSearch(sortedInts, 23))
        assertEquals(0, interpolationSearch(sortedInts, 2))
        assertEquals(9, interpolationSearch(sortedInts, 91))
        assertEquals(-1, interpolationSearch(sortedInts, 4))
    }
}
