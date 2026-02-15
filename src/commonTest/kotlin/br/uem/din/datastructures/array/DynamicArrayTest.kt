package br.uem.din.datastructures.array

import kotlin.test.*

class DynamicArrayTest {
    @Test
    fun testAddAndGet() {
        val array = DynamicArray<Int>(2)
        array.add(1)
        array.add(2)
        array.add(3) // Trigger resize
        
        assertEquals(3, array.size)
        assertEquals(1, array[0])
        assertEquals(3, array[2])
    }

    @Test
    fun testRemove() {
        val array = DynamicArray<String>()
        array.add("A")
        array.add("B")
        array.add("C")
        
        assertEquals("B", array.removeAt(1))
        assertEquals(2, array.size)
        assertEquals("C", array[1])
    }
}
