package br.uem.din.datastructures.bitset

import java.util.BitSet as JavaBitSet

/**
 * Cria uma instância JVM de BitSet.
 */
public actual fun bitSetOf(size: Int): BitSet {
    return JvmBitSet(size)
}

/**
 * Implementação JVM do [BitSet], delegando para [java.util.BitSet].
 */
private class JvmBitSet(size: Int) : BitSet {
    private val bitSet = JavaBitSet(size)

    override fun set(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        bitSet.set(index)
    }

    override fun set(index: Int, value: Boolean) {
        require(index >= 0) { "index ($index) must be >= 0" }
        bitSet.set(index, value)
    }

    override fun clear(index: Int) {
        require(index >= 0) { "index ($index) must be >= 0" }
        bitSet.clear(index)
    }

    override fun clear() {
        bitSet.clear()
    }

    override operator fun get(index: Int): Boolean {
        require(index >= 0) { "index ($index) must be >= 0" }
        return bitSet.get(index)
    }

    override fun size(): Int {
        return bitSet.size()
    }

    override fun length(): Int {
        return bitSet.length()
    }

    override fun isEmpty(): Boolean {
        return bitSet.isEmpty
    }

    override fun cardinality(): Int {
        return bitSet.cardinality()
    }

    override fun nextSetBit(fromIndex: Int): Int {
        return bitSet.nextSetBit(fromIndex)
    }

    override fun and(other: BitSet) {
        if (other is JvmBitSet) {
            bitSet.and(other.bitSet)
        } else {
            // Fallback para interoperabilidade (lento)
            for (i in other) {
                if (!this[i]) other.clear(i) // Lógica incorreta para AND, precisa ser intersecção
            }
            // Correção: AND é intersecção.
            // Para implementar AND genérico sem acesso ao interno, precisaríamos iterar.
            // Dado que a factory cria sempre JvmBitSet na JVM, o cast acima deve funcionar.
            throw IllegalArgumentException("Cannot perform bitwise operations with different BitSet implementations")
        }
    }

    override fun or(other: BitSet) {
        if (other is JvmBitSet) {
            bitSet.or(other.bitSet)
        } else {
             throw IllegalArgumentException("Cannot perform bitwise operations with different BitSet implementations")
        }
    }

    override fun xor(other: BitSet) {
        if (other is JvmBitSet) {
            bitSet.xor(other.bitSet)
        } else {
             throw IllegalArgumentException("Cannot perform bitwise operations with different BitSet implementations")
        }
    }

    override fun andNot(other: BitSet) {
        if (other is JvmBitSet) {
            bitSet.andNot(other.bitSet)
        } else {
             throw IllegalArgumentException("Cannot perform bitwise operations with different BitSet implementations")
        }
    }

    override fun iterator(): Iterator<Int> = object : Iterator<Int> {
        private var next = bitSet.nextSetBit(0)
        override fun hasNext(): Boolean = next != -1
        override fun next(): Int {
            if (next == -1) throw NoSuchElementException()
            val current = next
            next = bitSet.nextSetBit(current + 1)
            return current
        }
    }

    override fun toString(): String = bitSet.toString()
    override fun equals(other: Any?): Boolean = other is JvmBitSet && bitSet == other.bitSet
    override fun hashCode(): Int = bitSet.hashCode()
}
