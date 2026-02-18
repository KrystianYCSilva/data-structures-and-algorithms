package br.uem.din.datastructures.array


/**
 * Array Dinâmico (redimensionável).
 *
 * Typealias para `ArrayList<T>` do Kotlin stdlib, que já é implementado nativamente:
 * - **JVM**: `java.util.ArrayList` (array com doubling strategy)
 * - **JS**: array dinâmico baseado em JS Array
 * - **Native**: implementação manual do Kotlin
 *
 * Segue a diretriz do projeto de aproveitar implementações nativas de cada plataforma.
 *
 * Complexidades:
 * - Acesso por índice: O(1)
 * - Inserção no final: O(1) amortizado
 * - Inserção no início/meio: O(n)
 * - Remoção: O(n)
 * - Busca: O(n) linear, O(log n) com busca binária em lista ordenada
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 17 — Amortized Analysis.
 *
 * @see kotlin.collections.ArrayList
 */
public typealias DynamicArray<T> = ArrayList<T>
