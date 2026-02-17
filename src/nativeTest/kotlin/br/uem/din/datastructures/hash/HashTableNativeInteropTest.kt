package br.uem.din.datastructures.hash

import kotlin.random.Random
import kotlin.test.*

class HashTableNativeInteropTest {

    @Test
    fun testNativeTypealiasSupportsNullableKeysAndValues() {
        val table = HashTable<String?, Int?>()

        table[null] = 7
        table["x"] = null

        assertEquals(7, table[null])
        assertNull(table["x"])
        assertTrue(table.containsKey(null))
        assertTrue(table.containsKey("x"))
    }

    @Test
    fun testNativeTypealiasRandomizedParityWithMutableMap() {
        val random = Random(20260221)
        val table = HashTable<Int, Int>()
        val model = mutableMapOf<Int, Int>()

        repeat(1_600) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val key = random.nextInt(-250, 251)
                    val value = random.nextInt(-3_000, 3_001)
                    table[key] = value
                    model[key] = value
                }

                in 55..74 -> {
                    val key = random.nextInt(-250, 251)
                    assertEquals(model.remove(key), table.remove(key), "remove mismatch at step=$step")
                }

                in 75..89 -> {
                    val key = random.nextInt(-250, 251)
                    assertEquals(model[key], table[key], "get mismatch at step=$step")
                }

                else -> {
                    val key = random.nextInt(-250, 251)
                    assertEquals(model.containsKey(key), table.containsKey(key), "containsKey mismatch at step=$step")
                }
            }

            assertEquals(model.size, table.size, "size mismatch at step=$step")
            assertEquals(model, table, "map snapshot mismatch at step=$step")
        }
    }
}
