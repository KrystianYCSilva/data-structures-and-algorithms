package br.uem.din.algorithms.sorting

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SortingTest {

    private fun unsorted() = mutableListOf(64, 34, 25, 12, 22, 11, 90)
    private fun sorted() = listOf(11, 12, 22, 25, 34, 64, 90)

    @Test
    fun testSelectionSort() {
        val list = unsorted()
        selectionSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testShellSort() {
        val list = unsorted()
        shellSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testMergeSort() {
        val list = unsorted()
        mergeSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testQuickSort() {
        val list = unsorted()
        quickSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testHeapSort() {
        val list = unsorted()
        heapSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testCountingSort() {
        val list = mutableListOf(4, 2, 2, 8, 3, 3, 1)
        countingSort(list)
        assertEquals(listOf(1, 2, 2, 3, 3, 4, 8), list)
    }

    @Test
    fun testRadixSort() {
        val list = mutableListOf(170, 45, 75, 90, 802, 24, 2, 66)
        radixSort(list)
        assertEquals(listOf(2, 24, 45, 66, 75, 90, 170, 802), list)
    }

    @Test
    fun testBucketSort() {
        val list = mutableListOf(0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434)
        bucketSort(list)
        
        // Verifica se está ordenado
        for (i in 0 until list.size - 1) {
            assertTrue(list[i] <= list[i+1])
        }
    }

    @Test
    fun testBubbleSort() {
        val list = unsorted()
        bubbleSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testBubbleSortEmpty() {
        val list = mutableListOf<Int>()
        bubbleSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testBubbleSortSingleElement() {
        val list = mutableListOf(1)
        bubbleSort(list)
        assertEquals(listOf(1), list)
    }

    @Test
    fun testBubbleSortAlreadySorted() {
        val list = mutableListOf(1, 2, 3, 4, 5)
        bubbleSort(list)
        assertEquals(listOf(1, 2, 3, 4, 5), list)
    }

    @Test
    fun testInsertionSort() {
        val list = unsorted()
        insertionSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testInsertionSortEmpty() {
        val list = mutableListOf<Int>()
        insertionSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testInsertionSortSingleElement() {
        val list = mutableListOf(42)
        insertionSort(list)
        assertEquals(listOf(42), list)
    }

    @Test
    fun testInsertionSortReversed() {
        val list = mutableListOf(5, 4, 3, 2, 1)
        insertionSort(list)
        assertEquals(listOf(1, 2, 3, 4, 5), list)
    }

    @Test
    fun testInsertionSortDuplicates() {
        val list = mutableListOf(3, 1, 2, 1, 3)
        insertionSort(list)
        assertEquals(listOf(1, 1, 2, 3, 3), list)
    }
}
