package br.uem.din.datastructures.queue

/**
 * Fila de prioridade (Priority Queue) — declaração `expect` para Kotlin Multiplatform.
 *
 * Fila onde o elemento com maior prioridade (menor valor segundo o [Comparator] ou
 * ordenação natural [Comparable]) é sempre desinfileirado primeiro (min-heap).
 * Implementa [MutableQueue] com suporte completo a [Iterable].
 *
 * - **JVM**: delega a [java.util.PriorityQueue]
 * - **JS/Native**: implementação manual de binary min-heap
 *
 * Complexidades:
 * | Operação              | Complexidade |
 * |-----------------------|-------------|
 * | [enqueue]             | O(log n)    |
 * | [dequeue]             | O(log n)    |
 * | [peek]                | O(1)        |
 * | [contains]            | O(n)        |
 * | [clear]               | O(1)        |
 * | [size] / [isEmpty]    | O(1)        |
 *
 * @param T o tipo dos elementos. Deve ser [Comparable] se nenhum [Comparator] for fornecido.
 * @param comparator comparador opcional; se `null`, usa a ordenação natural de [T].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 6 — Heapsort / Priority Queues.
 *
 * @see ArrayQueue
 * @see LinkedQueue
 */
expect class PriorityQueue<T>(comparator: Comparator<T>? = null) : MutableQueue<T> {
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
