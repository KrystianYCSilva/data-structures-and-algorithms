package br.uem.din.datastructures.bitset

import kotlin.math.max
import kotlin.math.min

/**
 * Cria uma instância Native de BitSet.
 */
public actual fun bitSetOf(size: Int): MutableBitSet {
    return NativeBitSet(size)
}

/**
 * Implementação Native do [BitSet] usando [LongArray].
 */
private class NativeBitSet(size: Int) : MutableBitSet {
    private var words = LongArray((size + 63) / 64)

    override fun set(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        val wordIndex = index / 64
        ensureCapacity(wordIndex + 1)
        val bitIndex = index % 64
        words[wordIndex] = words[wordIndex] or (1L shl bitIndex)
    }

    override fun set(index: Int, value: Boolean) {
        if (value) set(index) else clear(index)
    }

    override fun clear(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        val wordIndex = index / 64
        if (wordIndex >= words.size) return
        val bitIndex = index % 64
        words[wordIndex] = words[wordIndex] and (1L shl bitIndex).inv()
    }

    override fun clear() {
        words.fill(0L)
    }

    override operator fun get(index: Int): Boolean {
        require(index >= 0) { "index ($index) must be >= 0" }
        val wordIndex = index / 64
        if (wordIndex >= words.size) return false
        val bitIndex = index % 64
        return (words[wordIndex] and (1L shl bitIndex)) != 0L
    }

    override fun size(): Int = words.size * 64

    override fun length(): Int {
        for (i in words.lastIndex downTo 0) {
            val word = words[i]
            if (word != 0L) {
                for (bit in 63 downTo 0) {
                    if ((word and (1L shl bit)) != 0L) {
                        return (i * 64) + bit + 1
                    }
                }
            }
        }
        return 0
    }

    override fun isEmpty(): Boolean {
        return words.all { it == 0L }
    }

    override fun cardinality(): Int {
        var count = 0
        for (word in words) {
            count += word.countOneBits()
        }
        return count
    }

    override fun nextSetBit(fromIndex: Int): Int {
        if (fromIndex < 0) return -1
        var wordIndex = fromIndex / 64
        if (wordIndex >= words.size) return -1
        var word = words[wordIndex] and (-1L shl (fromIndex % 64))
        while (true) {
            if (word != 0L) {
                return (wordIndex * 64) + word.countTrailingZeroBits()
            }
            wordIndex++
            if (wordIndex >= words.size) return -1
            word = words[wordIndex]
        }
    }

    override fun and(other: MutableBitSet) {
        if (other !is NativeBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        val commonWords = min(words.size, other.words.size)
        for (i in 0 until commonWords) {
            words[i] = words[i] and other.words[i]
        }
        for (i in commonWords until words.size) {
            words[i] = 0L
        }
    }

    override fun or(other: MutableBitSet) {
        if (other !is NativeBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        if (other.words.size > words.size) {
            words = words.copyOf(other.words.size)
        }
        for (i in other.words.indices) {
            words[i] = words[i] or other.words[i]
        }
    }

    override fun xor(other: MutableBitSet) {
        if (other !is NativeBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        if (other.words.size > words.size) {
            words = words.copyOf(other.words.size)
        }
        for (i in other.words.indices) {
            words[i] = words[i] xor other.words[i]
        }
    }

    override fun andNot(other: MutableBitSet) {
        if (other !is NativeBitSet) throw IllegalArgumentException("Incompatible BitSet implementation")
        val commonWords = min(words.size, other.words.size)
        for (i in 0 until commonWords) {
            words[i] = words[i] and other.words[i].inv()
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
        if (other !is NativeBitSet) return false
        val len = max(words.size, other.words.size)
        for (i in 0 until len) {
            val a = if (i < words.size) words[i] else 0L
            val b = if (i < other.words.size) other.words[i] else 0L
            if (a != b) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var h = 1234L
        for (i in words.indices.reversed()) {
            h = h xor (words[i] * (i + 1))
        }
        return (h shr 32 xor h).toInt()
    }

    private fun ensureCapacity(wordsRequired: Int) {
        if (words.size < wordsRequired) {
            val request = max(2 * words.size, wordsRequired)
            words = words.copyOf(request)
        }
    }
}
