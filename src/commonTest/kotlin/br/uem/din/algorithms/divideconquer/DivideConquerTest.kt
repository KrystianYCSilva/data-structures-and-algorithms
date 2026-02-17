package br.uem.din.algorithms.divideconquer

import kotlin.test.Test
import kotlin.test.assertEquals

class DivideConquerTest {

    @Test
    fun testMaxSubarray() {
        val nums = intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)
        assertEquals(6, maxSubarray(nums)) // [4, -1, 2, 1]
    }

    @Test
    fun testKaratsuba() {
        assertEquals(7006652, karatsuba(1234, 5678))
    }

    @Test
    fun testQuickSelect() {
        val list = mutableListOf(3, 2, 1, 5, 6, 4)
        assertEquals(2, quickSelect(list, 2)) // 2nd smallest is 2
        assertEquals(5, quickSelect(list, 5)) // 5th smallest is 5
    }
}
