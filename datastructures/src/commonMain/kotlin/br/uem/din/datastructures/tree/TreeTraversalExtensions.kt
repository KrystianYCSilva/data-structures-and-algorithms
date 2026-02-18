package br.uem.din.datastructures.tree

/**
 * Extensões de travessia para nós de árvores binárias.
 *
 * Estas funções de extensão fornecem os três percursos clássicos (in-order, pre-order, post-order)
 * para qualquer tipo de nó binário que possua propriedades `leftChild` e `rightChild`.
 *
 * Complexidade: O(n) para todos os percursos, onde n é o número de nós na subárvore.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 12 — Binary Search Trees.
 */

/**
 * Percurso em-ordem (in-order traversal): esquerda, raiz, direita.
 *
 * Em uma BST, produz os elementos em ordem crescente.
 *
 * @param visit função de callback invocada para cada valor visitado.
 */
internal fun <T> BinaryNode<T>.traverseInOrder(visit: (T) -> Unit) {
    leftChild?.traverseInOrder(visit)
    visit(value)
    rightChild?.traverseInOrder(visit)
}

/**
 * Percurso pré-ordem (pre-order traversal): raiz, esquerda, direita.
 *
 * @param visit função de callback invocada para cada valor visitado.
 */
internal fun <T> BinaryNode<T>.traversePreOrder(visit: (T) -> Unit) {
    visit(value)
    leftChild?.traversePreOrder(visit)
    rightChild?.traversePreOrder(visit)
}

/**
 * Percurso pós-ordem (post-order traversal): esquerda, direita, raiz.
 *
 * @param visit função de callback invocada para cada valor visitado.
 */
internal fun <T> BinaryNode<T>.traversePostOrder(visit: (T) -> Unit) {
    leftChild?.traversePostOrder(visit)
    rightChild?.traversePostOrder(visit)
    visit(value)
}

/**
 * Percurso em-ordem (in-order traversal): esquerda, raiz, direita.
 *
 * @param visit função de callback invocada para cada valor visitado.
 */
internal fun <T> AVLNode<T>.traverseInOrder(visit: (T) -> Unit) {
    leftChild?.traverseInOrder(visit)
    visit(value)
    rightChild?.traverseInOrder(visit)
}

/**
 * Percurso pré-ordem (pre-order traversal): raiz, esquerda, direita.
 *
 * @param visit função de callback invocada para cada valor visitado.
 */
internal fun <T> AVLNode<T>.traversePreOrder(visit: (T) -> Unit) {
    visit(value)
    leftChild?.traversePreOrder(visit)
    rightChild?.traversePreOrder(visit)
}

/**
 * Percurso pós-ordem (post-order traversal): esquerda, direita, raiz.
 *
 * @param visit função de callback invocada para cada valor visitado.
 */
internal fun <T> AVLNode<T>.traversePostOrder(visit: (T) -> Unit) {
    leftChild?.traversePostOrder(visit)
    rightChild?.traversePostOrder(visit)
    visit(value)
}
