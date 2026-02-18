package br.uem.din.datastructures.array

import kotlin.test.*

class DynamicArrayTest {
    @Test
    fun testDynamicArrayAddAndGet() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.add(2)
        array.add(3)

        assertEquals(3, array.size)
        assertEquals(1, array[0])
        assertEquals(3, array[2])
    }

    @Test
    fun testDynamicArrayRemoveAt() {
        val array = DynamicArray<String>()
        array.add("A")
        array.add("B")
        array.add("C")

        assertEquals("B", array.removeAt(1))
        assertEquals(2, array.size)
        assertEquals("C", array[1])
    }

    @Test
    fun testDynamicArrayInsertAtIndex() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.add(3)
        array.add(1, 2)

        assertEquals(3, array.size)
        assertEquals(2, array[1])
        assertEquals(3, array[2])
    }

    @Test
    fun testDynamicArrayIsEmpty() {
        val array = DynamicArray<Int>()
        assertTrue(array.isEmpty())
        array.add(42)
        assertFalse(array.isEmpty())
    }

    @Test
    fun testDynamicArraySet() {
        val array = DynamicArray<Int>()
        array.add(10)
        array.add(20)
        array[1] = 99
        assertEquals(99, array[1])
        assertEquals(2, array.size)
    }

    @Test
    fun testDynamicArrayRemoveElement() {
        val array = DynamicArray<String>()
        array.add("X")
        array.add("Y")
        array.add("Z")
        assertTrue(array.remove("Y"))
        assertFalse(array.remove("W"))
        assertEquals(listOf("X", "Z"), array.toList())
    }

    @Test
    fun testDynamicArrayContains() {
        val array = DynamicArray<Int>()
        array.add(5)
        array.add(10)
        assertTrue(array.contains(5))
        assertFalse(array.contains(99))
    }

    @Test
    fun testDynamicArrayIndexOf() {
        val array = DynamicArray<String>()
        array.add("a")
        array.add("b")
        array.add("c")
        assertEquals(1, array.indexOf("b"))
        assertEquals(-1, array.indexOf("z"))
    }

    @Test
    fun testDynamicArrayLastIndexOf() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.add(2)
        array.add(1)
        assertEquals(2, array.lastIndexOf(1))
        assertEquals(-1, array.lastIndexOf(9))
    }

    @Test
    fun testDynamicArrayClear() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.add(2)
        array.add(3)
        array.clear()
        assertTrue(array.isEmpty())
        assertEquals(0, array.size)
    }

    @Test
    fun testDynamicArrayAddAll() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.addAll(listOf(2, 3, 4))
        assertEquals(4, array.size)
        assertEquals(listOf(1, 2, 3, 4), array.toList())
    }

    @Test
    fun testDynamicArrayIterator() {
        val array = DynamicArray<Int>()
        array.add(10)
        array.add(20)
        array.add(30)
        val collected = mutableListOf<Int>()
        for (v in array) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testDynamicArraySubList() {
        val array = DynamicArray<Int>()
        array.addAll(listOf(0, 1, 2, 3, 4))
        val sub = array.subList(1, 4)
        assertEquals(listOf(1, 2, 3), sub)
    }

    @Test
    fun testDynamicArraySize() {
        val array = DynamicArray<Int>()
        assertEquals(0, array.size)
        array.add(1)
        assertEquals(1, array.size)
        array.add(2)
        array.add(3)
        assertEquals(3, array.size)
        array.removeAt(0)
        assertEquals(2, array.size)
    }

    @Test
    fun testDynamicArrayWithNullableType() {
        val array = DynamicArray<Int?>()
        array.add(null)
        array.add(1)
        array.add(null)
        assertEquals(3, array.size)
        assertNull(array[0])
        assertEquals(1, array[1])
        assertTrue(array.contains(null))
    }

    @Test
    fun testDynamicArrayManyElements() {
        val array = DynamicArray<Int>()
        for (i in 0 until 1000) {
            array.add(i)
        }
        assertEquals(1000, array.size)
        assertEquals(0, array[0])
        assertEquals(999, array[999])
    }
}
