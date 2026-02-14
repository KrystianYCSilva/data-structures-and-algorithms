package br.com.leandroluce.algoritmos.datastructures.unionfind

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UnionFindTest {

    @Test
    fun `union and find operations`() {
        val uf = UnionFind(10)
        assertEquals(10, uf.numberOfSets)

        uf.union(0, 1)
        uf.union(2, 3)
        uf.union(1, 2) // Now 0, 1, 2, 3 are connected

        assertEquals(uf.find(0), uf.find(3))
        assertTrue(uf.connected(0, 3))
        assertFalse(uf.connected(0, 4))
        assertEquals(7, uf.numberOfSets)
    }

    @Test
    fun `path compression works`() {
        val uf = UnionFind(5)
        uf.union(0, 1)
        uf.union(1, 2)
        uf.union(2, 3)
        uf.union(3, 4)

        val root = uf.find(0)
        assertEquals(root, uf.find(4))
    }
}
