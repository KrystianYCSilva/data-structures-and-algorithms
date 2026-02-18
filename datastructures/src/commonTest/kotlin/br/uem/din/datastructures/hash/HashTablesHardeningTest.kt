package br.uem.din.datastructures.hash

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.*

class HashTablesHardeningTest {

    @Test
    fun testEmptyStateSingleElementAndLargeCollection() {
        for (implementation in intImplementations()) {
            val table = implementation.factory()

            assertTrue(table.isEmpty(), "empty state failed for ${implementation.name}")
            assertEquals(0, table.size, "empty size failed for ${implementation.name}")
            assertNull(table.get(10), "get on empty failed for ${implementation.name}")
            assertNull(table.remove(10), "remove on empty failed for ${implementation.name}")

            table.put(7, 70)
            assertEquals(1, table.size, "single insert size failed for ${implementation.name}")
            assertEquals(70, table.get(7), "single insert get failed for ${implementation.name}")
            assertTrue(table.contains(7), "single insert contains failed for ${implementation.name}")

            table.put(7, 71)
            assertEquals(1, table.size, "overwrite size failed for ${implementation.name}")
            assertEquals(71, table.get(7), "overwrite get failed for ${implementation.name}")

            assertEquals(71, table.remove(7), "remove existing failed for ${implementation.name}")
            assertTrue(table.isEmpty(), "post-remove empty failed for ${implementation.name}")

            val total = 2_048
            for (i in 0 until total) {
                table.put(i, -i)
            }

            assertEquals(total, table.size, "large insert size failed for ${implementation.name}")
            for (i in 0 until total) {
                assertEquals(-i, table.get(i), "large insert retrieval failed for ${implementation.name}, key=$i")
            }
        }
    }

