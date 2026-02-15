package br.uem.din.datastructures.queue

/**
 * Implementação de [MutableQueue] baseada em array, com delegação a plataformas nativas
 * via `expect`/`actual`.
 *
 * - **JVM**: delega a [java.util.ArrayDeque]
 * - **JS/Native**: buffer circular com redimensionamento automático
 *
 * Complexidades:
 * | Operação              | Complexidade     |
 * |-----------------------|-----------------|
 * | [enqueue]             | O(1) amortizado |
 * | [dequeue] / [peek]    | O(1)            |
 * | [contains]            | O(n)            |
 * | [clear]               | O(1)            |
 * | [size] / [isEmpty]    | O(1)            |
 *
 * @param T o tipo dos elementos armazenados na fila.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 *
 * @see LinkedQueue
 * @see CircularQueue
 */
expect class ArrayQueue<T>() : MutableQueue<T> {
    override fun enqueue(element: T)
    override fun dequeue(): T?
    override fun peek(): T?
    override val size: Int
    override fun isEmpty(): Boolean
    override fun contains(element: T): Boolean
    override fun clear()
    override fun iterator(): Iterator<T>
    override fun toString(): String
}
