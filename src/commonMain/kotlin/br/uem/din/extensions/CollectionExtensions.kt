package br.uem.din.extensions

import br.uem.din.algorithms.backtracking.permutations
import br.uem.din.algorithms.combinatorics.combinations
import br.uem.din.algorithms.combinatorics.powerSet

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
