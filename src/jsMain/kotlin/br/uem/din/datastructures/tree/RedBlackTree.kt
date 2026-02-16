package br.uem.din.datastructures.tree

actual class RedBlackTree<T : Comparable<T>> {
    private val impl = RedBlackTreeImpl<T>()

    actual fun insert(value: T): Boolean = impl.insert(value)
    actual fun contains(value: T): Boolean = impl.contains(value)
    actual fun size(): Int = impl.size()
    actual fun isEmpty(): Boolean = impl.isEmpty()
}