    @Test
    fun testCollisionHandlingAndIteratorContract() {
        for (implementation in collisionHeavyImplementations()) {
            val table = implementation.factory()
            val total = 200

            for (i in 0 until total) {
                table.put(CollidingKey(i), i * 10)
            }

            for (i in 0 until total) {
                assertEquals(i * 10, table.get(CollidingKey(i)), "collision get failed for ${implementation.name}, key=$i")
            }

            for (i in 0 until total step 2) {
                assertEquals(i * 10, table.remove(CollidingKey(i)), "collision remove failed for ${implementation.name}, key=$i")
            }

            val iterator = table.iterator()
            val seenKeys = mutableSetOf<Int>()
            while (iterator.hasNext()) {
                val (key, value) = iterator.next()
                seenKeys.add(key.id)
                assertEquals(key.id * 10, value, "iterator pair mismatch for ${implementation.name}")
            }

            assertEquals((1 until total step 2).toSet(), seenKeys, "iterator coverage mismatch for ${implementation.name}")
            assertFalse(iterator.hasNext(), "iterator termination failed for ${implementation.name}")
            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
    }

    @Test
    fun testMutableAliasAndReadOnlyViewSnapshotAcrossMutations() {
        for (implementation in intImplementations()) {
            val mutableTable = implementation.factory()
            val alias: MutableOpenHashTable<Int, Int> = mutableTable

            mutableTable.put(1, 100)
            alias.put(2, 200)

            val readOnlyView: OpenHashTable<Int, Int> = mutableTable.asReadOnly()
            val snapshot = readOnlyView.entries().toMap()

            alias.remove(1)
            mutableTable.put(3, 300)

            assertEquals(mapOf(1 to 100, 2 to 200), snapshot, "snapshot stability failed for ${implementation.name}")
            assertEquals(2, readOnlyView.size, "read-only view size failed for ${implementation.name}")
            assertFalse(readOnlyView.contains(1), "read-only live view remove propagation failed for ${implementation.name}")
            assertEquals(200, readOnlyView.get(2), "read-only live view existing key failed for ${implementation.name}")
            assertEquals(300, readOnlyView.get(3), "read-only live view new key failed for ${implementation.name}")
        }
    }

    @Test
    fun testNullableValuesDoNotBreakContainsSemantics() {
        for (implementation in nullableValueImplementations()) {
            val table = implementation.factory()

            table.put(1, null)
            table.put(2, 20)

            assertEquals(2, table.size, "nullable insert size failed for ${implementation.name}")
            assertTrue(table.contains(1), "contains should be true for key with null value in ${implementation.name}")
            assertNull(table.get(1), "get null value failed for ${implementation.name}")

            assertNull(table.remove(1), "remove should return null value for nullable entry in ${implementation.name}")
            assertEquals(1, table.size, "size after nullable remove failed for ${implementation.name}")
            assertFalse(table.contains(1), "contains should be false after removal in ${implementation.name}")
            assertEquals(mapOf(2 to 20), table.entries().toMap(), "entries after nullable remove failed for ${implementation.name}")
        }
    }

    @Test
    fun testRandomizedOperationsAgainstReferenceModel() {
        for (implementation in intImplementations()) {
            repeat(6) { seed ->
                val random = Random(seed)
                val table = implementation.factory()
                val model = mutableMapOf<Int, Int>()

                repeat(900) { step ->
                    when (random.nextInt(100)) {
                        in 0..49 -> {
                            val key = random.nextInt(0, 220)
                            val value = random.nextInt(-5_000, 5_001)
                            table.put(key, value)
                            model[key] = value
                        }

                        in 50..69 -> {
                            val key = random.nextInt(0, 220)
                            val expected = model.remove(key)
                            assertEquals(expected, table.remove(key), "remove mismatch (${implementation.name}) seed=$seed step=$step key=$key")
                        }

                        in 70..84 -> {
                            val key = random.nextInt(0, 220)
                            assertEquals(model[key], table.get(key), "get mismatch (${implementation.name}) seed=$seed step=$step key=$key")
                        }

                        else -> {
                            val key = random.nextInt(0, 220)
                            assertEquals(
                                model.containsKey(key),
                                table.contains(key),
                                "contains mismatch (${implementation.name}) seed=$seed step=$step key=$key"
                            )
                        }
                    }

                    assertEquals(model.size, table.size, "size mismatch (${implementation.name}) seed=$seed step=$step")
                    assertEquals(model.isEmpty(), table.isEmpty(), "isEmpty mismatch (${implementation.name}) seed=$seed step=$step")

                    if (step % 50 == 0 || step == 899) {
                        val entries = table.entries()
                        assertEquals(entries.size, entries.map { it.first }.toSet().size, "duplicate keys in entries (${implementation.name}) seed=$seed step=$step")
                        assertEquals(model, entries.toMap(), "entries map mismatch (${implementation.name}) seed=$seed step=$step")
                        assertEquals(model.keys, table.keys().toSet(), "keys mismatch (${implementation.name}) seed=$seed step=$step")
                        assertEquals(valueBag(model.values), valueBag(table.values()), "values bag mismatch (${implementation.name}) seed=$seed step=$step")
                    }
                }
            }
        }
    }

    @Test
    fun testHashAndEqualsCallsStayNearLinearEnvelope() {
        val n = 512
        val hashUpperBoundPerElement = 120L
        val equalsUpperBoundPerElement = 180L

        for (implementation in lowCollisionImplementations()) {
            val counter = OperationCounter()
            val table = implementation.factory(counter)
            val keys = (0 until n).map { CountingKey(it, counter) }

            for (key in keys) {
                table.put(key, key.id)
            }

            for (key in keys) {
                assertEquals(key.id, table.get(key), "get during complexity envelope failed for ${implementation.name}")
            }

            for (key in keys) {
                assertEquals(key.id, table.remove(key), "remove during complexity envelope failed for ${implementation.name}")
            }

            assertTrue(table.isEmpty(), "table should be empty after removals for ${implementation.name}")
            assertTrue(
                counter.hashCalls <= hashUpperBoundPerElement * n,
                "hash calls too high for ${implementation.name}: ${counter.hashCalls}"
            )
            assertTrue(
                counter.equalsCalls <= equalsUpperBoundPerElement * n,
                "equals calls too high for ${implementation.name}: ${counter.equalsCalls}"
            )
        }
    }

    private fun intImplementations(): List<Implementation<Int, Int>> = listOf(
        Implementation("open-addressing-linear") {
            OpenAddressingHashTable(
                initialCapacity = 8,
                maxLoadFactor = 0.6,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.LINEAR
            )
        },
        Implementation("open-addressing-quadratic") {
            OpenAddressingHashTable(
                initialCapacity = 8,
                maxLoadFactor = 0.6,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.QUADRATIC
            )
        },
        Implementation("open-addressing-double-hashing") {
            OpenAddressingHashTable(
                initialCapacity = 8,
                maxLoadFactor = 0.6,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.DOUBLE_HASHING
            )
        },
        Implementation("cuckoo") { CuckooHashTable(initialCapacity = 8) },
        Implementation("separate-chaining") {
            SeparateChainingHashTable(initialCapacity = 8, maxLoadFactor = 0.6)
        }
    )

    private fun nullableValueImplementations(): List<Implementation<Int, Int?>> = listOf(
        Implementation("open-addressing-linear-nullable") {
            OpenAddressingHashTable(
                initialCapacity = 8,
                maxLoadFactor = 0.6,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.LINEAR
            )
        },
        Implementation("open-addressing-quadratic-nullable") {
            OpenAddressingHashTable(
                initialCapacity = 8,
                maxLoadFactor = 0.6,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.QUADRATIC
            )
        },
        Implementation("open-addressing-double-hashing-nullable") {
            OpenAddressingHashTable(
                initialCapacity = 8,
                maxLoadFactor = 0.6,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.DOUBLE_HASHING
            )
        },
        Implementation("cuckoo-nullable") { CuckooHashTable(initialCapacity = 8) },
        Implementation("separate-chaining-nullable") {
            SeparateChainingHashTable(initialCapacity = 8, maxLoadFactor = 0.6)
        }
    )

    private fun collisionHeavyImplementations(): List<Implementation<CollidingKey, Int>> = listOf(
        Implementation("open-addressing-linear-collision") {
            OpenAddressingHashTable(
                initialCapacity = 16,
                maxLoadFactor = 0.7,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.LINEAR
            )
        },
        Implementation("open-addressing-quadratic-collision") {
            OpenAddressingHashTable(
                initialCapacity = 16,
                maxLoadFactor = 0.7,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.QUADRATIC
            )
        },
        Implementation("open-addressing-double-hashing-collision") {
            OpenAddressingHashTable(
                initialCapacity = 16,
                maxLoadFactor = 0.7,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.DOUBLE_HASHING
            )
        },
        Implementation("separate-chaining-collision") {
            SeparateChainingHashTable(initialCapacity = 16, maxLoadFactor = 0.7)
        }
    )

    private fun lowCollisionImplementations(): List<CountingImplementation> = listOf(
        CountingImplementation("open-addressing-linear") { counter ->
            OpenAddressingHashTable(
                initialCapacity = 4_096,
                maxLoadFactor = 0.95,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.LINEAR
            )
        },
        CountingImplementation("open-addressing-quadratic") { counter ->
            OpenAddressingHashTable(
                initialCapacity = 4_096,
                maxLoadFactor = 0.95,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.QUADRATIC
            )
        },
        CountingImplementation("open-addressing-double-hashing") { counter ->
            OpenAddressingHashTable(
                initialCapacity = 4_096,
                maxLoadFactor = 0.95,
                probingStrategy = OpenAddressingHashTable.ProbingStrategy.DOUBLE_HASHING
            )
        },
        CountingImplementation("cuckoo") { counter ->
            CuckooHashTable(initialCapacity = 2_048)
        },
        CountingImplementation("separate-chaining") { counter ->
            SeparateChainingHashTable(initialCapacity = 4_096, maxLoadFactor = 0.95)
        }
    )

    private fun <T> valueBag(values: Iterable<T>): Map<T, Int> = values.groupingBy { it }.eachCount()

    private data class Implementation<K : Any, V>(
        val name: String,
        val factory: () -> MutableOpenHashTable<K, V>
    )

    private data class CountingImplementation(
        val name: String,
        val factory: (OperationCounter) -> MutableOpenHashTable<CountingKey, Int>
    )

    private data class CollidingKey(val id: Int) {
        override fun hashCode(): Int = id % 11
    }

    private data class OperationCounter(
        var hashCalls: Long = 0,
        var equalsCalls: Long = 0
    )

    private class CountingKey(
        val id: Int,
        private val counter: OperationCounter
    ) {
        override fun hashCode(): Int {
            counter.hashCalls++
            return id
        }

        override fun equals(other: Any?): Boolean {
            counter.equalsCalls++
            return other is CountingKey && id == other.id
        }
    }
}
