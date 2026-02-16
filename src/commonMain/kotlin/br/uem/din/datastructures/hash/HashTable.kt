package br.uem.din.datastructures.hash

/**
 * A Hash Table implementation.
 *
 * In Kotlin Multiplatform, `kotlin.collections.HashMap` is an alias for:
 * - `java.util.HashMap` on JVM
 * - A JS implementation on JavaScript
 * - A Native implementation on Native targets
 *
 * This meets the requirement of using native implementations where available.
 */
typealias HashTable<K, V> = kotlin.collections.HashMap<K, V>
