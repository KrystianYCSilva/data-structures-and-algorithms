    package br.uem.din.datastructures

import br.uem.din.datastructures.queue.MutableQueue
import br.uem.din.datastructures.queue.arrayQueueOf
import br.uem.din.datastructures.queue.priorityQueueOf
import br.uem.din.datastructures.stack.MutableStack
import br.uem.din.datastructures.stack.arrayStackOf
import br.uem.din.datastructures.linkedlist.MutableLinkedList
import br.uem.din.datastructures.linkedlist.LinkedList

/**
 * Extensões para conversão de coleções Kotlin padrão em estruturas de dados da biblioteca.
 *
 * Facilita a interoperabilidade e inicialização fluente.
 */

/**
 * Converte qualquer [Iterable] para uma [MutableStack].
 *
 * A ordem de iteração é preservada (o último elemento iterado será o topo da pilha).
 *
 * @return uma nova pilha contendo os elementos do iterável.
 */
public fun <T> Iterable<T>.toStack(): MutableStack<T> {
    val stack = arrayStackOf<T>()
    for (item in this) {
        stack.push(item)
    }
    return stack
}

/**
 * Converte qualquer [Iterable] para uma [MutableQueue].
 *
 * @return uma nova fila contendo os elementos na ordem de iteração.
 */
public fun <T> Iterable<T>.toQueue(): MutableQueue<T> {
    val queue = arrayQueueOf<T>()
    for (item in this) {
        queue.enqueue(item)
    }
    return queue
}

/**
 * Converte qualquer [Iterable] para uma [MutableLinkedList].
 *
 * @return uma nova lista ligada contendo os elementos.
 */
public fun <T> Iterable<T>.toLinkedList(): MutableLinkedList<T> {
    val list = LinkedList<T>()
    for (item in this) {
        list.addLast(item)
    }
    return list
}

/**
 * Converte qualquer [Iterable] para uma Fila de Prioridade (Min-Heap).
 *
 * @param comparator comparador opcional. Se nulo, assume-se ordem natural.
 * @return uma nova [MutableQueue] implementada como PriorityQueue.
 */
public fun <T> Iterable<T>.toPriorityQueue(comparator: Comparator<T>? = null): MutableQueue<T> {
    val pq = priorityQueueOf(comparator)
    for (item in this) {
        pq.enqueue(item)
    }
    return pq
}
