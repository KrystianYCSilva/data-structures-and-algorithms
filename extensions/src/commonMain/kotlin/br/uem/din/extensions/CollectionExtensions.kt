package br.uem.din.extensions

import br.uem.din.algorithms.backtracking.permutations
import br.uem.din.algorithms.combinatorics.combinations
import br.uem.din.algorithms.combinatorics.powerSet
import br.uem.din.algorithms.dp.knapsack01
import br.uem.din.algorithms.dp.longestIncreasingSubsequence
import br.uem.din.algorithms.dp.coinChange
import br.uem.din.algorithms.searching.binarySearch
import br.uem.din.algorithms.searching.interpolationSearch
import br.uem.din.algorithms.searching.ternarySearch
import br.uem.din.algorithms.sorting.bubbleSort
import br.uem.din.algorithms.sorting.mergeSort
import br.uem.din.algorithms.sorting.quickSort

/**
 * Extensões para Coleções integrando algoritmos combinatórios.
 */

/**
 * Retorna todas as permutações dos elementos desta coleção.
 * Atenção: Complexidade O(n!), usar apenas para listas pequenas.
 */
public fun <T> Iterable<T>.permutations(): List<List<T>> {
    val list = this.toList()
    // Mapeamento para usar a implementação baseada em índices se necessário,
    // ou implementar genérico aqui. Como a implementação atual de 'permutations'
    // aceita IntArray, vamos adaptar ou reimplementar a genérica aqui para facilitar.
    
    val result = mutableListOf<List<T>>()
    permuteGeneric(list.toMutableList(), 0, result)
    return result
}

private fun <T> permuteGeneric(list: MutableList<T>, start: Int, result: MutableList<List<T>>) {
    if (start == list.size) {
        result.add(list.toList())
        return
    }
    for (i in start until list.size) {
        val temp = list[start]
        list[start] = list[i]
        list[i] = temp
        
        permuteGeneric(list, start + 1, result)
        
        // Backtrack
        list[i] = list[start]
        list[start] = temp
    }
}

/**
 * Retorna todas as combinações de tamanho k.
 */
public fun <T> Iterable<T>.combinations(k: Int): List<List<T>> {
    return combinations(this.toList(), k)
}

/**
 * Retorna o conjunto das partes (Power Set).
 */
public fun <T> Iterable<T>.powerSet(): List<List<T>> {
    return powerSet(this.toList())
}

/**
 * Ordena esta lista mutável in-place usando Bubble Sort.
 *
 * Complexidade Temporal (Pior Caso): O(n^2)
 */
public fun <T : Comparable<T>> MutableList<T>.bubbleSort() {
    br.uem.din.algorithms.sorting.bubbleSort(this)
}

/**
 * Ordena esta lista mutável in-place usando Merge Sort.
 *
 * Complexidade Temporal: O(n log n)
 */
public fun <T : Comparable<T>> MutableList<T>.mergeSort() {
    br.uem.din.algorithms.sorting.mergeSort(this)
}

/**
 * Ordena esta lista mutável in-place usando Quick Sort.
 *
 * Complexidade Temporal (Média): O(n log n)
 */
public fun <T : Comparable<T>> MutableList<T>.quickSort() {
    br.uem.din.algorithms.sorting.quickSort(this)
}

/**
 * Realiza uma busca binária na lista (que deve estar ordenada).
 *
 * @param element o elemento a ser buscado.
 * @return o índice do elemento, ou -1 se não encontrado.
 *
 * Complexidade Temporal: O(log n)
 */
public fun <T : Comparable<T>> List<T>.binarySearch(element: T): Int {
    return br.uem.din.algorithms.searching.binarySearch(this, element)
}

/**
 * Realiza uma busca ternária em uma lista ordenada.
 *
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(log_3 n)
 */
public infix fun <T : Comparable<T>> List<T>.ternarySearch(target: T): Int {
    return ternarySearch(this, target)
}

/**
 * Realiza uma busca por interpolação em uma lista de inteiros ordenada.
 *
 * @param target o elemento a ser buscado.
 * @return o índice do elemento se encontrado, ou -1 caso contrário.
 *
 * Complexidade Temporal: O(log log n) média, O(n) pior caso.
 */
public infix fun List<Int>.interpolationSearch(target: Int): Int {
    return interpolationSearch(this, target)
}

/**
 * Resolve o problema da Mochila 0/1 (Knapsack 0/1) para uma lista de itens.
 *
 * @param capacity a capacidade máxima da mochila.
 * @param weightSelector função que extrai o peso de um item.
 * @param valueSelector função que extrai o valor de um item.
 * @return o valor máximo que pode ser obtido.
 *
 * Complexidade Temporal: O(n * capacity)
 */
public fun <T> List<T>.knapsack(capacity: Int, weightSelector: (T) -> Int, valueSelector: (T) -> Int): Int {
    val weights = this.map(weightSelector).toIntArray()
    val values = this.map(valueSelector).toIntArray()
    return knapsack01(weights, values, capacity)
}

/**
 * Calcula o comprimento da Maior Subsequência Crescente (LIS) deste array.
 *
 * Complexidade Temporal: O(n^2)
 */
public fun IntArray.longestIncreasingSubsequenceLength(): Int {
    return longestIncreasingSubsequence(this)
}

/**
 * Resolve o problema do Troco (Coin Change) para o valor especificado.
 *
 * Assume que este array contém os valores das moedas disponíveis.
 *
 * @param amount valor total a ser trocado.
 * @return o número mínimo de moedas necessárias, ou -1 se não for possível formar o valor.
 *
 * Complexidade Temporal: O(amount * n)
 */
public infix fun IntArray.minCoinsFor(amount: Int): Int {
    return coinChange(this, amount)
}

