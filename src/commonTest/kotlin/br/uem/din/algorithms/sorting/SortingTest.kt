package br.uem.din.algorithms.sorting

import kotlin.random.Random
import kotlin.test.*

class SortingTest {

    private fun unsorted() = mutableListOf(64, 34, 25, 12, 22, 11, 90)
    private fun sorted() = listOf(11, 12, 22, 25, 34, 64, 90)

    private data class Keyed(val key: Int, val order: Int) : Comparable<Keyed> {
        override fun compareTo(other: Keyed): Int = key.compareTo(other.key)
    }

    private fun stabilityInput(): MutableList<Keyed> = mutableListOf(
        Keyed(3, 0), Keyed(1, 1), Keyed(2, 2), Keyed(1, 3),
        Keyed(3, 4), Keyed(2, 5), Keyed(1, 6)
    )

    private fun assertStable(sorted: List<Keyed>) {
        for (i in 0 until sorted.size - 1) {
            assertTrue(sorted[i].key <= sorted[i + 1].key, "Not sorted at index $i")
            if (sorted[i].key == sorted[i + 1].key) {
                assertTrue(sorted[i].order < sorted[i + 1].order,
                    "Stability violated: ${sorted[i]} before ${sorted[i + 1]}")
            }
        }
    }

    private fun randomList(size: Int, seed: Int): MutableList<Int> {
        val random = Random(seed)
        return MutableList(size) { random.nextInt(-10_000, 10_001) }
    }

    // ==================== BubbleSort ====================

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
    fun testBubbleSortReversed() {
        val list = mutableListOf(5, 4, 3, 2, 1)
        bubbleSort(list)
        assertEquals(listOf(1, 2, 3, 4, 5), list)
    }

    @Test
    fun testBubbleSortDuplicates() {
        val list = mutableListOf(3, 1, 2, 1, 3)
        bubbleSort(list)
        assertEquals(listOf(1, 1, 2, 3, 3), list)
    }

    @Test
    fun testBubbleSortAllSame() {
        val list = MutableList(10) { 7 }
        bubbleSort(list)
        assertEquals(List(10) { 7 }, list)
    }

    @Test
    fun testBubbleSortStability() {
        val list = stabilityInput()
        bubbleSort(list)
        assertStable(list)
    }

    // ==================== InsertionSort ====================

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

    @Test
    fun testInsertionSortStability() {
        val list = stabilityInput()
        insertionSort(list)
        assertStable(list)
    }

    // ==================== SelectionSort ====================

