package br.uem.din.datastructures.bitset

import java.util.BitSet as JavaBitSet

/**
 * Implementação JVM do [BitSet], delegando para [java.util.BitSet].
 *
 * Utiliza `long[]` internamente (palavras de 64 bits), com operações
 * nativas otimizadas pela JVM e suporte a crescimento automático.
 *
 * @see java.util.BitSet
 */
actual class BitSet actual constructor(size: Int) : Iterable<Int> {
    private val bitSet = JavaBitSet(size)

    actual fun set(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        bitSet.set(index)
    }

    actual fun set(index: Int, value: Boolean) {
        require(index >= 0) { "index ($index) must be >= 0" }
        bitSet.set(index, value)
    }

    actual fun clear(index: Int) {
        bitSet.clear(index)
    }

    actual fun clear() {
        bitSet.clear()
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

    actual fun cardinality(): Int {
        return bitSet.cardinality()
    }

    actual fun nextSetBit(fromIndex: Int): Int {
        return bitSet.nextSetBit(fromIndex)
    }

    actual fun and(other: BitSet) {
        bitSet.and(other.bitSet)
    }

    actual fun or(other: BitSet) {
        bitSet.or(other.bitSet)
    }

    actual fun xor(other: BitSet) {
        bitSet.xor(other.bitSet)
    }

    actual fun andNot(other: BitSet) {
        bitSet.andNot(other.bitSet)
    }

    actual override fun iterator(): Iterator<Int> = object : Iterator<Int> {
        private var next = bitSet.nextSetBit(0)
        override fun hasNext(): Boolean = next != -1
        override fun next(): Int {
            if (next == -1) throw NoSuchElementException()
            val current = next
            next = bitSet.nextSetBit(current + 1)
            return current
        }
    }

    actual override fun toString(): String = bitSet.toString()
    actual override fun equals(other: Any?): Boolean = other is BitSet && bitSet == other.bitSet
    actual override fun hashCode(): Int = bitSet.hashCode()
}
