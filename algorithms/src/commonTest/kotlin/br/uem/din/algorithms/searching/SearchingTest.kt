package br.uem.din.algorithms.searching

import kotlin.random.Random
import kotlin.test.*

class SearchingTest {

    private val sortedInts = listOf(2, 5, 8, 12, 16, 23, 38, 56, 72, 91)
    private val unsortedInts = listOf(42, 1, 99, 5, 12)

    private val sortedSearchAlgorithms: List<Pair<String, (List<Int>, Int) -> Int>> = listOf(
        "binarySearch" to ::binarySearch,
        "ternarySearch" to ::ternarySearch,
        "jumpSearch" to ::jumpSearch,
        "exponentialSearch" to ::exponentialSearch,
        "interpolationSearch" to ::interpolationSearch,
    )

    // ==================== LinearSearch ====================

    @Test
    fun testLinearSearch() {
        assertEquals(3, linearSearch(unsortedInts, 5))
        assertEquals(-1, linearSearch(unsortedInts, 100))
    }

    @Test
    fun testLinearSearchFirstAndLast() {
        assertEquals(0, linearSearch(unsortedInts, 42))
        assertEquals(4, linearSearch(unsortedInts, 12))
    }

    @Test
    fun testLinearSearchEmpty() {
        assertEquals(-1, linearSearch(emptyList<Int>(), 5))
    }

    @Test
    fun testLinearSearchSingle() {
        assertEquals(0, linearSearch(listOf(7), 7))
        assertEquals(-1, linearSearch(listOf(7), 3))
    }

    @Test
    fun testLinearSearchDuplicates() {
        val list = listOf(3, 1, 3, 5, 3)
        assertEquals(0, linearSearch(list, 3))
    }

    @Test
    fun testLinearSearchStrings() {
        val list = listOf("banana", "apple", "cherry")
        assertEquals(1, linearSearch(list, "apple"))
        assertEquals(-1, linearSearch(list, "date"))
    }

    // ==================== BinarySearch ====================

    @Test
    fun testBinarySearch() {
        assertEquals(5, binarySearch(sortedInts, 23))
        assertEquals(0, binarySearch(sortedInts, 2))
        assertEquals(9, binarySearch(sortedInts, 91))
        assertEquals(-1, binarySearch(sortedInts, 100))
    }

    @Test
    fun testBinarySearchBelowMin() {
        assertEquals(-1, binarySearch(sortedInts, 1))
    }

    @Test
    fun testBinarySearchEmpty() {
        assertEquals(-1, binarySearch(emptyList<Int>(), 5))
    }

    @Test
    fun testBinarySearchSingle() {
        assertEquals(0, binarySearch(listOf(10), 10))
        assertEquals(-1, binarySearch(listOf(10), 5))
    }

    @Test
    fun testBinarySearchTwo() {
        val list = listOf(1, 3)
        assertEquals(0, binarySearch(list, 1))
        assertEquals(1, binarySearch(list, 3))
        assertEquals(-1, binarySearch(list, 2))
    }

    @Test
    fun testBinarySearchDuplicates() {
        val list = listOf(1, 3, 3, 3, 5)
        val result = binarySearch(list, 3)
        assertTrue(result in 1..3, "Expected index 1..3, got $result")
    }

    @Test
    fun testBinarySearchStrings() {
        val list = listOf("apple", "banana", "cherry", "date")
        assertEquals(2, binarySearch(list, "cherry"))
        assertEquals(-1, binarySearch(list, "elderberry"))
    }

    // ==================== TernarySearch ====================

    @Test
    fun testTernarySearch() {
        assertEquals(5, ternarySearch(sortedInts, 23))
        assertEquals(0, ternarySearch(sortedInts, 2))
        assertEquals(9, ternarySearch(sortedInts, 91))
        assertEquals(-1, ternarySearch(sortedInts, 4))
    }

    @Test
    fun testTernarySearchEmpty() {
        assertEquals(-1, ternarySearch(emptyList<Int>(), 5))
    }

    @Test
    fun testTernarySearchSingle() {
        assertEquals(0, ternarySearch(listOf(10), 10))
        assertEquals(-1, ternarySearch(listOf(10), 5))
    }

    @Test
    fun testTernarySearchTwo() {
        assertEquals(0, ternarySearch(listOf(1, 3), 1))
        assertEquals(1, ternarySearch(listOf(1, 3), 3))
        assertEquals(-1, ternarySearch(listOf(1, 3), 2))
    }

    // ==================== JumpSearch ====================

    @Test
    fun testJumpSearch() {
        assertEquals(5, jumpSearch(sortedInts, 23))
        assertEquals(0, jumpSearch(sortedInts, 2))
        assertEquals(9, jumpSearch(sortedInts, 91))
        assertEquals(-1, jumpSearch(sortedInts, 4))
    }

    @Test
    fun testJumpSearchEmpty() {
        assertEquals(-1, jumpSearch(emptyList<Int>(), 5))
    }

    @Test
    fun testJumpSearchSingle() {
        assertEquals(0, jumpSearch(listOf(10), 10))
        assertEquals(-1, jumpSearch(listOf(10), 5))
    }

    // ==================== ExponentialSearch ====================

