package br.uem.din.datastructures.probabilistic

import br.uem.din.datastructures.asReadOnly
import kotlin.test.*

class BloomFilterTest {

    @Test
    fun testAddAndContains() {
        val bloomFilter = BloomFilter(100, 0.01)

        bloomFilter.add("apple")
        bloomFilter.add("banana")
        bloomFilter.add("orange")

        assertTrue(bloomFilter.contains("apple"))
        assertTrue(bloomFilter.contains("banana"))
        assertTrue(bloomFilter.contains("orange"))

        assertFalse(bloomFilter.contains("grape"))
        assertFalse(bloomFilter.contains("watermelon"))
    }

    @Test
    fun testEmptyFilterContainsNothing() {
        val bf = BloomFilter(100, 0.01)
        assertFalse(bf.contains("anything"))
        assertFalse(bf.contains(""))
    }

    @Test
    fun testNoFalseNegatives() {
        val bf = BloomFilter(1000, 0.01)
        val items = (0 until 200).map { "item_$it" }
        items.forEach { bf.add(it) }
        items.forEach { assertTrue(bf.contains(it), "False negative for $it") }
    }

    @Test
    fun testSizeAndHashFunctions() {
        val bf = BloomFilter(100, 0.01)
        assertTrue(bf.size() > 0)
        assertTrue(bf.countHashFunctions() > 0)
    }

    @Test
    fun testLargerFilterHasMoreBits() {
        val small = BloomFilter(10, 0.01)
        val large = BloomFilter(1000, 0.01)
        assertTrue(large.size() > small.size())
    }

    @Test
    fun testLowerFpProbabilityUsesMoreBits() {
        val relaxed = BloomFilter(100, 0.1)
        val strict = BloomFilter(100, 0.001)
        assertTrue(strict.size() > relaxed.size())
    }

    @Test
    fun testEmptyStringElement() {
        val bf = BloomFilter(100, 0.01)
        bf.add("")
        assertTrue(bf.contains(""))
    }

    @Test
    fun testAddSingleElement() {
        val bf = BloomFilter(100, 0.01)
        bf.add("hello")
        assertTrue(bf.contains("hello"))
    }

    @Test
    fun testContainsNegativeMultiple() {
        val bf = BloomFilter(100, 0.01)
        bf.add("alpha")
        bf.add("beta")
        assertFalse(bf.contains("gamma"))
        assertFalse(bf.contains("delta"))
        assertFalse(bf.contains("epsilon"))
    }

    @Test
    fun testManyInsertionsNoFalseNegatives() {
        val bf = BloomFilter(5000, 0.01)
        val items = (0 until 1000).map { "element_$it" }
        items.forEach { bf.add(it) }
        items.forEach { assertTrue(bf.contains(it), "False negative for $it") }
    }

    @Test
    fun testFalsePositiveRateWithinBound() {
        val n = 500
        val fpRate = 0.05
        val bf = BloomFilter(n, fpRate)
        for (i in 0 until n) {
            bf.add("inserted_$i")
        }
        var falsePositives = 0
        val testCount = 10000
        for (i in 0 until testCount) {
            if (bf.contains("notinserted_$i")) {
                falsePositives++
            }
        }
        val observedRate = falsePositives.toDouble() / testCount
        assertTrue(observedRate < fpRate * 3, "Observed FP rate $observedRate exceeds 3x target $fpRate")
    }

    @Test
    fun testHashFunctionCountIncreasesWithLowerFpRate() {
        val relaxed = BloomFilter(100, 0.1)
        val strict = BloomFilter(100, 0.001)
        assertTrue(strict.countHashFunctions() >= relaxed.countHashFunctions())
    }

    @Test
    fun testAddDuplicateElement() {
        val bf = BloomFilter(100, 0.01)
        bf.add("duplicate")
        bf.add("duplicate")
        assertTrue(bf.contains("duplicate"))
    }

    @Test
    fun testLongStringElement() {
        val bf = BloomFilter(100, 0.01)
        val longString = "a".repeat(10000)
        bf.add(longString)
        assertTrue(bf.contains(longString))
        assertFalse(bf.contains("a".repeat(9999)))
    }

    @Test
    fun testSpecialCharacterElements() {
        val bf = BloomFilter(100, 0.01)
        val specials = listOf("hello world", "foo\tbar", "line\nbreak", "emoji☺", "日本語")
        specials.forEach { bf.add(it) }
        specials.forEach { assertTrue(bf.contains(it), "False negative for '$it'") }
    }

    @Test
    fun testImmutableBloomFilterView() {
        val mutable: MutableBloomFilter = BloomFilter(100, 0.01)
        mutable.add("alpha")
        mutable.add("beta")
        val readOnly: ImmutableBloomFilter = mutable.asReadOnly()
        assertTrue(readOnly.contains("alpha"))
        assertTrue(readOnly.contains("beta"))
        assertFalse(readOnly.contains("gamma"))
        assertEquals(mutable.size(), readOnly.size())
        assertEquals(mutable.countHashFunctions(), readOnly.countHashFunctions())

        mutable.add("gamma")
        assertTrue(readOnly.contains("gamma"))
    }

    @Test
    fun testExpectedInsertions1() {
        val bf = BloomFilter(1, 0.01)
        assertTrue(bf.size() > 0)
        assertTrue(bf.countHashFunctions() > 0)
        bf.add("only")
        assertTrue(bf.contains("only"))
    }

    @Test
    fun testHighFalsePositiveProbability() {
        val bf = BloomFilter(10, 0.5)
        assertTrue(bf.size() > 0)
        assertTrue(bf.countHashFunctions() >= 1)
        bf.add("x")
        assertTrue(bf.contains("x"))
    }

    @Test
    fun testMutableBloomFilterInterface() {
        val bf: MutableBloomFilter = BloomFilter(100, 0.01)
        bf.add("test")
        assertTrue(bf.contains("test"))
        assertTrue(bf.size() > 0)
        assertTrue(bf.countHashFunctions() > 0)
    }
}
