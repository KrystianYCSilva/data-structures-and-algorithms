package br.uem.din.datastructures.stack

import java.util.ArrayDeque

/**
 * Cria uma instância JVM de ArrayStack delegando para java.util.ArrayDeque.
 */
public actual fun <T> arrayStackOf(): MutableStack<T> {
    return JvmArrayStack()
}

/**
 * Wrapper privado para java.util.ArrayDeque.
 */
private class JvmArrayStack<T> : MutableStack<T> {
    private val deque = ArrayDeque<T>()

    override fun push(element: T): T {
        deque.push(element)
        return element
    }

    override fun pop(): T? {
        if (deque.isEmpty()) return null
        return deque.pop()
    }

    override fun peek(): T? {
        if (deque.isEmpty()) return null
        return deque.peek()
    }

    override val size: Int get() = deque.size

    override fun isEmpty(): Boolean = deque.isEmpty()

    override fun contains(element: T): Boolean = deque.contains(element)

    override fun clear(): Unit = deque.clear()

    override fun iterator(): Iterator<T> = deque.iterator()

    override fun toString(): String = deque.toList().toString()
}
