package br.uem.din.algorithms.dp

import kotlin.test.Test
import kotlin.test.assertEquals

class DPAlgorithmsTest {

    @Test
    fun testLCS() {
        assertEquals(3, longestCommonSubsequence("abcde", "ace"))
        assertEquals(0, longestCommonSubsequence("abc", "def"))
    }

    @Test
    fun testKnapsack() {
        val w = intArrayOf(10, 20, 30)
        val v = intArrayOf(60, 100, 120)
        assertEquals(220, knapsack01(w, v, 50))
    }

    @Test
    fun testEditDistance() {
        assertEquals(3, editDistance("kitten", "sitting"))
        assertEquals(5, editDistance("intention", "execution"))
    }

    @Test
    fun testLIS() {
        val nums = intArrayOf(10, 9, 2, 5, 3, 7, 101, 18)
        assertEquals(4, longestIncreasingSubsequence(nums))
    }

    @Test
    fun testFibonacci() {
        assertEquals(55, fibonacciDP(10))
    }

    @Test
    fun testCoinChange() {
        assertEquals(3, coinChange(intArrayOf(1, 2, 5), 11))
        assertEquals(-1, coinChange(intArrayOf(2), 3))
    }
}
