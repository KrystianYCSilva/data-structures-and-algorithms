package br.uem.din.datastructures.stack

import br.uem.din.datastructures.linkedlist.LinkedList

/**
 * Implementação de [MutableStack] baseada em [LinkedList] simplesmente encadeada.
 *
 * Todas as operações de pilha (push, pop, peek) delegam a operações O(1) na cabeça
 * da lista ligada, evitando realocação de arrays.
 *
 * Complexidades:
 * | Operação           | Complexidade |
 * |--------------------|-------------|
 * | [push]             | O(1)        |
 * | [pop] / [peek]     | O(1)        |
 * | [contains]         | O(n)        |
 * | [clear]            | O(1)        |
 * | [size] / [isEmpty] | O(1)        |
 *
 * @param T o tipo dos elementos armazenados na pilha.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 *
 * @see ArrayStack
 */
class LinkedStack<T> : MutableStack<T> {
    private val list = LinkedList<T>()

    override fun push(element: T): T {
        list.addFirst(element)
        return element
    }

    override fun pop(): T? = list.removeFirst()

    override fun peek(): T? = if (list.isEmpty()) null else list[0]

    override val size: Int get() = list.size

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun contains(element: T): Boolean = list.contains(element)

    override fun clear() = list.clear()

    override fun iterator(): Iterator<T> = list.iterator()

    override fun toString(): String = list.toString()
}
