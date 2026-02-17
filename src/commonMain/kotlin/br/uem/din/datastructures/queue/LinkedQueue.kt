package br.uem.din.datastructures.queue

import br.uem.din.datastructures.linkedlist.LinkedList

/**
 * Fila encadeada (Linked Queue) — implementação baseada em lista ligada simplesmente encadeada.
 *
 * Utiliza [LinkedList] como estrutura interna, delegando as operações de inserção e
 * remoção às extremidades da lista. Não possui limite de capacidade fixa.
 * Implementa [MutableQueue] com suporte completo a [Iterable].
 *
 * Complexidades:
 * | Operação              | Complexidade |
 * |-----------------------|-------------|
 * | [enqueue]             | O(1)        |
 * | [dequeue] / [peek]    | O(1)        |
 * | [contains]            | O(n)        |
 * | [clear]               | O(1)        |
 * | [size] / [isEmpty]    | O(1)        |
 *
 * @param T o tipo dos elementos armazenados na fila.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 */
public class LinkedQueue<T> : MutableQueue<T> {
    private val list = LinkedList<T>()

    public override fun enqueue(element: T) {
        list.addLast(element)
    }

    public override fun dequeue(): T? = list.removeFirst()

    public override fun peek(): T? = if (list.isEmpty()) null else list[0]

    public override val size: Int get() = list.size

    public override fun isEmpty(): Boolean = list.isEmpty()

    public override fun contains(element: T): Boolean = list.contains(element)

    public override fun clear(): Unit = list.clear()

    public override fun iterator(): Iterator<T> = list.iterator()

    public override fun toString(): String = list.toString()
}
