package br.uem.din.datastructures.hash

import java.util.HashMap as JavaHashMap
import kotlin.random.Random
import kotlin.test.*

class HashTableJvmInteropTest {

    @Test
    fun testTypealiasBacksJavaHashMapOnJvm() {
        val table = HashTable<String, Int>()
        assertTrue(table is JavaHashMap<*, *>)

        table["a"] = 1
        val javaMap: JavaHashMap<String, Int> = table
        javaMap["b"] = 2

        assertEquals(2, table.size)
        assertEquals(2, javaMap.size)
        assertEquals(1, javaMap["a"])
        assertEquals(2, table["b"])
    }

    @Test
    fun testJvmBoundarySupportsNullableKeysAndValues() {
        val table = HashTable<String?, Int?>()
        val javaMap: JavaHashMap<String?, Int?> = table

        table[null] = 7
        javaMap["x"] = null

        assertEquals(7, javaMap[null])
        assertNull(table["x"])
        assertTrue(table.containsKey(null))
        assertTrue(javaMap.containsKey("x"))
    }

    @Test
    fun testRandomizedParityWithJavaHashMap() {
        val random = Random(20260217)
        val ours = HashTable<Int, Int>()
        val oracle = JavaHashMap<Int, Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val key = random.nextInt(-300, 301)
                    val value = random.nextInt(-5_000, 5_001)
                    ours[key] = value
                    oracle[key] = value
                }

                in 55..74 -> {
                    val key = random.nextInt(-300, 301)
                    assertEquals(oracle.remove(key), ours.remove(key), "remove mismatch at step=$step")
                }

                in 75..89 -> {
                    val key = random.nextInt(-300, 301)
                    assertEquals(oracle[key], ours[key], "get mismatch at step=$step")
                }

                else -> {
                    val key = random.nextInt(-300, 301)
                    assertEquals(oracle.containsKey(key), ours.containsKey(key), "containsKey mismatch at step=$step")
                }
            }

            assertEquals(oracle.size, ours.size, "size mismatch at step=$step")
            assertEquals(oracle, ours, "map snapshot mismatch at step=$step")
        }
    }
}
