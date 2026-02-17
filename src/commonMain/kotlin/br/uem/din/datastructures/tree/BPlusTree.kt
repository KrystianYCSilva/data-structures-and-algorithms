package br.uem.din.datastructures.tree

/**
 * Árvore B+ (B+ Tree) — variante da Árvore B onde todos os dados residem nas folhas.
 *
 * Os nós internos servem exclusivamente como roteadores (guias de busca), armazenando
 * cópias das chaves para direcionar a navegação. As folhas formam uma lista ligada
 * ordenada, permitindo travessias sequenciais e consultas por faixa (range queries)
 * de forma eficiente.
 *
 * Propriedades fundamentais (para ordem M):
 * 1. Nós internos têm no máximo M filhos e M-1 chaves.
 * 2. Nós internos (exceto raiz) têm no mínimo ⌈M/2⌉ filhos.
 * 3. Todas as folhas estão no mesmo nível.
 * 4. Folhas contêm os dados reais e são encadeadas sequencialmente.
 *
 * Complexidades:
 * - [search]: O(log_M(n))
 * - [insert]: O(log_M(n))
 * - [remove]: O(log_M(n))
 * - [rangeSearch]: O(log_M(n) + k), onde k é o número de resultados.
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 * @param order a ordem M da árvore (número máximo de filhos por nó interno, padrão: 4).
 *
 * Referência: Comer, D. "The Ubiquitous B-Tree" (1979);
 *             Bayer, R. & McCreight, E. "Organization and Maintenance of Large Ordered Indexes" (1970);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 18;
 *             Ramakrishnan, R. & Gehrke, J. "Database Management Systems", Cap. 10.
 */
public class BPlusTree<T : Comparable<T>>(private val order: Int = 4) {

    init {
        require(order >= 3) { "A ordem deve ser >= 3." }
    }

    private abstract class BPlusNode<T>

    private class InternalNode<T> : BPlusNode<T>() {
        val keys: MutableList<T> = mutableListOf()
        val children: MutableList<BPlusNode<T>> = mutableListOf()
    }

    private class LeafNode<T> : BPlusNode<T>() {
        val keys: MutableList<T> = mutableListOf()
        var next: LeafNode<T>? = null
    }

    private var root: BPlusNode<T> = LeafNode()

    /**
     * Número de chaves armazenadas na árvore.
     */
    public var size: Int = 0
        private set

    /**
     * Busca uma chave na árvore B+.
     *
     * Navega pelos nós internos até encontrar a folha apropriada e verifica
     * se a chave está presente.
     *
     * Complexidade: O(log_M(n)).
     *
     * @param value a chave a ser procurada.
     * @return `true` se a chave existir na árvore, `false` caso contrário.
     */
    public fun search(value: T): Boolean {
        val leaf = findLeaf(root, value)
        return leaf.keys.contains(value)
    }

    /**
     * Realiza uma consulta por faixa (range query), retornando todos os valores
     * no intervalo [from, to] (inclusive).
     *
     * Navega até a folha contendo [from] e percorre a lista encadeada de folhas
     * até ultrapassar [to].
     *
     * Complexidade: O(log_M(n) + k), onde k é o número de resultados.
     *
     * @param from limite inferior do intervalo (inclusive).
     * @param to limite superior do intervalo (inclusive).
     * @return lista ordenada dos valores no intervalo.
     */
    public fun rangeSearch(from: T, to: T): List<T> {
        val result = mutableListOf<T>()
        var leaf: LeafNode<T>? = findLeaf(root, from)
        while (leaf != null) {
            for (key in leaf.keys) {
                if (key > to) return result
                if (key >= from) result.add(key)
            }
            leaf = leaf.next
        }
        return result
    }

    /**
     * Insere uma chave na árvore B+.
     *
     * Localiza a folha apropriada e insere a chave. Se a folha exceder a capacidade
     * máxima (order - 1 chaves), realiza split e propaga a chave promovida para cima.
     *
     * Complexidade: O(log_M(n)).
     *
     * @param value a chave a ser inserida.
     */
    public fun insert(value: T) {
        val result = insert(root, value)
        if (result != null) {
            val newRoot = InternalNode<T>()
            newRoot.keys.add(result.first)
            newRoot.children.add(root)
            newRoot.children.add(result.second)
            root = newRoot
        }
        size++
    }

