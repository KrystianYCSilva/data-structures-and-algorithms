package br.uem.din.datastructures.bitset

actual class BitSet actual constructor(size: Int) {
    private var words = LongArray((size + 63) / 64)

    actual fun set(index: Int) {
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

    actual fun isEmpty(): Boolean = length() == 0

    private fun ensureCapacity(wordsRequired: Int) {
        if (words.size < wordsRequired) {
            val request = kotlin.math.max(2 * words.size, wordsRequired)
            words = words.copyOf(request)
        }
    }
}
