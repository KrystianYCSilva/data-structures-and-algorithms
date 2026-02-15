package br.uem.din.datastructures.tree

import java.util.TreeSet

actual class RedBlackTree<T : Comparable<T>> {
    private val set = TreeSet<T>()

    actual fun insert(value: T): Boolean = set.add(value)
    actual fun contains(value: T): Boolean = set.contains(value)
    actual fun size(): Int = set.size
    actual fun isEmpty(): Boolean = set.isEmpty()
}
