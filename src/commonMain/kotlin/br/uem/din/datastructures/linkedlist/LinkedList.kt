package br.uem.din.datastructures.linkedlist

/**
 * Lista ligada simplesmente encadeada (Singly Linked List).
 *
 * Implementação genérica e mutável de uma lista ligada onde cada nó contém uma referência
 * para o próximo nó. Mantém referências para a cabeça (head) e cauda (tail)
 * para otimizar operações de inserção nas extremidades. Implementa [Iterable] para
 * permitir uso idiomático com `for`, `map`, `filter` e demais operações do Kotlin stdlib.
 *
 * Complexidades:
 * | Operação               | Complexidade |
 * |------------------------|-------------|
 * | [push] / [addFirst]    | O(1)        |
 * | [append] / [addLast]   | O(1)        |
 * | [insert]               | O(1)        |
 * | [pop] / [removeFirst]  | O(1)        |
 * | [removeLast]           | O(n)        |
 * | [get] / [nodeAt]       | O(n)        |
 * | [set]                  | O(n)        |
 * | [contains] / [indexOf] | O(n)        |
 * | [removeAt]             | O(n)        |
 * | [insertAt]             | O(n)        |
 * | [reverse]              | O(n)        |
 * | [clear]                | O(1)        |
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.2 — Linked Lists.
 *
 * @see DoublyLinkedList
 * @see CircularLinkedList
 */
class LinkedList<T> : MutableLinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    /**
     * Número de elementos atualmente na lista.
     *
     * Complexidade: O(1).
     */
    override var size = 0
        private set

    /**
     * Verifica se a lista está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se a lista não contiver elementos, `false` caso contrário.
     */
    override fun isEmpty() = size == 0

    /**
     * Retorna a representação textual da lista no formato `[v1, v2, ..., vn]`.
     *
     * Complexidade: O(n).
     *
     * @return string formatada com os elementos, ou `"[]"` se vazia.
     */
    override fun toString(): String {
        if (isEmpty()) return "[]"
        return joinToString(prefix = "[", postfix = "]")
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
        addFirst(value)
        return this
    }

    /**
     * Insere um elemento no início da lista.
     *
     * Complexidade: O(1).
     *
     * @param value o valor a ser inserido.
     */
    override fun addFirst(element: T) {
        head = Node(value = element, next = head)
        if (tail == null) {
            tail = head
        }
        size++
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
        addLast(value)
        return this
    }

    /**
     * Insere um elemento no final da lista.
     *
     * Complexidade: O(1).
     *
     * @param value o valor a ser inserido.
     */
    override fun addLast(element: T) {
        if (isEmpty()) {
            addFirst(element)
            return
        }
        val newNode = Node(value = element)
        tail!!.next = newNode
        tail = newNode
        size++
    }

    /**
     * Retorna o nó na posição especificada pelo índice.
     *
     * Complexidade: O(n), onde n é o valor do índice.
     *
     * @param index a posição desejada (0-based).
     * @return o [Node] na posição indicada, ou `null` se o índice for inválido.
     */
    internal fun nodeAt(index: Int): Node<T>? {
        if (index < 0) return null
        var currentNode = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    /**
     * Retorna o valor na posição especificada.
     *
     * Complexidade: O(n).
     *
     * @param index a posição desejada (0-based).
     * @return o valor na posição indicada.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    operator fun get(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index $index, size $size")
        return nodeAt(index)!!.value
    }

    /**
     * Substitui o valor na posição especificada.
     *
     * Complexidade: O(n).
     *
     * @param index a posição desejada (0-based).
     * @param value o novo valor.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    operator fun set(index: Int, value: T) {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index $index, size $size")
        nodeAt(index)!!.value = value
    }

    /**
     * Verifica se a lista contém o elemento especificado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return `true` se o elemento existir na lista, `false` caso contrário.
     */
    override fun contains(element: T): Boolean {
        for (v in this) {
            if (v == element) return true
        }
        return false
    }

    /**
     * Retorna o índice da primeira ocorrência do valor, ou -1 se não encontrado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return o índice (0-based), ou -1.
     */
    override fun indexOf(element: T): Int {
        var idx = 0
        for (v in this) {
            if (v == element) return idx
            idx++
        }
        return -1
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
    internal fun insert(value: T, afterNode: Node<T>): Node<T> {
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
     * Insere um elemento na posição especificada, deslocando os subsequentes.
     *
     * Complexidade: O(n).
     *
     * @param index a posição de inserção (0-based). Se 0, insere no início; se [size], insere no final.
     * @param value o valor a ser inserido.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun insertAt(index: Int, value: T) {
        if (index < 0 || index > size) throw IndexOutOfBoundsException("Index $index, size $size")
        when (index) {
            0 -> addFirst(value)
            size -> addLast(value)
            else -> insert(value, nodeAt(index - 1)!!)
        }
    }

    /**
     * Remove e retorna o elemento no início da lista (head-removal).
     *
     * Complexidade: O(1).
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    fun pop(): T? = removeFirst()

    /**
     * Remove e retorna o primeiro elemento da lista.
     *
     * Complexidade: O(1).
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    override fun removeFirst(): T? {
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
    override fun removeLast(): T? {
        if (head?.next == null) return removeFirst()
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
     * Remove e retorna o elemento na posição especificada.
     *
     * Complexidade: O(n).
     *
     * @param index a posição do elemento a ser removido (0-based).
     * @return o valor removido.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun removeAt(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index $index, size $size")
        if (index == 0) return removeFirst()!!
        val prev = nodeAt(index - 1)!!
        val removed = prev.next!!.value
        if (prev.next == tail) {
            tail = prev
        }
        prev.next = prev.next?.next
        size--
        return removed
    }

    /**
     * Remove e retorna o elemento imediatamente após o nó especificado.
     *
     * Complexidade: O(1).
     *
     * @param node o nó cujo sucessor será removido.
     * @return o valor do nó removido, ou `null` se não houver sucessor.
     */
    internal fun removeAfter(node: Node<T>): T? {
        val result = node.next?.value ?: return null
        if (node.next == tail) {
            tail = node
        }
        node.next = node.next?.next
        size--
        return result
    }

    /**
     * Remove todos os elementos da lista.
     *
     * Complexidade: O(1).
     */
    override fun clear() {
        head = null
        tail = null
        size = 0
    }

    /**
     * Inverte a ordem dos elementos da lista in-place.
     *
     * Complexidade: O(n).
     */
    fun reverse() {
        tail = head
        var prev: Node<T>? = null
        var current = head
        while (current != null) {
            val next = current.next
            current.next = prev
            prev = current
            current = next
        }
        head = prev
    }

    /**
     * Retorna uma cópia dos elementos como [List] imutável do Kotlin stdlib.
     *
     * Complexidade: O(n).
     *
     * @return lista imutável contendo todos os elementos na ordem de inserção.
     */
    override fun toList(): List<T> = iterator().asSequence().toList()

    /**
     * Retorna um [Iterator] que percorre os elementos da lista do início ao fim.
     *
     * Complexidade: O(1) para criação; O(n) para travessia completa.
     *
     * @return iterador sobre os elementos da lista.
     */
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var current = head
        override fun hasNext(): Boolean = current != null
        override fun next(): T {
            val value = current?.value ?: throw NoSuchElementException()
            current = current?.next
            return value
        }
    }
}
