package br.uem.din.datastructures.queue

class ArrayQueue<T> : Queue<T> {

    private val storage = arrayListOf<T>()

    override val count: Int
        get() = storage.size

    override fun enqueue(element: T) {
        storage.add(element)
    }

    override fun dequeue(): T? {
        return if (isEmpty) {
            null
        } else {
            storage.removeAt(0)
        }
    }

    override fun peek(): T? = storage.getOrNull(0)

    override fun toString() = buildString {
        appendLine("----top----")
        storage.forEach {
            appendLine(it)
        }
        appendLine("-----------")
    }
}
