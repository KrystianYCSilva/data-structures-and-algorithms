package br.uem.din.datastructures.linkedlist

/**
 * Lista ligada simplesmente encadeada (Singly Linked List).
 *
 * Implementação genérica de uma lista ligada onde cada nó contém uma referência
 * para o próximo nó. Mantém referências para a cabeça (head) e cauda (tail)
 * para otimizar operações de inserção nas extremidades.
 *
 * Complexidades:
 * - Inserção no início ([push]): O(1)
 * - Inserção no final ([append]): O(1)
 * - Inserção após um nó ([insert]): O(1)
 * - Remoção do início ([pop]): O(1)
 * - Remoção do final ([removeLast]): O(n)
 * - Acesso por índice ([nodeAt]): O(n)
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists.
 */
class LinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    /** Número de elementos atualmente na lista. */
    var size = 0
        private set

    /**
     * Verifica se a lista está vazia.
     *
     * @return `true` se a lista não contiver elementos, `false` caso contrário.
     */
    fun isEmpty() = size == 0

    /**
     * Retorna a representação textual da lista, delegando ao [Node.toString].
     *
     * @return cadeia de valores no formato `v1 -> v2 -> ... -> vn`, ou `"Empty list"` se vazia.
     */
    override fun toString(): String {
        return if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }
    }

    /**
     * Insere um elemento no início da lista (head-insertion).
     *
     * Complexidade: O(1).
     *
     * @param value o valor a ser inserido.
     * @return esta instância de [LinkedList] para encadeamento fluente.
     */
    fun push(value: T): LinkedList<T> {
        head = Node(value = value, next = head)
        if (tail == null) {
            tail = head
        }
        size++
        return this
    }

    /**
     * Insere um elemento no final da lista (tail-insertion).
     *
     * Complexidade: O(1) — graças à referência direta ao nó cauda.
     *
     * @param value o valor a ser inserido.
     * @return esta instância de [LinkedList] para encadeamento fluente.
     */
    fun append(value: T): LinkedList<T> {
        if (isEmpty()) {
            push(value)
            return this
        }
        val newNode = Node(value = value)
        tail!!.next = newNode
        tail = newNode
        size++
        return this
    }

    /**
     * Retorna o nó na posição especificada pelo índice.
     *
     * Complexidade: O(n), onde n é o valor do índice.
     *
     * @param index a posição desejada (0-based).
     * @return o [Node] na posição indicada, ou `null` se o índice for inválido.
     */
    fun nodeAt(index: Int): Node<T>? {
        var currentNode = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    /**
     * Insere um novo valor imediatamente após o nó especificado.
     *
     * Complexidade: O(1).
     *
     * @param value o valor a ser inserido.
     * @param afterNode o nó após o qual o novo nó será inserido.
     * @return o novo [Node] criado.
     */
    fun insert(value: T, afterNode: Node<T>): Node<T> {
        if (tail == afterNode) {
            append(value)
            return tail!!
        }
        val newNode = Node(value = value, next = afterNode.next)
        afterNode.next = newNode
        size++
        return newNode
    }

    /**
     * Remove e retorna o elemento no início da lista (head-removal).
     *
     * Complexidade: O(1).
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    fun pop(): T? {
        if (isEmpty()) return null
        val result = head?.value
        head = head?.next
        size--
        if (isEmpty()) {
            tail = null
        }
        return result
    }

    /**
     * Remove e retorna o último elemento da lista (tail-removal).
     *
     * Complexidade: O(n) — requer travessia até o penúltimo nó, pois a lista é simplesmente encadeada.
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    fun removeLast(): T? {
        if (head?.next == null) return pop()
        var prev = head
        var current = head
        var next = current?.next
        while (next != null) {
            prev = current
            current = next
            next = current.next
        }
        prev?.next = null
        tail = prev
        size--
        return current?.value
    }

    /**
     * Remove e retorna o elemento imediatamente após o nó especificado.
     *
     * Complexidade: O(1).
     *
     * @param node o nó cujo sucessor será removido.
     * @return o valor do nó removido, ou `null` se não houver sucessor.
     */
    fun removeAfter(node: Node<T>): T? {
        val result = node.next?.value
        if (node.next == tail) {
            tail = node
        }
        node.next = node.next?.next
        size--
        return result
    }
}
