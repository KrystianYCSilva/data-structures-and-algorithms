package br.uem.din.datastructures.queue

/**
 * Fila circular de capacidade fixa (Circular Queue / Ring Buffer).
 *
 * Implementação baseada em buffer circular (ring buffer) que utiliza dois ponteiros —
 * [readIndex] e [writeIndex] — para gerenciar as posições de leitura e escrita sem
 * necessidade de deslocamento de elementos.
 *
 * O buffer circular é uma técnica clássica para implementar filas de tamanho fixo
 * com operações O(1) para enqueue/dequeue, amplamente utilizada em sistemas operacionais
 * e buffers de I/O.
 *
 * Complexidades:
 * - [enqueue]: O(1)
 * - [dequeue]: O(1)
 * - [peek]: O(1)
 *
 * @param T o tipo dos elementos armazenados na fila.
 * @param capacity a capacidade máxima da fila (um slot é reservado para diferenciar fila cheia de vazia).
 *
 * Referência: Sedgewick, R. "Algorithms in Java", Cap. 4 — Queues.
 */
class CircularQueue<T>(private val capacity: Int) : MutableQueue<T> {
    private val storage: MutableList<T?> = MutableList(capacity) { null }
    private var readIndex = 0
    private var writeIndex = 0

    private val internalCount: Int
        get() = if (writeIndex >= readIndex) writeIndex - readIndex else writeIndex + capacity - readIndex

    /** Indica se a fila atingiu sua capacidade máxima. */
    val isFull: Boolean
        get() = internalCount == capacity - 1

    override fun size(): Int = internalCount

    override fun isEmpty(): Boolean = internalCount == 0

    /**
     * Insere um elemento no final da fila.
     *
     * Complexidade: O(1).
     *
     * @param element o elemento a ser inserido.
     * @throws IllegalStateException se a fila estiver cheia.
     */
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
        writeIndex = (writeIndex + 1) % capacity
        return true
    }

    /**
     * Remove e retorna o elemento no início da fila.
     *
     * Complexidade: O(1).
     *
     * @return o elemento removido, ou `null` se a fila estiver vazia.
     */
    override fun dequeue(): T? {
        if (isEmpty()) return null
        val dequeued = storage[readIndex]
        storage[readIndex] = null // Help GC
        readIndex = (readIndex + 1) % capacity
        return dequeued
    }

    /**
     * Retorna o elemento no início da fila sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o primeiro elemento da fila, ou `null` se vazia.
     */
    override fun peek(): T? = storage[readIndex]
}
