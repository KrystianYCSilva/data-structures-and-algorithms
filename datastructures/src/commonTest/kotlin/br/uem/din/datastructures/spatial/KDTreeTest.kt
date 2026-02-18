package br.uem.din.datastructures.spatial

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.*

class KDTreeTest {

    @Test
    fun testInsertAndContains() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.0, 2.0))
        assertTrue(tree.contains(doubleArrayOf(1.0, 2.0)))
        assertFalse(tree.contains(doubleArrayOf(9.0, 9.0)))
    }

    @Test
    fun testSize() {
        val tree = KDTree(2)
        assertEquals(0, tree.size)
        tree.insert(doubleArrayOf(1.0, 1.0))
        tree.insert(doubleArrayOf(2.0, 2.0))
        tree.insert(doubleArrayOf(3.0, 3.0))
        assertEquals(3, tree.size)
    }

    @Test
    fun testIsEmpty() {
        val tree = KDTree(2)
        assertTrue(tree.isEmpty())
        tree.insert(doubleArrayOf(1.0, 1.0))
        assertFalse(tree.isEmpty())
    }

    @Test
    fun testNearestNeighbor() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(0.0, 0.0))
        tree.insert(doubleArrayOf(10.0, 10.0))
        tree.insert(doubleArrayOf(5.0, 5.0))
        val result = tree.nearestNeighbor(doubleArrayOf(4.0, 4.0))
        assertNotNull(result)
        assertTrue(result.contentEquals(doubleArrayOf(5.0, 5.0)))
    }

    @Test
    fun testNearestNeighborEmpty() {
        val tree = KDTree(2)
        assertNull(tree.nearestNeighbor(doubleArrayOf(1.0, 1.0)))
    }

    @Test
    fun testRangeSearch() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.0, 1.0))
        tree.insert(doubleArrayOf(5.0, 5.0))
        tree.insert(doubleArrayOf(3.0, 3.0))
        tree.insert(doubleArrayOf(8.0, 8.0))
        val results = tree.rangeSearch(doubleArrayOf(2.0, 2.0), doubleArrayOf(6.0, 6.0))
        assertEquals(2, results.size)
        assertTrue(results.any { it.contentEquals(doubleArrayOf(5.0, 5.0)) })
        assertTrue(results.any { it.contentEquals(doubleArrayOf(3.0, 3.0)) })
    }

    @Test
    fun testRangeSearchEmpty() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.0, 1.0))
        tree.insert(doubleArrayOf(8.0, 8.0))
        val results = tree.rangeSearch(doubleArrayOf(3.0, 3.0), doubleArrayOf(5.0, 5.0))
        assertTrue(results.isEmpty())
    }

    @Test
    fun testContainsExact() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.5, 2.5))
        assertTrue(tree.contains(doubleArrayOf(1.5, 2.5)))
    }

    @Test
    fun test3D() {
        val tree = KDTree(3)
        tree.insert(doubleArrayOf(1.0, 2.0, 3.0))
        tree.insert(doubleArrayOf(4.0, 5.0, 6.0))
        assertTrue(tree.contains(doubleArrayOf(1.0, 2.0, 3.0)))
        assertTrue(tree.contains(doubleArrayOf(4.0, 5.0, 6.0)))
    }

    @Test
    fun testInsertMany() {
        val tree = KDTree(2)
        val points = (0 until 10).map { doubleArrayOf(it.toDouble(), it.toDouble() * 2) }
        points.forEach { tree.insert(it) }
        assertEquals(10, tree.size)
        points.forEach { assertTrue(tree.contains(it)) }
    }

    @Test
    fun testInvalidDimensionK0() {
        assertFailsWith<IllegalArgumentException> { KDTree(0) }
    }

    @Test
    fun testInsertWrongDimensionThrows() {
        val tree = KDTree(2)
        assertFailsWith<IllegalArgumentException> { tree.insert(doubleArrayOf(1.0)) }
        assertFailsWith<IllegalArgumentException> { tree.insert(doubleArrayOf(1.0, 2.0, 3.0)) }
    }

    @Test
    fun testContainsWrongDimensionThrows() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.0, 2.0))
        assertFailsWith<IllegalArgumentException> { tree.contains(doubleArrayOf(1.0)) }
    }

    @Test
    fun testNearestNeighborWrongDimensionThrows() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.0, 2.0))
        assertFailsWith<IllegalArgumentException> { tree.nearestNeighbor(doubleArrayOf(1.0, 2.0, 3.0)) }
    }

    @Test
    fun testRangeSearchWrongDimensionThrows() {
        val tree = KDTree(2)
        assertFailsWith<IllegalArgumentException> {
            tree.rangeSearch(doubleArrayOf(0.0), doubleArrayOf(1.0, 1.0))
        }
        assertFailsWith<IllegalArgumentException> {
            tree.rangeSearch(doubleArrayOf(0.0, 0.0), doubleArrayOf(1.0))
        }
    }

    @Test
    fun test1D() {
        val tree = KDTree(1)
        tree.insert(doubleArrayOf(5.0))
        tree.insert(doubleArrayOf(3.0))
        tree.insert(doubleArrayOf(8.0))
        assertTrue(tree.contains(doubleArrayOf(3.0)))
        val nn = tree.nearestNeighbor(doubleArrayOf(4.0))
        assertNotNull(nn)
        assertTrue(nn.contentEquals(doubleArrayOf(3.0)) || nn.contentEquals(doubleArrayOf(5.0)))
    }

    @Test
    fun testHighDimensional() {
        val tree = KDTree(5)
        val p1 = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val p2 = doubleArrayOf(6.0, 7.0, 8.0, 9.0, 10.0)
        tree.insert(p1)
        tree.insert(p2)
        assertTrue(tree.contains(p1))
        assertFalse(tree.contains(doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0)))
    }

    @Test
    fun testDuplicateInsert() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.0, 1.0))
        tree.insert(doubleArrayOf(1.0, 1.0))
        assertEquals(2, tree.size)
        assertTrue(tree.contains(doubleArrayOf(1.0, 1.0)))
    }

    @Test
    fun testNegativeCoordinates() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(-5.0, -3.0))
        tree.insert(doubleArrayOf(-1.0, -1.0))
        assertTrue(tree.contains(doubleArrayOf(-5.0, -3.0)))
        val nn = tree.nearestNeighbor(doubleArrayOf(-4.0, -2.0))
        assertNotNull(nn)
        assertTrue(nn.contentEquals(doubleArrayOf(-5.0, -3.0)))
    }

    @Test
    fun testRangeSearchOnEmptyTree() {
        val tree = KDTree(2)
        val results = tree.rangeSearch(doubleArrayOf(0.0, 0.0), doubleArrayOf(10.0, 10.0))
        assertTrue(results.isEmpty())
    }

    @Test
    fun testRandomizedInsertContains() {
        val tree = KDTree(3)
        val rng = Random(42)
        val points = List(100) { doubleArrayOf(rng.nextDouble(), rng.nextDouble(), rng.nextDouble()) }
        points.forEach { tree.insert(it) }
        assertEquals(100, tree.size)
        points.forEach { assertTrue(tree.contains(it)) }
    }

    @Test
    fun testImmutableKDTreeView() {
        val tree = KDTree(2)
        tree.insert(doubleArrayOf(1.0, 2.0))
        tree.insert(doubleArrayOf(3.0, 4.0))
        val readOnly: ImmutableKDTree = tree.asReadOnly()
        assertEquals(2, readOnly.size)
        assertTrue(readOnly.contains(doubleArrayOf(1.0, 2.0)))
        assertFalse(readOnly.isEmpty())
        assertNotNull(readOnly.nearestNeighbor(doubleArrayOf(2.0, 3.0)))
    }

    @Test
    fun testImmutableKDTreeViewReflectsMutations() {
        val tree = KDTree(2)
        val readOnly: ImmutableKDTree = tree.asReadOnly()
        assertEquals(0, readOnly.size)
        tree.insert(doubleArrayOf(5.0, 5.0))
        assertEquals(1, readOnly.size)
        assertTrue(readOnly.contains(doubleArrayOf(5.0, 5.0)))
    }
}
