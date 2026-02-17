package br.uem.din.algorithms.combinatorics

/**
 * Gera todas as combinações de k elementos de uma lista.
 *
 * @param list lista de entrada.
 * @param k tamanho das combinações.
 * @return lista de combinações (listas de tamanho k).
 *
 * Complexidade: O(C(n, k))
 */
public fun <T> combinations(list: List<T>, k: Int): List<List<T>> {
    if (k == 0) return listOf(emptyList())
    if (list.isEmpty()) return emptyList()
    
    val head = list.first()
    val tail = list.subList(1, list.size)
    
    // Combinações que incluem head + combinações de (k-1) elementos do tail
    val withHead = combinations(tail, k - 1).map { listOf(head) + it }
    
    // Combinações que NÃO incluem head (apenas do tail)
    val withoutHead = combinations(tail, k)
    
    return withHead + withoutHead
}

/**
 * Gera o conjunto das partes (Power Set).
 *
 * @param list lista de entrada.
 * @return lista contendo todos os subconjuntos possíveis.
 *
 * Complexidade: O(2^n)
 */
public fun <T> powerSet(list: List<T>): List<List<T>> {
    if (list.isEmpty()) return listOf(emptyList())
    
    val head = list.first()
    val tail = list.subList(1, list.size)
    
    val subPowerSet = powerSet(tail)
    
    // Para cada subconjunto do tail, cria uma versão com head e outra sem head
    val withHead = subPowerSet.map { listOf(head) + it }
    
    return subPowerSet + withHead
}

/**
 * Produto Cartesiano de duas listas.
 *
 * @param list1 primeira lista.
 * @param list2 segunda lista.
 * @return lista de pares.
 */
public fun <T, U> cartesianProduct(list1: List<T>, list2: List<U>): List<Pair<T, U>> {
    return list1.flatMap { e1 -> list2.map { e2 -> e1 to e2 } }
}
