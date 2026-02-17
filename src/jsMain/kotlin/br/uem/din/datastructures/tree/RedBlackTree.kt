package br.uem.din.datastructures.tree

/**
 * Implementação JavaScript da Árvore Rubro-Negra.
 *
 * Delega todas as operações para [RedBlackTreeImpl], implementação
 * manual CLRS em Kotlin puro.
 */
public actual fun <T : Comparable<T>> redBlackTreeOf(): MutableSearchTree<T> = JsRedBlackTree()

private class JsRedBlackTree<T : Comparable<T>> : MutableSearchTree<T> {
    private val impl = RedBlackTreeImpl<T>()

    override fun insert(element: T): Boolean = impl.insert(element)
    override fun remove(element: T): Boolean = impl.remove(element)
    override fun contains(element: T): Boolean = impl.contains(element)
    override fun inOrder(): List<T> = impl.inOrder()
    override val size: Int get() = impl.size()
    override fun isEmpty(): Boolean = impl.isEmpty()
}
