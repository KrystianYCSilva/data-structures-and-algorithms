package br.uem.din.datastructures.stack

import kotlin.random.Random
import kotlin.test.*

class StackTest {

    @Test
    fun testArrayStackContractBoundariesAndInvariants() {
        runContractSuite { arrayStackOf<Int>() }
    }

    @Test
    fun testLinkedStackContractBoundariesAndInvariants() {
        runContractSuite { LinkedStack<Int>() }
    }

    @Test
    fun testArrayStackRandomizedAgainstReferenceModel() {
        runRandomizedSuite(seedOffset = 0) { arrayStackOf<Int>() }
    }

    @Test
    fun testLinkedStackRandomizedAgainstReferenceModel() {
        runRandomizedSuite(seedOffset = 1000) { LinkedStack<Int>() }
    }

    @Test
    fun testPushReturnsElementAndSupportsNullOnCommonImplementations() {
        val stack = LinkedStack<Int?>()
        assertNull(stack.push(null))
        assertNull(stack.peek())
        assertNull(stack.pop())
    }

    private fun runContractSuite(factory: () -> MutableStack<Int>) {
        val stack = factory()

        assertTrue(stack.isEmpty())
        assertEquals(0, stack.size)
        assertNull(stack.peek())
        assertNull(stack.pop())
        assertFalse(stack.contains(1))
        assertEquals(emptyList(), stack.toList())

        assertEquals(42, stack.push(42))
        assertFalse(stack.isEmpty())
        assertEquals(1, stack.size)
        assertEquals(42, stack.peek())
        assertEquals(42, stack.pop())
        assertNull(stack.pop())
        assertTrue(stack.isEmpty())

        for (i in 0 until 20_000) {
            stack.push(i)
        }
        assertEquals(20_000, stack.size)
        assertEquals(19_999, stack.peek())

        for (i in 19_999 downTo 0) {
            assertEquals(i, stack.pop())
        }

        assertTrue(stack.isEmpty())

        stack.push(1)
        stack.push(2)
        stack.push(3)

        val alias: MutableStack<Int> = stack
        val readOnly: Stack<Int> = stack
        val snapshot = readOnly.toList()

        assertEquals(3, alias.pop())
        assertEquals(2, readOnly.peek())
        assertEquals(2, readOnly.size)
        assertEquals(listOf(3, 2, 1), snapshot)

        val iterator = readOnly.iterator()
        assertEquals(2, iterator.next())
        assertEquals(1, iterator.next())
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }

        alias.clear()
        assertTrue(readOnly.isEmpty())
        assertEquals(0, readOnly.size)
        assertNull(readOnly.peek())
    }

    private fun runRandomizedSuite(seedOffset: Int, factory: () -> MutableStack<Int>) {
        repeat(8) { seed ->
            val random = Random(seed + seedOffset)
            val stack = factory()
            val model = mutableListOf<Int>()

            repeat(1_200) {
                when (random.nextInt(100)) {
                    in 0..54 -> {
                        val value = random.nextInt(-1_000, 1_001)
                        assertEquals(value, stack.push(value))
                        model.add(value)
                    }

                    in 55..79 -> {
                        val expected = if (model.isEmpty()) null else model.removeAt(model.lastIndex)
                        assertEquals(expected, stack.pop())
                    }

                    in 80..89 -> {
                        assertEquals(model.lastOrNull(), stack.peek())
                    }

                    else -> {
                        val candidate = random.nextInt(-1_000, 1_001)
                        assertEquals(model.contains(candidate), stack.contains(candidate))
                    }
                }

                assertEquals(model.size, stack.size)
                assertEquals(model.isEmpty(), stack.isEmpty())
                assertEquals(model.lastOrNull(), stack.peek())
                assertEquals(model.asReversed(), stack.toList())
            }

            while (model.isNotEmpty()) {
                assertEquals(model.removeAt(model.lastIndex), stack.pop())
            }
            assertNull(stack.pop())
        }
    }
}
