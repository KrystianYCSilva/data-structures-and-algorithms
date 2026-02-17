package br.uem.din.datastructures.tree

/**
 * Árvore Trie (Prefix Tree / Árvore de Prefixos).
 *
 * Estrutura de dados especializada para armazenamento e recuperação eficiente de sequências
 * (strings, listas de chaves). Cada caminho da raiz até um nó terminante representa
 * uma sequência completa inserida.
 *
 * Particularmente útil para autocompletar, verificação ortográfica e busca por prefixo.
 *
 * Complexidades (onde m é o comprimento da sequência):
 * - [insert]: O(m)
 * - [contains]: O(m)
 * - [remove]: O(m)
 * - [collections] (busca por prefixo): O(m + k), onde k é o total de nós na subárvore do prefixo.
 *
 * @param Key o tipo das chaves que compõem as sequências.
 *
 * Referência: Fredkin, E. "Trie Memory" (1960);
 *             Sedgewick, R. & Wayne, K. "Algorithms", Cap. 5.2 — Tries.
 */
public class Trie<Key> {
    private val root = TrieNode<Key>(key = null, parent = null)

    /**
     * Insere uma sequência de chaves na Trie.
     *
     * Cria novos nós conforme necessário e marca o último nó como terminante.
     *
     * Complexidade: O(m), onde m é o comprimento da lista.
     *
     * @param list a sequência de chaves a ser inserida.
     */
    public fun insert(list: List<Key>) {
        var current = root
        list.forEach { element ->
            if (current.children[element] == null) {
                current.children[element] = TrieNode(key = element, parent = current)
            }
            current = current.children[element]!!
        }
        current.isTerminating = true
    }

    /**
     * Verifica se a Trie contém a sequência completa especificada.
     *
     * Retorna `true` apenas se toda a sequência existir e o último nó for terminante.
     *
     * Complexidade: O(m), onde m é o comprimento da lista.
     *
     * @param list a sequência de chaves a ser verificada.
     * @return `true` se a sequência existir na Trie, `false` caso contrário.
     */
    public fun contains(list: List<Key>): Boolean {
        var current = root
        list.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        return current.isTerminating
    }

    /**
     * Remove uma sequência da Trie.
     *
     * Desmarca o nó terminante e remove nós órfãos (sem filhos e não terminantes)
     * percorrendo de volta em direção à raiz.
     *
     * Complexidade: O(m), onde m é o comprimento da lista.
     *
     * @param list a sequência de chaves a ser removida.
     */
    public fun remove(list: List<Key>) {
        var current = root
        list.forEach { element ->
            val child = current.children[element] ?: return
            current = child
        }
        if (!current.isTerminating) return
        current.isTerminating = false
        while (current.parent != null && !current.isTerminating && current.children.isEmpty()) {
            current.parent!!.children.remove(current.key)
            current = current.parent!!
        }
    }

    /**
     * Retorna todas as sequências armazenadas na Trie que começam com o prefixo especificado.
     *
     * Complexidade: O(m + k), onde m é o comprimento do prefixo e k é o número total
     * de nós na subárvore do prefixo.
     *
     * @param prefix o prefixo a ser buscado.
     * @return lista de todas as sequências completas que possuem o prefixo dado.
     */
    public fun collections(prefix: List<Key>): List<List<Key>> {
        var current = root
        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }
        return collections(prefix, current)
    }

    /**
     * Coleta recursiva de todas as sequências completas a partir de um nó.
     *
     * @param prefix o prefixo acumulado até o momento.
     * @param node o nó atual da recursão.
     * @return lista de sequências completas encontradas.
     */
    private fun collections(prefix: List<Key>, node: TrieNode<Key>?): List<List<Key>> {
        val results = mutableListOf<List<Key>>()
        if (node?.isTerminating == true) {
            results.add(prefix)
        }
        node?.children?.forEach { (key, node) ->
            results.addAll(collections(prefix + key, node))
        }
        return results
    }
}
