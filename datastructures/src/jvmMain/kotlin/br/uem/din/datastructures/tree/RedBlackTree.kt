package br.uem.din.datastructures.tree

import java.util.TreeSet

/**
 * Implementação JVM da Árvore Rubro-Negra.
 *
 * Delega todas as operações para [java.util.TreeSet], que internamente
 * utiliza uma árvore rubro-negra (`TreeMap`), aproveitando a implementação
 * otimizada da biblioteca padrão do Java.
 */
public actual fun <T : Comparable<T>> redBlackTreeOf(): MutableSearchTree<T> = JvmRedBlackTree()

private class JvmRedBlackTree<T : Comparable<T>> : MutableSearchTree<T> {
    private val set = TreeSet<T>()

    override fun insert(element: T): Boolean = set.add(element)
    override fun remove(element: T): Boolean = set.remove(element)
    override fun contains(element: T): Boolean = set.contains(element)
    override fun inOrder(): List<T> = set.toList()
    override val size: Int get() = set.size
    override fun isEmpty(): Boolean = set.isEmpty()
    override fun iterator(): Iterator<T> = set.iterator()
}
