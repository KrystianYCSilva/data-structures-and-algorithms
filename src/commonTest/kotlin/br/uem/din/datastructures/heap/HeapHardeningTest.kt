package br.uem.din.datastructures.heap

import kotlin.random.Random
import kotlin.test.*

class HeapHardeningTest {

    @Test
    fun testComparableHeapRandomizedAgainstOracle() {
        repeat(6) { seed ->
            val random = Random(seed)
            val heap = ComparableHeapImpl<Int>()
            val model = mutableListOf<Int>()

            repeat(900) { step ->
                when (random.nextInt(100)) {
                    in 0..54 -> {
                        val value = random.nextInt(-4_000, 4_001)
                        heap.insert(value)
                        model.add(value)
                    }

                    in 55..79 -> {
                        val expected = model.minOrNull()
                        val actual = heap.remove()
                        assertEquals(expected, actual, "remove mismatch seed=$seed step=$step")
                        if (expected != null) {
                            model.remove(expected)
                        }
                    }

                    in 80..89 -> {
                        assertEquals(model.minOrNull(), heap.peek(), "peek mismatch seed=$seed step=$step")
                    }

                    else -> {
                        val candidate = random.nextInt(-4_000, 4_001)
                        assertEquals(model.contains(candidate), heap.contains(candidate), "contains mismatch seed=$seed step=$step")
                    }
                }

                assertEquals(model.size, heap.size, "size mismatch seed=$seed step=$step")
                assertEquals(model.isEmpty(), heap.isEmpty(), "isEmpty mismatch seed=$seed step=$step")
                assertEquals(model.minOrNull(), heap.peek(), "head mismatch seed=$seed step=$step")
            }
        }
    }

    @Test
    fun testComparatorHeapRandomizedMaxOrderAgainstOracle() {
        repeat(6) { seed ->
            val random = Random(seed + 100)
            val heap = ComparatorHeapImpl(compareByDescending<Int> { it })
            val model = mutableListOf<Int>()

            repeat(900) { step ->
                when (random.nextInt(100)) {
                    in 0..54 -> {
                        val value = random.nextInt(-4_000, 4_001)
                        heap.insert(value)
                        model.add(value)
                    }

                    in 55..79 -> {
                        val expected = model.maxOrNull()
                        val actual = heap.remove()
                        assertEquals(expected, actual, "remove mismatch seed=$seed step=$step")
                        if (expected != null) {
                            model.remove(expected)
                        }
                    }

                    in 80..89 -> {
                        assertEquals(model.maxOrNull(), heap.peek(), "peek mismatch seed=$seed step=$step")
                    }

                    else -> {
                        val candidate = random.nextInt(-4_000, 4_001)
                        assertEquals(model.contains(candidate), heap.contains(candidate), "contains mismatch seed=$seed step=$step")
                    }
                }

                assertEquals(model.size, heap.size, "size mismatch seed=$seed step=$step")
                assertEquals(model.isEmpty(), heap.isEmpty(), "isEmpty mismatch seed=$seed step=$step")
                assertEquals(model.maxOrNull(), heap.peek(), "head mismatch seed=$seed step=$step")
            }
        }
    }

    @Test
    fun testComparatorHeapHandlesNullableElementsWithCustomComparator() {
        val comparator = Comparator<Int?> { a, b ->
            when {
                a == b -> 0
                a == null -> -1
                b == null -> 1
                else -> a.compareTo(b)
            }
        }
        val heap = ComparatorHeapImpl(comparator)

        heap.insert(3)
        heap.insert(null)
        heap.insert(1)

        assertNull(heap.remove())
        assertEquals(1, heap.remove())
        assertEquals(3, heap.remove())
        assertNull(heap.remove())
    }

    @Test
    fun testBinaryHeapBoundaryIndicesAndIteratorTermination() {
        val comparableHeap = ComparableHeapImpl<Int>()
        comparableHeap.insert(2)
        comparableHeap.insert(1)

        assertFailsWith<IndexOutOfBoundsException> { comparableHeap.remove(-1) }
        assertNull(comparableHeap.remove(99))

        val comparableIterator = comparableHeap.iterator()
        while (comparableIterator.hasNext()) {
            comparableIterator.next()
        }
        assertFalse(comparableIterator.hasNext())
        assertFailsWith<NoSuchElementException> { comparableIterator.next() }

        val comparatorHeap = ComparatorHeapImpl(compareBy<Int> { it })
        comparatorHeap.insert(2)
        comparatorHeap.insert(1)

        assertFailsWith<IndexOutOfBoundsException> { comparatorHeap.remove(-1) }
        assertNull(comparatorHeap.remove(99))

        val comparatorIterator = comparatorHeap.iterator()
        while (comparatorIterator.hasNext()) {
            comparatorIterator.next()
        }
        assertFalse(comparatorIterator.hasNext())
        assertFailsWith<NoSuchElementException> { comparatorIterator.next() }
    }

    @Test
    fun testHeapInterfaceAliasMutatesInPlaceAndSnapshotRemainsStable() {
        val heap = ComparableHeapImpl<Int>()
        val mutableAlias: MutableHeap<Int> = heap

        mutableAlias.insert(5)
        mutableAlias.insert(1)
        heap.insert(3)

        val readOnlyView: Heap<Int> = heap
        val snapshot = readOnlyView.toList()

        assertEquals(1, mutableAlias.remove())
        mutableAlias.insert(0)

        assertEquals(3, readOnlyView.size)
        assertEquals(0, readOnlyView.peek())
        assertEquals(3, snapshot.size)
        assertTrue(snapshot.containsAll(listOf(5, 1, 3)))
    }

