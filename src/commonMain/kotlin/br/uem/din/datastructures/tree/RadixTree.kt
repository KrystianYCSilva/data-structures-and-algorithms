package br.uem.din.datastructures.tree

/**
 * Árvore Radix (Radix Tree / Patricia Trie / Compressed Trie).
 *
 * Introduzida por Morrison (1968) como PATRICIA (Practical Algorithm To Retrieve
 * Information Coded In Alphanumeric), a Radix Tree é uma Trie comprimida onde
 * cadeias de nós com um único filho são mescladas em uma única aresta com rótulo
 * multi-caractere. Isso reduz significativamente o uso de memória e o número
 * de nós em relação a uma Trie padrão.
 *
 * Cada aresta armazena uma substring (rótulo), e a concatenação dos rótulos da raiz
 * até uma folha (ou nó terminante) forma uma chave completa.
 *
 * Complexidades (onde m é o comprimento da chave):
 * - [insert]: O(m)
 * - [search]: O(m)
 * - [delete]: O(m)
 * - [prefixSearch]: O(m + k), onde k é o total de caracteres nas chaves com o prefixo.
 *
 * @property size número de chaves armazenadas na árvore.
 *
 * Referência: Morrison, D. R. "PATRICIA — Practical Algorithm To Retrieve Information
 *             Coded In Alphanumeric" (1968);
 *             Knuth, D. E. "The Art of Computer Programming", Vol. 3, Seção 6.3;
 *             Sedgewick, R. & Wayne, K. "Algorithms", Cap. 5.2.
 */
class RadixTree {

    private class Node(
        var label: String = "",
        var isTerminal: Boolean = false,
        val children: MutableMap<Char, Node> = mutableMapOf()
    )

    private val root = Node()

    /**
     * Número de chaves armazenadas na árvore.
     */
    var size: Int = 0
        private set

    /**
     * Insere uma chave (string) na Radix Tree.
     *
     * Percorre a árvore comparando prefixos dos rótulos das arestas. Quando um
     * rótulo compartilha apenas parte do prefixo com a chave restante, o nó é
     * dividido (split) para acomodar a nova ramificação.
     *
     * Complexidade: O(m), onde m é o comprimento da chave.
     *
     * @param key a chave a ser inserida.
     */
    fun insert(key: String) {
        if (key.isEmpty()) {
            if (!root.isTerminal) {
                root.isTerminal = true
                size++
            }
            return
        }
        insert(root, key)
    }

    private fun insert(node: Node, remaining: String) {
        val firstChar = remaining[0]
        val child = node.children[firstChar]
        if (child == null) {
            node.children[firstChar] = Node(label = remaining, isTerminal = true)
            size++
            return
        }
        val commonLen = commonPrefixLength(child.label, remaining)
        if (commonLen == child.label.length && commonLen == remaining.length) {
            if (!child.isTerminal) {
                child.isTerminal = true
                size++
            }
            return
        }
        if (commonLen == child.label.length) {
            insert(child, remaining.substring(commonLen))
            return
        }
        val splitNode = Node(label = child.label.substring(0, commonLen))
        node.children[firstChar] = splitNode
        child.label = child.label.substring(commonLen)
        splitNode.children[child.label[0]] = child
        if (commonLen == remaining.length) {
            splitNode.isTerminal = true
            size++
        } else {
            val newSuffix = remaining.substring(commonLen)
            splitNode.children[newSuffix[0]] = Node(label = newSuffix, isTerminal = true)
            size++
        }
    }

    /**
     * Busca uma chave na Radix Tree.
     *
     * Complexidade: O(m), onde m é o comprimento da chave.
     *
     * @param key a chave a ser procurada.
     * @return `true` se a chave existir na árvore, `false` caso contrário.
     */
    fun search(key: String): Boolean {
        if (key.isEmpty()) return root.isTerminal
        return search(root, key)
    }

