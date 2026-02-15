package br.uem.din.datastructures.tree

/**
 * Árvore B (B-Tree) — árvore de busca balanceada multi-vias otimizada para acesso a disco.
 *
 * Introduzida por Bayer e McCreight (1970), a B-Tree é uma generalização da BST
 * onde cada nó pode conter até 2t-1 chaves e ter até 2t filhos, sendo t o grau
 * mínimo (minimum degree). Todas as folhas estão no mesmo nível, garantindo
 * balanceamento perfeito.
 *
 * Propriedades fundamentais (para grau mínimo t >= 2):
 * 1. Cada nó tem no máximo 2t-1 chaves.
 * 2. Cada nó interno (exceto raiz) tem no mínimo t-1 chaves.
 * 3. A raiz tem no mínimo 1 chave (se não vazia).
 * 4. Todas as folhas estão no mesmo nível.
 * 5. Um nó com k chaves tem k+1 filhos (se não for folha).
 *
 * Complexidades:
 * - [search]: O(t · log_t(n))
 * - [insert]: O(t · log_t(n))
 * - [remove]: O(t · log_t(n))
 * - Altura máxima: O(log_t(n))
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 * @param minimumDegree o grau mínimo t da árvore (padrão: 2, que resulta em uma árvore 2-3-4).
 *
 * Referência: Bayer, R. & McCreight, E. "Organization and Maintenance of Large Ordered Indexes" (1970);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 18 — B-Trees;
 *             Knuth, D. E. "The Art of Computer Programming", Vol. 3, Seção 6.2.4.
 */
class BTree<T : Comparable<T>>(private val minimumDegree: Int = 2) {

    init {
        require(minimumDegree >= 2) { "O grau mínimo deve ser >= 2." }
    }

    private class Node<T>(var leaf: Boolean = true) {
        val keys: MutableList<T> = mutableListOf()
        val children: MutableList<Node<T>> = mutableListOf()
    }

    private var root: Node<T> = Node(leaf = true)

    /**
     * Número de chaves armazenadas na árvore.
     */
    var size: Int = 0
        private set

    /**
     * Busca uma chave na árvore B.
     *
     * Percorre a árvore de cima para baixo, realizando busca linear nas chaves
     * de cada nó para determinar qual filho visitar.
     *
     * Complexidade: O(t · log_t(n)).
     *
     * @param value a chave a ser procurada.
     * @return `true` se a chave existir na árvore, `false` caso contrário.
     */
    fun search(value: T): Boolean = search(root, value)

    private fun search(node: Node<T>, value: T): Boolean {
        var i = 0
        while (i < node.keys.size && value > node.keys[i]) {
            i++
        }
        if (i < node.keys.size && value == node.keys[i]) return true
        if (node.leaf) return false
        return search(node.children[i], value)
    }

    /**
     * Insere uma chave na árvore B.
     *
     * Se a raiz estiver cheia (2t-1 chaves), realiza split preventivo criando
     * nova raiz. A inserção é então realizada de forma top-down, dividindo
     * nós cheios encontrados no caminho (split preemptivo, conforme CLRS).
     *
     * Complexidade: O(t · log_t(n)).
     *
     * @param value a chave a ser inserida.
     */
    fun insert(value: T) {
        val r = root
        if (r.keys.size == 2 * minimumDegree - 1) {
            val s = Node<T>(leaf = false)
            s.children.add(r)
            splitChild(s, 0)
            root = s
            insertNonFull(s, value)
        } else {
            insertNonFull(r, value)
        }
        size++
    }

    /**
     * Insere uma chave em um nó que não está cheio.
     *
     * Se o nó é folha, insere diretamente. Se é interno, determina o filho
     * apropriado e, se estiver cheio, realiza split antes de descer.
     *
     * @param node o nó (não cheio) onde inserir.
     * @param value a chave a ser inserida.
     */
    private fun insertNonFull(node: Node<T>, value: T) {
        var i = node.keys.size - 1
        if (node.leaf) {
            while (i >= 0 && value < node.keys[i]) {
                i--
            }
            node.keys.add(i + 1, value)
        } else {
            while (i >= 0 && value < node.keys[i]) {
                i--
            }
            i++
            if (node.children[i].keys.size == 2 * minimumDegree - 1) {
                splitChild(node, i)
                if (value > node.keys[i]) {
                    i++
                }
            }
            insertNonFull(node.children[i], value)
        }
    }

