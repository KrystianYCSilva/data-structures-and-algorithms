package br.uem.din.datastructures

import br.uem.din.datastructures.queue.arrayQueueOf
import br.uem.din.datastructures.stack.arrayStackOf
import kotlin.test.*

class CollectionsExtensionsTest {

    @Test
    fun testStackOf() {
        val stack = stackOf(1, 2, 3)
        assertEquals(3, stack.size)
        assertEquals(3, stack.pop())
        assertEquals(2, stack.pop())
        assertEquals(1, stack.pop())
    }

    @Test
    fun testStackOfEmpty() {
        val stack = stackOf<Int>()
        assertTrue(stack.isEmpty())
    }

    @Test
    fun testQueueOf() {
        val queue = queueOf(1, 2, 3)
        assertEquals(3, queue.size)
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())
        assertEquals(3, queue.dequeue())
    }

    @Test
    fun testQueueOfEmpty() {
        val queue = queueOf<Int>()
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testLinkedListOf() {
        val list = linkedListOf(10, 20, 30)
        assertEquals(3, list.size)
        assertEquals(listOf(10, 20, 30), list.toList())
    }

    @Test
    fun testToStack() {
        val stack = listOf(1, 2, 3).toStack()
        assertEquals(3, stack.size)
        assertEquals(3, stack.pop())
    }

    @Test
    fun testToQueue() {
        val queue = listOf(1, 2, 3).toQueue()
        assertEquals(3, queue.size)
        assertEquals(1, queue.dequeue())
    }

    @Test
    fun testToLinkedList() {
        val list = listOf("a", "b").toLinkedList()
        assertEquals(2, list.size)
    }

    @Test
    fun testToSearchTree() {
        val tree = listOf(5, 3, 7, 1, 4).toSearchTree()
        assertEquals(5, tree.size)
        assertTrue(tree.contains(3))
        assertTrue(tree.contains(7))
        assertFalse(tree.contains(99))
    }

    @Test
    fun testToPriorityQueue() {
        val pq = listOf(3, 1, 2).toPriorityQueue()
        assertEquals(1, pq.dequeue())
        assertEquals(2, pq.dequeue())
        assertEquals(3, pq.dequeue())
    }

    @Test
    fun testBuildStack() {
        val stack = buildStack<Int> {
            push(10)
            push(20)
        }
        assertEquals(2, stack.size)
        assertEquals(20, stack.pop())
    }

    @Test
    fun testBuildQueue() {
        val queue = buildQueue<String> {
            enqueue("hello")
            enqueue("world")
        }
        assertEquals(2, queue.size)
        assertEquals("hello", queue.dequeue())
    }

    @Test
    fun testBuildLinkedList() {
        val list = buildLinkedList<Int> {
            addLast(1)
            addLast(2)
        }
        assertEquals(2, list.size)
    }

    @Test
    fun testStackPlus() {
        val stack = stackOf(1, 2)
        val result = stack + 3
        assertEquals(3, result.size)
        assertEquals(3, result.pop())
    }

    @Test
    fun testQueuePlus() {
        val queue = queueOf(1, 2)
        val result = queue + 3
        assertEquals(3, result.size)
        assertEquals(1, result.dequeue())
    }
}
