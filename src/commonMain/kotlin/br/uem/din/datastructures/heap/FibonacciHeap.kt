package br.uem.din.datastructures.heap

import kotlin.math.ln
import kotlin.math.floor

/**
 * Heap de Fibonacci (Fibonacci Heap) — heap mergeable com operações amortizadas eficientes.
 *
 * Um Fibonacci Heap é uma coleção de árvores com propriedade de min-heap, organizadas
 * em uma lista circular duplamente ligada de raízes. A estrutura é "preguiçosa":
 * a consolidação das árvores é adiada até que [extractMin] seja chamado.
 *
 * A eficiência amortizada é alcançada através de duas técnicas:
 * 1. **Consolidação preguiçosa**: árvores são mescladas apenas durante [extractMin].
 * 2. **Corte em cascata (cascading cut)**: quando um nó perde dois filhos, ele é cortado
 *    de seu pai e promovido à lista de raízes, limitando o grau máximo a O(log n).
 *
 * O grau máximo de qualquer nó é limitado por ⌊log_φ(n)⌋, onde φ = (1+√5)/2 é a razão
 * áurea. Isso é garantido pela propriedade de que uma subárvore de grau k contém pelo
 * menos F_{k+2} ≥ φ^k nós, onde F_k é o k-ésimo número de Fibonacci.
 *
 * Complexidades amortizadas:
 * - [insert]: O(1)
 * - [extractMin]: O(log n)
 * - [merge]: O(1)
 * - [decreaseKey]: O(1)
 * - [peek]: O(1)
 * - [isEmpty]: O(1)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Fredman, M. L. & Tarjan, R. E. "Fibonacci heaps and their uses in improved
 *             network optimization algorithms" (1987);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 19 — Fibonacci Heaps.
 */
class FibonacciHeap<T : Comparable<T>> {

    /**
     * Nó interno do Fibonacci Heap.
     *
     * Utiliza lista circular duplamente ligada para os filhos e para a lista de raízes.
     * O campo [mark] indica se o nó perdeu um filho desde a última vez que se tornou
     * filho de outro nó (usado no corte em cascata).
     *
     * @property key a chave armazenada neste nó.
     * @property degree o número de filhos deste nó.
     * @property mark indica se o nó perdeu um filho (para cascading cut).
     * @property parent referência ao nó pai, ou `null` se for raiz.
     * @property child referência a um dos filhos (entrada na lista circular de filhos).
     * @property left irmão esquerdo na lista circular duplamente ligada.
     * @property right irmão direito na lista circular duplamente ligada.
     */
    class Node<T>(
        var key: T,
        var degree: Int = 0,
        var mark: Boolean = false,
        var parent: Node<T>? = null,
        var child: Node<T>? = null
    ) {
        var left: Node<T> = this
        var right: Node<T> = this
    }

    private var min: Node<T>? = null

    /** Número de elementos armazenados no heap. */
    var size: Int = 0
        private set

    /**
     * Verifica se o heap está vazio.
     *
     * Complexidade: O(1).
     *
     * @return `true` se o heap não contiver elementos.
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * Retorna o menor elemento do heap sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o menor elemento, ou `null` se o heap estiver vazio.
     */
    fun peek(): T? = min?.key

    /**
     * Insere um elemento no Fibonacci Heap.
     *
     * Cria um novo nó e o adiciona à lista de raízes. Atualiza o ponteiro [min]
     * se o novo elemento for menor que o mínimo atual.
     *
     * Complexidade amortizada: O(1).
     *
     * @param key o elemento a ser inserido.
     * @return o [Node] criado (pode ser usado posteriormente em [decreaseKey]).
     */
    fun insert(key: T): Node<T> {
        val node = Node(key)
        if (min == null) {
            min = node
        } else {
            addToRootList(node)
            if (node.key < min!!.key) {
                min = node
            }
        }
        size++
        return node
    }

