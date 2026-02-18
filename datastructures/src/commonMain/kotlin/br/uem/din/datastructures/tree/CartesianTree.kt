package br.uem.din.datastructures.tree

/**
 * Árvore Cartesiana (Cartesian Tree).
 *
 * Introduzida por Vuillemin (1980), a Árvore Cartesiana é uma árvore binária construída
 * a partir de uma sequência de valores, satisfazendo simultaneamente:
 * 1. **Propriedade de heap (min-heap)**: o valor de cada nó é menor ou igual ao
 *    valor de seus filhos.
 * 2. **Propriedade de BST por índice**: o percurso in-order recupera a sequência
 *    original na ordem dos índices.
 *
 * A construção é realizada em O(n) utilizando uma pilha (stack-based algorithm),
 * processando os elementos da esquerda para a direita e mantendo a propriedade
 * de heap com operações de desempilhamento.
 *
 * Aplicações incluem Range Minimum Queries (RMQ), Treaps e parsing de expressões.
 *
 * Complexidades:
 * - [buildFromArray]: O(n)
 * - [inOrder]: O(n)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Vuillemin, J. "A Unifying Look at Data Structures" (1980);
 *             Gabow, H. N., Bentley, J. L. & Tarjan, R. E. "Scaling and Related Techniques
 *             for Geometry Problems" (1984);
 *             Bender, M. A. & Farach-Colton, M. "The LCA Problem Revisited" (2000).
 */
public class CartesianTree<T : Comparable<T>> {

    /**
     * Nó da Árvore Cartesiana.
     *
     * @property value o valor armazenado.
     * @property index o índice original do elemento na sequência de entrada.
     * @property left filho esquerdo, ou `null` se ausente.
     * @property right filho direito, ou `null` se ausente.
     */
    public class Node<T>(
        public val value: T,
        public val index: Int,
        public var left: Node<T>? = null,
        public var right: Node<T>? = null
    )

    /**
     * Raiz da árvore, ou `null` se a árvore estiver vazia.
     */
    public var root: Node<T>? = null
        private set

    /**
     * Constrói a Árvore Cartesiana a partir de um array de valores usando o algoritmo
     * baseado em pilha (stack-based linear construction).
     *
     * Processa os elementos da esquerda para a direita. Para cada novo elemento,
     * desempilha todos os nós com valor maior (violam a propriedade min-heap),
     * e o último desempilhado torna-se filho esquerdo do novo nó. O novo nó
     * torna-se filho direito do topo da pilha (se existir).
     *
     * Complexidade: O(n), pois cada elemento é empilhado e desempilhado no máximo uma vez.
     *
     * @param values o array de valores a partir do qual a árvore será construída.
     */
    public fun buildFromArray(values: List<T>) {
        if (values.isEmpty()) {
            root = null
            return
        }
        val stack = ArrayDeque<Node<T>>()
        for (i in values.indices) {
            val newNode = Node(values[i], i)
            var lastPopped: Node<T>? = null
            while (stack.isNotEmpty() && stack.last().value > newNode.value) {
                lastPopped = stack.removeLast()
            }
            newNode.left = lastPopped
            if (stack.isNotEmpty()) {
                stack.last().right = newNode
            }
            stack.addLast(newNode)
        }
        root = stack.first()
    }

    /**
     * Retorna os valores da árvore em percurso in-order, que corresponde
     * à sequência original de entrada.
     *
     * Complexidade: O(n).
     *
     * @return lista com os valores em ordem dos índices originais.
     */
    public fun inOrder(): List<T> {
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

    /**
     * Verifica se a árvore satisfaz a propriedade de min-heap.
     *
     * Complexidade: O(n).
     *
     * @return `true` se a propriedade de min-heap for válida para todos os nós.
     */
    public fun isValidMinHeap(): Boolean = isValidMinHeap(root)

    private fun isValidMinHeap(node: Node<T>?): Boolean {
        node ?: return true
        val leftValid = node.left?.let { node.value <= it.value && isValidMinHeap(it) } ?: true
        val rightValid = node.right?.let { node.value <= it.value && isValidMinHeap(it) } ?: true
        return leftValid && rightValid
    }
}
