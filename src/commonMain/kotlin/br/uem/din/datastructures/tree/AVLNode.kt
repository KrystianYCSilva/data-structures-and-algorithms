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

    /**
     * Percurso em-ordem (in-order traversal): esquerda, raiz, direita.
     *
     * Complexidade: O(n), onde n é o número de nós na subárvore.
     *
     * @param visit função de callback invocada para cada valor visitado.
     */
    fun traverseInOrder(visit: (T) -> Unit) {
        leftChild?.traverseInOrder(visit)
        visit(value)
        rightChild?.traverseInOrder(visit)
    }

    /**
     * Percurso pré-ordem (pre-order traversal): raiz, esquerda, direita.
     *
     * Complexidade: O(n), onde n é o número de nós na subárvore.
     *
     * @param visit função de callback invocada para cada valor visitado.
     */
    fun traversePreOrder(visit: (T) -> Unit) {
        visit(value)
        leftChild?.traversePreOrder(visit)
        rightChild?.traversePreOrder(visit)
    }

    /**
     * Percurso pós-ordem (post-order traversal): esquerda, direita, raiz.
     *
     * Complexidade: O(n), onde n é o número de nós na subárvore.
     *
     * @param visit função de callback invocada para cada valor visitado.
     */
    fun traversePostOrder(visit: (T) -> Unit) {
        leftChild?.traversePostOrder(visit)
        rightChild?.traversePostOrder(visit)
        visit(value)
    }
}
