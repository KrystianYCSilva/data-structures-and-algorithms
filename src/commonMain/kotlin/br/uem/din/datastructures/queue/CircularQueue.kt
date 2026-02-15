package br.uem.din.datastructures.queue

/**
 * Fila circular de capacidade fixa (Circular Queue / Ring Buffer).
 *
 * Implementação baseada em buffer circular (ring buffer) que utiliza dois ponteiros —
 * leitura e escrita — para gerenciar as posições sem necessidade de deslocamento de elementos.
 * Um slot extra é alocado internamente para diferenciar fila cheia de vazia, de modo que
 * `CircularQueue(n)` suporta exatamente `n` elementos.
 *
 * Complexidades:
 * | Operação              | Complexidade |
 * |-----------------------|-------------|
 * | [enqueue] / [offer]   | O(1)        |
 * | [dequeue] / [peek]    | O(1)        |
 * | [contains]            | O(n)        |
 * | [clear]               | O(n)        |
 * | [size] / [isEmpty]    | O(1)        |
 *
 * @param T o tipo dos elementos armazenados na fila.
 * @param capacity a capacidade máxima de elementos da fila.
 *
 * Referência: Sedgewick, R. "Algorithms in Java", Cap. 4 — Queues.
 */
class CircularQueue<T>(private val capacity: Int) : MutableQueue<T> {
    private val internalCapacity = capacity + 1
    private val storage: MutableList<T?> = MutableList(internalCapacity) { null }
    private var readIndex = 0
    private var writeIndex = 0

    private val internalCount: Int
        get() = if (writeIndex >= readIndex) writeIndex - readIndex else writeIndex + internalCapacity - readIndex

    /**
     * Indica se a fila atingiu sua capacidade máxima.
     *
     * Complexidade: O(1).
     */
    val isFull: Boolean
        get() = internalCount == capacity

    override fun size(): Int = internalCount

    override fun isEmpty(): Boolean = internalCount == 0

    override fun enqueue(element: T) {
        if (!offer(element)) {
            throw IllegalStateException("Queue is full")
        }
    }

    /**
     * Tenta inserir um elemento na fila sem lançar exceção.
     *
     * Complexidade: O(1).
     *
     * @param element o elemento a ser inserido.
     * @return `true` se o elemento foi inserido com sucesso, `false` se a fila estiver cheia.
     */
    fun offer(element: T): Boolean {
        if (isFull) return false
        storage[writeIndex] = element
        writeIndex = (writeIndex + 1) % internalCapacity
        return true
    }

    override fun dequeue(): T? {
        if (isEmpty()) return null
        val dequeued = storage[readIndex]
        storage[readIndex] = null
        readIndex = (readIndex + 1) % internalCapacity
        return dequeued
    }

    override fun peek(): T? {
        if (isEmpty()) return null
        return storage[readIndex]
    }

    override fun contains(element: T): Boolean {
        for (v in this) {
            if (v == element) return true
        }
        return false
    }

    override fun clear() {
        for (i in storage.indices) storage[i] = null
        readIndex = 0
        writeIndex = 0
    }

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var index = readIndex
        private var remaining = internalCount

        override fun hasNext(): Boolean = remaining > 0

        @Suppress("UNCHECKED_CAST")
        override fun next(): T {
            if (remaining <= 0) throw NoSuchElementException()
            val value = storage[index] as T
            index = (index + 1) % internalCapacity
            remaining--
            return value
        }
    }

    override fun toString(): String {
        if (isEmpty()) return "[]"
        return iterator().asSequence().joinToString(prefix = "[", postfix = "]")
    }
}
