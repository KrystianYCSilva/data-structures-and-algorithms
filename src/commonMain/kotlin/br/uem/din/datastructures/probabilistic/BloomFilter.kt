package br.uem.din.datastructures.probabilistic

import br.uem.din.datastructures.bitset.BitSet
import kotlin.math.ln
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.abs

/**
 * A probabilistic data structure that tells us whether an element is definitely not in the set or may be in the set.
 */
class BloomFilter(private val expectedInsertions: Int, private val falsePositiveProbability: Double = 0.01) {

    private val bitSetSize: Int
    private val numHashFunctions: Int
    private val bitSet: BitSet

    init {
        // m = -(n * ln(p)) / (ln(2)^2)
        bitSetSize = ceil((-expectedInsertions * ln(falsePositiveProbability)) / (ln(2.0).pow(2))).toInt()
        
        // k = (m / n) * ln(2)
        numHashFunctions = ceil((bitSetSize.toDouble() / expectedInsertions) * ln(2.0)).toInt()
        
        bitSet = BitSet(bitSetSize)
    }

    fun add(element: String) {
        val hash1 = element.hashCode()
        val hash2 = hash2(element)
        
        for (i in 0 until numHashFunctions) {
            val combinedHash = abs(hash1 + i * hash2)
            val index = combinedHash % bitSetSize
            bitSet.set(index)
        }
    }

    fun contains(element: String): Boolean {
        val hash1 = element.hashCode()
        val hash2 = hash2(element)
        
        for (i in 0 until numHashFunctions) {
            val combinedHash = abs(hash1 + i * hash2)
            val index = combinedHash % bitSetSize
            if (!bitSet[index]) {
                return false
            }
        }
        return true
    }

    // A simple secondary hash function
    private fun hash2(s: String): Int {
        var hash = 5381
        for (char in s) {
            hash = ((hash shl 5) + hash) + char.code
        }
        return hash
    }
    
    fun size(): Int = bitSetSize
    
    fun countHashFunctions(): Int = numHashFunctions
}
