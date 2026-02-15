package br.uem.din.datastructures.tree

/**
 * Árvore binária de busca (Binary Search Tree — BST).
 *
 * Estrutura de dados de árvore onde, para cada nó, todos os valores na subárvore esquerda
 * são menores e todos os valores na subárvore direita são maiores ou iguais.
 *
 * Esta implementação não é autobalanceada; no pior caso (dados inseridos em ordem),
 * a árvore degenera em uma lista ligada.
 *
 * Complexidades (caso médio / pior caso):
 * - [insert]: O(log n) / O(n)
 * - [contains]: O(log n) / O(n)
 * - [remove]: O(log n) / O(n)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 12 — Binary Search Trees.
 */
class BinarySearchTree<T : Comparable<T>> {

    private var root: BinaryNode<T>? = null

    /**
     * Insere um valor na árvore, mantendo a propriedade BST.
     *
     * Complexidade: O(h), onde h é a altura da árvore.
     *
     * @param value o valor a ser inserido.
     */
    fun insert(value: T) {
        root = insert(root, value)
    }

    /**
     * Inserção recursiva auxiliar que retorna a raiz da subárvore modificada.
     *
     * @param node o nó atual da recursão (pode ser `null` para criar uma folha).
     * @param value o valor a ser inserido.
     * @return o nó raiz da subárvore após a inserção.
     */
    private fun insert(node: BinaryNode<T>?, value: T): BinaryNode<T> {
        node ?: return BinaryNode(value)
        if (value < node.value) {
            node.leftChild = insert(node.leftChild, value)
        } else {
            node.rightChild = insert(node.rightChild, value)
        }
        return node
    }

    /**
     * Verifica se a árvore contém o valor especificado.
     *
     * Utiliza busca iterativa percorrendo a árvore da raiz às folhas.
     *
     * Complexidade: O(h), onde h é a altura da árvore.
     *
     * @param value o valor a ser procurado.
     * @return `true` se o valor existir na árvore, `false` caso contrário.
     */
    fun contains(value: T): Boolean {
        var current = root
        while (current != null) {
            if (current.value == value) {
                return true
            }
            current = if (value < current.value) {
                current.leftChild
            } else {
                current.rightChild
            }
        }
        return false
    }

    /**
     * Remove o valor especificado da árvore, se existir.
     *
     * Utiliza o algoritmo de remoção com substituição pelo sucessor in-order
     * (menor valor da subárvore direita) quando o nó possui dois filhos.
     *
     * Complexidade: O(h), onde h é a altura da árvore.
     *
     * @param value o valor a ser removido.
     */
    fun remove(value: T) {
        root = remove(root, value)
    }

    /**
     * Remoção recursiva auxiliar que retorna a raiz da subárvore modificada.
     *
     * @param node o nó atual da recursão.
     * @param value o valor a ser removido.
     * @return o nó raiz da subárvore após a remoção, ou `null` se a subárvore ficou vazia.
     */
    private fun remove(node: BinaryNode<T>?, value: T): BinaryNode<T>? {
        node ?: return null

        when {
            value == node.value -> {
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
            value < node.value -> node.leftChild = remove(node.leftChild, value)
            else -> node.rightChild = remove(node.rightChild, value)
        }
        return node
    }
}

/**
 * Propriedade de extensão que retorna o nó com o menor valor na subárvore
 * (percorre recursivamente o filho esquerdo).
 *
 * Complexidade: O(h), onde h é a altura da subárvore.
 */
private val <T> BinaryNode<T>.min: BinaryNode<T>
    get() = leftChild?.min ?: this
