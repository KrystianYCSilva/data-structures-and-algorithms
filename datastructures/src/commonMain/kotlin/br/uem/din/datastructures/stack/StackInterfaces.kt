@file:JvmName("Stacks")

package br.uem.din.datastructures.stack

import kotlin.jvm.JvmName

/**
 * Interface somente-leitura para uma Pilha (Stack — LIFO).
 *
 * Segue o padrão do Kotlin stdlib de separar interfaces imutáveis e mutáveis
 * (ex.: [List]/[MutableList], [Set]/[MutableSet]). Implementa [Iterable] para
 * permitir uso com `for`, `map`, `filter`, etc. A iteração percorre do topo à base.
 *
 * @param T o tipo dos elementos armazenados na pilha.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 *
 * @see MutableStack
 */
public interface Stack<T> : Iterable<T> {
    /**
     * Retorna o elemento no topo da pilha sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o elemento no topo, ou `null` se a pilha estiver vazia.
     */
    public fun peek(): T?

    /**
     * Retorna o número de elementos na pilha.
     *
     * Complexidade: O(1).
     *
     * @return a quantidade de elementos.
     */
    public val size: Int

    /**
     * Verifica se a pilha está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    public fun isEmpty(): Boolean

    /**
     * Verifica se a pilha contém o elemento especificado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return `true` se encontrado, `false` caso contrário.
     */
    public fun contains(element: T): Boolean
}

/**
 * Interface mutável para uma Pilha (Stack — LIFO).
 *
 * Estende [Stack] adicionando operações de modificação ([push], [pop], [clear]).
 * Segue o padrão Kotlin de imutável/mutável.
 *
 * @param T o tipo dos elementos armazenados na pilha.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 *
 * @see Stack
 * @see ArrayStack
 * @see LinkedStack
 */
public interface MutableStack<T> : Stack<T> {
    /**
     * Empilha um elemento no topo da pilha.
     *
     * Complexidade: O(1).
     *
     * @param element o elemento a ser empilhado.
     * @return o próprio elemento empilhado.
     */
    public fun push(element: T): T

    /**
     * Remove e retorna o elemento no topo da pilha.
     *
     * Complexidade: O(1).
     *
     * @return o elemento removido, ou `null` se a pilha estiver vazia.
     */
    public fun pop(): T?

    /**
     * Remove todos os elementos da pilha.
     *
     * Complexidade: O(1) na maioria das implementações.
     */
    public fun clear()
}

/**
 * Retorna uma cópia dos elementos como [List] imutável do Kotlin stdlib.
 * A lista é ordenada do topo para a base da pilha.
 *
 * Complexidade: O(n).
 *
 * @return lista imutável dos elementos.
 */
public fun <T> Stack<T>.toList(): List<T> = iterator().asSequence().toList()
