package br.uem.din.datastructures.bitset

import kotlin.math.max
import kotlin.math.min

/**
 * Cria uma instância JS de BitSet.
 */
public actual fun bitSetOf(size: Int): MutableBitSet {
    return JsBitSet(size)
}

/**
 * Implementação JS do [BitSet] usando [IntArray].
 */
private class JsBitSet(size: Int) : MutableBitSet {
    private var bits = IntArray((size + 31) / 32)

    override fun set(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        ensureCapacity(index)
        val wordIndex = index / 32
        val bitIndex = index % 32
        bits[wordIndex] = bits[wordIndex] or (1 shl bitIndex)
    }

    override fun set(index: Int, value: Boolean) {
        if (value) set(index) else clear(index)
    }

    override fun clear(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        val wordIndex = index / 32
        if (wordIndex >= bits.size) return
        val bitIndex = index % 32
        bits[wordIndex] = bits[wordIndex] and (1 shl bitIndex).inv()
    }

    override fun clear() {
        bits.fill(0)
    }

    override operator fun get(index: Int): Boolean {
        require(index >= 0) { "index ($index) must be >= 0" }
        val wordIndex = index / 32
        if (wordIndex >= bits.size) return false
        val bitIndex = index % 32
        return (bits[wordIndex] and (1 shl bitIndex)) != 0
    }

    override fun size(): Int {
        return bits.size * 32
    }

    override fun length(): Int {
        for (i in bits.indices.reversed()) {
            val word = bits[i]
            if (word != 0) {
                for (bit in 31 downTo 0) {
                    if ((word and (1 shl bit)) != 0) {
                        return (i * 32) + bit + 1
                    }
                }
            }
        }
        return 0
    }

    override fun isEmpty(): Boolean {
        return bits.all { it == 0 }
    }

    override fun cardinality(): Int {
        var count = 0
        for (word in bits) {
            count += word.countOneBits()
        }
        return count
    }

    override fun nextSetBit(fromIndex: Int): Int {
        if (fromIndex < 0) return -1
        var wordIndex = fromIndex / 32
        if (wordIndex >= bits.size) return -1
        var word = bits[wordIndex] and (-1 shl (fromIndex % 32))
        while (true) {
            if (word != 0) {
                return (wordIndex * 32) + word.countTrailingZeroBits()
            }
            wordIndex++
            if (wordIndex >= bits.size) return -1
            word = bits[wordIndex]
        }
    }

    override fun and(other: MutableBitSet) {
        if (other !is JsBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        val commonWords = min(bits.size, other.bits.size)
        for (i in 0 until commonWords) {
            bits[i] = bits[i] and other.bits[i]
        }
        for (i in commonWords until bits.size) {
            bits[i] = 0
        }
    }

    override fun or(other: MutableBitSet) {
        if (other !is JsBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        if (other.bits.size > bits.size) {
            bits = bits.copyOf(other.bits.size)
        }
        for (i in other.bits.indices) {
            bits[i] = bits[i] or other.bits[i]
        }
    }

    override fun xor(other: MutableBitSet) {
        if (other !is JsBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        if (other.bits.size > bits.size) {
            bits = bits.copyOf(other.bits.size)
        }
        for (i in other.bits.indices) {
            bits[i] = bits[i] xor other.bits[i]
        }
    }

    override fun andNot(other: MutableBitSet) {
        if (other !is JsBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        val commonWords = min(bits.size, other.bits.size)
        for (i in 0 until commonWords) {
            bits[i] = bits[i] and other.bits[i].inv()
        }
    }

    override fun iterator(): Iterator<Int> = object : Iterator<Int> {
        private var next = nextSetBit(0)
        override fun hasNext(): Boolean = next != -1
        override fun next(): Int {
            if (next == -1) throw NoSuchElementException()
            val current = next
            next = nextSetBit(current + 1)
            return current
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('{')
        var first = true
        for (i in this) {
            if (!first) sb.append(", ")
            sb.append(i)
            first = false
        }
        sb.append('}')
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JsBitSet) return false
        val len = max(bits.size, other.bits.size)
        for (i in 0 until len) {
            val a = if (i < bits.size) bits[i] else 0
            val b = if (i < other.bits.size) other.bits[i] else 0
            if (a != b) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var h = 1234L
        for (i in bits.indices.reversed()) {
            h = h xor (bits[i].toLong() * (i + 1))
        }
        return (h shr 32 xor h).toInt()
    }

    private fun ensureCapacity(bitIndex: Int) {
        val wordIndex = bitIndex / 32
        if (wordIndex >= bits.size) {
            val newSize = max(bits.size * 2, wordIndex + 1)
            bits = bits.copyOf(newSize)
        }
    }
}
