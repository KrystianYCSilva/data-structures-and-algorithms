package br.uem.din.algorithms.backtracking

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class BacktrackingTest {

    @Test
    fun testNQueens() {
        val solutions = nQueens(4)
        assertEquals(2, solutions.size) // 4-Queens tem 2 soluções
    }

    @Test
    fun testSubsetSum() {
        val set = intArrayOf(3, 34, 4, 12, 5, 2)
        assertTrue(subsetSum(set, 9)) // 4 + 5
        assertFalse(subsetSum(set, 100))
    }

    @Test
    fun testPermutations() {
        val nums = intArrayOf(1, 2, 3)
        val perms = permutations(nums)
        assertEquals(6, perms.size) // 3! = 6
    }
}