    /**
     * Divide o filho cheio [node].children[[index]] em dois nós, promovendo
     * a chave mediana para o nó pai.
     *
     * Após o split, o filho original retém as t-1 primeiras chaves, e o novo
     * nó recebe as t-1 últimas chaves. A chave mediana sobe para o pai.
     *
     * Complexidade: O(t).
     *
     * @param node o nó pai.
     * @param index o índice do filho a ser dividido.
     */
    private fun splitChild(node: Node<T>, index: Int) {
        val fullChild = node.children[index]
        val newChild = Node<T>(leaf = fullChild.leaf)
        val mid = minimumDegree - 1
        node.keys.add(index, fullChild.keys[mid])
        node.children.add(index + 1, newChild)
        newChild.keys.addAll(fullChild.keys.subList(mid + 1, fullChild.keys.size))
        val medianKey = fullChild.keys[mid]
        while (fullChild.keys.size > mid) {
            fullChild.keys.removeAt(fullChild.keys.size - 1)
        }
        fullChild.keys.remove(medianKey)
        if (!fullChild.leaf) {
            newChild.children.addAll(fullChild.children.subList(mid + 1, fullChild.children.size))
            while (fullChild.children.size > mid + 1) {
                fullChild.children.removeAt(fullChild.children.size - 1)
            }
        }
    }

    /**
     * Remove uma chave da árvore B.
     *
     * Implementa o algoritmo de remoção de CLRS com três casos:
     * 1. Chave encontrada em folha: remove diretamente.
     * 2. Chave encontrada em nó interno: substitui pelo predecessor/sucessor ou mescla filhos.
     * 3. Chave não encontrada no nó: garante que o filho a visitar tem >= t chaves.
     *
     * Complexidade: O(t · log_t(n)).
     *
     * @param value a chave a ser removida.
     */
    fun remove(value: T) {
        if (!search(value)) return
        remove(root, value)
        if (root.keys.isEmpty() && !root.leaf) {
            root = root.children[0]
        }
        size--
    }

    private fun remove(node: Node<T>, value: T) {
        var i = 0
        while (i < node.keys.size && value > node.keys[i]) {
            i++
        }
        if (i < node.keys.size && value == node.keys[i]) {
            if (node.leaf) {
                node.keys.removeAt(i)
            } else {
                removeFromInternal(node, i)
            }
        } else if (!node.leaf) {
            val targetChild = ensureMinKeys(node, i)
            var newI = 0
            while (newI < node.keys.size && value > node.keys[newI]) {
                newI++
            }
            if (newI < node.keys.size && value == node.keys[newI]) {
                removeFromInternal(node, newI)
            } else {
                val childIndex = if (targetChild < node.children.size) {
                    var ci = 0
                    while (ci < node.keys.size && value > node.keys[ci]) ci++
                    ci
                } else {
                    node.children.size - 1
                }
                remove(node.children[childIndex], value)
            }
        }
    }

    /**
     * Remove uma chave de um nó interno substituindo pelo predecessor in-order
     * (da subárvore esquerda) ou sucessor in-order (da subárvore direita),
     * ou mesclando os filhos adjacentes se ambos têm o mínimo de chaves.
     *
     * @param node o nó interno contendo a chave.
     * @param index o índice da chave no nó.
     */
    private fun removeFromInternal(node: Node<T>, index: Int) {
        val key = node.keys[index]
        if (node.children[index].keys.size >= minimumDegree) {
            val pred = findPredecessor(node.children[index])
            node.keys[index] = pred
            remove(node.children[index], pred)
        } else if (node.children[index + 1].keys.size >= minimumDegree) {
            val succ = findSuccessor(node.children[index + 1])
            node.keys[index] = succ
            remove(node.children[index + 1], succ)
        } else {
            mergeChildren(node, index)
            remove(node.children[index], key)
        }
    }

