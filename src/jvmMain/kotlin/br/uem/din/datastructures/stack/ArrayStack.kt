package br.uem.din.datastructures.stack

import java.util.Stack as JavaStack

actual class ArrayStack<T> : MutableStack<T> {
    private val javaStack = JavaStack<T>()

    actual override fun push(element: T): T {
        return javaStack.push(element)
    }

    actual override fun pop(): T? {
        if (javaStack.isEmpty()) return null
        return javaStack.pop()
    }

    actual override fun peek(): T? {
        if (javaStack.isEmpty()) return null
        return javaStack.peek()
    }

    actual override fun size(): Int = javaStack.size

    actual override fun isEmpty(): Boolean = javaStack.isEmpty()

    actual override fun toString(): String = javaStack.toString()
}
