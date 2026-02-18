package br.uem.din.datastructures.tree

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.*

class TreeCoreHardeningTest {

    @Test
    fun testSearchTreesEmptyAndSingleElementBoundaries() {
        for (impl in searchTreeImplementations()) {
            val tree = impl.factory()

            assertTrue(tree.isEmpty(), "empty state failed for ${impl.name}")
            assertEquals(0, tree.size, "empty size failed for ${impl.name}")
            assertFalse(tree.contains(1), "empty contains failed for ${impl.name}")
            assertEquals(emptyList(), tree.inOrder(), "empty inOrder failed for ${impl.name}")

            assertTrue(tree.insert(42), "single insert failed for ${impl.name}")
            assertFalse(tree.insert(42), "duplicate insert should be rejected for ${impl.name}")
            assertEquals(1, tree.size, "single insert size failed for ${impl.name}")
            assertTrue(tree.contains(42), "single contains failed for ${impl.name}")
            assertEquals(listOf(42), tree.inOrder(), "single inOrder failed for ${impl.name}")

            assertFalse(tree.remove(7), "remove missing should be false for ${impl.name}")
            assertTrue(tree.remove(42), "remove existing failed for ${impl.name}")
            assertFalse(tree.remove(42), "remove duplicate should be false for ${impl.name}")
            assertTrue(tree.isEmpty(), "tree should be empty at end for ${impl.name}")
        }
    }

    @Test
    fun testSearchTreesLargeSequentialAndReverseRemoval() {
        val n = 600

        for (impl in searchTreeImplementations()) {
            val tree = impl.factory()
            for (value in 1..n) {
                assertTrue(tree.insert(value), "insert($value) failed for ${impl.name}")
            }

            assertEquals(n, tree.size, "size after large insert failed for ${impl.name}")
            assertEquals((1..n).toList(), tree.inOrder(), "inOrder after large insert failed for ${impl.name}")

            for (value in n downTo 1) {
                assertTrue(tree.remove(value), "remove($value) failed for ${impl.name}")
            }

            assertTrue(tree.isEmpty(), "tree should be empty after reverse removals for ${impl.name}")
            assertEquals(emptyList(), tree.inOrder(), "inOrder should be empty after removals for ${impl.name}")
        }
    }

    @Test
    fun testSearchTreesRandomizedOperationsAgainstSetOracle() {
        for (impl in searchTreeImplementations()) {
            repeat(6) { seed ->
                val tree = impl.factory()
                val model = mutableSetOf<Int>()
                val random = Random(seed)

                repeat(800) { step ->
                    when (random.nextInt(100)) {
                        in 0..54 -> {
                            val value = random.nextInt(0, 240)
                            assertEquals(
                                model.add(value),
                                tree.insert(value),
                                "insert mismatch (${impl.name}) seed=$seed step=$step value=$value"
                            )
                        }

                        in 55..79 -> {
                            val value = random.nextInt(0, 240)
                            assertEquals(
                                model.remove(value),
                                tree.remove(value),
                                "remove mismatch (${impl.name}) seed=$seed step=$step value=$value"
                            )
                        }

                        else -> {
                            val value = random.nextInt(0, 240)
                            assertEquals(
                                model.contains(value),
                                tree.contains(value),
                                "contains mismatch (${impl.name}) seed=$seed step=$step value=$value"
                            )
                        }
                    }

                    assertEquals(model.size, tree.size, "size mismatch (${impl.name}) seed=$seed step=$step")
                    assertEquals(model.isEmpty(), tree.isEmpty(), "isEmpty mismatch (${impl.name}) seed=$seed step=$step")

                    if (step % 20 == 0) {
                        val expected = model.toList().sorted()
                        assertEquals(expected, tree.inOrder(), "inOrder mismatch (${impl.name}) seed=$seed step=$step")
                        assertEquals(expected, tree.iterator().asSequence().toList(), "iterator mismatch (${impl.name}) seed=$seed step=$step")
                    }
                }
            }
        }
    }

