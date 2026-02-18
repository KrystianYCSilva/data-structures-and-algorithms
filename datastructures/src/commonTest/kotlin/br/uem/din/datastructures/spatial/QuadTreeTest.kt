package br.uem.din.datastructures.spatial

import br.uem.din.datastructures.asReadOnly
import kotlin.test.*

class QuadTreeTest {

    private fun defaultBoundary() = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)

    @Test
    fun testInsertAndContains() {
        val tree = QuadTree(defaultBoundary())
        assertTrue(tree.insert(QuadTree.Point(1.0, 1.0)))
        assertTrue(tree.contains(QuadTree.Point(1.0, 1.0)))
        assertFalse(tree.contains(QuadTree.Point(99.0, 99.0)))
    }

    @Test
    fun testSize() {
        val tree = QuadTree(defaultBoundary())
        assertEquals(0, tree.size)
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(2.0, 2.0))
        tree.insert(QuadTree.Point(3.0, 3.0))
        assertEquals(3, tree.size)
    }

    @Test
    fun testInsertOutOfBounds() {
        val tree = QuadTree(defaultBoundary())
        assertFalse(tree.insert(QuadTree.Point(50.0, 50.0)))
        assertEquals(0, tree.size)
    }

    @Test
    fun testQuery() {
        val tree = QuadTree(defaultBoundary())
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(5.0, 5.0))
        tree.insert(QuadTree.Point(9.0, 9.0))
        val results = tree.query(QuadTree.Rectangle(0.0, 0.0, 6.0, 6.0))
        assertTrue(results.any { it.x == 1.0 && it.y == 1.0 })
        assertTrue(results.any { it.x == 5.0 && it.y == 5.0 })
        assertFalse(results.any { it.x == 9.0 && it.y == 9.0 })
    }

    @Test
    fun testQueryNoResults() {
        val tree = QuadTree(defaultBoundary())
        tree.insert(QuadTree.Point(1.0, 1.0))
        val results = tree.query(QuadTree.Rectangle(8.0, 8.0, 1.0, 1.0))
        assertTrue(results.isEmpty())
    }

    @Test
    fun testAllPoints() {
        val tree = QuadTree(defaultBoundary())
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(2.0, 2.0))
        tree.insert(QuadTree.Point(3.0, 3.0))
        tree.insert(QuadTree.Point(4.0, 4.0))
        tree.insert(QuadTree.Point(5.0, 5.0))
        assertEquals(5, tree.allPoints().size)
    }

    @Test
    fun testSubdivision() {
        val tree = QuadTree(defaultBoundary(), nodeCapacity = 2)
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(2.0, 2.0))
        tree.insert(QuadTree.Point(3.0, 3.0))
        tree.insert(QuadTree.Point(4.0, 4.0))
        tree.insert(QuadTree.Point(5.0, 5.0))
        assertTrue(tree.contains(QuadTree.Point(1.0, 1.0)))
        assertTrue(tree.contains(QuadTree.Point(5.0, 5.0)))
    }

    @Test
    fun testContainsAfterSubdivide() {
        val tree = QuadTree(defaultBoundary(), nodeCapacity = 2)
        val points = listOf(
            QuadTree.Point(1.0, 1.0),
            QuadTree.Point(2.0, 2.0),
            QuadTree.Point(3.0, 3.0),
            QuadTree.Point(7.0, 7.0),
            QuadTree.Point(8.0, 8.0)
        )
        points.forEach { tree.insert(it) }
        points.forEach { assertTrue(tree.contains(it)) }
    }

    @Test
    fun testInsertAtBoundary() {
        val tree = QuadTree(defaultBoundary())
        assertTrue(tree.insert(QuadTree.Point(10.0, 10.0)))
    }

    @Test
    fun testQueryFullRange() {
        val tree = QuadTree(defaultBoundary())
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(5.0, 5.0))
        tree.insert(QuadTree.Point(9.0, 9.0))
        val results = tree.query(defaultBoundary())
        assertEquals(3, results.size)
    }

    @Test
    fun testEmptyTreeQueryReturnsEmpty() {
        val tree = QuadTree(defaultBoundary())
        assertTrue(tree.query(defaultBoundary()).isEmpty())
        assertTrue(tree.allPoints().isEmpty())
    }

    @Test
    fun testNegativeCoordinates() {
        val boundary = QuadTree.Rectangle(-10.0, -10.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        assertTrue(tree.insert(QuadTree.Point(-5.0, -5.0)))
        assertTrue(tree.insert(QuadTree.Point(-1.0, -8.0)))
        assertTrue(tree.contains(QuadTree.Point(-5.0, -5.0)))
        assertEquals(2, tree.size)
    }

    @Test
    fun testRectangleContainsPoint() {
        val rect = QuadTree.Rectangle(5.0, 5.0, 3.0, 3.0)
        assertTrue(rect.containsPoint(QuadTree.Point(5.0, 5.0)))
        assertTrue(rect.containsPoint(QuadTree.Point(8.0, 8.0)))
        assertTrue(rect.containsPoint(QuadTree.Point(2.0, 2.0)))
        assertFalse(rect.containsPoint(QuadTree.Point(9.0, 9.0)))
    }

    @Test
    fun testRectangleIntersects() {
        val r1 = QuadTree.Rectangle(0.0, 0.0, 5.0, 5.0)
        val r2 = QuadTree.Rectangle(3.0, 3.0, 5.0, 5.0)
        assertTrue(r1.intersects(r2))
        assertTrue(r2.intersects(r1))
        val r3 = QuadTree.Rectangle(20.0, 20.0, 1.0, 1.0)
        assertFalse(r1.intersects(r3))
    }

    @Test
    fun testInsertManyWithSubdivision() {
        val tree = QuadTree(defaultBoundary(), nodeCapacity = 2)
        for (i in 0 until 20) {
            tree.insert(QuadTree.Point(i * 0.5, i * 0.4))
        }
        assertEquals(20, tree.size)
        assertEquals(20, tree.allPoints().size)
    }

    @Test
    fun testInsertAtOrigin() {
        val tree = QuadTree(defaultBoundary())
        assertTrue(tree.insert(QuadTree.Point(0.0, 0.0)))
        assertTrue(tree.contains(QuadTree.Point(0.0, 0.0)))
    }

    @Test
    fun testImmutableQuadTreeView() {
        val tree = QuadTree(defaultBoundary())
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(5.0, 5.0))
        val readOnly: ImmutableQuadTree = tree.asReadOnly()
        assertEquals(2, readOnly.size)
        assertTrue(readOnly.contains(QuadTree.Point(1.0, 1.0)))
        assertEquals(2, readOnly.allPoints().size)
    }

    @Test
    fun testImmutableQuadTreeViewReflectsMutations() {
        val tree = QuadTree(defaultBoundary())
        val readOnly: ImmutableQuadTree = tree.asReadOnly()
        assertEquals(0, readOnly.size)
        tree.insert(QuadTree.Point(3.0, 3.0))
        assertEquals(1, readOnly.size)
        assertTrue(readOnly.contains(QuadTree.Point(3.0, 3.0)))
    }

    @Test
    fun testQueryAfterSubdivision() {
        val tree = QuadTree(defaultBoundary(), nodeCapacity = 1)
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(8.0, 8.0))
        tree.insert(QuadTree.Point(2.0, 2.0))
        val results = tree.query(QuadTree.Rectangle(0.0, 0.0, 5.0, 5.0))
        assertEquals(2, results.size)
    }
}
