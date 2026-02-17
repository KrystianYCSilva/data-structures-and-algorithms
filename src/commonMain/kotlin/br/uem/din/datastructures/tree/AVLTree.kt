package br.uem.din.datastructures.tree

import kotlin.math.max

/**
 * Árvore AVL (AVL Tree) — árvore binária de busca autobalanceada.
 *
 * Inventada por Adelson-Velsky e Landis (1962), mantém a propriedade de que o fator
 * de balanceamento de cada nó está em {-1, 0, 1}, garantindo altura O(log n)
 * e, consequentemente, operações eficientes.
 *
 * O balanceamento é restaurado após inserções e remoções através de rotações simples
 * e duplas (left, right, left-right, right-left).
 *
 * Complexidades (garantidas pelo balanceamento):
 * - [insert]: O(log n)
 * - [remove]: O(log n)
 * - [contains]: O(log n)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Adelson-Velsky, G. M. & Landis, E. M. "An algorithm for the organization of information" (1962);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 13.
 */
public class AVLTree<T : Comparable<T>> : MutableSearchTree<T> {

    /** Raiz da árvore, ou `null` se a árvore estiver vazia. */
    internal var root: AVLNode<T>? = null
        private set

    /**
     * Número de elementos armazenados na árvore.
     */
    public override var size: Int = 0
        private set

    /**
     * Verifica se a árvore está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se a árvore não contiver elementos.
     */
    public override fun isEmpty(): Boolean = size == 0

    /**
     * Insere um elemento na árvore AVL, rebalanceando se necessário.
     *
     * Rejeita duplicatas: se o elemento já existir, retorna `false`.
     *
     * Complexidade: O(log n).
     *
     * @param element o elemento a ser inserido.
     * @return `true` se o elemento foi inserido, `false` se já existia.
     */
    public override fun insert(element: T): Boolean {
        if (contains(element)) return false
        root = insert(root, element)
        size++
        return true
    }

    private fun insert(node: AVLNode<T>?, element: T): AVLNode<T> {
        node ?: return AVLNode(element)
        if (element < node.value) {
            node.leftChild = insert(node.leftChild, element)
        } else {
            node.rightChild = insert(node.rightChild, element)
        }
        val balancedNode = balanced(node)
        balancedNode.height = max(balancedNode.leftHeight, balancedNode.rightHeight) + 1
        return balancedNode
    }

    /**
     * Remove o elemento especificado da árvore AVL, rebalanceando se necessário.
     *
     * Complexidade: O(log n).
     *
     * @param element o elemento a ser removido.
     * @return `true` se o elemento foi removido, `false` se não existia.
     */
    public override fun remove(element: T): Boolean {
        if (!contains(element)) return false
        root = remove(root, element)
        size--
        return true
    }

    private fun remove(node: AVLNode<T>?, element: T): AVLNode<T>? {
        node ?: return null
        when {
            element == node.value -> {
                if (node.leftChild == null && node.rightChild == null) {
                    return null
                }
                if (node.leftChild == null) {
                    return node.rightChild
                }
                if (node.rightChild == null) {
                    return node.leftChild
                }
                node.rightChild?.min?.value?.let {
                    node.value = it
                }
                node.rightChild = remove(node.rightChild, node.value)
            }
            element < node.value -> node.leftChild = remove(node.leftChild, element)
            else -> node.rightChild = remove(node.rightChild, element)
        }
        val balancedNode = balanced(node)
        balancedNode.height = max(balancedNode.leftHeight, balancedNode.rightHeight) + 1
        return balancedNode
    }

    /**
     * Verifica se a árvore contém o elemento especificado.
     *
     * Complexidade: O(log n).
     *
     * @param element o elemento a ser procurado.
     * @return `true` se o elemento existir na árvore, `false` caso contrário.
     */
    public override fun contains(element: T): Boolean {
        var current = root
        while (current != null) {
            if (current.value == element) {
                return true
            }
            current = if (element < current.value) {
                current.leftChild
            } else {
                current.rightChild
            }
        }
        return false
    }

