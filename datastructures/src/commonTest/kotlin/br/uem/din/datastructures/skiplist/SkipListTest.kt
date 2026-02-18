package br.uem.din.datastructures.skiplist

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.*

class SkipListTest {

    @Test
    fun testInsertAndContains() {
        val skipList = SkipList<Int>()
        skipList.insert(10)
        skipList.insert(5)
        skipList.insert(20)

        assertTrue(skipList.contains(10))
        assertTrue(skipList.contains(5))
        assertTrue(skipList.contains(20))
        assertFalse(skipList.contains(15))
        assertEquals(3, skipList.size)
    }

    @Test
    fun testLargeInsert() {
        val skipList = SkipList<Int>()
        val n = 100
        for (i in 0 until n) {
            skipList.insert(i)
        }
        assertEquals(n, skipList.size)
        assertTrue(skipList.contains(50))
        assertFalse(skipList.contains(101))
    }

    @Test
    fun testDuplicateInsert() {
        val skipList = SkipList<Int>()
        skipList.insert(5)
        skipList.insert(5)
        assertEquals(1, skipList.size)
    }

    @Test
    fun testRemove() {
        val skipList = SkipList<Int>()
        skipList.insert(1)
        skipList.insert(2)
        skipList.insert(3)
        assertTrue(skipList.remove(2))
        assertFalse(skipList.contains(2))
        assertEquals(2, skipList.size)
    }

    @Test
    fun testRemoveNonExistent() {
        val skipList = SkipList<Int>()
        skipList.insert(1)
        assertFalse(skipList.remove(99))
        assertEquals(1, skipList.size)
    }

    @Test
    fun testRemoveFromEmpty() {
        val skipList = SkipList<Int>()
        assertFalse(skipList.remove(1))
    }

    @Test
    fun testIsEmpty() {
        val skipList = SkipList<Int>()
        assertTrue(skipList.isEmpty())
        skipList.insert(1)
        assertFalse(skipList.isEmpty())
    }

    @Test
    fun testClear() {
        val skipList = SkipList<Int>()
        skipList.insert(1)
        skipList.insert(2)
        skipList.insert(3)
        skipList.clear()
        assertTrue(skipList.isEmpty())
        assertEquals(0, skipList.size)
        assertFalse(skipList.contains(1))
    }

    @Test
    fun testIterator() {
        val skipList = SkipList<Int>()
        skipList.insert(30)
        skipList.insert(10)
        skipList.insert(20)
        val collected = mutableListOf<Int>()
        for (v in skipList) collected.add(v)
        assertEquals(listOf(10, 20, 30), collected)
    }

    @Test
    fun testToList() {
        val skipList = SkipList<Int>()
        skipList.insert(3)
        skipList.insert(1)
        skipList.insert(2)
        assertEquals(listOf(1, 2, 3), skipList.toList())
    }

    @Test
    fun testToString() {
        val skipList = SkipList<Int>()
        assertEquals("[]", skipList.toString())
        skipList.insert(1)
        skipList.insert(2)
        assertEquals("[1, 2]", skipList.toString())
    }

    @Test
    fun testStringType() {
        val skipList = SkipList<String>()
        skipList.insert("banana")
        skipList.insert("apple")
        skipList.insert("cherry")
        assertTrue(skipList.contains("apple"))
        assertEquals(listOf("apple", "banana", "cherry"), skipList.toList())
    }

    @Test
    fun testContainsOnEmpty() {
        val skipList = SkipList<Int>()
        assertFalse(skipList.contains(1))
    }

    @Test
    fun testMutableSkipListInterface() {
        val sl: MutableSkipList<Int> = SkipList()
        sl.insert(30)
        sl.insert(10)
        sl.insert(20)
        assertEquals(3, sl.size)
        assertTrue(sl.contains(10))
        assertFalse(sl.isEmpty())
        assertEquals(listOf(10, 20, 30), sl.toList())
        assertTrue(sl.remove(10))
        assertFalse(sl.contains(10))
        sl.clear()
        assertTrue(sl.isEmpty())
    }

