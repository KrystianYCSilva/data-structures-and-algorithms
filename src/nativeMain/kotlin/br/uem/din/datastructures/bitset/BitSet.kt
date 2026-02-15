package br.uem.din.datastructures.bitset

import kotlin.math.max
import kotlin.math.min

/**
 * Implementação Native do [BitSet] usando [LongArray] (palavras de 64 bits).
 *
 * Kotlin/Native não dispõe de `java.util.BitSet`, por isso esta implementação
 * manual utiliza `LongArray` para eficiência máxima de armazenamento em plataformas
 * de 64 bits. Cresce automaticamente quando bits além da capacidade são acessados.
 */
actual class BitSet actual constructor(size: Int) : Iterable<Int> {
    private var words = LongArray((size + 63) / 64)

    actual fun set(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        val wordIndex = index / 64
        ensureCapacity(wordIndex + 1)
        val bitIndex = index % 64
        words[wordIndex] = words[wordIndex] or (1L shl bitIndex)
    }

    actual fun set(index: Int, value: Boolean) {
        if (value) set(index) else clear(index)
    }

    actual fun clear(index: Int) {
        val wordIndex = index / 64
        if (wordIndex >= words.size) return
        val bitIndex = index % 64
        words[wordIndex] = words[wordIndex] and (1L shl bitIndex).inv()
    }

    actual fun clear() {
        words.fill(0L)
    }

    actual operator fun get(index: Int): Boolean {
        val wordIndex = index / 64
        if (wordIndex >= words.size) return false
        val bitIndex = index % 64
        return (words[wordIndex] and (1L shl bitIndex)) != 0L
    }

    actual fun size(): Int = words.size * 64

    actual fun length(): Int {
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

    actual fun isEmpty(): Boolean {
        return words.all { it == 0L }
    }

    actual fun cardinality(): Int {
        var count = 0
        for (word in words) {
            count += word.countOneBits()
        }
        return count
    }

    actual fun nextSetBit(fromIndex: Int): Int {
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

    actual fun and(other: BitSet) {
        val commonWords = min(words.size, other.words.size)
        for (i in 0 until commonWords) {
            words[i] = words[i] and other.words[i]
        }
        for (i in commonWords until words.size) {
            words[i] = 0L
        }
    }

    actual fun or(other: BitSet) {
        if (other.words.size > words.size) {
            words = words.copyOf(other.words.size)
        }
        for (i in other.words.indices) {
            words[i] = words[i] or other.words[i]
        }
    }

    actual fun xor(other: BitSet) {
        if (other.words.size > words.size) {
            words = words.copyOf(other.words.size)
        }
        for (i in other.words.indices) {
            words[i] = words[i] xor other.words[i]
        }
    }

    actual fun andNot(other: BitSet) {
        val commonWords = min(words.size, other.words.size)
        for (i in 0 until commonWords) {
            words[i] = words[i] and other.words[i].inv()
        }
    }

    actual override fun iterator(): Iterator<Int> = object : Iterator<Int> {
        private var next = nextSetBit(0)
        override fun hasNext(): Boolean = next != -1
        override fun next(): Int {
            if (next == -1) throw NoSuchElementException()
            val current = next
            next = nextSetBit(current + 1)
            return current
        }
    }

    actual override fun toString(): String {
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

    actual override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BitSet) return false
        val len = max(words.size, other.words.size)
        for (i in 0 until len) {
            val a = if (i < words.size) words[i] else 0L
            val b = if (i < other.words.size) other.words[i] else 0L
            if (a != b) return false
        }
        return true
    }

    actual override fun hashCode(): Int {
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
