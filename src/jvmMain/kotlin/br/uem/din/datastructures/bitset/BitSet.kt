package br.uem.din.datastructures.bitset

import java.util.BitSet as JavaBitSet

actual class BitSet actual constructor(size: Int) {
    private val bitSet = JavaBitSet(size)

    actual fun set(index: Int) {
        bitSet.set(index)
    }

    actual fun set(index: Int, value: Boolean) {
        bitSet.set(index, value)
    }

    actual fun clear(index: Int) {
        bitSet.clear(index)
    }

    actual operator fun get(index: Int): Boolean {
        return bitSet.get(index)
    }

    actual fun size(): Int {
        return bitSet.size()
    }

    actual fun length(): Int {
        return bitSet.length()
    }

    actual fun isEmpty(): Boolean {
        return bitSet.isEmpty
    }
    
    override fun toString(): String = bitSet.toString()
    override fun equals(other: Any?): Boolean = other is BitSet && bitSet == other.bitSet
    override fun hashCode(): Int = bitSet.hashCode()
}
