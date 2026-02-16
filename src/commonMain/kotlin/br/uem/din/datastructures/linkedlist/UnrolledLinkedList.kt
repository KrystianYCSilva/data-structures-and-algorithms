package br.uem.din.datastructures.linkedlist

/**
 * Lista ligada desenrolada (Unrolled Linked List).
 *
 * Variante de lista ligada onde cada nó armazena um array de múltiplos elementos
 * em vez de um único valor. Essa abordagem melhora a localidade de cache (cache locality)
 * e reduz o overhead de ponteiros por elemento, combinando vantagens de arrays e listas ligadas.
 * Implementa [Iterable] para uso idiomático com `for`, `map`, `filter` e demais operações
 * do Kotlin stdlib.
 *
 * Cada nó interno possui capacidade fixa definida por [nodeCapacity]. Quando um nó está cheio,
 * um novo nó é alocado e encadeado.
 *
 * Complexidades:
 * | Operação            | Complexidade      |
 * |---------------------|-------------------|
 * | [add]               | O(1) amortizado   |
 * | [addFirst]          | O(nodeCapacity)   |
 * | [addLast]           | O(1) amortizado   |
 * | [removeFirst]       | O(nodeCapacity)   |
 * | [get]               | O(n/nodeCapacity)  |
 * | [removeAt]          | O(n/nodeCapacity)  |
 * | [contains]          | O(n)              |
 * | [clear]             | O(1)              |
 *
 * @param T o tipo dos elementos armazenados.
 * @property nodeCapacity capacidade máxima de elementos por nó interno (padrão: 16).
 *
 * Referência: Shao, Z. et al. "Cache-Conscious Structure Layout" (1999);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10 — Elementary Data Structures.
 */
class UnrolledLinkedList<T>(val nodeCapacity: Int = 16) : MutableLinkedList<T> {

    /**
     * Nó interno da lista desenrolada, contendo um array de elementos com capacidade fixa.
     *
     * @property next referência ao próximo nó na cadeia.
     * @property elements array de armazenamento interno.
     * @property count número de elementos efetivamente armazenados neste nó.
     */
    private inner class UnrolledNode(var next: UnrolledNode? = null) {
        val elements = arrayOfNulls<Any?>(nodeCapacity)
        var count = 0

        fun isFull() = count == nodeCapacity

        fun add(element: T) {
            elements[count++] = element
        }

        fun removeAt(index: Int) {
            for (i in index until count - 1) {
                elements[i] = elements[i + 1]
            }
            elements[--count] = null
        }
    }

    private var head: UnrolledNode? = null
    private var tail: UnrolledNode? = null

    /**
     * Número total de elementos armazenados na lista.
     *
     * Complexidade: O(1).
     */
    override var size = 0
        private set

    /**
     * Insere um elemento no início da lista desenrolada.
     *
     * Insere no primeiro nó interno, deslocando os elementos existentes. Se o nó estiver cheio,
     * cria um novo nó no início.
     *
     * Complexidade: O(nodeCapacity) para deslocamento dentro do nó.
     *
     * @param element o elemento a ser adicionado.
     */
    override fun addFirst(element: T) {
        if (head == null) {
            head = UnrolledNode()
            tail = head
        }
        if (head!!.isFull()) {
            val newNode = UnrolledNode()
            newNode.next = head
            head = newNode
        }
        val node = head!!
        for (i in node.count downTo 1) {
            node.elements[i] = node.elements[i - 1]
        }
        node.elements[0] = element
        node.count++
        size++
    }

    /**
     * Insere um elemento no final da lista desenrolada.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param element o elemento a ser adicionado.
     */
    override fun addLast(element: T) = add(element)

    /**
     * Remove e retorna o primeiro elemento da lista desenrolada.
     *
     * Complexidade: O(nodeCapacity) para deslocamento dentro do nó.
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    @Suppress("UNCHECKED_CAST")
    override fun removeFirst(): T? {
        if (isEmpty()) return null
        return removeAt(0)
    }

    /**
     * Remove e retorna o último elemento da lista desenrolada.
     *
     * Complexidade: O(n/nodeCapacity).
     *
     * @return o valor removido, ou `null` se a lista estiver vazia.
     */
    override fun removeLast(): T? {
        if (isEmpty()) return null
        return removeAt(size - 1)
    }

    /**
     * Adiciona um elemento ao final da lista.
     *
     * Se o último nó estiver cheio, um novo nó é alocado automaticamente.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param element o elemento a ser adicionado.
     */
    fun add(element: T) {
        if (head == null) {
            head = UnrolledNode()
            tail = head
        }

        if (tail!!.isFull()) {
            val newNode = UnrolledNode()
            tail!!.next = newNode
            tail = newNode
        }

        tail!!.add(element)
        size++
    }

    /**
     * Retorna o elemento na posição especificada.
     *
     * Percorre os nós internos sequencialmente até localizar o nó que contém o índice desejado.
     *
     * Complexidade: O(n/nodeCapacity), onde n é o índice solicitado.
     *
     * @param index a posição desejada (0-based).
     * @return o elemento na posição indicada.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index $index, size $size")
        var node = head
        var idx = index
        while (node != null) {
            if (idx < node.count) {
                return node.elements[idx] as T
            }
            idx -= node.count
            node = node.next
        }
        throw IndexOutOfBoundsException()
    }

    /**
     * Remove e retorna o elemento na posição especificada.
     *
     * Complexidade: O(n/nodeCapacity).
     *
     * @param index a posição do elemento a ser removido (0-based).
     * @return o valor removido.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    @Suppress("UNCHECKED_CAST")
    fun removeAt(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index $index, size $size")
        var node = head
        var prev: UnrolledNode? = null
        var idx = index
        while (node != null) {
            if (idx < node.count) {
                val removed = node.elements[idx] as T
                node.removeAt(idx)
                size--
                if (node.count == 0) {
                    if (prev == null) {
                        head = node.next
                    } else {
                        prev.next = node.next
                    }
                    if (node == tail) {
                        tail = prev
                    }
                }
                return removed
            }
            idx -= node.count
            prev = node
            node = node.next
        }
        throw IndexOutOfBoundsException()
    }

    /**
     * Verifica se a lista contém o elemento especificado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return `true` se encontrado, `false` caso contrário.
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
     * Verifica se a lista está vazia.
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    override fun isEmpty() = size == 0

    /**
     * Retorna uma cópia dos elementos como [List] imutável do Kotlin stdlib.
     *
     * Complexidade: O(n).
     *
     * @return lista imutável contendo todos os elementos na ordem de inserção.
     */
    override fun toList(): List<T> = iterator().asSequence().toList()

    /**
     * Retorna representação textual da lista no formato `[v1, v2, ..., vn]`.
     *
     * Complexidade: O(n).
     *
     * @return string formatada com os elementos da lista.
     */
    override fun toString(): String {
        if (isEmpty()) return "[]"
        return joinToString(prefix = "[", postfix = "]")
    }

    /**
     * Retorna um [Iterator] que percorre todos os elementos de todos os nós sequencialmente.
     *
     * Complexidade: O(1) para criação; O(n) para travessia completa.
     *
     * @return iterador sobre os elementos.
     */
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var currentNode = head
        private var currentIndex = 0

        override fun hasNext(): Boolean {
            while (currentNode != null && currentIndex >= currentNode!!.count) {
                currentNode = currentNode!!.next
                currentIndex = 0
            }
            return currentNode != null
        }

        @Suppress("UNCHECKED_CAST")
        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()
            return currentNode!!.elements[currentIndex++] as T
        }
    }
}
