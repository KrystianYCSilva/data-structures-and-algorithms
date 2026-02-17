package br.uem.din.algorithms.geometry

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class GeometryTest {

    @Test
    fun testConvexHull() {
        val points = listOf(
            Point2D(0.0, 3.0),
            Point2D(2.0, 2.0),
            Point2D(1.0, 1.0),
            Point2D(2.0, 1.0),
            Point2D(3.0, 0.0),
            Point2D(0.0, 0.0),
            Point2D(3.0, 3.0)
        )
        val hull = convexHull(points)
        // Hull esperado: (0,0), (3,0), (3,3), (0,3) em ordem anti-horária
        assertEquals(4, hull.size)
        // Verifica se contém os pontos extremos
        assertTrue(hull.contains(Point2D(0.0, 0.0)))
        assertTrue(hull.contains(Point2D(3.0, 0.0)))
        assertTrue(hull.contains(Point2D(3.0, 3.0)))
        assertTrue(hull.contains(Point2D(0.0, 3.0)))
    }

    @Test
    fun testIntersection() {
        val p1 = Point2D(1.0, 1.0)
        val q1 = Point2D(10.0, 1.0)
        val p2 = Point2D(1.0, 2.0)
        val q2 = Point2D(10.0, 2.0)
        assertFalse(doIntersect(p1, q1, p2, q2))

        val p3 = Point2D(10.0, 0.0)
        val q3 = Point2D(0.0, 10.0)
        val p4 = Point2D(0.0, 0.0)
        val q4 = Point2D(10.0, 10.0)
        assertTrue(doIntersect(p3, q3, p4, q4))
    }
}
