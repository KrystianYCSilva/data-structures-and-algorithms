package br.uem.din.datastructures.probabilistic

import kotlin.test.*

class BloomFilterTest {

    @Test
    fun testAddAndContains() {
        // Create a Bloom Filter for 100 elements with 1% false positive probability
        val bloomFilter = BloomFilter(100, 0.01)

        bloomFilter.add("apple")
        bloomFilter.add("banana")
        bloomFilter.add("orange")

        // True positives (must be true)
        assertTrue(bloomFilter.contains("apple"))
        assertTrue(bloomFilter.contains("banana"))
        assertTrue(bloomFilter.contains("orange"))

        // False positives check (likely false, but could be true)
        // With small n and optimal k/m, probability is low.
        // We assert assuming no collision for these distinct strings in this config.
        assertFalse(bloomFilter.contains("grape")) 
        assertFalse(bloomFilter.contains("watermelon"))
    }
}
