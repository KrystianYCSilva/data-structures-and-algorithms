package br.uem.din.datastructures.tree

import kotlin.test.*

class FenwickTreeTest {

    @Test
    fun testBuildAndPrefixSum() {
        val tree = FenwickTree(5)
        tree.build(longArrayOf(1, 2, 3, 4, 5))
        assertEquals(1L, tree.prefixSum(0))
        assertEquals(6L, tree.prefixSum(2))
        assertEquals(15L, tree.prefixSum(4))
    }

    @Test
    fun testRangeSum() {
        val tree = FenwickTree(5)
        tree.build(longArrayOf(1, 2, 3, 4, 5))
        assertEquals(9L, tree.rangeSum(1, 3))
    }

    @Test
    fun testUpdate() {
        val tree = FenwickTree(5)
        tree.build(longArrayOf(1, 2, 3, 4, 5))
        tree.update(2, 10)
        assertEquals(16L, tree.prefixSum(2))
        assertEquals(13L, tree.pointQuery(2))
    }

    @Test
    fun testPointQuery() {
        val tree = FenwickTree(5)
        tree.build(longArrayOf(1, 2, 3, 4, 5))
        assertEquals(1L, tree.pointQuery(0))
        assertEquals(4L, tree.pointQuery(3))
    }

    @Test
    fun testRangeSumFullRange() {
        val tree = FenwickTree(3)
        tree.build(longArrayOf(1, 2, 3))
        assertEquals(6L, tree.rangeSum(0, 2))
    }

    @Test
    fun testUpdateAndQuery() {
        val tree = FenwickTree(4)
        tree.build(longArrayOf(0, 0, 0, 0))
        tree.update(0, 5)
        tree.update(1, 3)
        tree.update(2, 7)
        tree.update(3, 2)
        assertEquals(5L, tree.prefixSum(0))
        assertEquals(8L, tree.prefixSum(1))
        assertEquals(15L, tree.prefixSum(2))
        assertEquals(17L, tree.prefixSum(3))
    }

    @Test
    fun testSingleElement() {
        val tree = FenwickTree(1)
        tree.build(longArrayOf(7))
        assertEquals(7L, tree.prefixSum(0))
    }

    @Test
    fun testRangeSumSingleElement() {
        val tree = FenwickTree(3)
        tree.build(longArrayOf(1, 2, 3))
        assertEquals(2L, tree.rangeSum(1, 1))
    }

    @Test
    fun testNegativeDeltaUpdate() {
        val tree = FenwickTree(3)
        tree.build(longArrayOf(10, 20, 30))
        tree.update(1, -5)
        assertEquals(15L, tree.pointQuery(1))
        assertEquals(25L, tree.prefixSum(1))
    }

    @Test
    fun testLargeArray() {
        val n = 100
        val tree = FenwickTree(n)
        val arr = LongArray(n) { it.toLong() + 1 }
        tree.build(arr)
        val expectedTotal = (n.toLong() * (n + 1)) / 2
        assertEquals(expectedTotal, tree.prefixSum(n - 1))
        assertEquals(arr[50], tree.pointQuery(50))
    }

    @Test
    fun testAllZeros() {
        val tree = FenwickTree(5)
        tree.build(longArrayOf(0, 0, 0, 0, 0))
        assertEquals(0L, tree.prefixSum(4))
        assertEquals(0L, tree.rangeSum(1, 3))
    }
}
