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
public class LinkedStack<T> : MutableStack<T> {
    private val list = LinkedList<T>()

    public override fun push(element: T): T {
        list.addFirst(element)
        return element
    }

    public override fun pop(): T? = list.removeFirst()

    public override fun peek(): T? = if (list.isEmpty()) null else list[0]

    public override val size: Int get() = list.size

    public override fun isEmpty(): Boolean = list.isEmpty()

    public override fun contains(element: T): Boolean = list.contains(element)

    public override fun clear(): Unit = list.clear()

    public override fun iterator(): Iterator<T> = list.iterator()

    public override fun toString(): String = list.toString()
}
