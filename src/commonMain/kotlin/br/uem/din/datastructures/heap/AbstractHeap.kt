package br.uem.din.datastructures.heap

import br.uem.din.datastructures.queue.MutableQueue

/**
 * Heap abstrato (Abstract Heap) — classe base para implementações de heap binário.
 *
 * Define a interface comum e os cálculos de índices para navegação na árvore
 * heap representada implicitamente em um array:
 * - Filho esquerdo de `i`: `2i + 1`
 * - Filho direito de `i`: `2i + 2`
 * - Pai de `i`: `(i - 1) / 2`
 *
 * Implementa [MutableQueue] delegando [enqueue] para [insert] e [dequeue] para [remove],
 * permitindo que heaps sejam usados como filas de prioridade.
 *
 * Complexidades (para heap binário):
 * - [insert] / [enqueue]: O(log n)
 * - [remove] / [dequeue]: O(log n)
 * - [peek]: O(1)
 *
 * @param T o tipo dos elementos armazenados no heap.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 6 — Heapsort;
 *             Williams, J. W. J. "Algorithm 232 — Heapsort" (1964).
 */
internal abstract class AbstractHeap<T> : MutableQueue<T> {

    public abstract override val size: Int
    public abstract override fun peek(): T?

    /**
     * Insere um elemento no heap, restaurando a propriedade heap via sift-up.
     *
     * Complexidade: O(log n).
     *
     * @param element o elemento a ser inserido.
     */
    public abstract fun insert(element: T)

    /**
     * Insere um elemento no heap (delega para [insert]).
     *
     * Complexidade: O(log n).
     *
     * @param element o elemento a ser inserido.
     */
    public override fun enqueue(element: T) {
        insert(element)
    }

    /**
     * Remove e retorna o elemento de maior prioridade (delega para [remove]).
     *
     * Complexidade: O(log n).
     *
     * @return o elemento removido, ou `null` se o heap estiver vazio.
     */
    public override fun dequeue(): T? {
        return remove()
    }

    /** Calcula o índice do filho esquerdo de um nó na representação em array. */
    protected fun leftChildIndex(index: Int): Int = (2 * index) + 1
    /** Calcula o índice do filho direito de um nó na representação em array. */
    protected fun rightChildIndex(index: Int): Int = (2 * index) + 2
    /** Calcula o índice do nó pai na representação em array. */
    protected fun parentIndex(index: Int): Int = (index - 1) / 2

    /**
     * Remove e retorna o elemento raiz (de maior prioridade) do heap.
     *
     * Complexidade: O(log n).
     *
     * @return o elemento removido, ou `null` se vazio.
     */
    public abstract fun remove(): T?

    /**
     * Remove e retorna o elemento na posição especificada do heap.
     *
     * Complexidade: O(log n).
     *
     * @param index a posição do elemento a ser removido.
     * @return o elemento removido, ou `null` se o índice for inválido.
     */
    public abstract fun remove(index: Int): T?

    /**
     * Move um elemento para baixo no heap até restaurar a propriedade heap (heapify-down).
     *
     * Complexidade: O(log n).
     *
     * @param index a posição do elemento a ser movido.
     */
    protected abstract fun siftDown(index: Int)

    /**
     * Move um elemento para cima no heap até restaurar a propriedade heap (heapify-up).
     *
     * Complexidade: O(log n).
     *
     * @param index a posição do elemento a ser movido.
     */
    protected abstract fun siftUp(index: Int)

    /**
     * Verifica se o heap contém o elemento especificado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return `true` se encontrado, `false` caso contrário.
     */
    public abstract override fun contains(element: T): Boolean

    /**
     * Remove todos os elementos do heap.
     *
     * Complexidade: O(1).
     */
    public abstract override fun clear()

    /**
     * Retorna um [Iterator] sobre os elementos do heap.
     *
     * A ordem de iteração não é garantida como sendo a ordem de prioridade.
     *
     * @return iterador sobre os elementos.
     */
    public abstract override fun iterator(): Iterator<T>
}