    @Test
    fun testSearchTreesReadOnlyViewSnapshotAndLiveSemantics() {
        for (impl in searchTreeImplementations()) {
            val mutableTree = impl.factory()
            mutableTree.insert(10)
            mutableTree.insert(5)
            mutableTree.insert(20)

            val readOnlyView: SearchTree<Int> = mutableTree.asReadOnly()
            val snapshot = readOnlyView.inOrder()

            mutableTree.insert(7)
            mutableTree.remove(20)

            assertEquals(listOf(5, 10, 20), snapshot, "snapshot stability failed for ${impl.name}")
            assertEquals(listOf(5, 7, 10), readOnlyView.inOrder(), "live read-only view failed for ${impl.name}")
            assertEquals(3, readOnlyView.size, "live size failed for ${impl.name}")
            assertTrue(readOnlyView.contains(7), "live contains failed for ${impl.name}")
        }
    }

    @Test
    fun testSearchTreeIteratorTerminatesAndThrowsAfterExhaustion() {
        for (impl in searchTreeImplementations()) {
            val tree = impl.factory()
            tree.insert(2)
            tree.insert(1)
            tree.insert(3)

            val iterator = tree.iterator()
            val values = mutableListOf<Int>()
            while (iterator.hasNext()) {
                values.add(iterator.next())
            }

            assertEquals(listOf(1, 2, 3), values, "iterator values mismatch for ${impl.name}")
            assertFalse(iterator.hasNext(), "iterator termination mismatch for ${impl.name}")
            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
    }

    @Test
    fun testRedBlackComparatorCallsStayWithinNLogNEnvelope() {
        val n = 1024
        val counter = CompareCounter()
        val tree = redBlackTreeOf<CountedKey>()

        for (value in n downTo 1) {
            assertTrue(tree.insert(CountedKey(value, counter)))
        }

        for (value in 1..n) {
            assertTrue(tree.contains(CountedKey(value, counter)))
        }

        for (value in 1..n) {
            assertTrue(tree.remove(CountedKey(value, counter)))
        }

        assertTrue(tree.isEmpty())

        val logN = ceilLog2(n + 1).toLong()
        val upperBound = (90L * n.toLong() * logN) + (400L * n.toLong())
        assertTrue(
            counter.comparisons <= upperBound,
            "red-black compare calls should stay near O(n log n). comparisons=${counter.comparisons}, upperBound=$upperBound"
        )
    }

    @Test
    fun testTrieEmptySingleAndLargeBoundary() {
        val trie = Trie<Char>()

        assertFalse(trie.contains(listOf('a')))
        assertEquals(emptySet<List<Char>>(), trie.collections(emptyList()).toSet())

        trie.insert(listOf('c', 'a', 't'))
        assertTrue(trie.contains(listOf('c', 'a', 't')))
        assertFalse(trie.contains(listOf('c', 'a')))

        trie.remove(listOf('c', 'a'))
        assertTrue(trie.contains(listOf('c', 'a', 't')))

        for (i in 0 until 700) {
            trie.insert("word$i".toList())
        }

        val all = trie.collections(emptyList()).toSet()
        assertTrue(all.contains(listOf('c', 'a', 't')))
        assertTrue(all.contains("word699".toList()))
        assertEquals(701, all.size)
    }

    @Test
    fun testTrieRandomizedOperationsAgainstSetOracle() {
        repeat(6) { seed ->
            val trie = Trie<Char>()
            val model = mutableSetOf<List<Char>>()
            val random = Random(seed)

            repeat(800) { step ->
                when (random.nextInt(100)) {
                    in 0..54 -> {
                        val word = randomWord(random)
                        trie.insert(word)
                        model.add(word)
                    }

                    in 55..74 -> {
                        val word = randomWord(random)
                        trie.remove(word)
                        model.remove(word)
                    }

                    in 75..89 -> {
                        val word = randomWord(random)
                        assertEquals(model.contains(word), trie.contains(word), "contains mismatch seed=$seed step=$step")
                    }

                    else -> {
                        val prefix = randomPrefix(random)
                        val expected = model.filter { it.startsWith(prefix) }.toSet()
                        val actual = trie.collections(prefix).toSet()
                        assertEquals(expected, actual, "collections mismatch seed=$seed step=$step prefix=$prefix")
                    }
                }

                if (step % 20 == 0) {
                    assertEquals(model, trie.collections(emptyList()).toSet(), "full collection mismatch seed=$seed step=$step")
                }
            }
        }
    }

    @Test
    fun testTrieReadOnlyViewSnapshotAndLiveSemantics() {
        val mutableTrie = Trie<Char>()
        mutableTrie.insert(listOf('c', 'a', 't'))
        mutableTrie.insert(listOf('c', 'a', 'r'))

        val readOnlyView: ImmutableTrie<Char> = mutableTrie.asReadOnly()
        val snapshot = readOnlyView.collections(emptyList()).toSet()

        mutableTrie.remove(listOf('c', 'a', 't'))
        mutableTrie.insert(listOf('d', 'o', 'g'))

        assertEquals(setOf(listOf('c', 'a', 't'), listOf('c', 'a', 'r')), snapshot)
        assertEquals(setOf(listOf('c', 'a', 'r'), listOf('d', 'o', 'g')), readOnlyView.collections(emptyList()).toSet())
    }

    @Test
    fun testTrieCollectionsPrefixClosure() {
        val trie = Trie<Char>()
        val words = listOf(
            listOf('d', 'o'),
            listOf('d', 'o', 'g'),
            listOf('d', 'o', 't'),
            listOf('d', 'a', 't', 'a')
        )
        words.forEach { trie.insert(it) }

        assertEquals(setOf(listOf('d', 'o'), listOf('d', 'o', 'g'), listOf('d', 'o', 't')), trie.collections(listOf('d', 'o')).toSet())
        assertEquals(setOf(listOf('d', 'a', 't', 'a')), trie.collections(listOf('d', 'a')).toSet())
        assertTrue(trie.collections(listOf('z')).isEmpty())
    }

    private fun searchTreeImplementations(): List<SearchTreeImplementation> = listOf(
        SearchTreeImplementation("bst") { BinarySearchTree() },
        SearchTreeImplementation("avl") { AVLTree() },
        SearchTreeImplementation("splay") { SplayTree() },
        SearchTreeImplementation("treap") { Treap() },
        SearchTreeImplementation("red-black") { redBlackTreeOf() }
    )

    private fun randomWord(random: Random): List<Char> {
        val alphabet = charArrayOf('a', 'b', 'c', 'd', 'e')
        val length = random.nextInt(0, 7)
        return List(length) { alphabet[random.nextInt(alphabet.size)] }
    }

    private fun randomPrefix(random: Random): List<Char> {
        val alphabet = charArrayOf('a', 'b', 'c', 'd', 'e')
        val length = random.nextInt(0, 4)
        return List(length) { alphabet[random.nextInt(alphabet.size)] }
    }

    private fun List<Char>.startsWith(prefix: List<Char>): Boolean {
        if (prefix.size > size) return false
        for (index in prefix.indices) {
            if (this[index] != prefix[index]) return false
        }
        return true
    }

    private data class SearchTreeImplementation(
        val name: String,
        val factory: () -> MutableSearchTree<Int>
    )

    private data class CompareCounter(var comparisons: Long = 0)

    private class CountedKey(
        private val value: Int,
        private val counter: CompareCounter
    ) : Comparable<CountedKey> {
        override fun compareTo(other: CountedKey): Int {
            counter.comparisons++
            return value.compareTo(other.value)
        }

        override fun equals(other: Any?): Boolean = other is CountedKey && value == other.value

        override fun hashCode(): Int = value
    }

    private fun ceilLog2(n: Int): Int {
        require(n > 0)
        var power = 0
        var value = 1
        while (value < n) {
            value = value shl 1
            power++
        }
        return power
    }
}
