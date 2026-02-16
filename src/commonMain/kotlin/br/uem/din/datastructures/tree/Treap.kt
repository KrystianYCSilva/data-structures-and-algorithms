package br.uem.din.datastructures.tree

import kotlin.random.Random

/**
 * Treap — árvore binária de busca aleatória com propriedade de heap.
 *
 * Inventada por Aragon e Seidel (1989), a Treap combina as propriedades de uma
 * BST (ordenada pelas chaves) e de um heap (ordenado por prioridades aleatórias).
 * Cada nó recebe uma prioridade aleatória na inserção; a árvore mantém a propriedade
 * BST pelas chaves e a propriedade max-heap pelas prioridades.
 *
 * As operações fundamentais [split] e [merge] permitem implementar inserção e remoção
 * de forma elegante. A aleatoriedade garante que a árvore resultante é equivalente,
 * em expectativa, a uma BST construída por inserções em ordem aleatória.
 *
 * Complexidades (esperadas):
 * - [insert]: O(log n)
 * - [contains]: O(log n)
 * - [remove]: O(log n)
 * - [split]: O(log n)
 * - [merge]: O(log n)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Aragon, C. R. & Seidel, R. "Randomized Search Trees" (1989);
 *             Naor, M. & Nissim, K. "Certificate Revocation and Certificate Update" (1998);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Problema 13-4 (Treaps).
 */
class Treap<T : Comparable<T>> {

    private class Node<T>(
        var value: T,
        var priority: Long = Random.nextLong(),
        var left: Node<T>? = null,
        var right: Node<T>? = null
    )

    private var root: Node<T>? = null

    /**
     * Número de elementos armazenados na Treap.
     */
    var size: Int = 0
        private set

    /**
     * Divide a Treap em duas Treaps: uma com todos os valores menores que [value]
     * e outra com valores maiores ou iguais a [value].
     *
     * Operação fundamental da Treap utilizada por [insert] e [remove].
     *
     * Complexidade esperada: O(log n).
     *
     * @param node a raiz da subárvore a ser dividida.
     * @param value o valor de corte.
     * @return par (left, right) onde left contém valores < [value] e right contém valores >= [value].
     */
    private fun split(node: Node<T>?, value: T): Pair<Node<T>?, Node<T>?> {
        node ?: return Pair(null, null)
        return if (node.value < value) {
            val (left, right) = split(node.right, value)
            node.right = left
            Pair(node, right)
        } else {
            val (left, right) = split(node.left, value)
            node.left = right
            Pair(left, node)
        }
    }

    /**
     * Mescla duas Treaps em uma única Treap, assumindo que todos os valores
     * em [left] são menores que todos os valores em [right].
     *
     * A mesclagem respeita a propriedade max-heap das prioridades.
     *
     * Complexidade esperada: O(log n).
     *
     * @param left a Treap com valores menores.
     * @param right a Treap com valores maiores.
     * @return a raiz da Treap mesclada.
     */
    private fun merge(left: Node<T>?, right: Node<T>?): Node<T>? {
        if (left == null) return right
        if (right == null) return left
        return if (left.priority > right.priority) {
            left.right = merge(left.right, right)
            left
        } else {
            right.left = merge(left, right.left)
            right
        }
    }

    /**
     * Insere um valor na Treap.
     *
     * Realiza split pelo valor, verifica se já existe e, se não, cria novo nó
     * e mescla as três partes.
     *
     * Complexidade esperada: O(log n).
     *
     * @param value o valor a ser inserido.
     */
    fun insert(value: T) {
        if (contains(value)) return
        val (left, right) = split(root, value)
        val newNode = Node(value)
        root = merge(merge(left, newNode), right)
        size++
    }

    /**
     * Verifica se a Treap contém o valor especificado.
     *
     * Realiza busca BST padrão percorrendo a árvore da raiz às folhas.
     *
     * Complexidade esperada: O(log n).
     *
     * @param value o valor a ser procurado.
     * @return `true` se o valor existir na Treap, `false` caso contrário.
     */
    fun contains(value: T): Boolean {
        var current = root
        while (current != null) {
            val cmp = value.compareTo(current.value)
            current = when {
                cmp == 0 -> return true
                cmp < 0 -> current.left
                else -> current.right
            }
        }
        return false
    }

    /**
     * Remove o valor especificado da Treap.
     *
     * Localiza o nó, substitui-o pelo merge de seus filhos.
     *
     * Complexidade esperada: O(log n).
     *
     * @param value o valor a ser removido.
     */
    fun remove(value: T) {
        root = remove(root, value)
    }

    private fun remove(node: Node<T>?, value: T): Node<T>? {
        node ?: return null
        val cmp = value.compareTo(node.value)
        when {
            cmp < 0 -> node.left = remove(node.left, value)
            cmp > 0 -> node.right = remove(node.right, value)
            else -> {
                size--
                return merge(node.left, node.right)
            }
        }
        return node
    }

    /**
     * Retorna os elementos da Treap em ordem crescente (in-order traversal).
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
