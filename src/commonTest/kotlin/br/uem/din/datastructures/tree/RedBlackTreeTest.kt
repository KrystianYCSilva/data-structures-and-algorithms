package br.uem.din.datastructures.tree

import kotlin.test.*

class RedBlackTreeTest {

    @Test
    fun testInsert() {
        val rbt = RedBlackTree<Int>()
        rbt.insert(10)
        rbt.insert(20)
        rbt.insert(5)

        assertEquals(3, rbt.size)
        assertTrue(rbt.contains(10))
        assertTrue(rbt.contains(20))
        assertTrue(rbt.contains(5))
        assertFalse(rbt.contains(15))
    }

    @Test
    fun testBalance() {
        val rbt = RedBlackTree<Int>()
        // Inserting sorted elements would degenerate a BST to O(N) height (linked list).
        // RBT should remain O(log N).
        val n = 100
        for (i in 0 until n) {
            rbt.insert(i)
        }

        assertEquals(n, rbt.size)
        
        // Max height for RBT is 2 * log2(n+1)
        // log2(101) ~ 6.6 -> max height ~ 13.
        // A degenerate tree would be 100.
        assertTrue(rbt.height() < 20, "Tree height ${rbt.height()} is too large, balancing might be broken")
    }
}
