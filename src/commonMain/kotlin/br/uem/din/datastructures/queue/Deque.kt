package br.uem.din.datastructures.queue

/**
 * Fila de dupla entrada (Double-Ended Queue — Deque).
 *
 * Estrutura de dados que permite inserção e remoção em ambas as extremidades (frente e trás).
 * Implementa [MutableQueue] para interoperabilidade com o restante do pacote `queue`.
 * A operação [enqueue] insere no final e [dequeue] remove da frente (comportamento FIFO padrão).
 * Implementa [Iterable] para uso com `for`, `map`, `filter`, etc.
 *
 * Complexidades:
 * | Operação              | Complexidade     |
 * |-----------------------|-----------------|
 * | [enqueue] (final)     | O(1) amortizado |
 * | [enqueueFront]        | O(1) amortizado |
 * | [dequeue] (início)    | O(1) amortizado |
 * | [dequeueBack]         | O(1) amortizado |
 * | [peek] / [peekBack]   | O(1)            |
 * | [contains]            | O(n)            |
 * | [clear]               | O(1)            |
 *
 * @param T o tipo dos elementos armazenados no deque.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 1, Sec. 2.2.1 — Deques.
 */
class Deque<T> : MutableQueue<T> {
    private val storage = ArrayDeque<T>()

    override val size: Int get() = storage.size

    override fun isEmpty(): Boolean = storage.isEmpty()

    override fun enqueue(element: T) {
        storage.add(element)
    }

    /**
     * Insere um elemento no início do deque (front-enqueue).
     *
     * Complexidade: O(1) amortizado.
     *
     * @param element o elemento a ser inserido.
     */
    fun enqueueFront(element: T) {
        storage.addFirst(element)
    }

    override fun dequeue(): T? {
        if (isEmpty()) return null
        return storage.removeFirst()
    }

    /**
     * Remove e retorna o elemento do final do deque (rear-dequeue).
     *
     * Complexidade: O(1) amortizado.
     *
     * @return o elemento removido, ou `null` se o deque estiver vazio.
     */
    fun dequeueBack(): T? {
        if (isEmpty()) return null
        return storage.removeLast()
    }

    override fun peek(): T? = storage.firstOrNull()

    /**
     * Retorna o elemento do final do deque sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o último elemento, ou `null` se vazio.
     */
    fun peekBack(): T? = storage.lastOrNull()

    override fun contains(element: T): Boolean = storage.contains(element)

    override fun clear() = storage.clear()

    override fun iterator(): Iterator<T> = storage.iterator()

    override fun toString(): String = storage.toString()
}