    /**
     * Encontra o predecessor in-order (maior chave da subárvore).
     *
     * @param node a raiz da subárvore.
     * @return a maior chave da subárvore.
     */
    private fun findPredecessor(node: Node<T>): T {
        var current = node
        while (!current.leaf) {
            current = current.children[current.children.size - 1]
        }
        return current.keys[current.keys.size - 1]
    }

    /**
     * Encontra o sucessor in-order (menor chave da subárvore).
     *
     * @param node a raiz da subárvore.
     * @return a menor chave da subárvore.
     */
    private fun findSuccessor(node: Node<T>): T {
        var current = node
        while (!current.leaf) {
            current = current.children[0]
        }
        return current.keys[0]
    }

    /**
     * Garante que node.children[[index]] possui pelo menos t chaves antes de descer.
     *
     * Se o filho tem exatamente t-1 chaves, tenta emprestar de um irmão adjacente
     * ou mescla com um irmão.
     *
     * @param node o nó pai.
     * @param index o índice do filho-alvo.
     * @return o índice efetivo do filho após possível mesclagem.
     */
    private fun ensureMinKeys(node: Node<T>, index: Int): Int {
        if (index < node.children.size && node.children[index].keys.size >= minimumDegree) {
            return index
        }
        val idx = index.coerceAtMost(node.children.size - 1)
        if (node.children[idx].keys.size >= minimumDegree) return idx
        if (idx > 0 && node.children[idx - 1].keys.size >= minimumDegree) {
            borrowFromLeft(node, idx)
            return idx
        }
        if (idx < node.children.size - 1 && node.children[idx + 1].keys.size >= minimumDegree) {
            borrowFromRight(node, idx)
            return idx
        }
        return if (idx > 0) {
            mergeChildren(node, idx - 1)
            idx - 1
        } else {
            mergeChildren(node, idx)
            idx
        }
    }

    /**
     * Empresta uma chave do irmão esquerdo via rotação pelo pai.
     *
     * @param node o nó pai.
     * @param index o índice do filho que precisa de chaves.
     */
    private fun borrowFromLeft(node: Node<T>, index: Int) {
        val child = node.children[index]
        val leftSibling = node.children[index - 1]
        child.keys.add(0, node.keys[index - 1])
        node.keys[index - 1] = leftSibling.keys.removeAt(leftSibling.keys.size - 1)
        if (!leftSibling.leaf) {
            child.children.add(0, leftSibling.children.removeAt(leftSibling.children.size - 1))
        }
    }

    /**
     * Empresta uma chave do irmão direito via rotação pelo pai.
     *
     * @param node o nó pai.
     * @param index o índice do filho que precisa de chaves.
     */
    private fun borrowFromRight(node: Node<T>, index: Int) {
        val child = node.children[index]
        val rightSibling = node.children[index + 1]
        child.keys.add(node.keys[index])
        node.keys[index] = rightSibling.keys.removeAt(0)
        if (!rightSibling.leaf) {
            child.children.add(rightSibling.children.removeAt(0))
        }
    }

    /**
     * Mescla node.children[[index]] com node.children[[index]+1], incorporando
     * a chave separadora do pai.
     *
     * @param node o nó pai.
     * @param index o índice da chave separadora no pai.
     */
    private fun mergeChildren(node: Node<T>, index: Int) {
        val leftChild = node.children[index]
        val rightChild = node.children[index + 1]
        leftChild.keys.add(node.keys.removeAt(index))
        leftChild.keys.addAll(rightChild.keys)
        leftChild.children.addAll(rightChild.children)
        node.children.removeAt(index + 1)
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

    private fun inOrder(node: Node<T>, result: MutableList<T>) {
        for (i in node.keys.indices) {
            if (!node.leaf) {
                inOrder(node.children[i], result)
            }
            result.add(node.keys[i])
        }
        if (!node.leaf && node.children.size > node.keys.size) {
            inOrder(node.children[node.keys.size], result)
        }
    }
}
