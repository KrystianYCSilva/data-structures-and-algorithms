package br.uem.din.datastructures.queue

/**
 * Interface somente-leitura para uma Fila (Queue — FIFO).
 *
 * Segue o padrão do Kotlin stdlib de separar interfaces imutáveis e mutáveis
 * (ex.: [List]/[MutableList], [Set]/[MutableSet]). Implementa [Iterable] para
 * permitir uso com `for`, `map`, `filter`, etc. A iteração percorre da frente ao final.
 *
 * @param T o tipo dos elementos armazenados na fila.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 *
 * @see MutableQueue
 */
interface Queue<T> : Iterable<T> {
    /**
     * Retorna o elemento na frente da fila sem removê-lo.
     *
     * Complexidade: O(1).
     *
     * @return o primeiro elemento, ou `null` se a fila estiver vazia.
     */
    fun peek(): T?

    /**
     * Retorna o número de elementos na fila.
     *
     * Complexidade: O(1).
     *
     * @return a quantidade de elementos.
     */
    val size: Int

    /**
     * Verifica se a fila está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se não houver elementos, `false` caso contrário.
     */
    fun isEmpty(): Boolean

    /**
     * Verifica se a fila contém o elemento especificado.
     *
     * Complexidade: O(n).
     *
     * @param element o valor a ser procurado.
     * @return `true` se encontrado, `false` caso contrário.
     */
    fun contains(element: T): Boolean
}

/**
 * Interface mutável para uma Fila (Queue — FIFO).
 *
 * Estende [Queue] adicionando operações de modificação ([enqueue], [dequeue], [clear]).
 * Segue o padrão Kotlin de imutável/mutável.
 *
 * @param T o tipo dos elementos armazenados na fila.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10.1 — Stacks and Queues.
 *
 * @see Queue
 * @see ArrayQueue
 * @see LinkedQueue
 * @see CircularQueue
 */
interface MutableQueue<T> : Queue<T> {
    /**
     * Insere um elemento no final da fila.
     *
     * Complexidade: O(1) na maioria das implementações.
     *
     * @param element o elemento a ser inserido.
     */
    fun enqueue(element: T)

    /**
     * Remove e retorna o elemento na frente da fila.
     *
     * Complexidade: O(1) na maioria das implementações.
     *
     * @return o elemento removido, ou `null` se a fila estiver vazia.
     */
    fun dequeue(): T?

    /**
     * Remove todos os elementos da fila.
     *
     * Complexidade: O(1) na maioria das implementações.
     */
    fun clear()
}

/**
 * Retorna uma cópia dos elementos como [List] imutável do Kotlin stdlib.
 * A lista é ordenada da frente ao final da fila.
 *
 * Complexidade: O(n).
 *
 * @return lista imutável dos elementos.
 */
fun <T> Queue<T>.toList(): List<T> = iterator().asSequence().toList()
