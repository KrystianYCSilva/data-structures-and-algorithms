package br.uem.din.datastructures.heap

import br.uem.din.datastructures.queue.MutableQueue

/**
 * Heap Binomial (Binomial Heap) — heap mergeable composto por uma coleção de árvores binomiais.
 *
 * Uma árvore binomial B_k é definida recursivamente:
 * - B_0 é um único nó.
 * - B_k é formada ligando duas árvores B_{k-1}, onde a raiz de uma se torna o filho
 *   mais à esquerda da raiz da outra.
 *
 * Um heap binomial é um conjunto de árvores binomiais que satisfazem:
 * 1. Cada árvore binomial obedece à propriedade de min-heap.
 * 2. Para cada ordem k, existe no máximo uma árvore binomial B_k no heap.
 *
 * A representação binária do número de elementos n determina quais árvores binomiais
 * estão presentes: se o bit k está ativo, B_k está presente.
 *
 * Complexidades:
 * - [insert]: O(log n) pior caso, O(1) amortizado
 * - [extractMin]: O(log n)
 * - [merge]: O(log n)
 * - [decreaseKey]: O(log n)
 * - [peek]: O(log n), ou O(1) se o ponteiro para o mínimo for mantido
 * - [isEmpty]: O(1)
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Vuillemin, J. "A data structure for manipulating priority queues" (1978);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 19 — Binomial Heaps.
 */
public class BinomialHeap<T : Comparable<T>> : MutableQueue<T> {

    /**
     * Nó interno de uma árvore binomial.
     *
     * Utiliza a representação filho-esquerdo, irmão-direito (left-child, right-sibling):
     * [child] aponta para o filho mais à esquerda e [sibling] para o próximo irmão.
     *
     * @property key a chave armazenada neste nó.
     * @property degree o grau (número de filhos) deste nó.
     * @property parent referência ao nó pai, ou `null` se for raiz.
     * @property child referência ao filho mais à esquerda, ou `null` se folha.
     * @property sibling referência ao próximo irmão, ou `null` se for o último filho.
     */
    public class Node<T> internal constructor(
        public var key: T,
        public var degree: Int = 0,
        public var parent: Node<T>? = null,
        public var child: Node<T>? = null,
        public var sibling: Node<T>? = null
    )

    private var head: Node<T>? = null

    /** Número de elementos armazenados no heap. */
    public override var size: Int = 0
        private set

    /**
     * Verifica se o heap está vazio.
     *
     * Complexidade: O(1).
     *
     * @return `true` se o heap não contiver elementos.
     */
    public override fun isEmpty(): Boolean = size == 0

    /**
     * Retorna o menor elemento do heap sem removê-lo.
     *
     * Percorre a lista de raízes para encontrar a raiz com a menor chave.
     *
     * Complexidade: O(log n), pois há no máximo ⌊log₂ n⌋ + 1 árvores binomiais.
     *
     * @return o menor elemento, ou `null` se o heap estiver vazio.
     */
    public override fun peek(): T? {
        var minNode: Node<T>? = null
        var current = head
        while (current != null) {
            if (minNode == null || current.key < minNode.key) {
                minNode = current
            }
            current = current.sibling
        }
        return minNode?.key
    }

    /**
     * Insere um elemento no heap binomial.
     *
     * Cria um heap contendo apenas o novo elemento e realiza merge com o heap atual.
     *
     * Complexidade: O(log n) pior caso, O(1) amortizado.
     *
     * @param key o elemento a ser inserido.
     */
    public fun insert(key: T) {
        val newHeap = BinomialHeap<T>()
        newHeap.head = Node(key)
        newHeap.size = 1
        mergeWith(newHeap)
    }

    /**
     * Remove e retorna o menor elemento do heap binomial.
     *
     * O procedimento encontra a árvore com a menor raiz, remove-a da lista de raízes,
     * inverte a lista de filhos dessa raiz (para obter uma lista de raízes válida)
     * e realiza merge do resultado com o heap restante.
     *
     * Complexidade: O(log n).
     *
     * @return o menor elemento removido, ou `null` se o heap estiver vazio.
     */
    public fun extractMin(): T? {
        if (head == null) return null

        var minPrev: Node<T>? = null
        var minNode: Node<T> = head!!
        var prev: Node<T>? = null
        var current = head

        while (current != null) {
            if (current.key < minNode.key) {
                minNode = current
                minPrev = prev
            }
            prev = current
            current = current.sibling
        }

        if (minPrev == null) {
            head = minNode.sibling
        } else {
            minPrev.sibling = minNode.sibling
        }

        var child = minNode.child
        var reversedHead: Node<T>? = null
        var childCount = 0
        while (child != null) {
            val next = child.sibling
            child.sibling = reversedHead
            child.parent = null
            reversedHead = child
            child = next
            childCount++
        }

        val childHeap = BinomialHeap<T>()
        childHeap.head = reversedHead
        childHeap.size = (1 shl minNode.degree) - 1

        size -= childHeap.size + 1
        mergeWith(childHeap)

        return minNode.key
    }

    /** {@inheritDoc} */
    public override fun enqueue(element: T) {
        insert(element)
    }

