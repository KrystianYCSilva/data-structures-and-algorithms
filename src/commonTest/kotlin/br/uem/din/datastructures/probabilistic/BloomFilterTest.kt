package br.uem.din.datastructures.probabilistic

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
}
