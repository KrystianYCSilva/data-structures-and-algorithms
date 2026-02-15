package br.uem.din.datastructures.stack

import java.util.ArrayDeque

/**
 * Implementação JVM de [ArrayStack], delegando a [java.util.ArrayDeque].
 *
 * Utiliza [ArrayDeque] (não-sincronizado) em vez da legada [java.util.Stack] (sincronizada),
 * seguindo a recomendação do JDK para pilhas de uso geral.
 *
 * @param T o tipo dos elementos armazenados na pilha.
 *
 * Referência: JDK ArrayDeque — "A more complete and consistent set of LIFO stack operations."
 */
actual class ArrayStack<T> : MutableStack<T> {
    private val deque = ArrayDeque<T>()

    actual override fun push(element: T): T {
        deque.push(element)
        return element
    }

    actual override fun pop(): T? {
        if (deque.isEmpty()) return null
        return deque.pop()
    }

    actual override fun peek(): T? {
        if (deque.isEmpty()) return null
        return deque.peek()
    }

    actual override val size: Int get() = deque.size

    actual override fun isEmpty(): Boolean = deque.isEmpty()

    actual override fun contains(element: T): Boolean = deque.contains(element)

    actual override fun clear() = deque.clear()

    actual override fun iterator(): Iterator<T> = deque.iterator()

    actual override fun toString(): String = deque.toList().toString()
}