    @Test
    fun testBinomialHeapRandomizedAgainstOracle() {
        repeat(5) { seed ->
            val random = Random(seed + 200)
            val heap = BinomialHeap<Int>()
            val model = mutableListOf<Int>()

            repeat(700) { step ->
                when (random.nextInt(100)) {
                    in 0..59 -> {
                        val value = random.nextInt(-2_500, 2_501)
                        heap.insert(value)
                        model.add(value)
                    }

                    in 60..84 -> {
                        val expected = model.minOrNull()
                        val actual = heap.extractMin()
                        assertEquals(expected, actual, "extractMin mismatch seed=$seed step=$step")
                        if (expected != null) {
                            model.remove(expected)
                        }
                    }

                    in 85..94 -> {
                        assertEquals(model.minOrNull(), heap.peek(), "peek mismatch seed=$seed step=$step")
                    }

                    else -> {
                        val candidate = random.nextInt(-2_500, 2_501)
                        assertEquals(model.contains(candidate), heap.contains(candidate), "contains mismatch seed=$seed step=$step")
                    }
                }

                assertEquals(model.size, heap.size, "size mismatch seed=$seed step=$step")
                assertEquals(model.isEmpty(), heap.isEmpty(), "isEmpty mismatch seed=$seed step=$step")
            }
        }
    }

    @Test
    fun testFibonacciHeapRandomizedAgainstOracle() {
        repeat(5) { seed ->
            val random = Random(seed + 300)
            val heap = FibonacciHeap<Int>()
            val model = mutableListOf<Int>()

            repeat(700) { step ->
                when (random.nextInt(100)) {
                    in 0..59 -> {
                        val value = random.nextInt(-2_500, 2_501)
                        heap.insert(value)
                        model.add(value)
                    }

                    in 60..84 -> {
                        val expected = model.minOrNull()
                        val actual = heap.extractMin()
                        assertEquals(expected, actual, "extractMin mismatch seed=$seed step=$step")
                        if (expected != null) {
                            model.remove(expected)
                        }
                    }

                    in 85..94 -> {
                        assertEquals(model.minOrNull(), heap.peek(), "peek mismatch seed=$seed step=$step")
                    }

                    else -> {
                        val candidate = random.nextInt(-2_500, 2_501)
                        assertEquals(model.contains(candidate), heap.contains(candidate), "contains mismatch seed=$seed step=$step")
                    }
                }

                assertEquals(model.size, heap.size, "size mismatch seed=$seed step=$step")
                assertEquals(model.isEmpty(), heap.isEmpty(), "isEmpty mismatch seed=$seed step=$step")
            }
        }
    }

    @Test
    fun testMergePreservesGlobalMinOrderForBinomialAndFibonacci() {
        val valuesA = listOf(7, 1, 9, 2, 6)
        val valuesB = listOf(8, 3, 5, 4, 0)
        val expected = (valuesA + valuesB).sorted()

        val binomialA = BinomialHeap<Int>()
        val binomialB = BinomialHeap<Int>()
        valuesA.forEach { binomialA.insert(it) }
        valuesB.forEach { binomialB.insert(it) }
        binomialA.merge(binomialB)

        val binomialExtracted = mutableListOf<Int>()
        while (!binomialA.isEmpty()) {
            binomialExtracted.add(binomialA.extractMin()!!)
        }
        assertEquals(expected, binomialExtracted)

        val fibonacciA = FibonacciHeap<Int>()
        val fibonacciB = FibonacciHeap<Int>()
        valuesA.forEach { fibonacciA.insert(it) }
        valuesB.forEach { fibonacciB.insert(it) }
        fibonacciA.merge(fibonacciB)

        val fibonacciExtracted = mutableListOf<Int>()
        while (!fibonacciA.isEmpty()) {
            fibonacciExtracted.add(fibonacciA.extractMin()!!)
        }
        assertEquals(expected, fibonacciExtracted)
    }

    @Test
    fun testFibonacciDecreaseKeyRejectsGreaterKey() {
        val heap = FibonacciHeap<Int>()
        val node = heap.insert(10)

        assertFailsWith<IllegalArgumentException> {
            heap.decreaseKey(node, 11)
        }
    }

    @Test
    fun testComparatorCallCountStaysWithinNLogNEnvelope() {
        val n = 2_048
        val comparator = CountingComparator()
        val heap = ComparatorHeapImpl(comparator)

        for (value in n downTo 1) {
            heap.insert(value)
        }

        for (expected in 1..n) {
            assertEquals(expected, heap.remove())
        }

        val logN = ceilLog2(n + 1).toLong()
        val upperBound = (14L * n.toLong() * logN) + (64L * n.toLong())

        assertTrue(
            comparator.comparisons <= upperBound,
            "Comparator calls should remain near O(n log n). comparisons=${comparator.comparisons}, upperBound=$upperBound"
        )
    }

    private class CountingComparator : Comparator<Int> {
        var comparisons: Long = 0

        override fun compare(a: Int, b: Int): Int {
            comparisons++
            return a.compareTo(b)
        }
    }

    private fun ceilLog2(n: Int): Int {
        require(n > 0)
        var power = 0
        var value = 1
        while (value < n) {
            value = value shl 1
            power++
        }
        return power
    }
}