    @Test
    fun testSelectionSort() {
        val list = unsorted()
        selectionSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testSelectionSortEmpty() {
        val list = mutableListOf<Int>()
        selectionSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testSelectionSortSingle() {
        val list = mutableListOf(99)
        selectionSort(list)
        assertEquals(listOf(99), list)
    }

    @Test
    fun testSelectionSortReversed() {
        val list = mutableListOf(5, 4, 3, 2, 1)
        selectionSort(list)
        assertEquals(listOf(1, 2, 3, 4, 5), list)
    }

    @Test
    fun testSelectionSortAllSame() {
        val list = MutableList(8) { 5 }
        selectionSort(list)
        assertEquals(List(8) { 5 }, list)
    }

    // ==================== ShellSort ====================

    @Test
    fun testShellSort() {
        val list = unsorted()
        shellSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testShellSortEmpty() {
        val list = mutableListOf<Int>()
        shellSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testShellSortSingle() {
        val list = mutableListOf(7)
        shellSort(list)
        assertEquals(listOf(7), list)
    }

    @Test
    fun testShellSortReversed() {
        val list = mutableListOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
        shellSort(list)
        assertEquals((1..10).toList(), list)
    }

    @Test
    fun testShellSortDuplicates() {
        val list = mutableListOf(4, 2, 4, 1, 2, 1)
        shellSort(list)
        assertEquals(listOf(1, 1, 2, 2, 4, 4), list)
    }

    // ==================== MergeSort ====================

    @Test
    fun testMergeSort() {
        val list = unsorted()
        mergeSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testMergeSortEmpty() {
        val list = mutableListOf<Int>()
        mergeSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testMergeSortSingle() {
        val list = mutableListOf(42)
        mergeSort(list)
        assertEquals(listOf(42), list)
    }

    @Test
    fun testMergeSortReversed() {
        val list = mutableListOf(5, 4, 3, 2, 1)
        mergeSort(list)
        assertEquals(listOf(1, 2, 3, 4, 5), list)
    }

    @Test
    fun testMergeSortStability() {
        val list = stabilityInput()
        mergeSort(list)
        assertStable(list)
    }

    // ==================== QuickSort ====================

    @Test
    fun testQuickSort() {
        val list = unsorted()
        quickSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testQuickSortEmpty() {
        val list = mutableListOf<Int>()
        quickSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testQuickSortSingle() {
        val list = mutableListOf(1)
        quickSort(list)
        assertEquals(listOf(1), list)
    }

    @Test
    fun testQuickSortAlreadySorted() {
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
        quickSort(list)
        assertEquals((1..8).toList(), list)
    }

    @Test
    fun testQuickSortReversed() {
        val list = mutableListOf(8, 7, 6, 5, 4, 3, 2, 1)
        quickSort(list)
        assertEquals((1..8).toList(), list)
    }

    @Test
    fun testQuickSortAllSame() {
        val list = MutableList(20) { 3 }
        quickSort(list)
        assertEquals(List(20) { 3 }, list)
    }

    // ==================== HeapSort ====================

    @Test
    fun testHeapSort() {
        val list = unsorted()
        heapSort(list)
        assertEquals(sorted(), list)
    }

    @Test
    fun testHeapSortEmpty() {
        val list = mutableListOf<Int>()
        heapSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testHeapSortSingle() {
        val list = mutableListOf(10)
        heapSort(list)
        assertEquals(listOf(10), list)
    }

    @Test
    fun testHeapSortReversed() {
        val list = mutableListOf(5, 4, 3, 2, 1)
        heapSort(list)
        assertEquals(listOf(1, 2, 3, 4, 5), list)
    }

    @Test
    fun testHeapSortDuplicates() {
        val list = mutableListOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5)
        heapSort(list)
        assertEquals(listOf(1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9), list)
    }

    // ==================== CountingSort ====================

    @Test
    fun testCountingSort() {
        val list = mutableListOf(4, 2, 2, 8, 3, 3, 1)
        countingSort(list)
        assertEquals(listOf(1, 2, 2, 3, 3, 4, 8), list)
    }

    @Test
    fun testCountingSortEmpty() {
        val list = mutableListOf<Int>()
        countingSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testCountingSortSingle() {
        val list = mutableListOf(5)
        countingSort(list)
        assertEquals(listOf(5), list)
    }

    @Test
    fun testCountingSortAllSame() {
        val list = MutableList(10) { 3 }
        countingSort(list)
        assertEquals(List(10) { 3 }, list)
    }

    @Test
    fun testCountingSortZeros() {
        val list = mutableListOf(0, 0, 1, 0, 2)
        countingSort(list)
        assertEquals(listOf(0, 0, 0, 1, 2), list)
    }

    @Test
    fun testCountingSortNegativeThrows() {
        val list = mutableListOf(3, -1, 2)
        assertFailsWith<IllegalArgumentException> { countingSort(list) }
    }

    // ==================== RadixSort ====================

    @Test
    fun testRadixSort() {
        val list = mutableListOf(170, 45, 75, 90, 802, 24, 2, 66)
        radixSort(list)
        assertEquals(listOf(2, 24, 45, 66, 75, 90, 170, 802), list)
    }

    @Test
    fun testRadixSortEmpty() {
        val list = mutableListOf<Int>()
        radixSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testRadixSortSingle() {
        val list = mutableListOf(42)
        radixSort(list)
        assertEquals(listOf(42), list)
    }

    @Test
    fun testRadixSortAllSame() {
        val list = MutableList(5) { 100 }
        radixSort(list)
        assertEquals(List(5) { 100 }, list)
    }

    @Test
    fun testRadixSortZeros() {
        val list = mutableListOf(0, 0, 0)
        radixSort(list)
        assertEquals(listOf(0, 0, 0), list)
    }

    // ==================== BucketSort ====================

    @Test
    fun testBucketSort() {
        val list = mutableListOf(0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434)
        bucketSort(list)
        assertEquals(
            listOf(0.1234, 0.3434, 0.565, 0.656, 0.665, 0.897),
            list
        )
    }

    @Test
    fun testBucketSortEmpty() {
        val list = mutableListOf<Double>()
        bucketSort(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun testBucketSortSingle() {
        val list = mutableListOf(0.5)
        bucketSort(list)
        assertEquals(listOf(0.5), list)
    }

    @Test
    fun testBucketSortAllSame() {
        val list = MutableList(5) { 0.5 }
        bucketSort(list)
        assertEquals(List(5) { 0.5 }, list)
    }

    // ==================== Randomized (all comparison-based) ====================

    @Test
    fun testAllComparisonSortsRandomized() {
        val sorts: List<Pair<String, (MutableList<Int>) -> Unit>> = listOf(
            "bubbleSort" to ::bubbleSort,
            "insertionSort" to ::insertionSort,
            "selectionSort" to ::selectionSort,
            "shellSort" to ::shellSort,
            "mergeSort" to ::mergeSort,
            "quickSort" to ::quickSort,
            "heapSort" to ::heapSort,
        )

        for ((name, sort) in sorts) {
            val input = randomList(200, 9001)
            val expected = input.sorted()
            sort(input)
            assertEquals(expected, input, "$name failed on random input")
        }
    }

    @Test
    fun testCountingRadixRandomized() {
        val random = Random(9002)
        val input = MutableList(200) { random.nextInt(0, 10_000) }
        val expected = input.sorted()

        val countingInput = input.toMutableList()
        countingSort(countingInput)
        assertEquals(expected, countingInput, "countingSort failed on random input")

        val radixInput = input.toMutableList()
        radixSort(radixInput)
        assertEquals(expected, radixInput, "radixSort failed on random input")
    }

    @Test
    fun testBucketSortRandomized() {
        val random = Random(9003)
        val input = MutableList(100) { random.nextDouble(0.0, 1.0) }
        val expected = input.sorted()
        bucketSort(input)
        assertEquals(expected, input, "bucketSort failed on random input")
    }

    // ==================== Adversarial inputs ====================

    @Test
    fun testQuickSortAdversarialSorted() {
        val list = (1..200).toMutableList()
        quickSort(list)
        assertEquals((1..200).toList(), list)
    }

    @Test
    fun testQuickSortAdversarialReversed() {
        val list = (200 downTo 1).toMutableList()
        quickSort(list)
        assertEquals((1..200).toList(), list)
    }

    @Test
    fun testQuickSortAdversarialAllSame() {
        val list = MutableList(200) { 42 }
        quickSort(list)
        assertEquals(List(200) { 42 }, list)
    }

    // ==================== Negative values (comparison-based) ====================

    @Test
    fun testComparisonSortsWithNegatives() {
        val sorts: List<Pair<String, (MutableList<Int>) -> Unit>> = listOf(
            "bubbleSort" to ::bubbleSort,
            "insertionSort" to ::insertionSort,
            "selectionSort" to ::selectionSort,
            "shellSort" to ::shellSort,
            "mergeSort" to ::mergeSort,
            "quickSort" to ::quickSort,
            "heapSort" to ::heapSort,
        )

        for ((name, sort) in sorts) {
            val input = mutableListOf(-5, 3, -1, 0, -10, 7, 2)
            val expected = listOf(-10, -5, -1, 0, 2, 3, 7)
            sort(input)
            assertEquals(expected, input, "$name failed with negatives")
        }
    }

    // ==================== Two-element boundary ====================

    @Test
    fun testAllSortsTwoElements() {
        val sorts: List<Pair<String, (MutableList<Int>) -> Unit>> = listOf(
            "bubbleSort" to ::bubbleSort,
            "insertionSort" to ::insertionSort,
            "selectionSort" to ::selectionSort,
            "shellSort" to ::shellSort,
            "mergeSort" to ::mergeSort,
            "quickSort" to ::quickSort,
            "heapSort" to ::heapSort,
        )

        for ((name, sort) in sorts) {
            val a = mutableListOf(2, 1)
            sort(a)
            assertEquals(listOf(1, 2), a, "$name failed on [2,1]")

            val b = mutableListOf(1, 2)
            sort(b)
            assertEquals(listOf(1, 2), b, "$name failed on [1,2]")
        }
    }

    // ==================== String type (generics) ====================

    @Test
    fun testComparisonSortsWithStrings() {
        val sorts: List<Pair<String, (MutableList<String>) -> Unit>> = listOf(
            "bubbleSort" to ::bubbleSort,
            "insertionSort" to ::insertionSort,
            "selectionSort" to ::selectionSort,
            "shellSort" to ::shellSort,
            "mergeSort" to ::mergeSort,
            "quickSort" to ::quickSort,
            "heapSort" to ::heapSort,
        )

        for ((name, sort) in sorts) {
            val input = mutableListOf("banana", "apple", "cherry", "date")
            sort(input)
            assertEquals(listOf("apple", "banana", "cherry", "date"), input, "$name failed on strings")
        }
    }
}