    /**
     * Inserção recursiva que retorna um par (chave promovida, novo nó) se ocorreu split,
     * ou `null` se não houve split.
     *
     * @param node o nó atual.
     * @param value a chave a ser inserida.
     * @return par (chave, novo nó) em caso de split, ou `null`.
     */
    private fun insert(node: BPlusNode<T>, value: T): Pair<T, BPlusNode<T>>? {
        if (node is LeafNode<T>) {
            val pos = node.keys.indexOfFirst { it >= value }
            if (pos >= 0 && pos < node.keys.size && node.keys[pos] == value) {
                size--
                return null
            }
            if (pos < 0) node.keys.add(value) else node.keys.add(pos, value)
            return if (node.keys.size >= order) splitLeaf(node) else null
        }
        val internal = node as InternalNode<T>
        var childIndex = internal.keys.size
        for (i in internal.keys.indices) {
            if (value < internal.keys[i]) {
                childIndex = i
                break
            }
        }
        val result = insert(internal.children[childIndex], value)
        if (result != null) {
            internal.keys.add(childIndex, result.first)
            internal.children.add(childIndex + 1, result.second)
            if (internal.keys.size >= order) {
                return splitInternal(internal)
            }
        }
        return null
    }

    /**
     * Divide uma folha cheia em duas, promovendo a primeira chave da nova folha.
     *
     * A nova folha é inserida na lista encadeada de folhas.
     *
     * Complexidade: O(M).
     *
     * @param leaf a folha a ser dividida.
     * @return par (chave promovida, nova folha).
     */
    private fun splitLeaf(leaf: LeafNode<T>): Pair<T, LeafNode<T>> {
        val mid = leaf.keys.size / 2
        val newLeaf = LeafNode<T>()
        newLeaf.keys.addAll(leaf.keys.subList(mid, leaf.keys.size))
        while (leaf.keys.size > mid) {
            leaf.keys.removeAt(leaf.keys.size - 1)
        }
        newLeaf.next = leaf.next
        leaf.next = newLeaf
        return Pair(newLeaf.keys[0], newLeaf)
    }

    /**
     * Divide um nó interno cheio em dois, promovendo a chave mediana.
     *
     * Diferente do split de folha, a chave mediana é removida do nível atual
     * e promovida ao pai.
     *
     * Complexidade: O(M).
     *
     * @param node o nó interno a ser dividido.
     * @return par (chave promovida, novo nó interno).
     */
    private fun splitInternal(node: InternalNode<T>): Pair<T, InternalNode<T>> {
        val mid = node.keys.size / 2
        val promotedKey = node.keys[mid]
        val newNode = InternalNode<T>()
        newNode.keys.addAll(node.keys.subList(mid + 1, node.keys.size))
        newNode.children.addAll(node.children.subList(mid + 1, node.children.size))
        while (node.keys.size > mid) {
            node.keys.removeAt(node.keys.size - 1)
        }
        while (node.children.size > mid + 1) {
            node.children.removeAt(node.children.size - 1)
        }
        return Pair(promotedKey, newNode)
    }

    /**
     * Remove uma chave da árvore B+.
     *
     * Localiza a folha contendo a chave e a remove. Se a folha ficar com menos
     * chaves que o mínimo, tenta emprestar de um irmão ou mesclar com ele.
     *
     * Complexidade: O(log_M(n)).
     *
     * @param value a chave a ser removida.
     */
    public fun remove(value: T) {
        if (!search(value)) return
        remove(root, value, null, -1)
        if (root is InternalNode<T>) {
            val internal = root as InternalNode<T>
            if (internal.keys.isEmpty()) {
                root = internal.children[0]
            }
        }
        size--
    }

    private fun remove(node: BPlusNode<T>, value: T, parent: InternalNode<T>?, parentIndex: Int) {
        if (node is LeafNode<T>) {
            node.keys.remove(value)
            if (parent != null && node.keys.size < minLeafKeys()) {
                rebalanceLeaf(parent, parentIndex)
            }
            return
        }
        val internal = node as InternalNode<T>
        var childIndex = internal.keys.size
        for (i in internal.keys.indices) {
            if (value < internal.keys[i]) {
                childIndex = i
                break
            }
        }
        remove(internal.children[childIndex], value, internal, childIndex)
        if (parent != null && internal.keys.size < minInternalKeys()) {
            rebalanceInternal(parent, parentIndex)
        }
    }

