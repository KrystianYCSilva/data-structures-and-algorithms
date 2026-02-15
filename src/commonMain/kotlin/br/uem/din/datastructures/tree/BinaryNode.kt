package br.uem.din.datastructures.tree

/**
 * Nó de uma árvore binária (Binary Tree Node).
 *
 * Cada nó armazena um valor e possui referências opcionais para os filhos esquerdo e direito.
 * Oferece os três percursos clássicos: in-order, pre-order e post-order.
 *
 * @param T o tipo do valor armazenado no nó.
 * @property value o dado armazenado neste nó.
 * @property leftChild referência ao filho esquerdo, ou `null` se ausente.
 * @property rightChild referência ao filho direito, ou `null` se ausente.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 12 — Binary Search Trees.
 */
class BinaryNode<T>(var value: T) {
    var leftChild: BinaryNode<T>? = null
    var rightChild: BinaryNode<T>? = null

    /**
     * Percurso em-ordem (in-order traversal): esquerda, raiz, direita.
     *
     * Em uma BST, produz os elementos em ordem crescente.
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
