package br.com.leandroluce.algoritmos.algorithms.sorting

import kotlin.test.Test
import kotlin.test.assertEquals

class BubbleSortTest {

    @Test
    fun `bubbleSort should sort a list of integers`() {
        val list = mutableListOf(5, 1, 4, 2, 8)
        bubbleSort(list)
        assertEquals(listOf(1, 2, 4, 5, 8), list)
    }

    @Test
    fun `bubbleSort should handle an empty list`() {
        val list = mutableListOf<Int>()
        bubbleSort(list)
        assertEquals(emptyList(), list)
    }

    @Test
    fun `bubbleSort should handle a list with one element`() {
        val list = mutableListOf(1)
        bubbleSort(list)
        assertEquals(listOf(1), list)
    }

    @Test
    fun `bubbleSort should handle a list that is already sorted`() {
        val list = mutableListOf(1, 2, 3, 4, 5)
        bubbleSort(list)
        assertEquals(listOf(1, 2, 3, 4, 5), list)
    }
}
