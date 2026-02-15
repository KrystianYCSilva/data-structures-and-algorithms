package br.uem.din.datastructures.stack

class ArrayStack<T> : Stack<T> {

    private val storage = arrayListOf<T>()

    override val count: Int
        get() = storage.size

    override fun push(element: T) {
        storage.add(element)
    }

    override fun pop(): T? {
        return if (isEmpty) {
            null
        } else {
            storage.removeAt(count - 1)
        }
    }

    override fun peek(): T? = storage.lastOrNull()

    override fun toString() = buildString {
        appendLine("----top----")
        storage.reversed().forEach {
            appendLine(it)
        }
        appendLine("-----------")
    }
}
