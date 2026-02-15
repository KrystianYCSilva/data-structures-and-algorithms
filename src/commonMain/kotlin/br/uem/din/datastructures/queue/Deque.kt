package br.uem.din.datastructures.queue

/**
 * Fila de dupla entrada (Double-Ended Queue — Deque).
 *
 * Estrutura de dados que permite inserção e remoção em ambas as extremidades (frente e trás).
 * Implementada internamente com uma [MutableList].
 *
 * Complexidades:
 * - [enqueue] (final): O(1) amortizado
 * - [enqueueFront] (início): O(n) — requer deslocamento de elementos
 * - [dequeue] (início): O(n) — requer deslocamento de elementos
 * - [dequeueBack] (final): O(1) amortizado
 * - [peek] / [peekBack]: O(1)
 *
 * @param T o tipo dos elementos armazenados no deque.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 1, Sec. 2.2.1 — Deques.
 */
class Deque<T> {
    private val storage = mutableListOf<T>()

    /** Número de elementos no deque. */
    val count: Int
        get() = storage.size

    /** Indica se o deque está vazio. */
    val isEmpty: Boolean
        get() = storage.isEmpty()

    /**
     * Insere um elemento no final do deque (rear-enqueue).
     *
     * Complexidade: O(1) amortizado.
     *
     * @param element o elemento a ser inserido.
     */
    fun enqueue(element: T) {
        storage.add(element)
    }

    /**
     * Insere um elemento no início do deque (front-enqueue).
     *
     * Complexidade: O(n), pois requer deslocamento de todos os elementos.
     *
     * @param element o elemento a ser inserido.
     */
    fun enqueueFront(element: T) {
        storage.add(0, element)
    }

    /**
     * Remove e retorna o elemento do início do deque (front-dequeue).
     *
     * Complexidade: O(n), pois requer deslocamento de todos os elementos.
     *
     * @return o elemento removido, ou `null` se o deque estiver vazio.
     */
    fun dequeue(): T? {
        return if (isEmpty) {
            null
        } else {
            storage.removeAt(0)
        }
    }

    /**
     * Remove e retorna o elemento do final do deque (rear-dequeue).
     *
     * Complexidade: O(1) amortizado.
     *
     * @return o elemento removido, ou `null` se o deque estiver vazio.
     */
    fun dequeueBack(): T? {
        return if (isEmpty) {
            null
        } else {
            storage.removeAt(storage.size - 1)
        }
    }

    /**
     * Retorna o elemento do início do deque sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o primeiro elemento, ou `null` se vazio.
     */
    fun peek(): T? = storage.firstOrNull()

    /**
     * Retorna o elemento do final do deque sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o último elemento, ou `null` se vazio.
     */
    fun peekBack(): T? = storage.lastOrNull()
}
