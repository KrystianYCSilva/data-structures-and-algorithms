package br.com.leandroluce.algoritmos.datastructures.stack

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StackImplTest {

    @Test
    fun `push should add element to stack`() {
        val stack = StackImpl<Int>()
        stack.push(1)
        assertEquals(1, stack.count)
        assertEquals(1, stack.peek())
    }

    @Test
    fun `pop should remove element from stack`() {
        val stack = StackImpl<Int>()
        stack.push(1)
        stack.push(2)
        val popped = stack.pop()
        assertEquals(2, popped)
        assertEquals(1, stack.count)
        assertEquals(1, stack.peek())
    }

    @Test
    fun `pop on empty stack should return null`() {
        val stack = StackImpl<Int>()
        assertNull(stack.pop())
    }

    @Test
    fun `peek on empty stack should return null`() {
        val stack = StackImpl<Int>()
        assertNull(stack.peek())
    }

    @Test
    fun `peek should return element without removing it`() {
        val stack = StackImpl<Int>()
        stack.push(1)
        assertEquals(1, stack.peek())
        assertEquals(1, stack.count)
    }
}