    /**
     * Rotação simples à esquerda (left rotation).
     *
     * Utilizada quando a subárvore direita está mais pesada (balanceFactor == -2).
     *
     * Complexidade: O(1).
     *
     * @param node o nó desbalanceado.
     * @return o novo nó raiz da subárvore rotacionada.
     */
    private fun leftRotate(node: AVLNode<T>): AVLNode<T> {
        val pivot = node.rightChild!!
        node.rightChild = pivot.leftChild
        pivot.leftChild = node
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        return pivot
    }

    /**
     * Rotação simples à direita (right rotation).
     *
     * Utilizada quando a subárvore esquerda está mais pesada (balanceFactor == 2).
     *
     * Complexidade: O(1).
     *
     * @param node o nó desbalanceado.
     * @return o novo nó raiz da subárvore rotacionada.
     */
    private fun rightRotate(node: AVLNode<T>): AVLNode<T> {
        val pivot = node.leftChild!!
        node.leftChild = pivot.rightChild
        pivot.rightChild = node
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        return pivot
    }

    /**
     * Rotação dupla direita-esquerda (right-left rotation).
     *
     * Combinação de rotação direita no filho direito seguida de rotação esquerda no nó.
     *
     * Complexidade: O(1).
     *
     * @param node o nó desbalanceado.
     * @return o novo nó raiz da subárvore rotacionada.
     */
    private fun rightLeftRotate(node: AVLNode<T>): AVLNode<T> {
        val rightChild = node.rightChild ?: return node
        node.rightChild = rightRotate(rightChild)
        return leftRotate(node)
    }

    /**
     * Rotação dupla esquerda-direita (left-right rotation).
     *
     * Combinação de rotação esquerda no filho esquerdo seguida de rotação direita no nó.
     *
     * Complexidade: O(1).
     *
     * @param node o nó desbalanceado.
     * @return o novo nó raiz da subárvore rotacionada.
     */
    private fun leftRightRotate(node: AVLNode<T>): AVLNode<T> {
        val leftChild = node.leftChild ?: return node
        node.leftChild = leftRotate(leftChild)
        return rightRotate(node)
    }

    /**
     * Aplica a rotação apropriada para restaurar o balanceamento AVL do nó.
     *
     * Analisa o [AVLNode.balanceFactor] do nó e de seus filhos para determinar
     * qual tipo de rotação é necessária.
     *
     * Complexidade: O(1).
     *
     * @param node o nó possivelmente desbalanceado.
     * @return o nó raiz da subárvore balanceada.
     */
    private fun balanced(node: AVLNode<T>): AVLNode<T> {
        return when (node.balanceFactor) {
            2 -> {
                if (node.leftChild?.balanceFactor == -1) {
                    leftRightRotate(node)
                } else {
                    rightRotate(node)
                }
            }
            -2 -> {
                if (node.rightChild?.balanceFactor == 1) {
                    rightLeftRotate(node)
                } else {
                    leftRotate(node)
                }
            }
            else -> node
        }
    }

    /**
     * Retorna os elementos da árvore em ordem crescente (in-order traversal).
     *
     * Complexidade: O(n).
     *
     * @return lista com todos os elementos em ordem.
     */
    public override fun inOrder(): List<T> {
        val result = mutableListOf<T>()
        root?.traverseInOrder { result.add(it) }
        return result
    }

    /**
     * Retorna um iterador sobre os elementos da árvore em ordem crescente.
     */
    public override fun iterator(): Iterator<T> = inOrder().iterator()
}

/**
 * Propriedade de extensão que retorna o nó com o menor valor na subárvore AVL
 * (percorre recursivamente o filho esquerdo).
 *
 * Complexidade: O(log n) em uma árvore AVL balanceada.
 */
private val <T> AVLNode<T>.min: AVLNode<T>
    get() = leftChild?.min ?: this
