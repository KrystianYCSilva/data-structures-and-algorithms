package br.uem.din.datastructures.linkedlist

/**
 * Lista ligada circular (Circular Linked List).
 *
 * Variante de lista ligada onde o último nó aponta de volta para o primeiro,
 * formando um ciclo. Útil para aplicações como escalonamento round-robin
 * e buffers circulares. Implementa [Iterable] para uso idiomático com
 * `for`, `map`, `filter` e demais operações do Kotlin stdlib.
 *
 * Complexidades:
 * | Operação                     | Complexidade |
 * |------------------------------|-------------|
 * | [add] / [addLast]            | O(1)        |
 * | [addFirst]                   | O(1)        |
 * | [remove]                     | O(n)        |
 * | [removeAt]                   | O(n)        |
 * | [get]                        | O(n)        |
 * | [contains] / [indexOf]       | O(n)        |
 * | [clear]                      | O(1)        |
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 1, Sec. 2.2.4 — Circular Lists.
 *
 * @see LinkedList
 * @see DoublyLinkedList
 */
class CircularLinkedList<T> : Iterable<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    /**
     * Número de elementos atualmente na lista.
     *
     * Complexidade: O(1).
     */
    var size = 0
        private set

    /**
     * Verifica se a lista está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    fun isEmpty() = size == 0

    /**
     * Adiciona um elemento no início da lista circular.
     *
     * Complexidade: O(1).
     *
     * @param value o valor a ser adicionado.
     */
    fun addFirst(value: T) {
        val newNode = Node(value)
        if (isEmpty()) {
            head = newNode
            tail = newNode
            newNode.next = head
        } else {
            newNode.next = head
            head = newNode
            tail?.next = head
        }
        size++
    }

    /**
     * Adiciona um elemento ao final da lista circular.
     *
     * O nó inserido terá seu ponteiro `next` apontando para a cabeça da lista,
     * mantendo a propriedade circular.
     *
     * Complexidade: O(1).
     *
     * @param value o valor a ser adicionado.
     */
    fun addLast(value: T) {
        val newNode = Node(value)
        if (isEmpty()) {
            head = newNode
            tail = newNode
            newNode.next = head
        } else {
            tail?.next = newNode
            tail = newNode
            tail?.next = head
        }
        size++
    }

    /**
     * Adiciona um elemento ao final da lista circular.
     *
     * Alias para [addLast].
     *
     * Complexidade: O(1).
     *
     * @param value o valor a ser adicionado.
     */
    fun add(value: T) = addLast(value)

    /**
     * Remove a primeira ocorrência do valor especificado da lista circular.
     *
     * Trata os casos especiais de remoção do único nó, da cabeça e da cauda,
     * mantendo a integridade circular.
     *
     * Complexidade: O(n), onde n é o número de elementos.
     *
     * @param value o valor a ser removido.
     * @return `true` se o valor foi encontrado e removido, `false` caso contrário.
     */
    fun remove(value: T): Boolean {
        if (isEmpty()) return false

        var current = head
        var prev: Node<T>? = tail

        do {
            if (current?.value == value) {
                if (current == head && current == tail) {
                    head = null
                    tail = null
                } else if (current == head) {
                    head = head?.next
                    tail?.next = head
                } else if (current == tail) {
                    tail = prev
                    tail?.next = head
                } else {
                    prev?.next = current?.next
                }
                size--
                return true
            }
            prev = current
            current = current?.next
        } while (current != head)

        return false
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
        if (index == 0) {
            val value = head!!.value
            if (head == tail) {
                head = null
                tail = null
            } else {
                head = head?.next
                tail?.next = head
            }
            size--
            return value
        }
        var prev = head
        repeat(index - 1) { prev = prev?.next }
        val removed = prev?.next!!
        if (removed == tail) {
            tail = prev
            tail?.next = head
        } else {
            prev?.next = removed.next
        }
        size--
        return removed.value
    }

    /**
     * Retorna o valor na posição especificada.
     *
     * Complexidade: O(n), onde n é o índice solicitado.
     *
     * @param index a posição desejada (0-based).
     * @return o valor na posição, ou `null` se o índice for inválido.
     */
    operator fun get(index: Int): T? {
        if (isEmpty() || index < 0 || index >= size) return null
        var current = head
        repeat(index) {
            current = current?.next
        }
        return current?.value
    }

    /**
     * Verifica se a lista contém o elemento especificado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return `true` se encontrado, `false` caso contrário.
     */
    fun contains(element: T): Boolean {
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
    fun indexOf(element: T): Int {
        var idx = 0
        for (v in this) {
            if (v == element) return idx
            idx++
        }
        return -1
    }

    /**
     * Remove todos os elementos da lista.
     *
     * Complexidade: O(1).
     */
    fun clear() {
        head = null
        tail = null
        size = 0
    }

    /**
     * Retorna uma cópia dos elementos como [List] imutável do Kotlin stdlib.
     *
     * Complexidade: O(n).
     *
     * @return lista imutável contendo todos os elementos na ordem de inserção.
     */
    fun toList(): List<T> = iterator().asSequence().toList()

    /**
     * Retorna representação textual da lista no formato `[v1, v2, ..., vn]`.
     *
     * @return string formatada com os elementos da lista.
     */
    override fun toString(): String {
        if (isEmpty()) return "[]"
        return joinToString(prefix = "[", postfix = "]")
    }

    /**
     * Retorna um [Iterator] que percorre os elementos da lista circular uma vez (do head ao tail).
     *
     * Complexidade: O(1) para criação; O(n) para travessia completa.
     *
     * @return iterador sobre os elementos.
     */
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var current = head
        private var remaining = size
        override fun hasNext(): Boolean = remaining > 0
        override fun next(): T {
            if (remaining <= 0) throw NoSuchElementException()
            val value = current!!.value
            current = current?.next
            remaining--
            return value
        }
    }
}
