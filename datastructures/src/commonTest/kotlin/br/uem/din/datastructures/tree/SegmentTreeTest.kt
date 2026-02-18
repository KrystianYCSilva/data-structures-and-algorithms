package br.uem.din.datastructures.tree

import kotlin.test.*

class SegmentTreeTest {

    @Test
    fun testSumQuery() {
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(listOf(1L, 2L, 3L, 4L, 5L))
        assertEquals(15L, tree.query(0, 4))
        assertEquals(9L, tree.query(1, 3))
    }

    @Test
    fun testMinQuery() {
        val tree = SegmentTree(Long.MAX_VALUE) { a, b -> minOf(a, b) }
        tree.build(listOf(5L, 2L, 8L, 1L, 3L))
        assertEquals(1L, tree.query(0, 4))
        assertEquals(2L, tree.query(0, 2))
    }

    @Test
    fun testPointUpdate() {
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(listOf(1L, 2L, 3L, 4L, 5L))
        tree.update(2, 10L)
        assertEquals(22L, tree.query(0, 4))
    }

    @Test
    fun testQuerySingleElement() {
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(listOf(1L, 2L, 3L))
        assertEquals(2L, tree.query(1, 1))
    }

    @Test
    fun testBuildEmpty() {
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(emptyList())
    }

    @Test
    fun testRangeUpdate() {
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(listOf(0L, 0L, 0L, 0L, 0L))
        tree.rangeUpdate(1, 3, 5L)
        assertEquals(5L, tree.query(1, 1))
        assertEquals(5L, tree.query(2, 2))
        assertEquals(5L, tree.query(3, 3))
    }

    @Test
    fun testUpdateAndQuery() {
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(listOf(1L, 1L, 1L, 1L))
        tree.update(0, 5L)
        assertEquals(5L, tree.query(0, 0))
        assertEquals(8L, tree.query(0, 3))
    }

    @Test
    fun testLargeArray() {
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build((1L..100L).toList())
        assertEquals(5050L, tree.query(0, 99))
    }
}
