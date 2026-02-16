package br.uem.din.datastructures.tree

/**
 * Árvore de Sufixos (Suffix Tree) — armazena todos os sufixos de uma string.
 *
 * Construída utilizando o algoritmo de Ukkonen (1995), que opera em tempo O(n)
 * on-line, processando a string caractere por caractere. A árvore resultante
 * possui no máximo n folhas (uma para cada sufixo) e no máximo n-1 nós internos.
 *
 * Cada aresta é rotulada por uma substring da entrada, representada implicitamente
 * por índices (início, fim) para economizar espaço.
 *
 * Aplicações: busca de padrões em O(m), contagem de ocorrências, maior substring
 * repetida, menor substring única, e diversas operações em bioinformática.
 *
 * Complexidades:
 * - Construção ([build]): O(n) — algoritmo de Ukkonen.
 * - [searchPattern]: O(m), onde m é o comprimento do padrão.
 * - [countOccurrences]: O(m + k), onde k é o número de ocorrências.
 * - [longestRepeatedSubstring]: O(n).
 *
 * Referência: Ukkonen, E. "On-line Construction of Suffix Trees" (1995);
 *             Gusfield, D. "Algorithms on Strings, Trees, and Sequences" (1997), Cap. 6;
 *             Weiner, P. "Linear Pattern Matching Algorithms" (1973);
 *             McCreight, E. M. "A Space-Economical Suffix Tree Construction Algorithm" (1976).
 */
class SuffixTree {

    private class Node(
        var start: Int = -1,
        var end: IntRef? = null,
        var suffixLink: Node? = null,
        val children: MutableMap<Char, Node> = mutableMapOf(),
        var suffixIndex: Int = -1
    ) {
        fun edgeLength(globalEnd: IntRef): Int {
            val e = end ?: globalEnd
            return e.value - start + 1
        }
    }

    private class IntRef(var value: Int)

    private class ActivePoint(
        var node: Node,
        var edge: Char = '\u0000',
        var length: Int = 0
    )

    private var text: String = ""
    private var root: Node = Node()
    private val globalEnd = IntRef(-1)
    private var remainingSuffixCount = 0
    private val activePoint = ActivePoint(root)

    /**
     * Constrói a árvore de sufixos para o texto fornecido utilizando o algoritmo de Ukkonen.
     *
     * Adiciona o caractere sentinela '$' ao final do texto para garantir que
     * todos os sufixos terminem em folhas.
     *
     * Complexidade: O(n), onde n é o comprimento do texto.
     *
     * @param input o texto a partir do qual construir a árvore de sufixos.
     */
    fun build(input: String) {
        text = "$input$"
        root = Node()
        root.suffixLink = root
        globalEnd.value = -1
        remainingSuffixCount = 0
        activePoint.node = root
        activePoint.edge = '\u0000'
        activePoint.length = 0
        for (i in text.indices) {
            extendTree(i)
        }
        setSuffixIndices(root, 0)
    }

    /**
     * Fase i do algoritmo de Ukkonen: estende a árvore para incluir text[0..i].
     *
     * Aplica as três regras de extensão:
     * 1. Extensão de folha (incremento do end global).
     * 2. Criação de nova folha e possivelmente novo nó interno (branch).
     * 3. Caractere já presente — regra de parada (show-stopper).
     *
     * @param pos a posição atual no texto.
     */
    private fun extendTree(pos: Int) {
        globalEnd.value = pos
        remainingSuffixCount++
        var lastNewInternalNode: Node? = null

        while (remainingSuffixCount > 0) {
            if (activePoint.length == 0) {
                activePoint.edge = text[pos]
            }

            val edgeChild = activePoint.node.children[activePoint.edge]
            if (edgeChild == null) {
                activePoint.node.children[activePoint.edge] = Node(start = pos, end = null, suffixLink = root)
                lastNewInternalNode?.let { it.suffixLink = activePoint.node }
                lastNewInternalNode = null
            } else {
                if (activePoint.length >= edgeChild.edgeLength(globalEnd)) {
                    activePoint.edge = text[edgeChild.start + edgeChild.edgeLength(globalEnd)]
                    activePoint.length -= edgeChild.edgeLength(globalEnd)
                    activePoint.node = edgeChild
                    continue
                }
                if (text[edgeChild.start + activePoint.length] == text[pos]) {
                    activePoint.length++
                    lastNewInternalNode?.let { it.suffixLink = activePoint.node }
                    break
                }
                val splitEnd = IntRef(edgeChild.start + activePoint.length - 1)
                val internalNode = Node(start = edgeChild.start, end = splitEnd, suffixLink = root)
                activePoint.node.children[activePoint.edge] = internalNode
                internalNode.children[text[pos]] = Node(start = pos, end = null, suffixLink = root)
                edgeChild.start = edgeChild.start + activePoint.length
                internalNode.children[text[edgeChild.start]] = edgeChild
                lastNewInternalNode?.let { it.suffixLink = internalNode }
                lastNewInternalNode = internalNode
            }

            remainingSuffixCount--
            if (activePoint.node === root && activePoint.length > 0) {
                activePoint.length--
                activePoint.edge = text[pos - remainingSuffixCount + 1]
            } else {
                activePoint.node = activePoint.node.suffixLink ?: root
            }
        }
    }

