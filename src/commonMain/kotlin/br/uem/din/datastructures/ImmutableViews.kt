@file:JvmName("ImmutableViews")

package br.uem.din.datastructures

import kotlin.jvm.JvmName
import br.uem.din.datastructures.bitset.ImmutableBitSet
import br.uem.din.datastructures.bitset.MutableBitSet
import br.uem.din.datastructures.graph.Edge
import br.uem.din.datastructures.graph.Graph
import br.uem.din.datastructures.graph.MutableGraph
import br.uem.din.datastructures.graph.Vertex
import br.uem.din.datastructures.hash.MutableOpenHashTable
import br.uem.din.datastructures.hash.OpenHashTable
import br.uem.din.datastructures.linkedlist.MutableLinkedList
import br.uem.din.datastructures.linkedlist.ImmutableLinkedList
import br.uem.din.datastructures.probabilistic.MutableBloomFilter
import br.uem.din.datastructures.probabilistic.ImmutableBloomFilter
import br.uem.din.datastructures.queue.MutableQueue
import br.uem.din.datastructures.queue.Queue
import br.uem.din.datastructures.set.MutableMultiset
import br.uem.din.datastructures.set.ImmutableMultiset
import br.uem.din.datastructures.skiplist.MutableSkipList
import br.uem.din.datastructures.skiplist.ImmutableSkipList
import br.uem.din.datastructures.stack.MutableStack
import br.uem.din.datastructures.stack.Stack
import br.uem.din.datastructures.tree.MutableRadixTree
import br.uem.din.datastructures.tree.MutableSearchTree
import br.uem.din.datastructures.tree.MutableTrie
import br.uem.din.datastructures.tree.ImmutableRadixTree
import br.uem.din.datastructures.tree.ImmutableTrie
import br.uem.din.datastructures.tree.SearchTree
import br.uem.din.datastructures.spatial.ImmutableKDTree
import br.uem.din.datastructures.spatial.ImmutableQuadTree
import br.uem.din.datastructures.spatial.MutableKDTree
import br.uem.din.datastructures.spatial.MutableQuadTree
import br.uem.din.datastructures.spatial.QuadTree
import br.uem.din.datastructures.unionfind.MutableUnionFind
import br.uem.din.datastructures.unionfind.ImmutableUnionFind
import kotlin.jvm.JvmInline

/**
 * Funções auxiliares para criar vistas somente-leitura (read-only views) de estruturas mutáveis.
 *
 * Utiliza `value class` (Inline Classes) para garantir abstração de custo zero (Zero-Overhead)
 * na JVM. A vista é "live": alterações na estrutura original são refletidas imediatamente.
 */