    /**
     * Remove e retorna o menor elemento do Fibonacci Heap.
     *
     * O procedimento promove todos os filhos do nó mínimo à lista de raízes,
     * remove o nó mínimo e executa a consolidação para reduzir o número de
     * árvores, unindo árvores de mesmo grau.
     *
     * Complexidade amortizada: O(log n).
     *
     * @return o menor elemento removido, ou `null` se o heap estiver vazio.
     */
    fun extractMin(): T? {
        val z = min ?: return null
        val minKey = z.key

        var child = z.child
        if (child != null) {
            val children = collectSiblings(child)
            for (c in children) {
                addToRootList(c)
                c.parent = null
            }
        }

        removeFromRootList(z)

        if (z == z.right) {
            min = null
        } else {
            min = z.right
            consolidate()
        }

        size--
        return minKey
    }

    /**
     * Mescla (merge) outro Fibonacci Heap com este heap.
     *
     * Concatena as listas de raízes e atualiza o ponteiro [min].
     * O outro heap é esvaziado após a operação.
     *
     * Complexidade: O(1).
     *
     * @param other o outro heap a ser mesclado.
     */
    fun merge(other: FibonacciHeap<T>) {
        if (other.min == null) return

        if (min == null) {
            min = other.min
        } else {
            concatenateRootLists(other)
            if (other.min!!.key < min!!.key) {
                min = other.min
            }
        }
        size += other.size
        other.min = null
        other.size = 0
    }

    /**
     * Diminui a chave de um nó para um novo valor menor.
     *
     * Se a nova chave violar a propriedade de min-heap em relação ao pai,
     * o nó é cortado de seu pai e adicionado à lista de raízes (cut).
     * O corte em cascata (cascading cut) propaga cortes para cima enquanto
     * os ancestrais estiverem marcados.
     *
     * Complexidade amortizada: O(1).
     *
     * @param node o nó cuja chave será diminuída.
     * @param newKey o novo valor da chave (deve ser menor ou igual à chave atual).
     * @throws IllegalArgumentException se [newKey] for maior que a chave atual do nó.
     */
    fun decreaseKey(node: Node<T>, newKey: T) {
        require(newKey <= node.key) { "A nova chave deve ser menor ou igual à chave atual." }
        node.key = newKey
        val parent = node.parent
        if (parent != null && node.key < parent.key) {
            cut(node, parent)
            cascadingCut(parent)
        }
        if (node.key < min!!.key) {
            min = node
        }
    }

    /**
     * Consolida o Fibonacci Heap unindo árvores de mesmo grau na lista de raízes.
     *
     * Utiliza um array auxiliar indexado por grau. Para cada raiz, se já existir
     * outra árvore com o mesmo grau, as duas são ligadas (a de maior chave torna-se
     * filha da de menor chave) e o processo continua até que todos os graus sejam únicos.
     *
     * O tamanho do array é limitado por D(n) = ⌊log_φ(n)⌋ + 1.
     *
     * Complexidade amortizada: O(log n).
     */
    private fun consolidate() {
        val maxDegree = maxDegree()
        val degreeTable = arrayOfNulls<Node<T>>(maxDegree + 1)

        val roots = collectSiblings(min!!)

        for (w in roots) {
            var x = w
            var d = x.degree
            while (d <= maxDegree && degreeTable[d] != null) {
                var y = degreeTable[d]!!
                if (x.key > y.key) {
                    val temp = x
                    x = y
                    y = temp
                }
                link(y, x)
                degreeTable[d] = null
                d++
            }
            degreeTable[d] = x
        }

        min = null
        for (node in degreeTable) {
            if (node != null) {
                node.left = node
                node.right = node
                if (min == null) {
                    min = node
                } else {
                    addToRootList(node)
                    if (node.key < min!!.key) {
                        min = node
                    }
                }
            }
        }
    }

    /**
     * Liga a árvore com raiz [child] como filha da árvore com raiz [parent].
     *
     * Remove [child] da lista de raízes, torna-o filho de [parent],
     * incrementa o grau de [parent] e desmarca [child].
     *
     * Complexidade: O(1).
     *
     * @param child o nó que se tornará filho.
     * @param parent o nó que se tornará pai.
     */
    private fun link(child: Node<T>, parent: Node<T>) {
        child.left.right = child.right
        child.right.left = child.left

        child.parent = parent
        if (parent.child == null) {
            parent.child = child
            child.left = child
            child.right = child
        } else {
            child.left = parent.child!!
            child.right = parent.child!!.right
            parent.child!!.right.left = child
            parent.child!!.right = child
        }
        parent.degree++
        child.mark = false
    }

