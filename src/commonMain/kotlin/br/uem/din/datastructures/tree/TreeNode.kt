package br.uem.din.datastructures.tree

/**
 * Nó de uma árvore genérica (General Tree Node).
 *
 * Cada nó pode ter um número arbitrário de filhos, formando uma árvore n-ária.
 * Oferece métodos de travessia em profundidade (depth-first) e em largura (level-order),
 * além de busca por valor.
 *
 * @param T o tipo do valor armazenado no nó.
 * @property value o dado armazenado neste nó.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.4 — Representing rooted trees.
 */
class TreeNode<T>(val value: T) {
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    /**
     * Adiciona um nó filho a este nó.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param child o nó filho a ser adicionado.
     * @return `true` se o filho foi adicionado com sucesso.
     */
    fun add(child: TreeNode<T>) = children.add(child)

    /**
     * Percorre a árvore em profundidade (pre-order depth-first traversal),
     * visitando este nó antes dos seus filhos.
     *
     * Complexidade: O(n), onde n é o número total de nós na subárvore.
     *
     * @param visit função de callback invocada para cada nó visitado.
     */
    fun forEachDepthFirst(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }

    /**
     * Percorre a árvore em largura (level-order / breadth-first traversal),
     * visitando os nós nível por nível.
     *
     * Utiliza uma fila (implementada como lista) para controlar a ordem de visitação.
     *
     * Complexidade: O(n), onde n é o número total de nós na subárvore.
     *
     * @param visit função de callback invocada para cada nó visitado.
     */
    fun forEachLevelOrder(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        val queue = mutableListOf<TreeNode<T>>()
        children.forEach { queue.add(it) }

        var head = queue.firstOrNull()
        while (head != null) {
            visit(head)
            queue.removeAt(0)
            head.children.forEach { queue.add(it) }
            head = queue.firstOrNull()
        }
    }

    /**
     * Busca um nó com o valor especificado utilizando travessia em largura (level-order).
     *
     * Complexidade: O(n), onde n é o número total de nós.
     *
     * @param value o valor a ser procurado.
     * @return o [TreeNode] contendo o valor, ou `null` se não encontrado.
     */
    fun search(value: T): TreeNode<T>? {
        var result: TreeNode<T>? = null
        forEachLevelOrder {
            if (it.value == value) {
                result = it
            }
        }
        return result
    }
}
