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
public class BinarySearchTree<T : Comparable<T>> : MutableSearchTree<T> {

    private var root: BinaryNode<T>? = null

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
     * Insere um elemento na árvore, mantendo a propriedade BST.
     *
     * Rejeita duplicatas: se o elemento já existir, retorna `false`.
     *
     * Complexidade: O(h), onde h é a altura da árvore.
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

    private fun insert(node: BinaryNode<T>?, element: T): BinaryNode<T> {
        node ?: return BinaryNode(element)
        if (element < node.value) {
            node.leftChild = insert(node.leftChild, element)
        } else {
            node.rightChild = insert(node.rightChild, element)
        }
        return node
    }

    /**
     * Verifica se a árvore contém o elemento especificado.
     *
     * Utiliza busca iterativa percorrendo a árvore da raiz às folhas.
     *
     * Complexidade: O(h), onde h é a altura da árvore.
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
     * Remove o elemento especificado da árvore, se existir.
     *
     * Utiliza o algoritmo de remoção com substituição pelo sucessor in-order
     * (menor valor da subárvore direita) quando o nó possui dois filhos.
     *
     * Complexidade: O(h), onde h é a altura da árvore.
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

    private fun remove(node: BinaryNode<T>?, element: T): BinaryNode<T>? {
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
        return node
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
}

/**
 * Propriedade de extensão que retorna o nó com o menor valor na subárvore
 * (percorre recursivamente o filho esquerdo).
 *
 * Complexidade: O(h), onde h é a altura da subárvore.
 */
private val <T> BinaryNode<T>.min: BinaryNode<T>
    get() = leftChild?.min ?: this
