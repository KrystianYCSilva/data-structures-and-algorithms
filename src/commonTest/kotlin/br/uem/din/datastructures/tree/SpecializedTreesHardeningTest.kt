package br.uem.din.datastructures.tree

import kotlin.random.Random
import kotlin.test.*

class SpecializedTreesHardeningTest {

    @Test
    fun testBTreeRandomizedAgainstSetOracle() {
        for (minimumDegree in listOf(2, 3)) {
            repeat(5) { seed ->
                val tree = BTree<Int>(minimumDegree)
                val model = mutableSetOf<Int>()
                val random = Random(seed + minimumDegree * 100)

                repeat(700) { step ->
                    when (random.nextInt(100)) {
                        in 0..54 -> {
                            val value = random.nextInt(0, 280)
                            assertEquals(model.add(value), tree.insert(value), "insert mismatch degree=$minimumDegree seed=$seed step=$step")
                        }

                        in 55..79 -> {
                            val value = random.nextInt(0, 280)
                            assertEquals(model.remove(value), tree.remove(value), "remove mismatch degree=$minimumDegree seed=$seed step=$step")
                        }

                        else -> {
                            val value = random.nextInt(0, 280)
                            assertEquals(model.contains(value), tree.contains(value), "contains mismatch degree=$minimumDegree seed=$seed step=$step")
                        }
                    }

                    assertEquals(model.size, tree.size, "size mismatch degree=$minimumDegree seed=$seed step=$step")
                    assertEquals(model.isEmpty(), tree.isEmpty(), "isEmpty mismatch degree=$minimumDegree seed=$seed step=$step")

                    if (step % 25 == 0) {
                        val expected = model.toList().sorted()
                        assertEquals(expected, tree.inOrder(), "inOrder mismatch degree=$minimumDegree seed=$seed step=$step")
                        assertEquals(expected, tree.iterator().asSequence().toList(), "iterator mismatch degree=$minimumDegree seed=$seed step=$step")
                    }
                }
            }
        }
    }

    @Test
    fun testBPlusTreeRandomizedAgainstSetOracleAndRangeSearch() {
        for (order in listOf(3, 4, 5)) {
            repeat(4) { seed ->
                val tree = BPlusTree<Int>(order)
                val model = mutableSetOf<Int>()
                val random = Random(seed + order * 100)

                repeat(650) { step ->
                    when (random.nextInt(100)) {
                        in 0..44 -> {
                            val value = random.nextInt(0, 260)
                            assertEquals(model.add(value), tree.insert(value), "insert mismatch order=$order seed=$seed step=$step")
                        }

                        in 45..64 -> {
                            val value = random.nextInt(0, 260)
                            assertEquals(model.remove(value), tree.remove(value), "remove mismatch order=$order seed=$seed step=$step")
                        }

                        in 65..79 -> {
                            val value = random.nextInt(0, 260)
                            assertEquals(model.contains(value), tree.contains(value), "contains mismatch order=$order seed=$seed step=$step")
                        }

                        else -> {
                            val a = random.nextInt(0, 260)
                            val b = random.nextInt(0, 260)
                            val left = minOf(a, b)
                            val right = maxOf(a, b)
                            val expected = model.filter { it in left..right }.sorted()
                            assertEquals(expected, tree.rangeSearch(left, right), "rangeSearch mismatch order=$order seed=$seed step=$step")
                        }
                    }

                    assertEquals(model.size, tree.size, "size mismatch order=$order seed=$seed step=$step")
                    assertEquals(model.isEmpty(), tree.isEmpty(), "isEmpty mismatch order=$order seed=$seed step=$step")

                    if (step % 20 == 0) {
                        assertEquals(model.toList().sorted(), tree.inOrder(), "inOrder mismatch order=$order seed=$seed step=$step")
                    }
                }
            }
        }
    }

