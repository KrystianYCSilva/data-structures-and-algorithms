package br.uem.din.datastructures.spatial

import kotlin.test.*

class QuadTreeTest {

    @Test
    fun testInsertAndContains() {
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        assertTrue(tree.insert(QuadTree.Point(1.0, 1.0)))
        assertTrue(tree.contains(QuadTree.Point(1.0, 1.0)))
        assertFalse(tree.contains(QuadTree.Point(99.0, 99.0)))
    }

    @Test
    fun testSize() {
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        assertEquals(0, tree.size)
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(2.0, 2.0))
        tree.insert(QuadTree.Point(3.0, 3.0))
        assertEquals(3, tree.size)
    }

    @Test
    fun testInsertOutOfBounds() {
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        assertFalse(tree.insert(QuadTree.Point(50.0, 50.0)))
        assertEquals(0, tree.size)
    }

    @Test
    fun testQuery() {
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
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
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        tree.insert(QuadTree.Point(1.0, 1.0))
        val results = tree.query(QuadTree.Rectangle(8.0, 8.0, 1.0, 1.0))
        assertTrue(results.isEmpty())
    }

    @Test
    fun testAllPoints() {
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(2.0, 2.0))
        tree.insert(QuadTree.Point(3.0, 3.0))
        tree.insert(QuadTree.Point(4.0, 4.0))
        tree.insert(QuadTree.Point(5.0, 5.0))
        assertEquals(5, tree.allPoints().size)
    }

    @Test
    fun testSubdivision() {
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary, nodeCapacity = 2)
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
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary, nodeCapacity = 2)
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
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        assertTrue(tree.insert(QuadTree.Point(10.0, 10.0)))
    }

    @Test
    fun testQueryFullRange() {
        val boundary = QuadTree.Rectangle(0.0, 0.0, 10.0, 10.0)
        val tree = QuadTree(boundary)
        tree.insert(QuadTree.Point(1.0, 1.0))
        tree.insert(QuadTree.Point(5.0, 5.0))
        tree.insert(QuadTree.Point(9.0, 9.0))
        val results = tree.query(boundary)
        assertEquals(3, results.size)
    }
}
