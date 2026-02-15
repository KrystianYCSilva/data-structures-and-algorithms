package br.uem.din.datastructures.stack

/**
 * Implementação de [MutableStack] baseada em array, com delegação a plataformas nativas
 * via `expect`/`actual`.
 *
 * - **JVM**: delega a [java.util.ArrayDeque]
 * - **JS/Native**: utiliza [ArrayList] como armazenamento interno
 *
 * Complexidades:
 * | Operação           | Complexidade     |
 * |--------------------|-----------------|
 * | [push]             | O(1) amortizado |
 * | [pop] / [peek]     | O(1)            |
 * | [contains]         | O(n)            |
 * | [clear]            | O(1)            |
 * | [size] / [isEmpty] | O(1)            |
 *
 * @param T o tipo dos elementos armazenados na pilha.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 *
 * @see LinkedStack
 */
expect class ArrayStack<T>() : MutableStack<T> {
    override fun push(element: T): T
    override fun pop(): T?
    override fun peek(): T?
    override fun size(): Int
    override fun isEmpty(): Boolean
    override fun contains(element: T): Boolean
    override fun clear()
    override fun iterator(): Iterator<T>
    override fun toString(): String
}
