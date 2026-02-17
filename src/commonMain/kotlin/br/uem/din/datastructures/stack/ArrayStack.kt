package br.uem.din.datastructures.stack

/**
 * Cria uma nova instância de Pilha baseada em Array (Array Stack).
 *
 * Implementação:
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
public expect fun <T> arrayStackOf(): MutableStack<T>
