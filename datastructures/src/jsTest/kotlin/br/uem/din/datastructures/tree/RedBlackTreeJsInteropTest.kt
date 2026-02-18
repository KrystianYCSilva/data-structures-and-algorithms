package br.uem.din.datastructures.tree

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RedBlackTreeJsInteropTest {

    @Test
    fun testJsRedBlackParityWithSortedSetModelRandomized() {
        val random = Random(20260218)
        val wrapped = redBlackTreeOf<Int>()
        val model = mutableSetOf<Int>()

        repeat(1_500) { step ->
            when (random.nextInt(100)) {
                in 0..54 -> {
                    val value = random.nextInt(-3_000, 3_001)
                    assertEquals(model.add(value), wrapped.insert(value), "insert mismatch at step=$step")
                }

                in 55..79 -> {
                    val value = random.nextInt(-3_000, 3_001)
                    assertEquals(model.remove(value), wrapped.remove(value), "remove mismatch at step=$step")
                }

                in 80..89 -> {
                    val value = random.nextInt(-3_000, 3_001)
                    assertEquals(model.contains(value), wrapped.contains(value), "contains mismatch at step=$step")
                }

                else -> {
                    assertEquals(model.minOrNull(), wrapped.inOrder().firstOrNull(), "first element mismatch at step=$step")
                }
            }

            val expectedOrder = model.toList().sorted()
            assertEquals(model.size, wrapped.size, "size mismatch at step=$step")
            assertEquals(model.isEmpty(), wrapped.isEmpty(), "isEmpty mismatch at step=$step")
            assertEquals(expectedOrder, wrapped.inOrder(), "inOrder mismatch at step=$step")
            assertEquals(expectedOrder, wrapped.iterator().asSequence().toList(), "iterator mismatch at step=$step")
        }
    }

    @Test
    fun testJsRedBlackReadOnlyViewTracksLiveState() {
        val mutableTree = redBlackTreeOf<Int>()
        mutableTree.insert(10)
        mutableTree.insert(5)
        mutableTree.insert(20)

        val readOnlyView: SearchTree<Int> = mutableTree.asReadOnly()
        val snapshot = readOnlyView.inOrder()

        mutableTree.insert(7)
        mutableTree.remove(20)

        assertEquals(listOf(5, 10, 20), snapshot)
        assertEquals(listOf(5, 7, 10), readOnlyView.inOrder())
        assertTrue(readOnlyView.contains(7))
        assertFalse(readOnlyView.contains(20))
    }
}