    private fun search(node: Node, remaining: String): Boolean {
        val firstChar = remaining[0]
        val child = node.children[firstChar] ?: return false
        if (!remaining.startsWith(child.label)) return false
        if (child.label.length == remaining.length) return child.isTerminal
        return search(child, remaining.substring(child.label.length))
    }

    /**
     * Remove uma chave da Radix Tree.
     *
     * Após desmarcar o nó terminante, realiza compressão se o nó resultante
     * tiver exatamente um filho e não for terminante (mescla pai-filho).
     *
     * Complexidade: O(m), onde m é o comprimento da chave.
     *
     * @param key a chave a ser removida.
     */
    fun remove(key: String) {
        if (key.isEmpty()) {
            if (root.isTerminal) {
                root.isTerminal = false
                size--
            }
            return
        }
        removeRecursive(root, key)
    }

    @Deprecated("Use remove(key) para consistência com a API do projeto.", replaceWith = ReplaceWith("remove(key)"))
    fun delete(key: String) {
        remove(key)
    }

    private fun removeRecursive(node: Node, remaining: String): Boolean {
        val firstChar = remaining[0]
        val child = node.children[firstChar] ?: return false
        if (!remaining.startsWith(child.label)) return false
        if (child.label.length == remaining.length) {
            if (!child.isTerminal) return false
            child.isTerminal = false
            size--
            if (child.children.isEmpty()) {
                node.children.remove(firstChar)
            } else if (child.children.size == 1) {
                val grandchild = child.children.values.first()
                child.label = child.label + grandchild.label
                child.isTerminal = grandchild.isTerminal
                child.children.clear()
                child.children.putAll(grandchild.children)
            }
            return true
        }
        val deleted = removeRecursive(child, remaining.substring(child.label.length))
        if (deleted && !child.isTerminal && child.children.size == 1) {
            val grandchild = child.children.values.first()
            child.label = child.label + grandchild.label
            child.isTerminal = grandchild.isTerminal
            child.children.clear()
            child.children.putAll(grandchild.children)
        }
        return deleted
    }

    /**
     * Retorna todas as chaves que possuem o prefixo especificado.
     *
     * Complexidade: O(m + k), onde m é o comprimento do prefixo e k é o total
     * de caracteres nas chaves retornadas.
     *
     * @param prefix o prefixo a ser buscado.
     * @return lista de todas as chaves com o prefixo dado.
     */
    fun prefixSearch(prefix: String): List<String> {
        if (prefix.isEmpty()) {
            val result = mutableListOf<String>()
            collectAll(root, "", result)
            return result
        }
        return prefixSearch(root, prefix, "")
    }

    private fun prefixSearch(node: Node, remaining: String, accumulated: String): List<String> {
        val firstChar = remaining[0]
        val child = node.children[firstChar] ?: return emptyList()
        val commonLen = commonPrefixLength(child.label, remaining)
        if (commonLen < child.label.length && commonLen < remaining.length) {
            return emptyList()
        }
        if (commonLen >= remaining.length) {
            val result = mutableListOf<String>()
            collectAll(child, accumulated + child.label, result)
            return result
        }
        return prefixSearch(child, remaining.substring(child.label.length), accumulated + child.label)
    }

    /**
     * Coleta recursivamente todas as chaves a partir de um nó.
     *
     * @param node o nó atual.
     * @param prefix o prefixo acumulado até este nó.
     * @param result lista de resultados.
     */
    private fun collectAll(node: Node, prefix: String, result: MutableList<String>) {
        if (node.isTerminal) {
            result.add(prefix)
        }
        for ((_, child) in node.children) {
            collectAll(child, prefix + child.label, result)
        }
    }

    /**
     * Calcula o comprimento do prefixo comum entre duas strings.
     *
     * @param a primeira string.
     * @param b segunda string.
     * @return o número de caracteres comuns no início de ambas.
     */
    private fun commonPrefixLength(a: String, b: String): Int {
        val minLen = minOf(a.length, b.length)
        for (i in 0 until minLen) {
            if (a[i] != b[i]) return i
        }
        return minLen
    }
}
