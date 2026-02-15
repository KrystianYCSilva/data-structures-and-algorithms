package br.uem.din.datastructures.queue

import kotlin.test.*

class CircularQueueTest {
    @Test
    fun testCircularLogic() {
        // Capacity 3 means we can store 2 items if using standard circular buffer logic with one empty slot
        val queue = CircularQueue<Int>(3)
        queue.enqueue(1)
        queue.enqueue(2)
        
        assertEquals(2, queue.size())
        assertTrue(queue.isFull)
        
        // Should throw exception when full
        assertFailsWith<IllegalStateException> { queue.enqueue(3) }
        
        assertEquals(1, queue.dequeue())
        assertFalse(queue.isFull)
        
        queue.enqueue(3)
        assertEquals(2, queue.size())
        assertEquals(2, queue.peek())
    }
}