    /** {@inheritDoc} */
    public override fun dequeue(): T? = extractMin()

    /** {@inheritDoc} */
    public override fun contains(element: T): Boolean {
        return containsNode(head, element)
    }

    /** {@inheritDoc} */
    public override fun clear() {
        head = null
        size = 0
    }

    /** {@inheritDoc} */
    public override fun iterator(): Iterator<T> {
        val elements = mutableListOf<T>()
        collectElements(head, elements)
        return elements.iterator()
    }

    private fun containsNode(node: Node<T>?, element: T): Boolean {
        var current = node
        while (current != null) {
            if (current.key == element) return true
            if (containsNode(current.child, element)) return true
            current = current.sibling
        }
        return false
    }

    private fun collectElements(node: Node<T>?, result: MutableList<T>) {
        var current = node
        while (current != null) {
            result.add(current.key)
            collectElements(current.child, result)
            current = current.sibling
        }
    }

    /**
     * Mescla (merge) outro heap binomial com este heap.
     *
     * O procedimento intercala as listas de raízes em ordem crescente de grau e, em seguida,
     * consolida árvores de mesmo grau ligando-as (similar à adição binária com carry).
     *
     * Complexidade: O(log n), onde n é o número total de elementos nos dois heaps.
     *
     * @param other o outro heap a ser mesclado (será esvaziado após a operação).
     */
    public fun merge(other: BinomialHeap<T>) {
        mergeWith(other)
    }

    /**
     * Diminui a chave de um nó para um novo valor menor.
     *
     * Após a atualização, propaga o nó para cima na árvore binomial (bubble-up)
     * trocando chaves com o pai enquanto a propriedade de min-heap estiver violada.
     *
     * Complexidade: O(log n), pois a altura de B_k é k ≤ ⌊log₂ n⌋.
     *
     * @param node o nó cuja chave será diminuída.
     * @param newKey o novo valor da chave (deve ser menor ou igual à chave atual).
     * @throws IllegalArgumentException se [newKey] for maior que a chave atual do nó.
     */
    public fun decreaseKey(node: Node<T>, newKey: T) {
        require(newKey <= node.key) { "A nova chave deve ser menor ou igual à chave atual." }
        node.key = newKey
        bubbleUp(node)
    }

    /**
     * Intercala duas listas de raízes em ordem crescente de grau.
     *
     * Complexidade: O(log n).
     *
     * @param h1 cabeça da primeira lista de raízes.
     * @param h2 cabeça da segunda lista de raízes.
     * @return a cabeça da lista intercalada.
     */
    private fun mergeLists(h1: Node<T>?, h2: Node<T>?): Node<T>? {
        if (h1 == null) return h2
        if (h2 == null) return h1

        var a = h1
        var b = h2
        val dummy = Node(h1.key)
        var tail = dummy

        while (a != null && b != null) {
            if (a.degree <= b.degree) {
                tail.sibling = a
                tail = a
                a = a.sibling
            } else {
                tail.sibling = b
                tail = b
                b = b.sibling
            }
        }
        tail.sibling = a ?: b
        return dummy.sibling
    }

    /**
     * Liga (link) a árvore binomial com raiz [child] como filha da árvore com raiz [parent].
     *
     * Complexidade: O(1).
     *
     * @param child o nó que se tornará filho.
     * @param parent o nó que se tornará pai.
     */
    private fun link(child: Node<T>, parent: Node<T>) {
        child.parent = parent
        child.sibling = parent.child
        parent.child = child
        parent.degree++
    }

    /**
     * Realiza o merge interno e consolida as árvores binomiais.
     *
     * Após intercalar as listas de raízes, percorre a lista consolidando
     * árvores de mesmo grau. Há três casos a tratar durante a consolidação,
     * conforme descrito em Cormen et al., Cap. 19.
     *
     * @param other o heap a ser mesclado.
     */
    private fun mergeWith(other: BinomialHeap<T>) {
        head = mergeLists(head, other.head)
        size += other.size
        other.head = null
        other.size = 0

        if (head == null) return

        var prev: Node<T>? = null
        var curr: Node<T>? = head
        var next: Node<T>? = curr?.sibling

        while (next != null) {
            if (curr!!.degree != next.degree ||
                (next.sibling != null && next.sibling!!.degree == curr.degree)
            ) {
                prev = curr
                curr = next
            } else if (curr.key <= next.key) {
                curr.sibling = next.sibling
                link(next, curr)
            } else {
                if (prev == null) {
                    head = next
                } else {
                    prev.sibling = next
                }
                link(curr, next)
                curr = next
            }
            next = curr.sibling
        }
    }

    /**
     * Propaga um nó para cima na árvore binomial, trocando chaves com o pai
     * enquanto a propriedade de min-heap estiver violada.
     *
     * Complexidade: O(log n).
     *
     * @param node o nó a ser propagado.
     */
    private fun bubbleUp(node: Node<T>) {
        var current = node
        var parent = current.parent
        while (parent != null && current.key < parent.key) {
            val temp = current.key
            current.key = parent.key
            parent.key = temp
            current = parent
            parent = current.parent
        }
    }
}
