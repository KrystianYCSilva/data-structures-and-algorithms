@file:JvmName("CollectionsExtensions")

package br.uem.din.datastructures

import kotlin.jvm.JvmName
import br.uem.din.datastructures.linkedlist.LinkedList
import br.uem.din.datastructures.linkedlist.MutableLinkedList
import br.uem.din.datastructures.queue.MutableQueue
import br.uem.din.datastructures.queue.arrayQueueOf
import br.uem.din.datastructures.queue.priorityQueueOf
import br.uem.din.datastructures.stack.MutableStack
import br.uem.din.datastructures.stack.arrayStackOf
import br.uem.din.datastructures.tree.AVLTree
import br.uem.din.datastructures.tree.MutableSearchTree

/**
 * Extensões para conversão de coleções Kotlin padrão em estruturas de dados da biblioteca.
 *
 * Facilita a interoperabilidade e inicialização fluente.
 */

/**
 * Cria uma [MutableStack] contendo os elementos fornecidos.
 *
 * O primeiro argumento ficará na base; o último ficará no topo.
 *
 * @param elements os elementos a serem empilhados.
 * @return uma nova pilha contendo os elementos.
 */
public fun <T> stackOf(vararg elements: T): MutableStack<T> {
    val stack = arrayStackOf<T>()
    for (item in elements) {
        stack.push(item)
    }
    return stack
}

/**
 * Cria uma [MutableQueue] contendo os elementos fornecidos na ordem dada.
 *
 * @param elements os elementos a serem enfileirados.
 * @return uma nova fila contendo os elementos.
 */
public fun <T> queueOf(vararg elements: T): MutableQueue<T> {
    val queue = arrayQueueOf<T>()
    for (item in elements) {
        queue.enqueue(item)
    }
    return queue
}

/**
 * Cria uma [MutableLinkedList] contendo os elementos fornecidos.
 *
 * @param elements os elementos a serem adicionados à lista.
 * @return uma nova lista ligada contendo os elementos.
 */
public fun <T> linkedListOf(vararg elements: T): MutableLinkedList<T> {
    val list = LinkedList<T>()
    for (item in elements) {
        list.addLast(item)
    }
    return list
}

/**
 * Constrói uma [MutableStack] usando um bloco construtor DSL.
 *
 * Exemplo:
 * ```kotlin
 * val stack = buildStack<Int> {
 *     push(1)
 *     push(2)
 *     push(3)
 * }
 * ```
 *
 * @param block bloco de construção que recebe a pilha mutável.
 * @return a pilha construída.
 */
public inline fun <T> buildStack(block: MutableStack<T>.() -> Unit): MutableStack<T> {
    val stack = arrayStackOf<T>()
    stack.block()
    return stack
}

/**
 * Constrói uma [MutableQueue] usando um bloco construtor DSL.
 *
 * Exemplo:
 * ```kotlin
 * val queue = buildQueue<Int> {
 *     enqueue(1)
 *     enqueue(2)
 * }
 * ```
 *
 * @param block bloco de construção que recebe a fila mutável.
 * @return a fila construída.
 */
public inline fun <T> buildQueue(block: MutableQueue<T>.() -> Unit): MutableQueue<T> {
    val queue = arrayQueueOf<T>()
    queue.block()
    return queue
}

/**
 * Constrói uma [MutableLinkedList] usando um bloco construtor DSL.
 *
 * @param block bloco de construção que recebe a lista ligada mutável.
 * @return a lista ligada construída.
 */
public inline fun <T> buildLinkedList(block: MutableLinkedList<T>.() -> Unit): MutableLinkedList<T> {
    val list = LinkedList<T>()
    list.block()
    return list
}

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

/**
 * Converte qualquer [Iterable] de elementos [Comparable] para uma [MutableSearchTree] (AVL).
 *
 * Utiliza [AVLTree] como implementação padrão (árvore auto-balanceada).
 *
 * @return uma nova árvore de busca contendo os elementos.
 */
public fun <T : Comparable<T>> Iterable<T>.toSearchTree(): MutableSearchTree<T> {
    val tree = AVLTree<T>()
    for (item in this) {
        tree.insert(item)
    }
    return tree
}

/**
 * Retorna uma nova pilha contendo todos os elementos desta pilha mais o elemento adicionado no topo.
 *
 * @param element o elemento a ser adicionado.
 * @return nova pilha com o elemento adicionado.
 */
public operator fun <T> MutableStack<T>.plus(element: T): MutableStack<T> {
    val copy = arrayStackOf<T>()
    for (item in this) {
        copy.push(item)
    }
    copy.push(element)
    return copy
}

/**
 * Retorna uma nova fila contendo todos os elementos desta fila mais o elemento adicionado ao final.
 *
 * @param element o elemento a ser adicionado.
 * @return nova fila com o elemento adicionado.
 */
public operator fun <T> MutableQueue<T>.plus(element: T): MutableQueue<T> {
    val copy = arrayQueueOf<T>()
    for (item in this) {
        copy.enqueue(item)
    }
    copy.enqueue(element)
    return copy
}
