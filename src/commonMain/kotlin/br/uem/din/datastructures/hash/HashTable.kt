package br.uem.din.datastructures.hash

/**
 * Implementação de Tabela Hash (Hash Table).
 *
 * No Kotlin Multiplatform, `kotlin.collections.HashMap` é um alias para:
 * - `java.util.HashMap` na JVM
 * - Uma implementação JS no JavaScript
 * - Uma implementação nativa nos alvos Native
 *
 * Isso atende ao requisito de utilizar implementações nativas onde disponíveis.
 */
public typealias HashTable<K, V> = kotlin.collections.HashMap<K, V>
