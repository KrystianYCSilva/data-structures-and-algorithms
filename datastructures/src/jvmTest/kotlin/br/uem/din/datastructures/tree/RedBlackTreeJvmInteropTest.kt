package br.uem.din.datastructures.tree

import java.lang.reflect.InvocationTargetException
import java.util.TreeSet
import kotlin.random.Random
import kotlin.test.*

class RedBlackTreeJvmInteropTest {

    @Test
    fun testJvmRedBlackParityWithJavaTreeSetRandomized() {
        val random = Random(20260217)
        val wrapped = redBlackTreeOf<Int>()
        val javaSet = TreeSet<Int>()

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val value = random.nextInt(-4_000, 4_001)
                    assertEquals(javaSet.add(value), wrapped.insert(value), "insert mismatch at step=$step")
                }

                in 55..79 -> {
                    val value = random.nextInt(-4_000, 4_001)
                    assertEquals(javaSet.remove(value), wrapped.remove(value), "remove mismatch at step=$step")
                }

                in 80..89 -> {
                    val value = random.nextInt(-4_000, 4_001)
                    assertEquals(javaSet.contains(value), wrapped.contains(value), "contains mismatch at step=$step")
                }

                else -> {
                    assertEquals(javaSet.firstOrNull(), wrapped.inOrder().firstOrNull(), "first element mismatch at step=$step")
                }
            }

            assertEquals(javaSet.size, wrapped.size, "size mismatch at step=$step")
            assertEquals(javaSet.isEmpty(), wrapped.isEmpty(), "isEmpty mismatch at step=$step")
            assertEquals(javaSet.toList(), wrapped.inOrder(), "inOrder mismatch at step=$step")
            assertEquals(javaSet.toList(), wrapped.iterator().asSequence().toList(), "iterator mismatch at step=$step")
        }
    }

    @Test
    fun testJvmRedBlackRejectsNullAtJavaBoundary() {
        val wrapped = redBlackTreeOf<Int>()
        val method = wrapped.javaClass.getMethod("insert", Comparable::class.java)

        val exception = assertFailsWith<InvocationTargetException> {
            method.invoke(wrapped, null)
        }

        assertTrue(
            exception.cause is NullPointerException,
            "Expected NullPointerException from Java boundary, got: ${exception.cause}"
        )
    }
}
