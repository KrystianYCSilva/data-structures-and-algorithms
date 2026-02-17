package br.uem.din.datastructures.heap

import br.uem.din.datastructures.queue.Queue

/**
 * Interface somente-leitura para um Heap (Fila de Prioridade baseada em heap).
 *
 * Define operações de consulta: peek, tamanho, pertinência e iteração.
 * Estende [Queue] para compatibilidade com a hierarquia de filas.
 *
 * @param T o tipo dos elementos armazenados.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 6 — Heapsort.
 *
 * @see MutableHeap
 */
public interface Heap<T> : Queue<T>

/**
 * Interface mutável para um Heap (Fila de Prioridade baseada em heap).
 *
 * Estende [Heap] adicionando inserção, remoção e limpeza.
 *
 * @param T o tipo dos elementos armazenados.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 6 — Heapsort.
 *
 * @see Heap
 */
public interface MutableHeap<T> : Heap<T> {
    /**
     * Insere um elemento no heap.
     *
     * @param element o elemento a ser inserido.
     */
    public fun insert(element: T)

    /**
     * Remove e retorna o elemento de maior prioridade.
     *
     * @return o elemento removido, ou `null` se vazio.
     */
    public fun remove(): T?

    /**
     * Remove e retorna o elemento na posição especificada.
     *
     * @param index a posição do elemento.
     * @return o elemento removido, ou `null` se o índice for inválido.
     */
    public fun remove(index: Int): T?

    /**
     * Remove todos os elementos do heap.
     */
    public fun clear()
}
