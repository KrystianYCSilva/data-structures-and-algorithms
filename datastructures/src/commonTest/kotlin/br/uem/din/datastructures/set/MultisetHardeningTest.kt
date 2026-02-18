package br.uem.din.datastructures.set

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.*

class MultisetHardeningTest {

    @Test
    fun testEmptyStateSingleElementAndLargeBoundary() {
        val multiset = Multiset<Int>()

        assertTrue(multiset.isEmpty())
        assertEquals(0, multiset.size)
        assertEquals(0, multiset.distinctCount)
        assertEquals(0, multiset.count(10))

        multiset.add(42)
        assertFalse(multiset.isEmpty())
        assertEquals(1, multiset.size)
        assertEquals(1, multiset.distinctCount)
        assertTrue(multiset.contains(42))
        assertEquals(1, multiset.count(42))

        assertTrue(multiset.remove(42))
        assertTrue(multiset.isEmpty())

        val total = 4_000
        repeat(total) { index ->
            multiset.add(index % 25)
        }

        assertEquals(total, multiset.size)
        assertEquals(25, multiset.distinctCount)
        for (value in 0 until 25) {
            assertEquals(total / 25, multiset.count(value))
        }
    }

    @Test
    fun testMutableAliasAndReadOnlyViewSnapshotAcrossMutations() {
        val mutable = Multiset<String>()
        val alias: MutableMultiset<String> = mutable

        mutable.add("alpha", 2)
        alias.add("beta")

        val readOnly: ImmutableMultiset<String> = mutable.asReadOnly()
        val snapshot = multiplicities(readOnly)

        alias.remove("alpha")
        mutable.add("gamma")

        assertEquals(mapOf("alpha" to 2, "beta" to 1), snapshot)
        assertEquals(3, readOnly.size)
        assertEquals(1, readOnly.count("alpha"))
        assertEquals(1, readOnly.count("beta"))
        assertEquals(1, readOnly.count("gamma"))
    }

    @Test
    fun testIteratorTerminationAndPostExhaustionException() {
        val multiset = Multiset<Int>()
        multiset.add(1, 2)
        multiset.add(2, 3)

        val iterator = multiset.iterator()
        var count = 0
        while (iterator.hasNext()) {
            iterator.next()
            count++
        }

        assertEquals(multiset.size, count)
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testNullableElementsAndCounts() {
        val multiset = Multiset<String?>()

        multiset.add(null, 2)
        multiset.add("x")

        assertEquals(3, multiset.size)
        assertEquals(2, multiset.count(null))
        assertTrue(multiset.contains(null))

        assertTrue(multiset.remove(null))
        assertEquals(1, multiset.count(null))
        assertTrue(multiset.remove(null))
        assertFalse(multiset.contains(null))
        assertEquals(1, multiset.size)
    }

    @Test
    fun testRandomizedOperationsAgainstReferenceModel() {
        repeat(8) { seed ->
            val random = Random(seed)
            val multiset = Multiset<Int>()
            val model = mutableMapOf<Int, Int>()

            repeat(1_000) { step ->
                when (random.nextInt(100)) {
                    in 0..49 -> {
                        val value = random.nextInt(0, 60)
                        val occurrences = random.nextInt(1, 5)
                        multiset.add(value, occurrences)
                        model[value] = (model[value] ?: 0) + occurrences
                    }

                    in 50..74 -> {
                        val value = random.nextInt(0, 60)
                        val occurrences = random.nextInt(1, 5)
                        val existed = model.containsKey(value)
                        assertEquals(existed, multiset.remove(value, occurrences), "remove existence mismatch seed=$seed step=$step")

                        if (existed) {
                            val remaining = (model[value] ?: 0) - occurrences
                            if (remaining <= 0) {
                                model.remove(value)
                            } else {
                                model[value] = remaining
                            }
                        }
                    }

                    in 75..84 -> {
                        val value = random.nextInt(0, 60)
                        assertEquals(model[value] ?: 0, multiset.count(value), "count mismatch seed=$seed step=$step")
                    }

                    in 85..94 -> {
                        val value = random.nextInt(0, 60)
                        assertEquals(model.containsKey(value), multiset.contains(value), "contains mismatch seed=$seed step=$step")
                    }

                    else -> {
                        multiset.clear()
                        model.clear()
                    }
                }

                assertMultisetMatchesModel(multiset, model, seed, step)
            }
        }
    }

    @Test
    fun testHashAndEqualsCallsStayNearLinearEnvelope() {
        val n = 1_000
        val counter = Counter()
        val keys = (0 until n).map { CountingKey(it, counter) }
        val multiset = Multiset<CountingKey>()

        for (key in keys) {
            multiset.add(key)
        }

        for (key in keys) {
            assertEquals(1, multiset.count(key))
        }

        for (key in keys) {
            assertTrue(multiset.remove(key))
        }

        assertTrue(multiset.isEmpty())
        assertTrue(counter.hashCalls <= 320L * n, "hash calls too high: ${counter.hashCalls}")
        assertTrue(counter.equalsCalls <= 360L * n, "equals calls too high: ${counter.equalsCalls}")
    }

    private fun assertMultisetMatchesModel(
        multiset: Multiset<Int>,
        model: Map<Int, Int>,
        seed: Int,
        step: Int
    ) {
        assertEquals(model.values.sum(), multiset.size, "size mismatch seed=$seed step=$step")
        assertEquals(model.size, multiset.distinctCount, "distinctCount mismatch seed=$seed step=$step")
        assertEquals(model.isEmpty(), multiset.isEmpty(), "isEmpty mismatch seed=$seed step=$step")
        assertEquals(model.keys, multiset.distinctElements(), "distinctElements mismatch seed=$seed step=$step")

        for ((element, count) in model) {
            assertEquals(count, multiset.count(element), "count mismatch seed=$seed step=$step element=$element")
        }

        assertEquals(model, multiplicities(multiset), "iterator multiplicities mismatch seed=$seed step=$step")
    }

    private fun <T> multiplicities(values: Iterable<T>): Map<T, Int> = values.groupingBy { it }.eachCount()

    private data class Counter(
        var hashCalls: Long = 0,
        var equalsCalls: Long = 0
    )

    private class CountingKey(
        private val value: Int,
        private val counter: Counter
    ) {
        override fun hashCode(): Int {
            counter.hashCalls++
            return value
        }

        override fun equals(other: Any?): Boolean {
            counter.equalsCalls++
            return other is CountingKey && value == other.value
        }
    }
}
