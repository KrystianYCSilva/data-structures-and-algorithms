@file:JvmName("CollectionViews")

package br.uem.din.datastructures

import br.uem.din.datastructures.linkedlist.ImmutableLinkedList
import br.uem.din.datastructures.queue.Queue
import br.uem.din.datastructures.set.ImmutableMultiset
import br.uem.din.datastructures.skiplist.ImmutableSkipList
import br.uem.din.datastructures.stack.Stack
import br.uem.din.datastructures.tree.SearchTree
import kotlin.jvm.JvmName

/**
 * Cria uma vista (live view) de [Collection] para uma [Stack].
 * 
 * Permite passar a pilha para APIs que exigem [Collection] sem copiar os elementos (Zero-Overhead).
 */
public fun <T> Stack<T>.asCollection(): Collection<T> = object : AbstractCollection<T>() {
    override val size: Int get() = this@asCollection.size
    override fun isEmpty(): Boolean = this@asCollection.isEmpty()
    override fun contains(element: T): Boolean = this@asCollection.contains(element)
    override fun iterator(): Iterator<T> = this@asCollection.iterator()
}

/**
 * Cria uma vista (live view) de [Collection] para uma [Queue].
 */
public fun <T> Queue<T>.asCollection(): Collection<T> = object : AbstractCollection<T>() {
    override val size: Int get() = this@asCollection.size
    override fun isEmpty(): Boolean = this@asCollection.isEmpty()
    override fun contains(element: T): Boolean = this@asCollection.contains(element)
    override fun iterator(): Iterator<T> = this@asCollection.iterator()
}

/**
 * Cria uma vista (live view) de [Collection] para uma [SearchTree].
 */
public fun <T : Comparable<T>> SearchTree<T>.asCollection(): Collection<T> = object : AbstractCollection<T>() {
    override val size: Int get() = this@asCollection.size
    override fun isEmpty(): Boolean = this@asCollection.isEmpty()
    override fun contains(element: T): Boolean = this@asCollection.contains(element)
    override fun iterator(): Iterator<T> = this@asCollection.iterator()
}

/**
 * Cria uma vista (live view) de [Collection] para uma [ImmutableLinkedList].
 */
public fun <T> ImmutableLinkedList<T>.asCollection(): Collection<T> = object : AbstractCollection<T>() {
    override val size: Int get() = this@asCollection.size
    override fun isEmpty(): Boolean = this@asCollection.isEmpty()
    override fun contains(element: T): Boolean = this@asCollection.contains(element)
    override fun iterator(): Iterator<T> = this@asCollection.iterator()
}

/**
 * Cria uma vista (live view) de [Collection] para um [ImmutableMultiset].
 */
public fun <T> ImmutableMultiset<T>.asCollection(): Collection<T> = object : AbstractCollection<T>() {
    override val size: Int get() = this@asCollection.size
    override fun isEmpty(): Boolean = this@asCollection.isEmpty()
    override fun contains(element: T): Boolean = this@asCollection.contains(element)
    override fun iterator(): Iterator<T> = this@asCollection.iterator()
}

/**
 * Cria uma vista (live view) de [Collection] para uma [ImmutableSkipList].
 */
public fun <T : Comparable<T>> ImmutableSkipList<T>.asCollection(): Collection<T> = object : AbstractCollection<T>() {
    override val size: Int get() = this@asCollection.size
    override fun isEmpty(): Boolean = this@asCollection.isEmpty()
    override fun contains(element: T): Boolean = this@asCollection.contains(element)
    override fun iterator(): Iterator<T> = this@asCollection.iterator()
}
