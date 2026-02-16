package br.uem.din.datastructures.unionfind

import kotlin.test.*

class UnionFindTest {

    @Test
    fun testInitialState() {
        val uf = UnionFind(5)
        assertEquals(5, uf.size)
        assertEquals(5, uf.numberOfSets)
    }

    @Test
    fun testFindInitial() {
        val uf = UnionFind(5)
        for (i in 0 until 5) {
            assertEquals(i, uf.find(i))
        }
    }

    @Test
    fun testFindOutOfBounds() {
        val uf = UnionFind(3)
        assertFailsWith<IllegalArgumentException> { uf.find(-1) }
        assertFailsWith<IllegalArgumentException> { uf.find(3) }
    }

    @Test
    fun testUnion() {
        val uf = UnionFind(5)
        uf.union(0, 1)
        assertTrue(uf.connected(0, 1))
        assertEquals(4, uf.numberOfSets)
    }

    @Test
    fun testUnionTransitive() {
        val uf = UnionFind(5)
        uf.union(0, 1)
        uf.union(1, 2)
        assertTrue(uf.connected(0, 2))
        assertEquals(3, uf.numberOfSets)
    }

    @Test
    fun testUnionSameSet() {
        val uf = UnionFind(5)
        uf.union(0, 1)
        uf.union(0, 1)
        assertEquals(4, uf.numberOfSets)
    }

    @Test
    fun testConnectedInitially() {
        val uf = UnionFind(5)
        assertTrue(uf.connected(2, 2))
        assertFalse(uf.connected(0, 1))
    }

    @Test
    fun testUnionAll() {
        val uf = UnionFind(4)
        uf.union(0, 1)
        uf.union(2, 3)
        uf.union(0, 2)
        assertEquals(1, uf.numberOfSets)
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                assertTrue(uf.connected(i, j))
            }
        }
    }

    @Test
    fun testPathCompression() {
        val uf = UnionFind(10)
        uf.union(0, 1)
        uf.union(1, 2)
        uf.union(2, 3)
        uf.union(3, 4)
        val root = uf.find(4)
        assertEquals(root, uf.find(0))
        assertEquals(root, uf.find(1))
        assertEquals(root, uf.find(2))
        assertEquals(root, uf.find(3))
    }

    @Test
    fun testMultipleDisjointSets() {
        val uf = UnionFind(6)
        uf.union(0, 1)
        uf.union(2, 3)
        uf.union(4, 5)
        assertEquals(3, uf.numberOfSets)
        assertTrue(uf.connected(0, 1))
        assertTrue(uf.connected(2, 3))
        assertTrue(uf.connected(4, 5))
        assertFalse(uf.connected(0, 2))
        assertFalse(uf.connected(0, 4))
        assertFalse(uf.connected(2, 4))
    }
}
