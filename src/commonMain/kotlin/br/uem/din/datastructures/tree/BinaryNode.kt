package br.uem.din.datastructures.tree

/**
 * Nó de uma árvore binária (Binary Tree Node).
 *
 * Cada nó armazena um valor e possui referências opcionais para os filhos esquerdo e direito.
 * Os percursos clássicos (in-order, pre-order, post-order) são fornecidos como funções de
 * extensão em [br.uem.din.extensions.TreeTraversalExtensions].
 *
 * @param T o tipo do valor armazenado no nó.
 * @property value o dado armazenado neste nó.
 * @property leftChild referência ao filho esquerdo, ou `null` se ausente.
 * @property rightChild referência ao filho direito, ou `null` se ausente.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 12 — Binary Search Trees.
 */
internal class BinaryNode<T>(public var value: T) {
    public var leftChild: BinaryNode<T>? = null
    public var rightChild: BinaryNode<T>? = null
}
