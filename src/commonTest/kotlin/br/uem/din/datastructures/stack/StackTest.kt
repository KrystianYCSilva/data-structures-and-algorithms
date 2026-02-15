package br.uem.din.datastructures.stack

import kotlin.test.*

class StackTest {
    @Test
    fun testPushPop() {
        val stack = Stack<Int>()
        assertTrue(stack.isEmpty())
        stack.push(1)
        stack.push(2)
        assertEquals(2, stack.size())
        assertEquals(2, stack.peek())
        assertEquals(2, stack.pop())
        assertEquals(1, stack.size())
        assertEquals(1, stack.pop())
        assertTrue(stack.isEmpty())
    }
}
