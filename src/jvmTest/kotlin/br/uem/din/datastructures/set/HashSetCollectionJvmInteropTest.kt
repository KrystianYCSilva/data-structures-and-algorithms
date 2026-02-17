package br.uem.din.datastructures.set

import java.util.HashSet as JavaHashSet
import kotlin.random.Random
import kotlin.test.*

class HashSetCollectionJvmInteropTest {

    @Test
    fun testTypealiasBacksJavaHashSetOnJvm() {
        val set = HashSetCollection<Int>()
        assertTrue(set is JavaHashSet<*>)

        set.add(1)
        val javaSet: JavaHashSet<Int> = set
        javaSet.add(2)

        assertEquals(2, set.size)
        assertTrue(set.contains(2))
        assertTrue(javaSet.contains(1))
    }

    @Test
    fun testJvmBoundarySupportsNullableElements() {
        val set = HashSetCollection<String?>()
        val javaSet: JavaHashSet<String?> = set

        set.add(null)
        javaSet.add("x")

        assertTrue(set.contains(null))
        assertTrue(set.contains("x"))
        assertEquals(2, javaSet.size)
    }

    @Test
    fun testRandomizedParityWithJavaHashSet() {
        val random = Random(20260218)
        val ours = HashSetCollection<Int>()
        val oracle = JavaHashSet<Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val value = random.nextInt(-400, 401)
                    assertEquals(oracle.add(value), ours.add(value), "add mismatch at step=$step")
                }

                in 55..79 -> {
                    val value = random.nextInt(-400, 401)
                    assertEquals(oracle.remove(value), ours.remove(value), "remove mismatch at step=$step")
                }

                in 80..94 -> {
                    val value = random.nextInt(-400, 401)
                    assertEquals(oracle.contains(value), ours.contains(value), "contains mismatch at step=$step")
                }

                else -> {
                    ours.clear()
                    oracle.clear()
                }
            }

            assertEquals(oracle.size, ours.size, "size mismatch at step=$step")
            assertEquals(oracle, ours, "set snapshot mismatch at step=$step")
        }
    }
}
