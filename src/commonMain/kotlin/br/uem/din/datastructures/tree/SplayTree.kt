package br.uem.din.datastructures.tree

/**
 * Árvore Splay (Splay Tree) — árvore binária de busca autoajustável.
 *
 * Inventada por Sleator e Tarjan (1985), a Splay Tree reorganiza-se a cada acesso
 * movendo o nó acessado para a raiz através de operações de splaying. As três
 * rotações fundamentais são:
 * - **Zig**: rotação simples quando o nó é filho da raiz.
 * - **Zig-Zig**: duas rotações na mesma direção quando nó e pai são ambos filhos
 *   esquerdos (ou direitos).
 * - **Zig-Zag**: duas rotações em direções opostas quando o nó é filho esquerdo
 *   de um pai direito (ou vice-versa).
 *
 * A propriedade de autoajuste confere localidade temporal: elementos acessados
 * recentemente ficam próximos à raiz, beneficiando padrões de acesso com localidade.
 *
 * Complexidades (amortizadas):
 * - [insert]: O(log n)
 * - [contains]: O(log n)
 * - [remove]: O(log n)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Sleator, D. D. & Tarjan, R. E. "Self-Adjusting Binary Search Trees" (1985);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Problema 13-2;
 *             Goodrich, M. T. & Tamassia, R. "Data Structures and Algorithms in Java", Cap. 11.4.
 */
class SplayTree<T : Comparable<T>> {

    private class Node<T>(var value: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null
    }

    private var root: Node<T>? = null

    /**
     * Número de elementos armazenados na árvore.
     */
    var size: Int = 0
        private set

    /**
     * Insere um valor na árvore Splay.
     *
     * Se a árvore estiver vazia, cria a raiz. Caso contrário, realiza splay do valor
     * mais próximo, e insere o novo nó como raiz, dividindo a árvore anterior conforme
     * a comparação com a raiz splayed.
     *
     * Complexidade amortizada: O(log n).
     *
     * @param value o valor a ser inserido.
     */
    fun insert(value: T) {
        if (root == null) {
            root = Node(value)
            size++
            return
        }
        root = splay(root, value)
        val cmp = value.compareTo(root!!.value)
        if (cmp == 0) return
        val newNode = Node(value)
        if (cmp < 0) {
            newNode.right = root
            newNode.left = root!!.left
            root!!.left = null
        } else {
            newNode.left = root
            newNode.right = root!!.right
            root!!.right = null
        }
        root = newNode
        size++
    }

    /**
     * Verifica se a árvore contém o valor especificado.
     *
     * Realiza splay do valor buscado. Se o valor estiver presente, ele será
     * movido para a raiz.
     *
     * Complexidade amortizada: O(log n).
     *
     * @param value o valor a ser procurado.
     * @return `true` se o valor existir na árvore, `false` caso contrário.
     */
    fun contains(value: T): Boolean {
        root = splay(root, value)
        return root != null && root!!.value == value
    }

    /**
     * Remove o valor especificado da árvore Splay.
     *
     * Realiza splay do valor a ser removido. Se encontrado na raiz, a raiz é
     * substituída pelo resultado do merge das subárvores esquerda e direita.
     *
     * Complexidade amortizada: O(log n).
     *
     * @param value o valor a ser removido.
     */
    fun remove(value: T) {
        root ?: return
        root = splay(root, value)
        if (root!!.value != value) return
        if (root!!.left == null) {
            root = root!!.right
        } else {
            val rightSubtree = root!!.right
            root = splay(root!!.left, value)
            root!!.right = rightSubtree
        }
        size--
    }

    /**
     * Operação de splay: move o nó com valor mais próximo de [value] para a raiz
     * da subárvore enraizada em [node], utilizando rotações zig, zig-zig e zig-zag.
     *
     * Implementação top-down conforme descrito por Sleator e Tarjan (1985).
     *
     * Complexidade amortizada: O(log n).
     *
     * @param node a raiz da subárvore.
     * @param value o valor-alvo do splay.
     * @return a nova raiz da subárvore após o splay.
     */
    private fun splay(node: Node<T>?, value: T): Node<T>? {
        node ?: return null
        val cmp = value.compareTo(node.value)
        if (cmp < 0) {
            node.left ?: return node
            val cmp2 = value.compareTo(node.left!!.value)
            if (cmp2 < 0) {
                node.left!!.left = splay(node.left!!.left, value)
                val rotated = rotateRight(node)
                return if (rotated.left != null) rotateRight(rotated) else rotated
            } else if (cmp2 > 0) {
                node.left!!.right = splay(node.left!!.right, value)
                if (node.left!!.right != null) {
                    node.left = rotateLeft(node.left!!)
                }
                return rotateRight(node)
            }
            return rotateRight(node)
        } else if (cmp > 0) {
            node.right ?: return node
            val cmp2 = value.compareTo(node.right!!.value)
            if (cmp2 > 0) {
                node.right!!.right = splay(node.right!!.right, value)
                val rotated = rotateLeft(node)
                return if (rotated.right != null) rotateLeft(rotated) else rotated
            } else if (cmp2 < 0) {
                node.right!!.left = splay(node.right!!.left, value)
                if (node.right!!.left != null) {
                    node.right = rotateRight(node.right!!)
                }
                return rotateLeft(node)
            }
            return rotateLeft(node)
        }
        return node
    }

    /**
     * Rotação simples à direita.
     *
     * Complexidade: O(1).
     *
     * @param node o nó a ser rotacionado.
     * @return a nova raiz da subárvore rotacionada.
     */
    private fun rotateRight(node: Node<T>): Node<T> {
        val pivot = node.left!!
        node.left = pivot.right
        pivot.right = node
        return pivot
    }

    /**
     * Rotação simples à esquerda.
     *
     * Complexidade: O(1).
     *
     * @param node o nó a ser rotacionado.
     * @return a nova raiz da subárvore rotacionada.
     */
    private fun rotateLeft(node: Node<T>): Node<T> {
        val pivot = node.right!!
        node.right = pivot.left
        pivot.left = node
        return pivot
    }

    /**
     * Retorna os elementos da árvore em ordem crescente (in-order traversal).
     *
     * Complexidade: O(n).
     *
     * @return lista com todos os elementos em ordem.
     */
    fun inOrder(): List<T> {
        val result = mutableListOf<T>()
        inOrder(root, result)
        return result
    }

    private fun inOrder(node: Node<T>?, result: MutableList<T>) {
        node ?: return
        inOrder(node.left, result)
        result.add(node.value)
        inOrder(node.right, result)
    }
}
