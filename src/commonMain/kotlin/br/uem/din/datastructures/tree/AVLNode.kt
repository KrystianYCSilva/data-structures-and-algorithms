package br.uem.din.datastructures.tree

/**
 * Nó de uma árvore AVL (AVL Tree Node).
 *
 * Estende o conceito de nó binário com o campo [height] e propriedades derivadas
 * [leftHeight], [rightHeight] e [balanceFactor], utilizadas pelo algoritmo de
 * balanceamento AVL.
 *
 * O fator de balanceamento (balance factor) é definido como a diferença entre
 * a altura da subárvore esquerda e a direita. Em uma árvore AVL válida,
 * este valor é sempre -1, 0 ou 1.
 *
 * Os percursos clássicos (in-order, pre-order, post-order) são fornecidos como funções de
 * extensão em [br.uem.din.extensions.TreeTraversalExtensions].
 *
 * @param T o tipo do valor armazenado no nó.
 * @property value o dado armazenado neste nó.
 *
 * Referência: Adelson-Velsky, G. M. & Landis, E. M. "An algorithm for the organization of information" (1962);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 13 (árvores balanceadas).
 */
class AVLNode<T>(var value: T) {
    /** Filho esquerdo deste nó, ou `null` se ausente. */
    var leftChild: AVLNode<T>? = null
    /** Filho direito deste nó, ou `null` se ausente. */
    var rightChild: AVLNode<T>? = null

    /** Altura deste nó na árvore (folhas têm altura 0). */
    var height = 0

    /** Altura da subárvore esquerda; retorna -1 se o filho esquerdo não existir. */
    val leftHeight: Int
        get() = leftChild?.height ?: -1

    /** Altura da subárvore direita; retorna -1 se o filho direito não existir. */
    val rightHeight: Int
        get() = rightChild?.height ?: -1

    /** Fator de balanceamento: leftHeight - rightHeight. Valores fora de [-1, 1] indicam desbalanceamento. */
    val balanceFactor: Int
        get() = leftHeight - rightHeight
}
