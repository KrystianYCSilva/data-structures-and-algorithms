package br.uem.din.datastructures

import br.uem.din.datastructures.queue.arrayQueueOf
import br.uem.din.datastructures.stack.arrayStackOf
import kotlin.test.*

class ImmutableViewsTest {

    @Test
    fun testStackAsReadOnlyPeek() {
        val stack = arrayStackOf<Int>()
        stack.push(1)
        stack.push(2)
        val view = stack.asReadOnly()
        assertEquals(2, view.peek())
    }

    @Test
    fun testStackAsReadOnlySize() {
        val stack = arrayStackOf<Int>()
        stack.push(1)
        stack.push(2)
        val view = stack.asReadOnly()
        assertEquals(2, view.size)
        assertFalse(view.isEmpty())
    }

    @Test
    fun testStackAsReadOnlyContains() {
        val stack = arrayStackOf<Int>()
        stack.push(10)
        stack.push(20)
        val view = stack.asReadOnly()
        assertTrue(view.contains(10))
        assertTrue(view.contains(20))
        assertFalse(view.contains(30))
    }

    @Test
    fun testStackAsReadOnlyIterator() {
        val stack = arrayStackOf<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        val view = stack.asReadOnly()
        val collected = mutableListOf<Int>()
        for (v in view) collected.add(v)
        assertEquals(listOf(3, 2, 1), collected)
    }

    @Test
    fun testStackAsReadOnlyToString() {
        val stack = arrayStackOf<Int>()
        stack.push(1)
        stack.push(2)
        val view = stack.asReadOnly()
        assertEquals(stack.toString(), view.toString())
    }

    @Test
    fun testStackAsReadOnlyLiveView() {
        val stack = arrayStackOf<Int>()
        stack.push(1)
        val view = stack.asReadOnly()
        assertEquals(1, view.size)
        stack.push(2)
        assertEquals(2, view.size)
        assertEquals(2, view.peek())
    }

    @Test
    fun testStackAsReadOnlyEmpty() {
        val stack = arrayStackOf<Int>()
        val view = stack.asReadOnly()
        assertTrue(view.isEmpty())
        assertNull(view.peek())
        assertEquals(0, view.size)
    }

    @Test
    fun testQueueAsReadOnlyPeek() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        val view = queue.asReadOnly()
        assertEquals(1, view.peek())
    }

    @Test
    fun testQueueAsReadOnlySize() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        val view = queue.asReadOnly()
        assertEquals(2, view.size)
        assertFalse(view.isEmpty())
    }

    @Test
    fun testQueueAsReadOnlyContains() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(10)
        queue.enqueue(20)
        val view = queue.asReadOnly()
        assertTrue(view.contains(10))
        assertTrue(view.contains(20))
        assertFalse(view.contains(30))
    }

    @Test
    fun testQueueAsReadOnlyIterator() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        val view = queue.asReadOnly()
        val collected = mutableListOf<Int>()
        for (v in view) collected.add(v)
        assertEquals(listOf(1, 2, 3), collected)
    }

    @Test
    fun testQueueAsReadOnlyToString() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        val view = queue.asReadOnly()
        assertEquals(queue.toString(), view.toString())
    }

    @Test
    fun testQueueAsReadOnlyLiveView() {
        val queue = arrayQueueOf<Int>()
        queue.enqueue(1)
        val view = queue.asReadOnly()
        assertEquals(1, view.size)
        queue.enqueue(2)
        assertEquals(2, view.size)
    }

    @Test
    fun testQueueAsReadOnlyEmpty() {
        val queue = arrayQueueOf<Int>()
        val view = queue.asReadOnly()
        assertTrue(view.isEmpty())
        assertNull(view.peek())
        assertEquals(0, view.size)
    }
}