    @Test
    fun testReadOnlySkipListView() {
        val sl: MutableSkipList<Int> = SkipList()
        sl.insert(5)
        sl.insert(3)
        sl.insert(7)
        val readOnly: ImmutableSkipList<Int> = sl
        assertEquals(3, readOnly.size)
        assertTrue(readOnly.contains(5))
        assertEquals(listOf(3, 5, 7), readOnly.toList())
    }

    @Test
    fun testAsReadOnlyView() {
        val sl = SkipList<Int>()
        sl.insert(10); sl.insert(5); sl.insert(20)
        val readOnly: ImmutableSkipList<Int> = sl.asReadOnly()
        assertEquals(3, readOnly.size)
        assertTrue(readOnly.contains(10))
        assertFalse(readOnly.contains(99))
        assertEquals(listOf(5, 10, 20), readOnly.toList())

        sl.insert(15)
        assertTrue(readOnly.contains(15))
        assertEquals(4, readOnly.size)
    }

    @Test
    fun testLargeScaleStress1000() {
        val sl = SkipList<Int>()
        val n = 1000
        val shuffled = (0 until n).toMutableList().also { it.shuffle(Random(8001)) }
        shuffled.forEach { sl.insert(it) }
        assertEquals(n, sl.size)
        for (i in 0 until n) {
            assertTrue(sl.contains(i), "Missing element $i")
        }
        assertEquals((0 until n).toList(), sl.toList())
    }

    @Test
    fun testRandomizedInsertRemoveWithSeed() {
        val random = Random(8002)
        val sl = SkipList<Int>()
        val model = mutableListOf<Int>()

        repeat(1_000) { step ->
            when (random.nextInt(100)) {
                in 0..59 -> {
                    val v = random.nextInt(-500, 501)
                    sl.insert(v)
                    if (v !in model) {
                        model.add(v)
                        model.sort()
                    }
                }
                in 60..84 -> {
                    val v = random.nextInt(-500, 501)
                    val modelRemoved = model.remove(v)
                    val slRemoved = sl.remove(v)
                    assertEquals(modelRemoved, slRemoved, "remove mismatch at step=$step v=$v")
                }
                else -> {
                    val v = random.nextInt(-500, 501)
                    val modelContains = model.contains(v)
                    val slContains = sl.contains(v)
                    assertEquals(modelContains, slContains, "contains mismatch at step=$step v=$v")
                }
            }
            assertEquals(model.size, sl.size, "size mismatch at step=$step")
        }

        assertEquals(model, sl.toList())
    }

    @Test
    fun testNegativeValues() {
        val sl = SkipList<Int>()
        sl.insert(-100); sl.insert(-1); sl.insert(0); sl.insert(50)
        assertEquals(listOf(-100, -1, 0, 50), sl.toList())
        assertTrue(sl.contains(-100))
        assertTrue(sl.remove(-1))
        assertEquals(listOf(-100, 0, 50), sl.toList())
    }

    @Test
    fun testSingleElement() {
        val sl = SkipList<Int>()
        sl.insert(42)
        assertEquals(1, sl.size)
        assertTrue(sl.contains(42))
        assertEquals(listOf(42), sl.toList())
        assertTrue(sl.remove(42))
        assertTrue(sl.isEmpty())
    }

    @Test
    fun testInsertAfterClear() {
        val sl = SkipList<Int>()
        for (i in 1..50) sl.insert(i)
        sl.clear()
        assertTrue(sl.isEmpty())
        sl.insert(99)
        assertEquals(1, sl.size)
        assertTrue(sl.contains(99))
        assertEquals(listOf(99), sl.toList())
    }

    @Test
    fun testIteratorOnEmpty() {
        val sl = SkipList<Int>()
        assertFalse(sl.iterator().hasNext())
        assertEquals(emptyList(), sl.toList())
    }

    @Test
    fun testDoubleType() {
        val sl = SkipList<Double>()
        sl.insert(3.14); sl.insert(1.0); sl.insert(2.71)
        assertEquals(listOf(1.0, 2.71, 3.14), sl.toList())
    }
}
