package br.uem.din.datastructures.queue

import br.uem.din.datastructures.linkedlist.LinkedList

/**
 * Fila encadeada (Linked Queue) — implementação baseada em lista ligada simplesmente encadeada.
 *
 * Utiliza [LinkedList] como estrutura interna, delegando as operações de inserção (append)
 * e remoção (pop) às extremidades da lista. Não possui limite de capacidade fixa.
 *
 * Complexidades:
 * - [enqueue]: O(1)
 * - [dequeue]: O(1)
 * - [peek]: O(1)
 *
 * @param T o tipo dos elementos armazenados na fila.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 */
class LinkedQueue<T> : MutableQueue<T> {
    private val list = LinkedList<T>()

    /**
     * Insere um elemento no final da fila.
     *
     * Complexidade: O(1).
     *
     * @param element o elemento a ser inserido.
     */
    override fun enqueue(element: T) {
        list.append(element)
    }

    /**
     * Remove e retorna o elemento no início da fila.
     *
     * Complexidade: O(1).
     *
     * @return o elemento removido, ou `null` se a fila estiver vazia.
     */
    override fun dequeue(): T? {
        return list.pop()
    }

    /**
     * Retorna o elemento no início da fila sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o primeiro elemento, ou `null` se a fila estiver vazia.
     */
    override fun peek(): T? {
        return list.nodeAt(0)?.value
    }

    /** {@inheritDoc} */
    override fun size(): Int = list.size

    /** {@inheritDoc} */
    override fun isEmpty(): Boolean = list.isEmpty()

    /** {@inheritDoc} */
    override fun toString(): String = list.toString()
}