/**
 * Cria uma vista somente-leitura desta pilha mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableStack<T>.asReadOnly(): Stack<T> = ReadOnlyStack(this)

@JvmInline
private value class ReadOnlyStack<T>(private val delegate: MutableStack<T>) : Stack<T> {
    override fun peek(): T? = delegate.peek()
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta fila mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableQueue<T>.asReadOnly(): Queue<T> = ReadOnlyQueue(this)

@JvmInline
private value class ReadOnlyQueue<T>(private val delegate: MutableQueue<T>) : Queue<T> {
    override fun peek(): T? = delegate.peek()
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta lista ligada mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableLinkedList<T>.asReadOnly(): ImmutableLinkedList<T> = ImmutableLinkedListView(this)

@JvmInline
private value class ImmutableLinkedListView<T>(private val delegate: MutableLinkedList<T>) : ImmutableLinkedList<T> {
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun indexOf(element: T): Int = delegate.indexOf(element)
    override fun toList(): List<T> = delegate.toList()
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta árvore de busca mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T : Comparable<T>> MutableSearchTree<T>.asReadOnly(): SearchTree<T> = ReadOnlySearchTree(this)

@JvmInline
private value class ReadOnlySearchTree<T : Comparable<T>>(private val delegate: MutableSearchTree<T>) : SearchTree<T> {
    override fun contains(element: T): Boolean = delegate.contains(element)
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun inOrder(): List<T> = delegate.inOrder()
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura deste grafo mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableGraph<T>.asReadOnly(): Graph<T> = ReadOnlyGraphView(this)

@JvmInline
private value class ReadOnlyGraphView<T>(private val delegate: MutableGraph<T>) : Graph<T> {
    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> = delegate.edges(source)
    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? = delegate.weight(source, destination)
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta tabela hash mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <K : Any, V> MutableOpenHashTable<K, V>.asReadOnly(): OpenHashTable<K, V> = ReadOnlyHashTableView(this)

@JvmInline
private value class ReadOnlyHashTableView<K : Any, V>(private val delegate: MutableOpenHashTable<K, V>) : OpenHashTable<K, V> {
    override val size: Int get() = delegate.size
    override fun get(key: K): V? = delegate.get(key)
    override fun contains(key: K): Boolean = delegate.contains(key)
    override fun entries(): List<Pair<K, V>> = delegate.entries()
    override fun iterator(): Iterator<Pair<K, V>> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura deste multiset mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T> MutableMultiset<T>.asReadOnly(): ImmutableMultiset<T> = ImmutableMultisetView(this)

@JvmInline
private value class ImmutableMultisetView<T>(private val delegate: MutableMultiset<T>) : ImmutableMultiset<T> {
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun count(element: T): Int = delegate.count(element)
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun distinctElements(): Set<T> = delegate.distinctElements()
    override val distinctCount: Int get() = delegate.distinctCount
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta skip list mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <T : Comparable<T>> MutableSkipList<T>.asReadOnly(): ImmutableSkipList<T> = ImmutableSkipListView(this)

@JvmInline
private value class ImmutableSkipListView<T : Comparable<T>>(private val delegate: MutableSkipList<T>) : ImmutableSkipList<T> {
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun toList(): List<T> = delegate.toList()
    override fun iterator(): Iterator<T> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta trie mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun <Key> MutableTrie<Key>.asReadOnly(): ImmutableTrie<Key> = ImmutableTrieView(this)

@JvmInline
private value class ImmutableTrieView<Key>(private val delegate: MutableTrie<Key>) : ImmutableTrie<Key> {
    override fun contains(list: List<Key>): Boolean = delegate.contains(list)
    override fun collections(prefix: List<Key>): List<List<Key>> = delegate.collections(prefix)
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta árvore radix mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun MutableRadixTree.asReadOnly(): ImmutableRadixTree = ImmutableRadixTreeView(this)

@JvmInline
private value class ImmutableRadixTreeView(private val delegate: MutableRadixTree) : ImmutableRadixTree {
    override val size: Int get() = delegate.size
    override fun search(key: String): Boolean = delegate.search(key)
    override fun prefixSearch(prefix: String): List<String> = delegate.prefixSearch(prefix)
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura deste union-find mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun MutableUnionFind.asReadOnly(): ImmutableUnionFind = ImmutableUnionFindView(this)

@JvmInline
private value class ImmutableUnionFindView(private val delegate: MutableUnionFind) : ImmutableUnionFind {
    override val size: Int get() = delegate.size
    override val numberOfSets: Int get() = delegate.numberOfSets
    override fun find(i: Int): Int = delegate.find(i)
    override fun connected(i: Int, j: Int): Boolean = delegate.connected(i, j)
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura deste filtro de Bloom mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun MutableBloomFilter.asReadOnly(): ImmutableBloomFilter = ImmutableBloomFilterView(this)

@JvmInline
private value class ImmutableBloomFilterView(private val delegate: MutableBloomFilter) : ImmutableBloomFilter {
    override fun contains(element: String): Boolean = delegate.contains(element)
    override fun size(): Int = delegate.size()
    override fun countHashFunctions(): Int = delegate.countHashFunctions()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta KDTree mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun MutableKDTree.asReadOnly(): ImmutableKDTree = ImmutableKDTreeView(this)

@JvmInline
private value class ImmutableKDTreeView(private val delegate: MutableKDTree) : ImmutableKDTree {
    override val size: Int get() = delegate.size
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun contains(point: DoubleArray): Boolean = delegate.contains(point)
    override fun nearestNeighbor(target: DoubleArray): DoubleArray? = delegate.nearestNeighbor(target)
    override fun rangeSearch(lowerBound: DoubleArray, upperBound: DoubleArray): List<DoubleArray> =
        delegate.rangeSearch(lowerBound, upperBound)
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura desta QuadTree mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun MutableQuadTree.asReadOnly(): ImmutableQuadTree = ImmutableQuadTreeView(this)

@JvmInline
private value class ImmutableQuadTreeView(private val delegate: MutableQuadTree) : ImmutableQuadTree {
    override val size: Int get() = delegate.size
    override fun contains(point: QuadTree.Point): Boolean = delegate.contains(point)
    override fun query(range: QuadTree.Rectangle): List<QuadTree.Point> = delegate.query(range)
    override fun allPoints(): List<QuadTree.Point> = delegate.allPoints()
    override fun toString(): String = delegate.toString()
}

/**
 * Cria uma vista somente-leitura deste BitSet mutável.
 * Complexidade: O(1) e Zero Alocação.
 */
public fun MutableBitSet.asReadOnly(): ImmutableBitSet = ImmutableBitSetView(this)

@JvmInline
private value class ImmutableBitSetView(private val delegate: MutableBitSet) : ImmutableBitSet {
    override fun get(index: Int): Boolean = delegate[index]
    override fun size(): Int = delegate.size()
    override fun length(): Int = delegate.length()
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun cardinality(): Int = delegate.cardinality()
    override fun nextSetBit(fromIndex: Int): Int = delegate.nextSetBit(fromIndex)
    override fun iterator(): Iterator<Int> = delegate.iterator()
    override fun toString(): String = delegate.toString()
}
