package br.uem.din.datastructures.bitset

import kotlin.math.max

/**
 * Implementação JS do [BitSet] usando [IntArray] (palavras de 32 bits).
 *
 * JavaScript não possui tipos inteiros de 64 bits nativos, por isso utiliza
 * palavras de 32 bits com operações bitwise seguras no range de Int.
 * Cresce automaticamente quando bits além da capacidade são acessados.
 */
actual class BitSet actual constructor(size: Int) {
    private var bits = IntArray((size + 31) / 32)

    actual fun set(index: Int) {
        ensureCapacity(index)
        val wordIndex = index / 32
        val bitIndex = index % 32
        bits[wordIndex] = bits[wordIndex] or (1 shl bitIndex)
    }

    actual fun set(index: Int, value: Boolean) {
        if (value) set(index) else clear(index)
    }

    actual fun clear(index: Int) {
        val wordIndex = index / 32
        if (wordIndex >= bits.size) return
        val bitIndex = index % 32
        bits[wordIndex] = bits[wordIndex] and (1 shl bitIndex).inv()
    }

    actual operator fun get(index: Int): Boolean {
        val wordIndex = index / 32
        if (wordIndex >= bits.size) return false
        val bitIndex = index % 32
        return (bits[wordIndex] and (1 shl bitIndex)) != 0
    }

    actual fun size(): Int {
        return bits.size * 32
    }

    actual fun length(): Int {
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

    actual fun isEmpty(): Boolean {
        return length() == 0
    }

    private fun ensureCapacity(bitIndex: Int) {
        val wordIndex = bitIndex / 32
        if (wordIndex >= bits.size) {
            val newSize = max(bits.size * 2, wordIndex + 1)
            bits = bits.copyOf(newSize)
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('{')
        var first = true
        for (i in 0 until length()) {
            if (get(i)) {
                if (!first) {
                    sb.append(", ")
                }
                sb.append(i)
                first = false
            }
        }
        sb.append('}')
        return sb.toString()
    }
}
