package br.uem.din.datastructures

import br.uem.din.datastructures.queue.MutableQueue
import br.uem.din.datastructures.queue.Queue
import br.uem.din.datastructures.stack.MutableStack
import br.uem.din.datastructures.stack.Stack

/**
 * Funções auxiliares para criar vistas somente-leitura (read-only views) de estruturas mutáveis.
 *
 * Seguem o padrão do Kotlin stdlib onde coleções mutáveis podem ser vistas como imutáveis
 * sem cópia defensiva, incentivando o uso funcional. A vista é live — alterações na estrutura
 * original são refletidas na vista.
 *
 * @see Stack
 * @see Queue
 */

/**
 * Cria uma vista somente-leitura desta pilha mutável.
 *
 * A vista é live (sem cópia defensiva): alterações na pilha original são refletidas.
 *
 * Complexidade: O(1) para criação.
 *
 * @return [Stack] somente-leitura delegando a esta instância.
 */
fun <T> MutableStack<T>.asReadOnly(): Stack<T> = object : Stack<T> {
    override fun peek(): T? = this@asReadOnly.peek()
    override fun size(): Int = this@asReadOnly.size()
    override fun isEmpty(): Boolean = this@asReadOnly.isEmpty()
    override fun contains(element: T): Boolean = this@asReadOnly.contains(element)
    override fun iterator(): Iterator<T> = this@asReadOnly.iterator()
    override fun toString(): String = this@asReadOnly.toString()
}

/**
 * Cria uma vista somente-leitura desta fila mutável.
 *
 * A vista é live (sem cópia defensiva): alterações na fila original são refletidas.
 *
 * Complexidade: O(1) para criação.
 *
 * @return [Queue] somente-leitura delegando a esta instância.
 */
fun <T> MutableQueue<T>.asReadOnly(): Queue<T> = object : Queue<T> {
    override fun peek(): T? = this@asReadOnly.peek()
    override fun size(): Int = this@asReadOnly.size()
    override fun isEmpty(): Boolean = this@asReadOnly.isEmpty()
    override fun contains(element: T): Boolean = this@asReadOnly.contains(element)
    override fun iterator(): Iterator<T> = this@asReadOnly.iterator()
    override fun toString(): String = this@asReadOnly.toString()
}
