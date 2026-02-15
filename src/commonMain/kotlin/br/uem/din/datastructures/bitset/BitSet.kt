package br.uem.din.datastructures.bitset

/**
 * A bit set implementation.
 *
 * In JVM, this delegates to [java.util.BitSet].
 * In JS and Native, this is implemented manually.
 */
expect class BitSet(size: Int = 64) {
    /** Sets the bit at the specified index to true. */
    fun set(index: Int)
    
    /** Sets the bit at the specified index to the specified value. */
    fun set(index: Int, value: Boolean)

    /** Sets the bit at the specified index to false. */
    fun clear(index: Int)
    
    /** Returns the value of the bit with the specified index. */
    operator fun get(index: Int): Boolean
    
    /** Returns the number of bits of space actually in use by this BitSet to represent bit values. */
    fun size(): Int
    
    /** Returns the "logical size" of this BitSet: the index of the highest set bit in the BitSet plus one. */
    fun length(): Int
    
    /** Returns true if this BitSet contains no bits that are set to true. */
    fun isEmpty(): Boolean
}
