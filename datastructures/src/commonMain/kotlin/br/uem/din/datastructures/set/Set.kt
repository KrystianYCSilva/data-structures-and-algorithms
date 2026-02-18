package br.uem.din.datastructures.set

/**
 * Uma implementação de conjunto baseada em hash (Hash Set).
 *
 * No Kotlin Multiplatform, `kotlin.collections.HashSet` é um alias para:
 * - `java.util.HashSet` na JVM
 * - Uma implementação JS no JavaScript
 * - Uma implementação nativa nos alvos Native
 *
 * Isso atende ao requisito de utilizar implementações nativas da plataforma quando disponíveis.
 *
 * @param T o tipo dos elementos do conjunto.
 *
 * @see kotlin.collections.HashSet
 */
public typealias HashSetCollection<T> = kotlin.collections.HashSet<T>