    /**
     * Rebalanceia uma folha que ficou abaixo do número mínimo de chaves.
     *
     * Tenta emprestar de irmão esquerdo ou direito; se não for possível, mescla.
     *
     * @param parent o nó pai.
     * @param childIndex o índice do filho a rebalancear.
     */
    private fun rebalanceLeaf(parent: InternalNode<T>, childIndex: Int) {
        val child = parent.children[childIndex] as LeafNode<T>
        if (childIndex > 0) {
            val leftSibling = parent.children[childIndex - 1] as LeafNode<T>
            if (leftSibling.keys.size > minLeafKeys()) {
                child.keys.add(0, leftSibling.keys.removeAt(leftSibling.keys.size - 1))
                parent.keys[childIndex - 1] = child.keys[0]
                return
            }
        }
        if (childIndex < parent.children.size - 1) {
            val rightSibling = parent.children[childIndex + 1] as LeafNode<T>
            if (rightSibling.keys.size > minLeafKeys()) {
                child.keys.add(rightSibling.keys.removeAt(0))
                parent.keys[childIndex] = rightSibling.keys[0]
                return
            }
        }
        if (childIndex > 0) {
            val leftSibling = parent.children[childIndex - 1] as LeafNode<T>
            leftSibling.keys.addAll(child.keys)
            leftSibling.next = child.next
            parent.keys.removeAt(childIndex - 1)
            parent.children.removeAt(childIndex)
        } else {
            val rightSibling = parent.children[childIndex + 1] as LeafNode<T>
            child.keys.addAll(rightSibling.keys)
            child.next = rightSibling.next
            parent.keys.removeAt(childIndex)
            parent.children.removeAt(childIndex + 1)
        }
    }

    /**
     * Rebalanceia um nó interno que ficou abaixo do número mínimo de chaves.
     *
     * @param parent o nó pai.
     * @param childIndex o índice do filho a rebalancear.
     */
    private fun rebalanceInternal(parent: InternalNode<T>, childIndex: Int) {
        val child = parent.children[childIndex] as InternalNode<T>
        if (childIndex > 0) {
            val leftSibling = parent.children[childIndex - 1] as InternalNode<T>
            if (leftSibling.keys.size > minInternalKeys()) {
                child.keys.add(0, parent.keys[childIndex - 1])
                parent.keys[childIndex - 1] = leftSibling.keys.removeAt(leftSibling.keys.size - 1)
                child.children.add(0, leftSibling.children.removeAt(leftSibling.children.size - 1))
                return
            }
        }
        if (childIndex < parent.children.size - 1) {
            val rightSibling = parent.children[childIndex + 1] as InternalNode<T>
            if (rightSibling.keys.size > minInternalKeys()) {
                child.keys.add(parent.keys[childIndex])
                parent.keys[childIndex] = rightSibling.keys.removeAt(0)
                child.children.add(rightSibling.children.removeAt(0))
                return
            }
        }
        if (childIndex > 0) {
            val leftSibling = parent.children[childIndex - 1] as InternalNode<T>
            leftSibling.keys.add(parent.keys.removeAt(childIndex - 1))
            leftSibling.keys.addAll(child.keys)
            leftSibling.children.addAll(child.children)
            parent.children.removeAt(childIndex)
        } else {
            val rightSibling = parent.children[childIndex + 1] as InternalNode<T>
            child.keys.add(parent.keys.removeAt(childIndex))
            child.keys.addAll(rightSibling.keys)
            child.children.addAll(rightSibling.children)
            parent.children.removeAt(childIndex + 1)
        }
    }

    private fun minLeafKeys(): Int = (order - 1) / 2
    private fun minInternalKeys(): Int = (order - 1) / 2

    /**
     * Navega até a folha que deveria conter o valor especificado.
     *
     * @param node o nó atual.
     * @param value o valor-alvo.
     * @return a folha correspondente.
     */
    private fun findLeaf(node: BPlusNode<T>, value: T): LeafNode<T> {
        if (node is LeafNode<T>) return node
        val internal = node as InternalNode<T>
        var childIndex = internal.keys.size
        for (i in internal.keys.indices) {
            if (value < internal.keys[i]) {
                childIndex = i
                break
            }
        }
        return findLeaf(internal.children[childIndex], value)
    }

    /**
     * Retorna todos os elementos da árvore em ordem crescente, percorrendo
     * a lista encadeada de folhas.
     *
     * Complexidade: O(n).
     *
     * @return lista com todos os elementos em ordem.
     */
    public fun inOrder(): List<T> {
        val result = mutableListOf<T>()
        var leaf = findLeftmostLeaf(root)
        while (leaf != null) {
            result.addAll(leaf.keys)
            leaf = leaf.next
        }
        return result
    }

    private fun findLeftmostLeaf(node: BPlusNode<T>): LeafNode<T>? {
        var current = node
        while (current is InternalNode<T>) {
            current = current.children[0]
        }
        return current as? LeafNode<T>
    }
}
