package br.uem.din.datastructures.linkedlist

/**
 * Nó de uma lista ligada simplesmente encadeada (singly linked list node).
 *
 * Cada nó armazena um valor genérico e uma referência ao próximo nó da sequência.
 * Estrutura fundamental para a construção de listas ligadas, pilhas e filas encadeadas.
 *
 * Complexidade de acesso ao próximo nó: O(1).
 *
 * @param T o tipo do valor armazenado no nó.
 * @property value o dado armazenado neste nó.
 * @property next referência ao próximo nó na lista, ou `null` se este for o último.
 *
 * @see LinkedList
 * @see CircularLinkedList
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 10 — Elementary Data Structures.
 */
data class Node<T>(val value: T, var next: Node<T>? = null) {
    /**
     * Retorna uma representação textual do nó e de todos os nós subsequentes,
     * no formato `valor -> valor -> ... -> últimoValor`.
     *
     * Complexidade: O(n), onde n é o número de nós a partir deste.
     *
     * @return representação em cadeia (chain) dos valores dos nós.
     */
    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }
}
