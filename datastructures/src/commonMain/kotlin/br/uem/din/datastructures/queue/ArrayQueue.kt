package br.uem.din.datastructures.queue

/**
 * Cria uma nova instância de Fila baseada em Array (Array Queue).
 *
 * Implementação:
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
public expect fun <T> arrayQueueOf(): MutableQueue<T>
