package br.uem.din.datastructures.tree

expect class RedBlackTree<T : Comparable<T>>() {
    fun insert(value: T): Boolean
    fun contains(value: T): Boolean
    fun size(): Int
    fun isEmpty(): Boolean
    // fun height(): Int - Removed as Java TreeSet doesn't expose it
}
