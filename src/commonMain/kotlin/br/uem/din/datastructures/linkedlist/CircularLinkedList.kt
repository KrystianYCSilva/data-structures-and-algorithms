package br.uem.din.datastructures.linkedlist

/**
 * Lista ligada circular (Circular Linked List).
 *
 * Variante de lista ligada onde o último nó aponta de volta para o primeiro,
 * formando um ciclo. Útil para aplicações como escalonamento round-robin
 * e buffers circulares.
 *
 * Complexidades:
 * - Inserção ([add]): O(1)
 * - Remoção por valor ([remove]): O(n)
 * - Acesso por índice ([get]): O(n)
 *
 * @param T o tipo dos elementos armazenados na lista.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 1, Sec. 2.2.4 — Circular Lists.
 */
class CircularLinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    /** Número de elementos atualmente na lista. */
    var size = 0
        private set

    /**
     * Verifica se a lista está vazia.
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    fun isEmpty() = size == 0

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
    fun add(value: T) {
        val newNode = Node(value)
        if (isEmpty()) {
            head = newNode
            tail = newNode
            newNode.next = head // Points to itself
        } else {
            tail?.next = newNode
            tail = newNode
            tail?.next = head // Points back to head
        }
        size++
    }

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
                if (current == head && current == tail) { // Only one node
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
     * Retorna representação textual da lista no formato `[v1, v2, ..., vn]`.
     *
     * @return string formatada com os elementos da lista.
     */
    override fun toString(): String {
        if (isEmpty()) return "[]"
        val sb = StringBuilder("[")
        var current = head
        do {
            sb.append(current?.value)
            current = current?.next
            if (current != head) {
                sb.append(", ")
            }
        } while (current != head)
        sb.append("]")
        return sb.toString()
    }
}