    @Test
    fun testExponentialSearch() {
        assertEquals(5, exponentialSearch(sortedInts, 23))
        assertEquals(0, exponentialSearch(sortedInts, 2))
        assertEquals(9, exponentialSearch(sortedInts, 91))
        assertEquals(-1, exponentialSearch(sortedInts, 4))
    }

    @Test
    fun testExponentialSearchEmpty() {
        assertEquals(-1, exponentialSearch(emptyList<Int>(), 5))
    }

    @Test
    fun testExponentialSearchSingle() {
        assertEquals(0, exponentialSearch(listOf(10), 10))
        assertEquals(-1, exponentialSearch(listOf(10), 5))
    }

    // ==================== InterpolationSearch ====================

    @Test
    fun testInterpolationSearch() {
        assertEquals(5, interpolationSearch(sortedInts, 23))
        assertEquals(0, interpolationSearch(sortedInts, 2))
        assertEquals(9, interpolationSearch(sortedInts, 91))
        assertEquals(-1, interpolationSearch(sortedInts, 4))
    }

    @Test
    fun testInterpolationSearchEmpty() {
        assertEquals(-1, interpolationSearch(emptyList(), 5))
    }

    @Test
    fun testInterpolationSearchSingle() {
        assertEquals(0, interpolationSearch(listOf(10), 10))
        assertEquals(-1, interpolationSearch(listOf(10), 5))
    }

    @Test
    fun testInterpolationSearchAllSame() {
        val list = listOf(5, 5, 5, 5, 5)
        val result = interpolationSearch(list, 5)
        assertTrue(result in 0..4, "Expected index 0..4, got $result")
        assertEquals(-1, interpolationSearch(list, 3))
    }

    @Test
    fun testInterpolationSearchUniform() {
        val list = (0..99).toList()
        for (i in list) {
            assertEquals(i, interpolationSearch(list, i))
        }
        assertEquals(-1, interpolationSearch(list, -1))
        assertEquals(-1, interpolationSearch(list, 100))
    }

    // ==================== Cross-algorithm: all sorted searches ====================

    @Test
    fun testAllSortedSearchesEmptyList() {
        for ((name, search) in sortedSearchAlgorithms) {
            assertEquals(-1, search(emptyList(), 5), "$name failed on empty list")
        }
    }

    @Test
    fun testAllSortedSearchesSingleElement() {
        for ((name, search) in sortedSearchAlgorithms) {
            assertEquals(0, search(listOf(42), 42), "$name failed on single element (found)")
            assertEquals(-1, search(listOf(42), 99), "$name failed on single element (not found)")
        }
    }

    @Test
    fun testAllSortedSearchesBelowMinAboveMax() {
        for ((name, search) in sortedSearchAlgorithms) {
            assertEquals(-1, search(sortedInts, 1), "$name failed: below minimum")
            assertEquals(-1, search(sortedInts, 100), "$name failed: above maximum")
        }
    }

    @Test
    fun testAllSortedSearchesAllSame() {
        val list = listOf(5, 5, 5, 5, 5)
        for ((name, search) in sortedSearchAlgorithms) {
            val result = search(list, 5)
            assertTrue(result in 0..4, "$name returned $result for all-same list (found)")
            assertEquals(-1, search(list, 3), "$name failed on all-same list (not found)")
        }
    }

    @Test
    fun testAllSortedSearchesNegativeValues() {
        val list = listOf(-100, -50, -10, 0, 10, 50, 100)
        for ((name, search) in sortedSearchAlgorithms) {
            assertEquals(0, search(list, -100), "$name failed on negative value (first)")
            assertEquals(3, search(list, 0), "$name failed on zero")
            assertEquals(6, search(list, 100), "$name failed on positive value (last)")
            assertEquals(-1, search(list, -75), "$name failed on negative not-found")
        }
    }

    @Test
    fun testAllSortedSearchesTwoElements() {
        val list = listOf(10, 20)
        for ((name, search) in sortedSearchAlgorithms) {
            assertEquals(0, search(list, 10), "$name failed on two-element (first)")
            assertEquals(1, search(list, 20), "$name failed on two-element (last)")
            assertEquals(-1, search(list, 15), "$name failed on two-element (not found)")
        }
    }

    @Test
    fun testAllSortedSearchesLargeListRandomized() {
        val list = (0 until 1000).toList()
        val random = Random(4001)
        for ((name, search) in sortedSearchAlgorithms) {
            repeat(100) {
                val target = random.nextInt(0, 1000)
                assertEquals(target, search(list, target), "$name failed on large list target=$target")
            }
            repeat(50) {
                val missing = random.nextInt(1000, 2000)
                assertEquals(-1, search(list, missing), "$name failed on large list missing=$missing")
            }
        }
    }

    @Test
    fun testAllSortedSearchesDuplicates() {
        val list = listOf(1, 3, 3, 3, 5, 7, 7, 9)
        for ((name, search) in sortedSearchAlgorithms) {
            val result3 = search(list, 3)
            assertTrue(result3 in 1..3, "$name: expected index 1..3 for duplicate 3, got $result3")
            val result7 = search(list, 7)
            assertTrue(result7 in 5..6, "$name: expected index 5..6 for duplicate 7, got $result7")
            assertEquals(-1, search(list, 4), "$name failed: not-found between duplicates")
        }
    }
}