    /**
     * Corta o nó [node] de seu pai [parent] e o adiciona à lista de raízes.
     *
     * Complexidade: O(1).
     *
     * @param node o nó a ser cortado.
     * @param parent o pai atual do nó.
     */
    private fun cut(node: Node<T>, parent: Node<T>) {
        if (node == node.right) {
            parent.child = null
        } else {
            node.left.right = node.right
            node.right.left = node.left
            if (parent.child == node) {
                parent.child = node.right
            }
        }
        parent.degree--

        node.parent = null
        node.mark = false
        node.left = node
        node.right = node
        addToRootList(node)
    }

    /**
     * Corte em cascata (cascading cut).
     *
     * Se o nó [node] está marcado (já perdeu um filho anteriormente), ele é cortado
     * de seu pai e o processo é repetido recursivamente no pai. Se não estiver marcado,
     * ele é marcado para indicar que perdeu um filho.
     *
     * Essa técnica garante que o grau máximo permanece O(log n), essencial para
     * a análise amortizada do Fibonacci Heap.
     *
     * Complexidade amortizada: O(1) (via análise de potencial).
     *
     * @param node o nó a partir do qual o corte em cascata é iniciado.
     */
    private fun cascadingCut(node: Node<T>) {
        val parent = node.parent
        if (parent != null) {
            if (!node.mark) {
                node.mark = true
            } else {
                cut(node, parent)
                cascadingCut(parent)
            }
        }
    }

    /**
     * Adiciona um nó à lista circular de raízes, à direita do nó [min].
     *
     * Complexidade: O(1).
     *
     * @param node o nó a ser adicionado à lista de raízes.
     */
    private fun addToRootList(node: Node<T>) {
        val m = min ?: return
        node.left = m
        node.right = m.right
        m.right.left = node
        m.right = node
    }

    /**
     * Remove um nó da lista circular de raízes.
     *
     * Complexidade: O(1).
     *
     * @param node o nó a ser removido da lista de raízes.
     */
    private fun removeFromRootList(node: Node<T>) {
        node.left.right = node.right
        node.right.left = node.left
    }

    /**
     * Concatena a lista de raízes de [other] com a lista de raízes deste heap.
     *
     * Complexidade: O(1).
     *
     * @param other o heap cuja lista de raízes será concatenada.
     */
    private fun concatenateRootLists(other: FibonacciHeap<T>) {
        val otherMin = other.min ?: return
        val thisMin = min ?: return

        val thisRight = thisMin.right
        val otherLeft = otherMin.left

        thisMin.right = otherMin
        otherMin.left = thisMin
        otherLeft.right = thisRight
        thisRight.left = otherLeft
    }

    /**
     * Coleta todos os nós irmãos em uma lista circular a partir de [start].
     *
     * Complexidade: O(k), onde k é o número de irmãos.
     *
     * @param start o nó inicial na lista circular.
     * @return uma lista contendo todos os nós na lista circular.
     */
    private fun collectSiblings(start: Node<T>): List<Node<T>> {
        val nodes = mutableListOf<Node<T>>()
        var current = start
        do {
            nodes.add(current)
            current = current.right
        } while (current != start)
        return nodes
    }

    /**
     * Calcula o grau máximo possível para um heap com [size] elementos.
     *
     * O grau máximo D(n) = ⌊log_φ(n)⌋, onde φ = (1+√5)/2 ≈ 1.618.
     *
     * @return o grau máximo, com mínimo de 1.
     */
    private fun maxDegree(): Int {
        if (size <= 1) return 1
        val phi = (1.0 + kotlin.math.sqrt(5.0)) / 2.0
        return (floor(ln(size.toDouble()) / ln(phi))).toInt() + 1
    }
}