    @Test
    fun testBTreeAndBPlusTreeIteratorTermination() {
        val trees: List<Pair<String, MutableSearchTree<Int>>> = listOf(
            "btree" to BTree(),
            "bplustree" to BPlusTree()
        )

        for ((name, tree) in trees) {
            tree.insert(2)
            tree.insert(1)
            tree.insert(3)

            val iterator = tree.iterator()
            val values = mutableListOf<Int>()
            while (iterator.hasNext()) {
                values.add(iterator.next())
            }

            assertEquals(listOf(1, 2, 3), values, "iterator values mismatch for $name")
            assertFalse(iterator.hasNext(), "iterator termination mismatch for $name")
            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
    }

    @Test
    fun testFenwickTreeRandomizedAgainstArrayOracle() {
        val n = 160
        val random = Random(20260217)
        val initial = LongArray(n) { random.nextInt(-1000, 1001).toLong() }
        val tree = FenwickTree(n)
        val oracle = initial.copyOf()
        tree.build(initial)

        repeat(1_600) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val index = random.nextInt(0, n)
                    val delta = random.nextInt(-200, 201).toLong()
                    tree.update(index, delta)
                    oracle[index] += delta
                }

                in 55..74 -> {
                    val index = random.nextInt(0, n)
                    assertEquals(prefixSum(oracle, index), tree.prefixSum(index), "prefixSum mismatch step=$step")
                }

                in 75..89 -> {
                    val a = random.nextInt(0, n)
                    val b = random.nextInt(0, n)
                    val left = minOf(a, b)
                    val right = maxOf(a, b)
                    assertEquals(rangeSum(oracle, left, right), tree.rangeSum(left, right), "rangeSum mismatch step=$step")
                }

                else -> {
                    val index = random.nextInt(0, n)
                    assertEquals(oracle[index], tree.pointQuery(index), "pointQuery mismatch step=$step")
                }
            }
        }

        assertFailsWith<IllegalArgumentException> { tree.update(-1, 1) }
        assertFailsWith<IllegalArgumentException> { tree.update(n, 1) }
        assertFailsWith<IllegalArgumentException> { tree.prefixSum(n) }
        assertFailsWith<IllegalArgumentException> { tree.rangeSum(20, 10) }
    }

    @Test
    fun testSegmentTreeRandomizedPointUpdateAndQueryAgainstArrayOracle() {
        val n = 140
        val random = Random(20260218)
        val oracle = LongArray(n) { random.nextInt(-500, 501).toLong() }

        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(oracle.toList())

        repeat(1_300) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val index = random.nextInt(0, n)
                    val value = random.nextInt(-500, 501).toLong()
                    tree.update(index, value)
                    oracle[index] = value
                }

                in 55..89 -> {
                    val a = random.nextInt(0, n)
                    val b = random.nextInt(0, n)
                    val left = minOf(a, b)
                    val right = maxOf(a, b)
                    assertEquals(rangeSum(oracle, left, right), tree.query(left, right), "query mismatch step=$step")
                }

                else -> {
                    val index = random.nextInt(0, n)
                    assertEquals(oracle[index], tree.query(index, index), "point query mismatch step=$step")
                }
            }
        }

        assertFailsWith<IllegalArgumentException> { tree.query(-1, 3) }
        assertFailsWith<IllegalArgumentException> { tree.update(n, 10) }
        assertFailsWith<IllegalArgumentException> { tree.rangeUpdate(5, 4, 1) }
    }

    @Test
    fun testSegmentTreeRangeUpdateMatchesPointQueries() {
        val n = 96
        val random = Random(20260219)
        val oracle = LongArray(n) { 0L }
        val tree = SegmentTree(0L) { a, b -> a + b }
        tree.build(List(n) { 0L })

        repeat(400) { step ->
            val a = random.nextInt(0, n)
            val b = random.nextInt(0, n)
            val left = minOf(a, b)
            val right = maxOf(a, b)
            val delta = random.nextInt(-20, 21).toLong()

            tree.rangeUpdate(left, right, delta)
            for (index in left..right) {
                oracle[index] += delta
            }

            val probe = random.nextInt(0, n)
            assertEquals(oracle[probe], tree.query(probe, probe), "rangeUpdate point mismatch step=$step")
        }
    }

    @Test
    fun testRadixTreeRandomizedAgainstSetOracle() {
        repeat(6) { seed ->
            val random = Random(seed)
            val tree = RadixTree()
            val model = mutableSetOf<String>()

            repeat(900) { step ->
                when (random.nextInt(100)) {
                    in 0..49 -> {
                        val word = randomWord(random)
                        tree.insert(word)
                        model.add(word)
                    }

                    in 50..69 -> {
                        val word = randomWord(random)
                        tree.remove(word)
                        model.remove(word)
                    }

                    in 70..84 -> {
                        val word = randomWord(random)
                        assertEquals(model.contains(word), tree.search(word), "search mismatch seed=$seed step=$step")
                    }

                    else -> {
                        val prefix = randomPrefix(random)
                        val expected = model.filter { it.startsWith(prefix) }.toSet()
                        val actual = tree.prefixSearch(prefix).toSet()
                        assertEquals(expected, actual, "prefixSearch mismatch seed=$seed step=$step prefix='$prefix'")
                    }
                }

                assertEquals(model.size, tree.size, "size mismatch seed=$seed step=$step")

                if (step % 25 == 0) {
                    assertEquals(model, tree.prefixSearch("").toSet(), "full prefixSearch mismatch seed=$seed step=$step")
                }
            }
        }
    }

    @Test
    fun testCartesianTreeRandomizedBuildMaintainsInOrderAndMinHeap() {
        repeat(30) { seed ->
            val random = Random(seed)
            val values = List(random.nextInt(0, 220)) { random.nextInt(-1000, 1001) }
            val tree = CartesianTree<Int>()

            tree.buildFromArray(values)

            assertEquals(values, tree.inOrder(), "inOrder must match input sequence for seed=$seed")
            assertTrue(tree.isValidMinHeap(), "min-heap property failed for seed=$seed")

            if (values.isEmpty()) {
                assertNull(tree.root, "root should be null for empty input seed=$seed")
            } else {
                assertEquals(values.minOrNull(), tree.root?.value, "root should be global minimum for seed=$seed")
            }
        }
    }

    @Test
    fun testInvalidConstructionParametersThrow() {
        assertFailsWith<IllegalArgumentException> { BTree<Int>(minimumDegree = 1) }
        assertFailsWith<IllegalArgumentException> { BPlusTree<Int>(order = 2) }
    }

    private fun prefixSum(values: LongArray, index: Int): Long {
        var sum = 0L
        for (i in 0..index) {
            sum += values[i]
        }
        return sum
    }

    private fun rangeSum(values: LongArray, left: Int, right: Int): Long {
        var sum = 0L
        for (i in left..right) {
            sum += values[i]
        }
        return sum
    }

    private fun randomWord(random: Random): String {
        val alphabet = charArrayOf('a', 'b', 'c', 'd', 'e')
        val length = random.nextInt(0, 9)
        return buildString {
            repeat(length) { append(alphabet[random.nextInt(alphabet.size)]) }
        }
    }

    private fun randomPrefix(random: Random): String {
        val alphabet = charArrayOf('a', 'b', 'c', 'd', 'e')
        val length = random.nextInt(0, 4)
        return buildString {
            repeat(length) { append(alphabet[random.nextInt(alphabet.size)]) }
        }
    }
}
