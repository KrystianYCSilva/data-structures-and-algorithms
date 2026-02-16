package br.uem.din.datastructures.array

import kotlin.test.*

class DynamicArrayTest {
    @Test
    fun testAddAndGet() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.add(2)
        array.add(3)

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

    @Test
    fun testInsertAtIndex() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.add(3)
        array.add(1, 2)

        assertEquals(3, array.size)
        assertEquals(2, array[1])
        assertEquals(3, array[2])
    }

    @Test
    fun testIsEmpty() {
        val array = DynamicArray<Int>()
        assertTrue(array.isEmpty())
        array.add(42)
        assertFalse(array.isEmpty())
    }
}
