package br.uem.din.datastructures.queue

/**
 * Cria uma nova instância de uma Fila de Prioridade (Priority Queue).
 *
 * Fila onde o elemento com maior prioridade (menor valor segundo o [Comparator] ou
 * ordenação natural [Comparable]) é sempre desinfileirado primeiro (min-heap).
 *
 * Implementação:
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
 */
public expect fun <T> priorityQueueOf(comparator: Comparator<T>? = null): MutableQueue<T>
