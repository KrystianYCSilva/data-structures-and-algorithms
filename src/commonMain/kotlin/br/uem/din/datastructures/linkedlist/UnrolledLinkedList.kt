package br.uem.din.datastructures.linkedlist

/**
 * Lista ligada desenrolada (Unrolled Linked List).
 *
 * Variante de lista ligada onde cada nó armazena um array de múltiplos elementos
 * em vez de um único valor. Essa abordagem melhora a localidade de cache (cache locality)
 * e reduz o overhead de ponteiros por elemento, combinando vantagens de arrays e listas ligadas.
 *
 * Cada nó interno possui capacidade fixa definida por [nodeCapacity]. Quando um nó está cheio,
 * um novo nó é alocado e encadeado.
 *
 * Complexidades:
 * - Inserção ([add]): O(1) amortizado
 * - Acesso por índice ([get]): O(n/nodeCapacity)
 *
 * @param T o tipo dos elementos armazenados.
 * @property nodeCapacity capacidade máxima de elementos por nó interno (padrão: 16).
 *
 * Referência: Shao, Z. et al. "Cache-Conscious Structure Layout" (1999);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10 — Elementary Data Structures.
 */
class UnrolledLinkedList<T>(val nodeCapacity: Int = 16) {
    
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
        
        /**
         * Verifica se o nó atingiu sua capacidade máxima.
         *
         * @return `true` se o nó estiver cheio.
         */
        fun isFull() = count == nodeCapacity
        
        /**
         * Adiciona um elemento na próxima posição livre do nó.
         *
         * @param element o elemento a ser adicionado.
         */
        fun add(element: T) {
            elements[count++] = element
        }
    }

    private var head: UnrolledNode? = null
    private var tail: UnrolledNode? = null
    /** Número total de elementos armazenados na lista. */
    var size = 0
        private set

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
    operator fun get(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException()
        var node = head
        var idx = index
        while (node != null) {
            if (idx < node.count) {
                @Suppress("UNCHECKED_CAST")
                return node.elements[idx] as T
            }
            idx -= node.count
            node = node.next
        }
        throw IndexOutOfBoundsException()
    }
    
    /**
     * Verifica se a lista está vazia.
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    fun isEmpty() = size == 0
}
