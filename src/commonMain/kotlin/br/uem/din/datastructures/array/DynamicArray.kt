package br.uem.din.datastructures.array

/**
 * Array dinâmico (Dynamic Array) — implementação manual similar ao ArrayList.
 *
 * Utiliza estratégia de redimensionamento por duplicação (doubling strategy) quando a capacidade
 * é excedida, e redução pela metade quando a ocupação cai para 1/4 da capacidade.
 * Essa abordagem garante custo amortizado O(1) para inserções no final.
 *
 * Complexidades:
 * - [add] (final): O(1) amortizado
 * - [add] (por índice): O(n) — requer deslocamento de elementos
 * - [get]: O(1)
 * - [removeAt]: O(n) — requer deslocamento de elementos
 *
 * Uso de memória: Θ(capacity), onde capacity ≥ size.
 *
 * @param T o tipo dos elementos armazenados.
 * @param initialCapacity a capacidade inicial do array interno (padrão: 10).
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 17 — Amortized Analysis (table doubling).
 */
class DynamicArray<T>(initialCapacity: Int = 10) {
    
    private var elements = arrayOfNulls<Any?>(if (initialCapacity > 0) initialCapacity else 10)
    /** Número de elementos efetivamente armazenados no array. */
    var size = 0
        private set

    /** Capacidade atual do array interno. */
    val capacity: Int
        get() = elements.size

    /**
     * Adiciona um elemento ao final do array.
     *
     * Se o array estiver cheio, a capacidade é dobrada antes da inserção.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param element o elemento a ser adicionado.
     */
    fun add(element: T) {
        if (size == elements.size) {
            resize(elements.size * 2)
        }
        elements[size++] = element
    }

    /**
     * Insere um elemento na posição especificada, deslocando os elementos subsequentes.
     *
     * Complexidade: O(n), onde n é o número de elementos deslocados.
     *
     * @param index a posição de inserção (0-based).
     * @param element o elemento a ser inserido.
     * @throws IndexOutOfBoundsException se o índice for negativo ou maior que [size].
     */
    fun add(index: Int, element: T) {
        if (index < 0 || index > size) throw IndexOutOfBoundsException()
        if (size == elements.size) {
            resize(elements.size * 2)
        }
        // Shift elements right
        for (i in size downTo index + 1) {
            elements[i] = elements[i - 1]
        }
        elements[index] = element
        size++
    }

    /**
     * Retorna o elemento na posição especificada.
     *
     * Complexidade: O(1).
     *
     * @param index a posição desejada (0-based).
     * @return o elemento na posição indicada.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException()
        return elements[index] as T
    }

    /**
     * Remove e retorna o elemento na posição especificada.
     *
     * Desloca os elementos subsequentes para preencher a lacuna.
     * Se a ocupação cair para 1/4 da capacidade, o array é reduzido pela metade.
     *
     * Complexidade: O(n), onde n é o número de elementos deslocados.
     *
     * @param index a posição do elemento a ser removido (0-based).
     * @return o elemento removido.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    @Suppress("UNCHECKED_CAST")
    fun removeAt(index: Int): T {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException()
        val oldValue = elements[index] as T
        
        // Shift elements left
        for (i in index until size - 1) {
            elements[i] = elements[i + 1]
        }
        elements[size - 1] = null // Avoid leak
        size--
        
        // Optional: shrink if too empty
        if (size > 0 && size == elements.size / 4) {
            resize(elements.size / 2)
        }
        return oldValue
    }

    /**
     * Redimensiona o array interno para a nova capacidade, copiando os elementos existentes.
     *
     * Complexidade: O(n), onde n é o número de elementos copiados.
     *
     * @param newCapacity a nova capacidade desejada.
     */
    private fun resize(newCapacity: Int) {
        val newElements = arrayOfNulls<Any?>(newCapacity)
        for (i in 0 until size) {
            newElements[i] = elements[i]
        }
        elements = newElements
    }
    
    /**
     * Retorna representação textual do array no formato `[e1, e2, ..., en]`.
     *
     * @return string formatada com os elementos do array.
     */
    override fun toString(): String {
        val sb = StringBuilder("[")
        for (i in 0 until size) {
            sb.append(elements[i])
            if (i < size - 1) sb.append(", ")
        }
        sb.append("]")
        return sb.toString()
    }
}
