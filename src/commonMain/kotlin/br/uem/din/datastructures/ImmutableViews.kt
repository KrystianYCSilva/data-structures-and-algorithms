package br.uem.din.datastructures

import br.uem.din.datastructures.linkedlist.ImmutableLinkedList
import br.uem.din.datastructures.linkedlist.MutableLinkedList
import br.uem.din.datastructures.queue.MutableQueue
import br.uem.din.datastructures.queue.Queue
import br.uem.din.datastructures.stack.MutableStack
import br.uem.din.datastructures.stack.Stack
import br.uem.din.datastructures.tree.MutableSearchTree
import br.uem.din.datastructures.tree.SearchTree
import kotlin.jvm.JvmInline

/**
 * Funções auxiliares para criar vistas somente-leitura (read-only views) de estruturas mutáveis.
 *
 * Utiliza `value class` (Inline Classes) para garantir abstração de custo zero (Zero-Overhead)
 * na JVM. A vista é "live": alterações na estrutura original são refletidas imediatamente.
 */

/**
 * Cria uma vista somente-leitura desta pilha mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableStack<T>.asReadOnly(): Stack<T> = ReadOnlyStack(this)

@JvmInline
private value class ReadOnlyStack<T>(private val delegate: MutableStack<T>) : Stack<T> {
    override fun peek(): T? = delegate.peek()
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta fila mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableQueue<T>.asReadOnly(): Queue<T> = ReadOnlyQueue(this)

@JvmInline
private value class ReadOnlyQueue<T>(private val delegate: MutableQueue<T>) : Queue<T> {
    override fun peek(): T? = delegate.peek()
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta lista ligada mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableLinkedList<T>.asReadOnly(): ImmutableLinkedList<T> = ReadOnlyLinkedList(this)

@JvmInline
private value class ReadOnlyLinkedList<T>(private val delegate: MutableLinkedList<T>) : ImmutableLinkedList<T> {
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun indexOf(element: T): Int = delegate.indexOf(element)
    override fun toList(): List<T> = delegate.toList()
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta árvore de busca mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T : Comparable<T>> MutableSearchTree<T>.asReadOnly(): SearchTree<T> = ReadOnlySearchTree(this)

@JvmInline
private value class ReadOnlySearchTree<T : Comparable<T>>(private val delegate: MutableSearchTree<T>) : SearchTree<T> {
    override fun contains(element: T): Boolean = delegate.contains(element)
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun inOrder(): List<T> = delegate.inOrder()
    override fun toString(): String = delegate.toString()
}