    /**
     * Define os índices de sufixo para as folhas via DFS.
     *
     * @param node o nó atual.
     * @param depth a profundidade acumulada em caracteres.
     */
    private fun setSuffixIndices(node: Node, depth: Int) {
        if (node.children.isEmpty()) {
            node.suffixIndex = text.length - depth
            return
        }
        for ((_, child) in node.children) {
            setSuffixIndices(child, depth + child.edgeLength(globalEnd))
        }
    }

    /**
     * Busca se o padrão ocorre no texto.
     *
     * Percorre a árvore seguindo os caracteres do padrão. Se todo o padrão
     * for consumido, o padrão existe como substring do texto.
     *
     * Complexidade: O(m), onde m é o comprimento do padrão.
     *
     * @param pattern o padrão a ser buscado.
     * @return `true` se o padrão ocorrer no texto, `false` caso contrário.
     */
    fun searchPattern(pattern: String): Boolean {
        if (pattern.isEmpty()) return true
        var node = root
        var i = 0
        while (i < pattern.length) {
            val child = node.children[pattern[i]] ?: return false
            val edgeLen = child.edgeLength(globalEnd)
            var j = 0
            while (j < edgeLen && i < pattern.length) {
                if (text[child.start + j] != pattern[i]) return false
                i++
                j++
            }
            if (i < pattern.length) {
                node = child
            }
        }
        return true
    }

    /**
     * Conta o número de ocorrências do padrão no texto.
     *
     * Navega até o nó correspondente ao padrão e conta o número de folhas
     * na subárvore (cada folha representa uma ocorrência distinta).
     *
     * Complexidade: O(m + k), onde m é o comprimento do padrão e k é o número de ocorrências.
     *
     * @param pattern o padrão cuja frequência será contada.
     * @return o número de ocorrências do padrão no texto.
     */
    fun countOccurrences(pattern: String): Int {
        if (pattern.isEmpty()) return text.length
        val node = findNode(pattern) ?: return 0
        return countLeaves(node)
    }

    /**
     * Encontra a maior substring repetida no texto.
     *
     * Percorre a árvore em profundidade, acumulando os rótulos das arestas.
     * A maior substring repetida corresponde ao caminho mais profundo que
     * termina em um nó interno (com mais de um filho).
     *
     * Complexidade: O(n).
     *
     * @return a maior substring que ocorre mais de uma vez, ou string vazia se nenhuma existir.
     */
    fun longestRepeatedSubstring(): String {
        var best = ""
        fun dfs(node: Node, accumulated: String) {
            for ((_, child) in node.children) {
                val edgeEnd = (child.end ?: globalEnd).value
                val edgeStr = text.substring(child.start, edgeEnd + 1)
                val path = accumulated + edgeStr
                if (child.children.isNotEmpty()) {
                    if (path.length > best.length) {
                        best = path
                    }
                    dfs(child, path)
                }
            }
        }
        dfs(root, "")
        return best.removeSuffix("$")
    }

    /**
     * Navega até o nó correspondente ao padrão.
     *
     * @param pattern o padrão a navegar.
     * @return o nó alcançado, ou `null` se o padrão não existe.
     */
    private fun findNode(pattern: String): Node? {
        var node = root
        var i = 0
        while (i < pattern.length) {
            val child = node.children[pattern[i]] ?: return null
            val edgeLen = child.edgeLength(globalEnd)
            var j = 0
            while (j < edgeLen && i < pattern.length) {
                if (text[child.start + j] != pattern[i]) return null
                i++
                j++
            }
            node = child
        }
        return node
    }

    /**
     * Conta o número de folhas na subárvore enraizada em [node].
     *
     * @param node a raiz da subárvore.
     * @return o número de folhas.
     */
    private fun countLeaves(node: Node): Int {
        if (node.children.isEmpty()) return 1
        var count = 0
        for ((_, child) in node.children) {
            count += countLeaves(child)
        }
        return count
    }
}
