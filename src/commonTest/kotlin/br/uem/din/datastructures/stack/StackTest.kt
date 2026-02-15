package br.uem.din.datastructures.stack

import kotlin.test.*

class StackTest {

    @Test
    fun testArrayStackPushPop() {
        val stack = ArrayStack<Int>()
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

    @Test
    fun testArrayStackPopEmpty() {
        val stack = ArrayStack<Int>()
        assertNull(stack.pop())
        assertNull(stack.peek())
    }

    @Test
    fun testArrayStackContains() {
        val stack = ArrayStack<String>()
        stack.push("a")
        stack.push("b")
        stack.push("c")
        assertTrue(stack.contains("b"))
        assertFalse(stack.contains("z"))
    }

    @Test
    fun testArrayStackClear() {
        val stack = ArrayStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.clear()
        assertTrue(stack.isEmpty())
        assertEquals(0, stack.size())
        assertNull(stack.peek())
    }

    @Test
    fun testArrayStackIterator() {
        val stack = ArrayStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        val collected = mutableListOf<Int>()
        for (v in stack) collected.add(v)
        assertEquals(listOf(3, 2, 1), collected)
    }

    @Test
    fun testArrayStackToList() {
        val stack = ArrayStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        assertEquals(listOf(3, 2, 1), stack.toList())
    }

    @Test
    fun testArrayStackIterableExtensions() {
        val stack = ArrayStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        assertEquals(6, stack.sumOf { it })
        assertTrue(stack.any { it == 2 })
    }

    @Test
    fun testLinkedStackPushPop() {
        val stack = LinkedStack<Int>()
        assertTrue(stack.isEmpty())
        stack.push(10)
        stack.push(20)
        assertEquals(2, stack.size())
        assertEquals(20, stack.peek())
        assertEquals(20, stack.pop())
        assertEquals(10, stack.pop())
        assertNull(stack.pop())
    }

    @Test
    fun testLinkedStackContains() {
        val stack = LinkedStack<String>()
        stack.push("x")
        stack.push("y")
        assertTrue(stack.contains("x"))
        assertFalse(stack.contains("z"))
    }

    @Test
    fun testLinkedStackClear() {
        val stack = LinkedStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.clear()
        assertTrue(stack.isEmpty())
        assertEquals(0, stack.size())
    }

    @Test
    fun testLinkedStackIterator() {
        val stack = LinkedStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        val collected = mutableListOf<Int>()
        for (v in stack) collected.add(v)
        assertEquals(listOf(3, 2, 1), collected)
    }

    @Test
    fun testLinkedStackToList() {
        val stack = LinkedStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        assertEquals(listOf(3, 2, 1), stack.toList())
    }
}
